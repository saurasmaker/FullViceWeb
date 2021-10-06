package com.fullvicie.actions.user;

import java.sql.SQLException;

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
	
	
	/*
	 * Singleton
	 */
	private static RejectTeamInvitation instance;
	private RejectTeamInvitation() {}
	public static RejectTeamInvitation getInstance() {	
		if(instance == null)
			instance = new RejectTeamInvitation();	
		return instance;
	}
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		if(u==null)
			return ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
		
		String url = request.getHeader("referer");
		TeamInvitation teamInvitation = null;
		try {
			teamInvitation = MySQLTeamInvitationDAO.getInstance().read(request.getParameter(TeamInvitation.PARAM_TEAM_INVITATION_ID), SearchBy.ID);
		} catch (SQLException e) {
			e.printStackTrace();
			return ActionsController.ERROR_PAGE + ErrorType.READ_TEAM_INVITATION_ERROR;
		}
		
		if(MySQLTeamInvitationDAO.getInstance().delete(String.valueOf(teamInvitation.getId()), SearchBy.ID) == ErrorType.NO_ERROR) 
			return url;

		return ActionsController.ERROR_PAGE + ErrorType.DELETE_TEAM_INVITATION_ERROR;
	}

}
