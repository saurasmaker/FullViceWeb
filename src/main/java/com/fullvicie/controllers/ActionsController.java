package com.fullvicie.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.actions.crud.*;
// import com.fullvicie.actions.admin.*;
// import com.fullvicie.actions.moderator.*;
import com.fullvicie.actions.user.*;


/**
 * Servlet implementation class Controller
 */
@WebServlet({"/ActionsController","/actionscontroller", "/ACTIONSCONSTROLLER", "/ACTIONS_CONTROLLER"})
@MultipartConfig
public class ActionsController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public static final String PARAM_OBJECT_CLASS = "PARAM_OBJECT_CLASS", PARAM_SELECT_ACTION = "PARAM_SELECT_ACTION",
			INDEX_PAGE = "/index.jsp", ERROR_PAGE = "/pages/error.jsp?ERROR_TYPE=";

	private String url;

    public ActionsController() {
        super();
    }

 
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		//response.sendRedirect(request.getContextPath() + url);
		if(url.contains("http"))
			url = url.substring(url.indexOf(request.getContextPath())).substring(request.getContextPath().length()) ;
		
		request.getRequestDispatcher(url).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String selectedAction = request.getParameter(PARAM_SELECT_ACTION);

		switch(selectedAction) {
		
		case Logout.PARAM_LOGOUT_ACTION:
			url = Logout.getInstance().execute(request, response);
			break;
			
		case Login.PARAM_LOGIN_ACTION:
			url = Login.getInstance().execute(request, response);
			break;
			
		case Signup.PARAM_SIGNUP_ACTION:
			url = Signup.getInstance().execute(request, response);
			break;

		case ChangeUserPicture.PARAM_CHANGE_USER_PICTURE_ACTION:
			url = ChangeUserPicture.getInstance().execute(request, response);
			break;
			
		case ChangeTeamLogo.PARAM_CHANGE_TEAM_LOGO_ACTION:
			url = ChangeTeamLogo.getInstance().execute(request, response);
			break;
		
		case InvitePlayerToTeam.PARAM_INVITE_PLAYER_TO_TEAM_ACTION:
			url = InvitePlayerToTeam.getInstance().execute(request, response);
			break;
			
		case AcceptTeamInvitation.PARAM_ACCEPT_TEAM_INVITATION_ACTION:
			url = AcceptTeamInvitation.getInstance().execute(request, response);
			break;
			
		case RejectTeamInvitation.PARAM_REJECT_TEAM_INVITATION_ACTION:
			url = RejectTeamInvitation.getInstance().execute(request, response);
			break;
			
		case LeaveTeam.PARAM_LEAVE_TEAM_ACTION:
			url = LeaveTeam.getInstance().execute(request, response);
			break;	
		
		case KickPlayerFromTeam.PARAM_KICK_PLAYER_FROM_TEAM_ACTION:
			url = KickPlayerFromTeam.getInstance().execute(request, response);
			break;
			
		case ChangeTeamOwner.PARAM_CHANGE_TEAM_OWNER_ACTION:
			url = ChangeTeamOwner.getInstance().execute(request, response);
			break;
			
		case Create.PARAM_CREATE_ACTION:
			url = Create.getInstance().execute(request, response);
			break;
				
		case Read.PARAM_READ_ACTION:
			url = Read.getInstance().execute(request, response);
			break;
			
		case Update.PARAM_UPDATE_ACTION:
			url = Update.getInstance().execute(request, response);
			break;
			
		case Delete.PARAM_DELETE_ACTION:
			url = Delete.getInstance().execute(request, response);
			break;
				
		}
		
		doGet(request, response);
	}

}
