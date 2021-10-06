package com.fullvicie.actions.crud;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.User;

public class Read implements IAction{
	public static final String PARAM_READ_ACTION = "PARAM_READ_ACTION";

	
	/*
	 * Singleton
	 */
	private static Read instance;
	private Read() {}
	public static Read getInstance() {	
		if(instance == null)
			instance = new Read();	
		return instance;
	}
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		if(!u.isAdmin() && !u.isModerator())
			return ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
		
		return null;
	}
}
