package com.fullvicie.actions.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.daos.sql.UserSqlDao;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.User;

public class Signup implements IAction{

	public static final String PARAM_SIGNUP_ACTION = "PARAM_SIGNUP_ACTION";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
		UserSqlDao dao = new UserSqlDao();
		User user = new User(request);
		
		ErrorType errorType = dao.create(user);
		if(errorType != ErrorType.NO_ERROR) {		
			return "/mod/error.jsp?ERROR_TYPE=" + errorType;
		}
		else {
			user = dao.read(user.getUsername(), SearchBy.USERNAME);
			request.getSession().setAttribute(User.ATR_USER_LOGGED, user);
			return "/index.jsp";
		}
	}
}
