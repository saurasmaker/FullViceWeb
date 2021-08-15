package com.fullvicie.actions.user;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.daos.sql.UserSqlDao;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.User;


public class Logout implements IAction{

	public static final String PARAM_LOGOUT_ACTION = "PARAM_LOGOUT_ACTION";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		if(u==null)
			return request.getContextPath()+"/mod/error.jsp?ERROR_TYPE=" + ErrorType.ACCESS_DENIED_ERROR;
		
		new UserSqlDao().updateLastLogoutDatetime(String.valueOf(u.getId()), SearchBy.ID, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()));
		request.getSession().removeAttribute(User.ATR_USER_LOGGED_OBJ);
		
		return request.getContextPath()+"/index.jsp";
	}
	
}
