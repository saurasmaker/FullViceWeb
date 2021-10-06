package com.fullvicie.actions.user;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.mysql.MySQLGamerProfileDAO;
import com.fullvicie.daos.mysql.MySQLTeamInvitationDAO;
import com.fullvicie.daos.mysql.MySQLTeamDAO;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.GamerProfile;
import com.fullvicie.pojos.Team;
import com.fullvicie.pojos.TeamInvitation;

public class InvitePlayerToTeam implements IAction{

	public static final String PARAM_INVITE_PLAYER_TO_TEAM_ACTION = "PARAM_INVITE_PLAYER_TO_TEAM_ACTION";
	
	
	/*
	 * Singleton
	 */
	private static InvitePlayerToTeam instance;
	private InvitePlayerToTeam() {}
	public static InvitePlayerToTeam getInstance() {	
		if(instance == null)
			instance = new InvitePlayerToTeam();	
		return instance;
	}
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		ErrorType et = ErrorType.NO_ERROR;
		
		TeamInvitation teamInvitation = new TeamInvitation(request);
		if(teamInvitation.getReceiverUserId() < 1) 
			return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.USER_DOES_NOT_EXIST_ERROR;
		
		Team t = null;
		try {
			t = MySQLTeamDAO.getInstance().read(String.valueOf(teamInvitation.getTeamId()), SearchBy.ID);
		} catch (SQLException e) {
			e.printStackTrace();
			return ActionsController.ERROR_PAGE + ErrorType.READ_TEAM_ERROR;
		}
		
		
		for(int gpId: t.getGamerProfiles()) {
			GamerProfile gp = null;
			try {
				gp = MySQLGamerProfileDAO.getInstance().read(String.valueOf(gpId), SearchBy.ID);
			} catch (SQLException e) {
				e.printStackTrace();
				return ActionsController.ERROR_PAGE + ErrorType.READ_GAMER_PROFILE_ERROR;
			}
			if(gp!=null) if(teamInvitation.getReceiverUserId()==gp.getUserId())
				return ActionsController.ERROR_PAGE + ErrorType.USER_IS_ALREADY_A_TEAM_MEMBER_ERROR;
		}
		
		
		ArrayList<TeamInvitation> tisUser = null;
		try {
			tisUser = MySQLTeamInvitationDAO.getInstance().listBy(String.valueOf(teamInvitation.getReceiverUserId()), SearchBy.RECEIVER_USER_ID);
		} catch (SQLException e) {
			e.printStackTrace();
			return ActionsController.ERROR_PAGE + ErrorType.LIST_TEAM_INVITATIONS_ERROR;
		}
		
		
		for(TeamInvitation ti: tisUser)
			if(ti.getTeamId() == teamInvitation.getTeamId())
				return ActionsController.ERROR_PAGE + ErrorType.USER_IS_ALREADY_INVITED_TO_THIS_TEAM_ERROR;
		
		et = MySQLTeamInvitationDAO.getInstance().create(teamInvitation);
		
		if(et == ErrorType.NO_ERROR)
			return request.getHeader("referer");
		
		return ActionsController.ERROR_PAGE + ErrorType.CREATE_TEAM_INVITATION_ERROR;
	}

}
