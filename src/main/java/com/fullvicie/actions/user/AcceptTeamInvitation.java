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
import com.fullvicie.daos.sql.UserSqlDao;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.Team;
import com.fullvicie.pojos.TeamInvitation;
import com.fullvicie.pojos.User;
import com.fullvicie.pojos.GamerProfile;

public class AcceptTeamInvitation implements IAction{

	public static final String PARAM_ACCEPT_TEAM_INVITATION_ACTION = "PARAM_ACCEPT_TEAM_INVITATION_ACTION";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		if(u==null)
			return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
		
		/*
		 * Initialization necessary variables
		 */
		TeamInvitation teamInvitation = new TeamInvitationSqlDao().read(request.getParameter(TeamInvitation.PARAM_TEAM_INVITATION_ID), SearchBy.ID);
		Team team = new TeamSqlDao().read(String.valueOf(teamInvitation.getTeamId()), SearchBy.ID);
		User receiver = new UserSqlDao().read(String.valueOf(teamInvitation.getReceiverUserId()), SearchBy.ID);
		ArrayList<GamerProfile> userGamerProfiles = new GamerProfileSqlDao().listBy(SearchBy.USER_ID,String.valueOf(receiver.getId()));	
		GamerProfile gamerProfile = null;
		for(GamerProfile gp: userGamerProfiles)
			if(gp.getVideoGameId()==team.getVideoGameId()) 
				gamerProfile = gp;
		 
		
		if(team.getNumberOfPlayers() >= team.getGamerProfiles().length)
			return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.TEAM_PLACES_ARE_FULL_ERROR;
		
		if(gamerProfile!=null) {
			int[] playersProfilesId = team.getGamerProfiles();
			for(int i = 0; i < playersProfilesId.length; ++i) {
				if(playersProfilesId[i] <= 0) {
					playersProfilesId[i] = gamerProfile.getId();
					team.setGamerProfiles(playersProfilesId);
					ErrorType et = new TeamInvitationSqlDao().delete(String.valueOf(teamInvitation.getId()), SearchBy.ID);
					if(et != ErrorType.NO_ERROR) 
						return request.getContextPath() + ActionsController.ERROR_PAGE + et;
					
					et = new TeamSqlDao().update(String.valueOf(team.getId()), SearchBy.ID, team);
					if(et != ErrorType.NO_ERROR) 
						return request.getContextPath() + ActionsController.ERROR_PAGE + et;
					
					return request.getContextPath() + "/pages/team.jsp?PARAM_TEAM_ID=" + team.getId();

				}
			}
		}
		
		return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.MUST_HAVE_GAMER_PROFILE_TO_ACCEPT_INVITATION_ERROR;
	}

}
