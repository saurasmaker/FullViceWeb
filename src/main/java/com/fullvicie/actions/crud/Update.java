package com.fullvicie.actions.crud;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.mysql.*;
import com.fullvicie.enums.*;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.*;


public class Update implements IAction{
	
	public static final String PARAM_UPDATE_ACTION = "PARAM_UPDATE_ACTION";
	private static final int NO_OWNER = -1;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
			
			Forum forum =  new Forum(request);
			
			PermissionType ptForum = checkPermissions(sessionUser, forum.getUserId(),
					PermissionType.OWNER_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptForum == PermissionType.NO_PERMISSION)
					return ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = (new MySQLForumDAO()).update(request.getParameter(Forum.PARAM_FORUM_ID), SearchBy.ID, forum);
			url += "#forums-title";
			break;
			
			
			
		/*
		 * FORUM CATEGORY
		 */
		case "com.fullvicie.pojos.ForumCategory":
			
			PermissionType ptForumCategory = checkPermissions(sessionUser, NO_OWNER,
					PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptForumCategory == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = (new MySQLForumCategoryDAO()).update(request.getParameter(ForumCategory.PARAM_FORUM_CATEGORY_ID), SearchBy.ID, new ForumCategory(request));
			url += "#forum-categories-title";
			break;
			
			
			
		/*
		 * FORUM MESSAGE
		 */
		case "com.fullvicie.pojos.ForumMessage":
			
			ForumMessage forumMessage =  new ForumMessage(request);
			
			PermissionType ptForumMessage = checkPermissions(sessionUser, forumMessage.getUserId(),
					PermissionType.OWNER_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptForumMessage == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = (new MySQLForumMessageDAO()).update(request.getParameter(ForumMessage.PARAM_FORUM_MESSGAE_ID), SearchBy.ID, forumMessage);
			url += "#forum-messages-title";
			break;
			
			
			
		/*
		 * FORUM MESSAGE LIKE
		 */
		case "com.fullvicie.pojos.ForumMessageLike":
			et = (new MySQLForumMessageLikeDAO()).update(request.getParameter(ForumMessageLike.PARAM_FORUM_MESSAGE_LIKE_ID), SearchBy.ID, new ForumMessageLike(request));
			url += "#forum-message-likes-title";
			break;
		
			
			
		/*
		 * GAMER PROFILE
		 */
		case "com.fullvicie.pojos.GamerProfile":
						
			GamerProfile gamerProfile =  new GamerProfile(request);
			
			PermissionType ptGamerProfile = checkPermissions(sessionUser, gamerProfile.getUserId(),
					PermissionType.OWNER_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptGamerProfile == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = (new MySQLGamerProfileDAO()).update(request.getParameter(GamerProfile.PARAM_GAMER_PROFILE_ID), SearchBy.ID, gamerProfile);
			url += "#gamer-profiles-title";
			break;	
			
			
			
		/*
		 * POST
		 */
		case "com.fullvicie.pojos.Post":
			
			Post post =  new Post(request);
			
			PermissionType ptPost = checkPermissions(sessionUser, post.getUserId(),
					PermissionType.OWNER_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptPost == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = (new MySQLPostDAO()).update(request.getParameter(Post.PARAM_POST_ID), SearchBy.ID, post);
			url += "#posts-title";
			break;
			
			
		
		/*
		 * POST CATEGORY
		 */
		case "com.fullvicie.pojos.PostCategory":
					
			PermissionType ptPostCategory = checkPermissions(sessionUser, NO_OWNER,
					PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptPostCategory == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = (new MySQLPostCategoryDAO()).update(request.getParameter(PostCategory.PARAM_POST_CATEGORY_ID), SearchBy.ID, new PostCategory(request));
			url += "#post-categories-title";
			break;
			
			
			
		/*
		 * POST COMMENT
		 */
		case "com.fullvicie.pojos.PostComment":
			
			PostComment postComment =  new PostComment(request);
			
			PermissionType ptComment = checkPermissions(sessionUser, postComment.getUserId(),
					PermissionType.OWNER_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptComment == PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = (new MySQLPostCommentDAO()).update(request.getParameter(PostComment.PARAM_POST_COMMENT_ID), SearchBy.ID, postComment);
			url += "#post-comments-title";
			break;
			
			
			
		/*
		 * POST COMMENT LIKE
		 */
		case "com.fullvicie.pojos.PostCommentLike":
			et = (new MySQLPostCommentLikeDAO()).update(request.getParameter(PostCommentLike.PARAM_POST_COMMENT_LIKE_ID), SearchBy.ID, new PostCommentLike(request));
			url += "#post-comment-likes-title";
			break;
			
			
			
		/*
		 * POST LIKE
		 */
		case "com.fullvicie.pojos.PostLike":
			et = (new MySQLPostLikeDAO()).update(request.getParameter(PostLike.PARAM_POST_LIKE_ID), SearchBy.ID, new PostLike(request));
			url += "#post-likes-title";
			break;
		
			
			
		/*
		 * PROFILE
		 */
		case "com.fullvicie.pojos.Profile":
			PersonalInformation profile = new PersonalInformation(request);
			
			PermissionType ptProfile = checkPermissions(sessionUser, profile.getUserId(),
					PermissionType.OWNER_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptProfile == PermissionType.NO_PERMISSION) {
				return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			}
			
			et = (new MySQLProfileDAO()).update(request.getParameter(PersonalInformation.PARAM_PROFILE_ID), SearchBy.ID, profile) ;
			url += "#profiles-title";
			break;
		
			
			
		/*
		 * REPORT
		 */
		case "com.fullvicie.pojos.Report":
			
			PermissionType ptReport = checkPermissions(sessionUser, NO_OWNER,
					PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptReport==PermissionType.NO_PERMISSION)
				return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = (new MySQLReportDAO()).update(request.getParameter(Report.PARAM_REPORT_ID), SearchBy.ID, new Report(request));
			url += "#reports-title";
			break;
		
			
			
		/*
		 * TEAM
		 */
		case "com.fullvicie.pojos.Team":
			Team team = new Team(request);
						
			PermissionType ptTeam = checkPermissions(sessionUser, team.getUserOwnerId(),
					PermissionType.OWNER_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptTeam==PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			et = (new MySQLTeamDAO()).update(String.valueOf(team.getId()), SearchBy.ID, team);
			url += "#teams-title";
			break;
				
			
			/*
			 * TEAM INVITATION
			 */
			case "com.fullvicie.pojos.TeamInvitation":
				TeamInvitation teamInvitation = new TeamInvitation(request);
							
				PermissionType ptTeamInvitation = checkPermissions(sessionUser, NO_OWNER,
						PermissionType.ADMINISTRATOR_PERMISSION);
				
				if(ptTeamInvitation==PermissionType.NO_PERMISSION)
						return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
				
				et = (new MySQLTeamInvitationDAO()).update(String.valueOf(teamInvitation.getId()), SearchBy.ID, teamInvitation);
				url += "#team-invitations-title";
				break;
			
				
		/*
		 * USER
		 */
		case "com.fullvicie.pojos.User":
			User user = new User(request);
			
			PermissionType ptUser = checkPermissions(sessionUser, user.getId(),
					PermissionType.OWNER_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
			
			if(ptUser==PermissionType.NO_PERMISSION)
					return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
			
			else if(ptUser==PermissionType.OWNER_PERMISSION) {
				User actualUser = new MySQLUserDAO().read(String.valueOf(user.getId()), SearchBy.ID);
				user.setAdmin(actualUser.getAdmin());
				user.setModerator(actualUser.getModerator());
				user.setDeleted(actualUser.getDeleted());
			}
			
			et = (new MySQLUserDAO()).update(request.getParameter(User.PARAM_USER_ID), SearchBy.ID, user);
			url += "#users-title";
			break;
			
			
			/*
			 * VIDEO GAME
			 */
			case "com.fullvicie.pojos.VideoGame":
				VideoGame videoGame = new VideoGame(request);
				
				PermissionType ptVideoGame = checkPermissions(sessionUser, videoGame.getId(),
						PermissionType.MODERATOR_PERMISSION, PermissionType.ADMINISTRATOR_PERMISSION);
				
				if(ptVideoGame==PermissionType.NO_PERMISSION)
						return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
				
				et = (new MySQLVideoGameDAO()).update(request.getParameter(VideoGame.PARAM_VIDEO_GAME_ID), SearchBy.ID, videoGame);
				url += "#video-games-title";
				break;
			
			
		//ERROR: NO EXISTS THAT UPDATE ACTION
		default:
			
			break;
		}
		
		if(et != ErrorType.NO_ERROR)
			url = request.getContextPath() + ActionsController.ERROR_PAGE + et;
		
		return url;
	}
	
	
	
	/*
	 * Tool methods
	 */
	private PermissionType checkPermissions(User sessionUser, int ownerId, PermissionType ... permissionTypes) {
				
		for(int i = 0; i < permissionTypes.length; ++i) {
						
			switch(permissionTypes[i]) {
			case ADMINISTRATOR_PERMISSION:
				if(sessionUser.getAdmin()) return PermissionType.ADMINISTRATOR_PERMISSION;
				break;
			case MODERATOR_PERMISSION:
				if(sessionUser.getModerator()) return PermissionType.MODERATOR_PERMISSION;
				break;
			case OWNER_PERMISSION:
				if(sessionUser.getId() == ownerId) return PermissionType.OWNER_PERMISSION;
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
