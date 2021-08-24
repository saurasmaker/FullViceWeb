package com.fullvicie.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.actions.user.*;
import com.fullvicie.actions.moderator.*;
import com.fullvicie.actions.admin.*;


/**
 * Servlet implementation class Controller
 */
@WebServlet({"/ActionsController","/actionscontroller", "/ACTIONSCONSTROLLER", "/ACTIONS_CONTROLLER"})
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
		
		String selectedAction = request.getParameter(PARAM_SELECT_ACTION);

		switch(selectedAction) {
		
		case Logout.PARAM_LOGOUT_ACTION:
			url = (new Logout()).execute(request, response);
			break;
			
		case Login.PARAM_LOGIN_ACTION:
			url = (new Login()).execute(request, response);
			break;
			
		case Signup.PARAM_SIGNUP_ACTION:
			url = (new Signup()).execute(request, response);
			break;

		case ChangeUserPicture.PARAM_CHANGE_USER_PICTURE_ACTION:
			url = (new ChangeUserPicture()).execute(request, response);
			break;
			
		case Create.PARAM_CREATE_ACTION:
			url = (new Create()).execute(request, response);
			break;
				
		case Read.PARAM_READ_ACTION:
			url += (new Read()).execute(request, response);
			break;
			
		case Update.PARAM_UPDATE_ACTION:
			url = (new Update()).execute(request, response);
			break;
			
		case Delete.PARAM_DELETE_ACTION:
			url = (new Delete()).execute(request, response);
			break;
			
		case PseudoDelete.PARAM_PSEUDODELETE_ACTION:
			url += (new PseudoDelete()).execute(request, response);
			break;
				
		}
		
		doGet(request, response);
	}

}
