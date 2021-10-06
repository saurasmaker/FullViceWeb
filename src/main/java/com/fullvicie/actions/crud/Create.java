package com.fullvicie.actions.crud;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.mysql.*;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.PermissionType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.*;

public class Create implements IAction{
	
	public static final String PARAM_CREATE_ACTION = "PARAM_CREATE_ACTION";
	
	
	/*
	 * Singleton
	 */
	private static Create instance;
	private Create() {}
	public static Create getInstance() {	
		if(instance == null)
			instance = new Create();	
		return instance;
	}
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		User sessionUser = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String url = request.getHeader("referer");
		String objectClass = request.getParameter(ActionsController.PARAM_OBJECT_CLASS);		
					
		if(objectClass != null)
		switch(objectClass) {
		
		
		/*
		 * FORUM
		 */
		case "com.fullvicie.pojos.Forum":
			
			PermissionType ptForum = checkPermissions(sessionUser,
					PermissionType.USER_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptForum == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = MySQLForumDAO.getInstance().create(new Forum(request));
			url += "#forums-title";
			
			break;
			
			
		/*
		 * FORUM CATEGORY
		 */
		case "com.fullvicie.pojos.ForumCategory":
			
			PermissionType ptForumCategory = checkPermissions(sessionUser,
					PermissionType.USER_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptForumCategory == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = MySQLForumCategoryDAO.getInstance().create(new ForumCategory(request));
			url += "#forum-categories-title";
			
			break;
			
			
		/*
		 * FORUM MESSAGE
		 */
		case "com.fullvicie.pojos.ForumMessage":
			
			PermissionType ptForumMessage = checkPermissions(sessionUser,
					PermissionType.USER_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptForumMessage == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = MySQLForumMessageDAO.getInstance().create(new ForumMessage(request));
			url += "#forum-messages-title";
			
			break;
			
			
		/*
		 * FORUM MESSAGE LIKE
		 */
		case "com.fullvicie.pojos.ForumMessageLike":
			
			PermissionType ptForumMessageLike = checkPermissions(sessionUser,
					PermissionType.USER_PERMISSION);
			
			if(ptForumMessageLike == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = MySQLForumMessageLikeDAO.getInstance().create(new ForumMessageLike(request));
			url += "#forum-message-likes-title";
			
			break;
			
			
		/*
		 * FORUM CATEGORY
		 */
		case "com.fullvicie.pojos.GamerProfile":
			
			PermissionType ptGamerProfile = checkPermissions(sessionUser,
					PermissionType.USER_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptGamerProfile == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = MySQLGamerProfileDAO.getInstance().create(new GamerProfile(request));
			url += "#gamer-profile-title";
			
			break;	
			
		
		/*
		 * POST
		 */
		case "com.fullvicie.pojos.Post":
			
			PermissionType ptPost = checkPermissions(sessionUser,
					PermissionType.USER_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptPost == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = MySQLPostDAO.getInstance().create(new Post(request));
			url += "#posts-title";
			
			break;
			
			
		/*
		 * POST CATEGORY
		 */
		case "com.fullvicie.pojos.PostCategory":
			
			PermissionType ptPostCategory = checkPermissions(sessionUser,
					PermissionType.USER_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptPostCategory == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = MySQLPostCategoryDAO.getInstance().create(new PostCategory(request));
			url += "#post-categories-title";
			
			break;
			
			
		/*
		 * POST COMMENT
		 */
		case "com.fullvicie.pojos.PostComment":
			
			PermissionType ptPostComment = checkPermissions(sessionUser,
					PermissionType.USER_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptPostComment == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = MySQLPostCommentDAO.getInstance().create(new PostComment(request));
			url += "#post-comments-title";
			break;
			
			
		/*
		 * POST COMMENT LIKE
		 */
		case "com.fullvicie.pojos.PostCommentLike":
			
			PermissionType ptPostCommentLike = checkPermissions(sessionUser,
					PermissionType.USER_PERMISSION);
			
			if(ptPostCommentLike == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = MySQLPostCommentLikeDAO.getInstance().create(new PostCommentLike(request));
			url += "#post-comment-likes-title";
			break;
		
			
		/*
		 * POST LIKE
		 */
		case "com.fullvicie.pojos.PostLike":
			
			PermissionType ptPostLike = checkPermissions(sessionUser,
					PermissionType.USER_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptPostLike == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = MySQLPostLikeDAO.getInstance().create(new PostLike(request));
			url += "#post-likes-title";
			
			break;
		
			
		/*
		 * PROFILE
		 */
		case "com.fullvicie.pojos.Profile":
			
			PermissionType ptProfile = checkPermissions(sessionUser,
					PermissionType.USER_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptProfile == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = MySQLPersonalInformationDAO.getInstance().create(new PersonalInformation(request));
			url += "#profiles-title";
			
			break;
		
			
		/*
		 * REPORT
		 */
		case "com.fullvicie.pojos.Report":
			
			PermissionType ptReport = checkPermissions(sessionUser,
					PermissionType.USER_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptReport == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = MySQLReportDAO.getInstance().create(new Report(request));
			url += "#reports-title";
			
			break;
		
		/*
		 * TEAM
		 */
		case "com.fullvicie.pojos.Team":
						
			PermissionType ptTeam = checkPermissions(sessionUser,
					PermissionType.USER_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptTeam == PermissionType.NO_PERMISSION)
				return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			Team newTeam = new Team(request);
			ArrayList<GamerProfile> gamerProfilesUser = null;
			try {
				gamerProfilesUser = MySQLGamerProfileDAO.getInstance().listBy(String.valueOf(newTeam.getUserCreatorId()), SearchBy.USER_ID);
			} catch (SQLException e) {
				et = ErrorType.LIST_GAMER_PROFILES_ERROR;
				e.printStackTrace();
			}
			
			if(et != ErrorType.NO_ERROR)
				return request.getContextPath() + et;
				
			for(GamerProfile gp: gamerProfilesUser)
				if(gp.getVideoGameId()==newTeam.getVideoGameId()) {
					et = ErrorType.MUST_HAVE_CORRESPONDING_GAMER_PROFILE;
					newTeam.getGamerProfiles()[0] = gp.getId();
					break;
				}
			
			if(et != ErrorType.NO_ERROR)
				return request.getContextPath() + et;
			
			et = MySQLTeamDAO.getInstance().create(newTeam);
			url += "#teams-title";
			
			break;	
			
			
			/*
			 * TEAM
			 */
			case "com.fullvicie.pojos.TeamInvitation":
				
				PermissionType ptTeamInvitation = checkPermissions(sessionUser,
						PermissionType.USER_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
				
				if(ptTeamInvitation == PermissionType.NO_PERMISSION)
						return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
				
				et = MySQLTeamInvitationDAO.getInstance().create(new TeamInvitation(request));
				url += "#team-invitations-title";
				
				break;
			
			
		/*
		 * USER
		 */
		case "com.fullvicie.pojos.User":
			
			PermissionType ptUser = checkPermissions(sessionUser,
					PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptUser == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = MySQLUserDAO.getInstance().create(new User(request));
			url += "#users-title";
			
			break;

			
		
		/*
		 * VIDEOGAME
		 */
		case "com.fullvicie.pojos.VideoGame":
			
			PermissionType ptVideogame = checkPermissions(sessionUser,
					 PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptVideogame == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = MySQLVideoGameDAO.getInstance().create(new VideoGame(request));
			url += "#videogames-title";
			break;
			
			
		default:
			break;
		}
		
		
		if(et != ErrorType.NO_ERROR)
			return ActionsController.ERROR_PAGE + et;
		else
			return url;
	}
	
	
	/*
	 * Tool methods
	 */
	private PermissionType checkPermissions(User sessionUser, PermissionType ... permissionTypes) {
				
		for(int i = 0; i < permissionTypes.length; ++i) {
						
			switch(permissionTypes[i]) {
			case ADMINISTRATOR_PERMISSION:
				if(sessionUser.getAdmin()) return PermissionType.ADMINISTRATOR_PERMISSION;
				break;
			case MODERATOR_PERMISSION:
				if(sessionUser.getModerator()) return PermissionType.MODERATOR_PERMISSION;
				break;
			case USER_PERMISSION:
				if(sessionUser != null) return PermissionType.USER_PERMISSION;
				break;
			case WHOEVER_PERMISSION:
				return PermissionType.WHOEVER_PERMISSION;
			default:
				return PermissionType.NO_PERMISSION;
			}
		}
		return PermissionType.NO_PERMISSION;
	}
	
}
