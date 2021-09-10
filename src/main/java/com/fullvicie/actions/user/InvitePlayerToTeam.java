package com.fullvicie.actions.user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.sql.GamerProfileSqlDao;
import com.fullvicie.daos.sql.TeamInvitationSqlDao;
import com.fullvicie.daos.sql.TeamSqlDao;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.GamerProfile;
import com.fullvicie.pojos.Team;
import com.fullvicie.pojos.TeamInvitation;

public class InvitePlayerToTeam implements IAction{

	public static final String PARAM_INVITE_PLAYER_TO_TEAM_ACTION = "PARAM_INVITE_PLAYER_TO_TEAM_ACTION";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String url = request.getHeader("referer");
		
		try {			
			TeamInvitation teamInvitation = new TeamInvitation(request);
			if(teamInvitation.getReceiverUserId() < 1) 
				return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.USER_DOES_NOT_EXIST_ERROR;
			
			Team t = new TeamSqlDao().read(String.valueOf(teamInvitation.getTeamId()), SearchBy.ID);
			for(int gpId: t.getGamerProfiles()) {
				GamerProfile gp = new GamerProfileSqlDao().read(String.valueOf(gpId), SearchBy.ID);
				if(gp!=null) if(teamInvitation.getReceiverUserId()==gp.getUserId())
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.USER_IS_ALREADY_A_TEAM_MEMBER_ERROR;

			}
			ArrayList<TeamInvitation> tisUser = new TeamInvitationSqlDao().listBy(SearchBy.RECEIVER_USER_ID, String.valueOf(teamInvitation.getReceiverUserId()));
			for(TeamInvitation ti: tisUser)
				if(ti.getTeamId() == teamInvitation.getTeamId())
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.USER_IS_ALREADY_INVITED_TO_THIS_TEAM_ERROR;
			
			new TeamInvitationSqlDao().create(teamInvitation);
			
			return url;
		}
		catch(Exception e) { }
		
		return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.CREATE_TEAM_INVITATION_ERROR;
	}

}
