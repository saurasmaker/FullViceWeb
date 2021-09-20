package com.fullvicie.actions.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.mysql.MySQLTeamDAO;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.GamerProfile;
import com.fullvicie.pojos.Team;
import com.fullvicie.pojos.User;

public class KickPlayerFromTeam implements IAction{

	public static final String PARAM_KICK_PLAYER_FROM_TEAM_ACTION="PARAM_KICK_PLAYER_FROM_TEAM_ACTION";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		if(u==null)
			return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
		
		Team team = new MySQLTeamDAO().read(request.getParameter(Team.PARAM_TEAM_ID), SearchBy.ID);
		int playerId = Integer.valueOf(request.getParameter(GamerProfile.PARAM_GAMER_PROFILE_ID));
		
		int[] playersId = team.getGamerProfiles();
		for(int i = 0; i < playersId.length; ++i)
			if(playerId==playersId[i]) {
				playersId[i] = -1;
				team.setGamerProfiles(playersId);
				ErrorType et = new MySQLTeamDAO().update(String.valueOf(team.getId()), SearchBy.ID, team);
				if(et==ErrorType.NO_ERROR)
					return request.getHeader("referer");
				else
					return request.getContextPath() + ActionsController.ERROR_PAGE + et;
			}
				
		
		return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.PLAYER_IS_NOT_TEAM_MEMBER_ERROR;
	}

}
