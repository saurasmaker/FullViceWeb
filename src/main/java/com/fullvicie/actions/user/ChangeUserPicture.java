package com.fullvicie.actions.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.sql.UserSqlDao;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.User;

public class ChangeUserPicture implements IAction{

	public static final String PARAM_CHANGE_USER_PICTURE_ACTION = "PARAM_CHANGE_USER_PICTURE_ACTION";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String url = request.getHeader("referer");
		
		try {
			User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
			
			if(u!=null) {
				UserSqlDao usd = new UserSqlDao();
				usd.updatePicture(String.valueOf(u.getId()), SearchBy.ID, request.getPart(User.PART_USER_PICTURE).getInputStream());
			}
			
			return url;
		}
		catch(Exception e) {
			
		}
		
		return ActionsController.ERROR_PAGE + ErrorType.USER_DOES_NOT_EXIST_ERROR;
	}

}
