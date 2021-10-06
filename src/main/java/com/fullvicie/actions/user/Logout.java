package com.fullvicie.actions.user;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.mysql.MySQLUserDAO;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.User;


public class Logout implements IAction{

	public static final String PARAM_LOGOUT_ACTION = "PARAM_LOGOUT_ACTION";

	
	/*
	 * Singleton
	 */
	private static Logout instance;
	private Logout() {}
	public static Logout getInstance() {	
		if(instance == null)
			instance = new Logout();	
		return instance;
	}
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		if(u==null)
			return ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
		
		u.setLastLogoutDate(Date.valueOf(LocalDate.now()));
		u.setLastLogoutTime(Time.valueOf(LocalTime.now()));
		MySQLUserDAO.getInstance().update(String.valueOf(u.getId()), SearchBy.ID, u);
		request.getSession().removeAttribute(User.ATR_USER_LOGGED_OBJ);
		
		return ActionsController.INDEX_PAGE;
	}
	
}
