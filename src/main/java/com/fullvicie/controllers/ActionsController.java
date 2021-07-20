package com.fullvicie.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.actions.admin.Create;
import com.fullvicie.actions.admin.Delete;
import com.fullvicie.actions.admin.Read;
import com.fullvicie.actions.admin.Update;
import com.fullvicie.actions.user.Login;
import com.fullvicie.actions.user.Logout;
import com.fullvicie.actions.user.Signup;
import com.fullvicie.pojos.User;


/**
 * Servlet implementation class Controller
 */
@WebServlet({"/Controller","/controller", "/CONTROLLER"})
@MultipartConfig
public class ActionsController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public static final String PARAM_OBJECT_CLASS = "PARAM_OBJECT_CLASS", PARAM_SELECT_ACTION = "PARAM_SELECT_ACTION";

	private String url;


    public ActionsController() {
        super();
    }

 
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		response.sendRedirect(url);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		url = request.getContextPath();
		User user = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		String selectedAction = request.getParameter(PARAM_SELECT_ACTION);
						
		/*
		 * For no logged Users
		 */
		switch(selectedAction) {
		
		case Logout.PARAM_LOGOUT_ACTION:
			url += (new Logout()).execute(request, response);
			break;
			
		case Login.PARAM_LOGIN_ACTION:
			url += (new Login()).execute(request, response);
			break;
			
		case Signup.PARAM_SIGNUP_ACTION:
			url += (new Signup()).execute(request, response);
			break;
		}
		
		
		/*
		 * For logged Users
		 */
		if(user!=null) {
			switch(selectedAction) {
			
			}
			
			
			/*
			 * For admin Users
			 */
			if(user.isAdmin())
				switch(selectedAction) {
					
				case Create.PARAM_CREATE_ACTION:
					url += (new Create()).execute(request, response);
					break;
						
				case Read.PARAM_READ_ACTION:
					url += (new Read()).execute(request, response);
					break;
					
				case Update.PARAM_UPDATE_ACTION:
					url += (new Update()).execute(request, response);
					break;
					
				case Delete.PARAM_DELETE_ACTION:
					url += (new Delete()).execute(request, response);
					break;
						
				}
		
		}
		
		doGet(request, response);
	}

}
