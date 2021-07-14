package com.fullvicie.actions.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.User;


public class Logout implements IAction{

	public static final String PARAM_LOGOUT_ACTION = "PARAM_LOGOUT_ACTION";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getSession().removeAttribute(User.ATR_USER_LOGGED);
		
		return "/index.jsp";
	}
	
}
