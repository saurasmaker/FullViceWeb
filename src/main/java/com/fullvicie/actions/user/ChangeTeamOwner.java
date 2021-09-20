package com.fullvicie.actions.user;

import java.io.IOException;

import javax.servlet.ServletException;
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
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
			if(u==null)
				return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			GamerProfile gamerProfile = new MySQLGamerProfileDAO().read(request.getParameter(GamerProfile.PARAM_GAMER_PROFILE_ID), SearchBy.ID);
			Team team = new MySQLTeamDAO().read(request.getParameter(Team.PARAM_TEAM_ID), SearchBy.ID);
			
			team.setUserOwnerId(gamerProfile.getUserId());
			
			ErrorType et = new MySQLTeamDAO().update(String.valueOf(team.getId()), SearchBy.ID, team);
			
			if(et == ErrorType.NO_ERROR)
				return request.getHeader("referer");
			
			return request.getContextPath() + ActionsController.ERROR_PAGE + et;
			
		}catch(Exception t) { }
		
		return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.CHANGE_TEAM_OWNER_ERROR;
	}

}
