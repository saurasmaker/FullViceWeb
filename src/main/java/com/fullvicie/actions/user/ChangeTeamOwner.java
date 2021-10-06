package com.fullvicie.actions.user;

import java.sql.SQLException;

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

public class ChangeTeamOwner implements IAction{

	public static final String PARAM_CHANGE_TEAM_OWNER_ACTION = "PARAM_CHANGE_TEAM_OWNER_ACTION";
	
	
	/*
	 * Singleton
	 */
	private static ChangeTeamOwner instance;
	private ChangeTeamOwner() {}
	public static ChangeTeamOwner getInstance() {	
		if(instance == null)
			instance = new ChangeTeamOwner();	
		return instance;
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		if(u==null)
			return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
		
		GamerProfile gamerProfile = null;
		try {
			gamerProfile = MySQLGamerProfileDAO.getInstance().read(request.getParameter(GamerProfile.PARAM_GAMER_PROFILE_ID), SearchBy.ID);
		} catch (SQLException e) {
			e.printStackTrace();
			return ActionsController.ERROR_PAGE + ErrorType.READ_GAMER_PROFILE_ERROR;
		}
		Team team = null;
		try {
			team = MySQLTeamDAO.getInstance().read(request.getParameter(Team.PARAM_TEAM_ID), SearchBy.ID);
		} catch (SQLException e) {
			e.printStackTrace();
			return ActionsController.ERROR_PAGE + ErrorType.READ_TEAM_ERROR;
		}
		
		team.setUserOwnerId(gamerProfile.getUserId());
		
		ErrorType et = MySQLTeamDAO.getInstance().update(String.valueOf(team.getId()), SearchBy.ID, team);
		
		if(et == ErrorType.NO_ERROR)
			return request.getHeader("referer");
		
		return ActionsController.ERROR_PAGE + et;
	}

}
