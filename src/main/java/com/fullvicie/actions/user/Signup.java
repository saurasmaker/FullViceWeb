package com.fullvicie.actions.user;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.mysql.MySQLProfileDAO;
import com.fullvicie.daos.mysql.MySQLUserDAO;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.PersonalInformation;
import com.fullvicie.pojos.User;

public class Signup implements IAction{

	public static final String PARAM_SIGNUP_ACTION = "PARAM_SIGNUP_ACTION";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
		MySQLUserDAO udao = new MySQLUserDAO();
		MySQLProfileDAO pdao = new MySQLProfileDAO(); 
		User user = new User(request);
		user.setSignUpDate(Date.valueOf(LocalDate.now()));
		user.setSignUpTime(Time.valueOf(LocalTime.now()));
		user.setLastLogoutDate(Date.valueOf(LocalDate.now()));
		user.setLastLogoutTime(Time.valueOf(LocalTime.now()));
		
		ErrorType errorType = udao.create(user);
		if(errorType != ErrorType.NO_ERROR) {		
			return request.getContextPath() + ActionsController.ERROR_PAGE + errorType;
		}
		else {
			user = udao.read(user.getUsername(), SearchBy.USERNAME);
			
			if(user!=null) {
				PersonalInformation profile = new PersonalInformation();
				profile.setUserId(user.getId());
				pdao.create(profile);
			}

			request.getSession().setAttribute(User.ATR_USER_LOGGED_OBJ, user);
			return request.getContextPath() + ActionsController.INDEX_PAGE;
		}
	}
}
