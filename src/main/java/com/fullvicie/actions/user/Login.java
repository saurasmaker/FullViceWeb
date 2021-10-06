package com.fullvicie.actions.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.mysql.MySQLUserDAO;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.User;
import com.fullvicie.tools.Encryptor;


public class Login implements IAction{
	
	public static final String PARAM_LOGIN_ACTION = "PARAM_LOGIN_ACTION";

	private static String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";

	
	/*
	 * Singleton
	 */
	private static Login instance;
	private Login() {}
	public static Login getInstance() {	
		if(instance == null)
			instance = new Login();	
		return instance;
	}
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
				
		User userToCheck = new User();
		userToCheck.setUsername(request.getParameter(User.PARAM_USER_USERNAME));
		userToCheck.setPassword(request.getParameter(User.PARAM_USER_PASSWORD));
		
		User userFinded = null; 		
		Pattern pattern = Pattern.compile(emailPattern);
				
		if (userToCheck.getUsername() != null) {
			
			// Encrypt password
			userToCheck.setPassword(Encryptor.encrypt(userToCheck.getPassword()));
			
			// Logic to login 
		    Matcher matcher = pattern.matcher(userToCheck.getUsername());
		    try {
			    if (matcher.matches())			
					userFinded = MySQLUserDAO.getInstance().read(userToCheck.getUsername(), SearchBy.EMAIL);
				else
			    	userFinded = MySQLUserDAO.getInstance().read(userToCheck.getUsername(), SearchBy.USERNAME);
		    }catch(Exception e) {
		    	e.printStackTrace();
		    	return ActionsController.ERROR_PAGE + ErrorType.READ_USER_ERROR;
		    }
		}
		else {
			return ActionsController.ERROR_PAGE + ErrorType.LOGIN_ERROR;
		}
				
		if(userFinded != null) {
			if(userToCheck.getPassword().equals(userFinded.getPassword())) {
				request.getSession().setAttribute(User.ATR_USER_LOGGED_OBJ, userFinded);
				return request.getHeader("referer");
			}
			else
				return ActionsController.ERROR_PAGE + ErrorType.PASSWORDS_DOES_NOT_MATCHES_ERROR;
		}
		else {
			return ActionsController.ERROR_PAGE + ErrorType.USER_DOES_NOT_EXIST_ERROR;
		}
		
	}
	
	
}
