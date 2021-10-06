package com.fullvicie.actions.user;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.mysql.MySQLPersonalInformationDAO;
import com.fullvicie.daos.mysql.MySQLUserDAO;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.PersonalInformation;
import com.fullvicie.pojos.User;

public class Signup implements IAction{

	public static final String PARAM_SIGNUP_ACTION = "PARAM_SIGNUP_ACTION";

	
	/*
	 * Singleton
	 */
	private static Signup instance;
	private Signup() {}
	public static Signup getInstance() {	
		if(instance == null)
			instance = new Signup();	
		return instance;
	}
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		User user = new User(request);
		user.setSignUpDate(Date.valueOf(LocalDate.now()));
		user.setSignUpTime(Time.valueOf(LocalTime.now()));
		user.setLastLogoutDate(Date.valueOf(LocalDate.now()));
		user.setLastLogoutTime(Time.valueOf(LocalTime.now()));
		
		ErrorType et = MySQLUserDAO.getInstance().create(user);
		if(et != ErrorType.NO_ERROR) {		
			return ActionsController.ERROR_PAGE + et;
		}
		else {
			try {
				user = MySQLUserDAO.getInstance().read(user.getUsername(), SearchBy.USERNAME);
			} catch (SQLException e) {
				e.printStackTrace();
				return ActionsController.ERROR_PAGE + ErrorType.READ_USER_ERROR;
			}
			
			if(user!=null) {
				PersonalInformation profile = new PersonalInformation();
				profile.setUserId(user.getId());
				MySQLPersonalInformationDAO.getInstance().create(profile);
			}

			request.getSession().setAttribute(User.ATR_USER_LOGGED_OBJ, user);
			return ActionsController.INDEX_PAGE;
		}
	}
}
