package com.fullvicie.actions.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.mysql.MySQLUserDAO;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.User;

public class ChangeUserPicture implements IAction{

	public static final String PARAM_CHANGE_USER_PICTURE_ACTION = "PARAM_CHANGE_USER_PICTURE_ACTION";
	
	
	/*
	 * Singleton
	 */
	private static ChangeUserPicture instance;
	private ChangeUserPicture() {}
	public static ChangeUserPicture getInstance() {	
		if(instance == null)
			instance = new ChangeUserPicture();	
		return instance;
	}
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ErrorType et = ErrorType.NO_ERROR; 

		User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		
		if(u!=null) {
			u.setBase64Picture(Base64.encodeBase64String(request.getPart(User.PART_USER_PICTURE).getInputStream().readAllBytes()));
			et = MySQLUserDAO.getInstance().update(String.valueOf(u.getId()), SearchBy.ID, u);
			if(et == ErrorType.NO_ERROR)
				return request.getHeader("referer");
			
			return  ActionsController.ERROR_PAGE + et;
		}
		
		return ActionsController.ERROR_PAGE + ErrorType.USER_DOES_NOT_EXIST_ERROR;
	}

}
