package com.fullvicie.actions.user;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.sql.UserSqlDao;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.User;
import com.fullvicie.tools.Encryptor;


public class Login implements IAction{
	
	public static final String PARAM_LOGIN_ACTION = "PARAM_LOGIN_ACTION";

	private static String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";

	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		User userToCheck = new User();
		userToCheck.setUsername(request.getParameter(User.PARAM_USER_USERNAME));
		userToCheck.setPassword(request.getParameter(User.PARAM_USER_PASSWORD));
		
		User userFinded = null; 		
		Pattern pattern = Pattern.compile(emailPattern);
				
		if (userToCheck.getUsername() != null) {
			
			// Encrypt password
			userToCheck.setPassword(Encryptor.encrypt(userToCheck.getPassword()));
			
			// Logic to login 
			UserSqlDao dao = new UserSqlDao();
		    Matcher matcher = pattern.matcher(userToCheck.getUsername());
		    if (matcher.matches()) 
		    	userFinded = dao.read(userToCheck.getUsername(), SearchBy.EMAIL);
		    else
		    	userFinded = dao.read(userToCheck.getUsername(), SearchBy.USERNAME);
		}
		else {
			return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.LOGIN_ERROR;
		}
				
		if(userFinded != null && !userFinded.getDeleted()) {
			if(userToCheck.getPassword().equals(userFinded.getPassword())) {
				request.getSession().setAttribute(User.ATR_USER_LOGGED_OBJ, userFinded);
				return request.getContextPath() + ActionsController.INDEX_PAGE;
			}
			else
				return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.PASSWORDS_DOES_NOT_MATCHES_ERROR;
		}
		else {
			return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.USER_DOES_NOT_EXIST_ERROR;
		}
		
	}
	
	
}
