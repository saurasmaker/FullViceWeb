package com.fullvicie.actions.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.mysql.MySQLTeamDAO;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.Team;

public class ChangeTeamLogo implements IAction{
	
	public static final String PARAM_CHANGE_TEAM_LOGO_ACTION = "PARAM_CHANGE_TEAM_LOGO_ACTION";
	
	/*
	 * Singleton
	 */
	private static ChangeTeamLogo instance;
	private ChangeTeamLogo() {}
	public static ChangeTeamLogo getInstance() {	
		if(instance == null)
			instance = new ChangeTeamLogo();	
		return instance;
	}
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		Team t = (Team) request.getSession().getAttribute(Team.ATTR_TEAM_OBJ);
		if(t!=null) {
			t.setBase64Logo(Base64.encodeBase64String(request.getPart(Team.PARAM_TEAM_LOGO).getInputStream().readAllBytes()));
			et = MySQLTeamDAO.getInstance().update(String.valueOf(t.getId()), SearchBy.ID, t);
			
			if(et == ErrorType.NO_ERROR)
				return request.getHeader("referer");
			
			return  ActionsController.ERROR_PAGE + et;
		}		
		
		return ActionsController.ERROR_PAGE + ErrorType.USER_DOES_NOT_EXIST_ERROR;
	}
}
