package com.fullvicie.actions.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.sql.TeamSqlDao;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.Team;

public class ChangeTeamLogo implements IAction{
	
	public static final String PARAM_CHANGE_TEAM_LOGO_ACTION = "PARAM_CHANGE_TEAM_LOGO_ACTION";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String url = request.getHeader("referer");
		
		try {
			Team t = (Team) request.getSession().getAttribute(Team.ATTR_TEAM_OBJ);
			
			if(t!=null) {
				TeamSqlDao tsd = new TeamSqlDao();
				tsd.updatePicture(String.valueOf(t.getId()), SearchBy.ID, request.getPart(Team.PARAM_TEAM_LOGO).getInputStream());
			}
			
			return url;
		}
		catch(Exception e) {
			
		}
		
		return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.USER_DOES_NOT_EXIST_ERROR;
	}
}
