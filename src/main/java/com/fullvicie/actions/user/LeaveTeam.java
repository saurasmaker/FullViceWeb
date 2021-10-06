package com.fullvicie.actions.user;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.mysql.MySQLGamerProfileDAO;
import com.fullvicie.daos.mysql.MySQLTeamDAO;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.GamerProfile;
import com.fullvicie.pojos.Team;
import com.fullvicie.pojos.User;

public class LeaveTeam implements IAction{

	public static final String PARAM_LEAVE_TEAM_ACTION = "PARAM_LEAVE_TEAM_ACTION";
	
	
	/*
	 * Singleton
	 */
	private static LeaveTeam instance;
	private LeaveTeam() {}
	public static LeaveTeam getInstance() {	
		if(instance == null)
			instance = new LeaveTeam();	
		return instance;
	}
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		if(u==null)
			return ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
		
		Team team = null;
		try {
			team = MySQLTeamDAO.getInstance().read(request.getParameter(Team.PARAM_TEAM_ID), SearchBy.ID);
		} catch (SQLException e) {
			e.printStackTrace();
			return ActionsController.ERROR_PAGE + ErrorType.READ_TEAM_ERROR;
		}
		if(team.getUserOwnerId()==u.getId())
			return ActionsController.ERROR_PAGE + ErrorType.CAN_NOT_LEAVE_TEAM_IF_YOU_ARE_OWNER_ERROR;
		
		
		ArrayList<GamerProfile> gamerProfiles = null;
		try {
			gamerProfiles = MySQLGamerProfileDAO.getInstance().listBy(String.valueOf(u.getId()), SearchBy.USER_ID);
		} catch (SQLException e) {
			e.printStackTrace();
			return ActionsController.ERROR_PAGE + ErrorType.LIST_GAMER_PROFILES_ERROR;
		}
		
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
				
				ErrorType et = MySQLTeamDAO.getInstance().update(String.valueOf(team.getId()), SearchBy.ID, team);
				if(et == ErrorType.NO_ERROR)
					return request.getHeader("referer");
				
				return ActionsController.ERROR_PAGE + et;
			}	
		
		return ActionsController.ERROR_PAGE + ErrorType.PLAYER_IS_NOT_TEAM_MEMBER_ERROR;
	}
}
