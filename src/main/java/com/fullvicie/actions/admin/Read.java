package com.fullvicie.actions.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.enums.ErrorType;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.User;

public class Read implements IAction{
	public static final String PARAM_READ_ACTION = "PARAM_READ_ACTION";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		if(!u.isAdmin() && !u.isModerator())
			return "/mod/error.jsp?ERROR_TYPE=" + ErrorType.ACCESS_DENIED_ERROR;
		
		return null;
	}
}
