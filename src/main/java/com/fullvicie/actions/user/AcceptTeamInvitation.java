package com.fullvicie.actions.user;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.mysql.MySQLGamerProfileDAO;
import com.fullvicie.daos.mysql.MySQLTeamInvitationDAO;
import com.fullvicie.daos.mysql.MySQLTeamDAO;
import com.fullvicie.daos.mysql.MySQLUserDAO;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.Team;
import com.fullvicie.pojos.TeamInvitation;
import com.fullvicie.pojos.User;
import com.fullvicie.pojos.GamerProfile;

public class AcceptTeamInvitation implements IAction{

	public static final String PARAM_ACCEPT_TEAM_INVITATION_ACTION = "PARAM_ACCEPT_TEAM_INVITATION_ACTION";
	
	
	/*
	 * Singleton
	 */
	private static AcceptTeamInvitation instance;
	private AcceptTeamInvitation() {}
	public static AcceptTeamInvitation getInstance() {	
		if(instance == null)
			instance = new AcceptTeamInvitation();	
		return instance;
	}
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		if(u==null)
			return ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
		
		/*
		 * Initialization necessary variables
		 */
		TeamInvitation teamInvitation = null;
		Team team = null;
		User receiver = null;
		ArrayList<GamerProfile> userGamerProfiles = null;	
		
		try{
			teamInvitation = MySQLTeamInvitationDAO.getInstance().read(request.getParameter(TeamInvitation.PARAM_TEAM_INVITATION_ID), SearchBy.ID);
		}catch(Exception e) {
			e.printStackTrace();
			return ActionsController.ERROR_PAGE + ErrorType.READ_TEAM_INVITATION_ERROR;
		}
		
		try {
			team = MySQLTeamDAO.getInstance().read(String.valueOf(teamInvitation.getTeamId()), SearchBy.ID);
		}catch(Exception e) {
			e.printStackTrace();
			return ActionsController.ERROR_PAGE + ErrorType.READ_TEAM_ERROR;
		}
		
		try {
			receiver = MySQLUserDAO.getInstance().read(String.valueOf(teamInvitation.getReceiverUserId()), SearchBy.ID);
		}catch(Exception e) {
			e.printStackTrace();
			return ActionsController.ERROR_PAGE + ErrorType.READ_USER_ERROR;
		}
		
		try {
			userGamerProfiles = MySQLGamerProfileDAO.getInstance().listBy(String.valueOf(receiver.getId()), SearchBy.USER_ID);	
		}catch(Exception e) {
			e.printStackTrace();
			return ActionsController.ERROR_PAGE + ErrorType.LIST_GAMER_PROFILES_ERROR;
		}
		
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
					ErrorType et = MySQLTeamInvitationDAO.getInstance().delete(String.valueOf(teamInvitation.getId()), SearchBy.ID);
					if(et != ErrorType.NO_ERROR) 
						return ActionsController.ERROR_PAGE + et;
					
					et = MySQLTeamDAO.getInstance().update(String.valueOf(team.getId()), SearchBy.ID, team);
					if(et != ErrorType.NO_ERROR) 
						return ActionsController.ERROR_PAGE + et;
					
					return "/pages/team.jsp?PARAM_TEAM_ID=" + team.getId();

				}
			}
		}
		
		return ActionsController.ERROR_PAGE + ErrorType.MUST_HAVE_GAMER_PROFILE_TO_ACCEPT_INVITATION_ERROR;
	}

}
