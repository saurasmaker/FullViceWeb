package com.fullvicie.actions.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.mysql.MySQLTeamInvitationDAO;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.TeamInvitation;
import com.fullvicie.pojos.User;

public class RejectTeamInvitation implements IAction{

	public static final String PARAM_REJECT_TEAM_INVITATION_ACTION="PARAM_REJECT_TEAM_INVITATION_ACTION";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		if(u==null)
			return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
		
		String url = request.getHeader("referer");
		TeamInvitation teamInvitation = new MySQLTeamInvitationDAO().read(request.getParameter(TeamInvitation.PARAM_TEAM_INVITATION_ID), SearchBy.ID);
		
		if(new MySQLTeamInvitationDAO().delete(String.valueOf(teamInvitation.getId()), SearchBy.ID) == ErrorType.NO_ERROR) 
			return url;

		return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.DELETE_TEAM_INVITATION_ERROR;
	}

}
