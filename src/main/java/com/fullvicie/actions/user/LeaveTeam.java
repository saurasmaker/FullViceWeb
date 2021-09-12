package com.fullvicie.actions.user;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.sql.GamerProfileSqlDao;
import com.fullvicie.daos.sql.TeamSqlDao;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.GamerProfile;
import com.fullvicie.pojos.Team;
import com.fullvicie.pojos.User;

public class LeaveTeam implements IAction{

	public static final String PARAM_LEAVE_TEAM_ACTION = "PARAM_LEAVE_TEAM_ACTION";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		if(u==null)
			return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
		
		Team team = new TeamSqlDao().read(request.getParameter(Team.PARAM_TEAM_ID), SearchBy.ID);
		if(team.getUserOwnerId()==u.getId())
			return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.CAN_NOT_LEAVE_TEAM_IF_YOU_ARE_OWNER_ERROR;
		
		
		ArrayList<GamerProfile> gamerProfiles = new GamerProfileSqlDao().listBy(SearchBy.USER_ID, String.valueOf(u.getId()));
		GamerProfile userGamerProfile = null;
		for(GamerProfile gp: gamerProfiles)
			if(gp.getVideoGameId()==team.getVideoGameId()) {
				userGamerProfile = gp;
				break;
			}
		
		int[] playersId = team.getGamerProfiles();
		for(int i = 0; i < playersId.length; ++i)
			if(userGamerProfile.getId()==playersId[i]) {
				playersId[i] = -1;
				team.setGamerProfiles(playersId);
				
				ErrorType et = new TeamSqlDao().update(String.valueOf(team.getId()), SearchBy.ID, team);
				if(et == ErrorType.NO_ERROR)
					return request.getHeader("referer");
				
				return request.getContextPath() + ActionsController.ERROR_PAGE + et;
			}	
		
		return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.PLAYER_IS_NOT_TEAM_MEMBER_ERROR;
	}
}
