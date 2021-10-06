package com.fullvicie.actions.crud;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.mysql.*;
import com.fullvicie.interfaces.IAction;

import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.pojos.*;


public class Delete implements IAction{
	
	public static final String PARAM_DELETE_ACTION = "PARAM_DELETE_ACTION";
	
	
	/*
	 * Singleton
	 */
	private static Delete instance;
	private Delete() {}
	public static Delete getInstance() {	
		if(instance == null)
			instance = new Delete();	
		return instance;
	}
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		if(!u.isAdmin() && !u.isModerator())
			return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String url = request.getHeader("referer");
		String objectClass = request.getParameter(ActionsController.PARAM_OBJECT_CLASS);				
		
		if(objectClass != null)
		switch(objectClass) {
			
		case "com.fullvicie.pojos.Forum":
			et = MySQLForumDAO.getInstance().delete(request.getParameter(Forum.PARAM_FORUM_ID), SearchBy.ID);
			url += "#forums-title";
			break;
			
		case "com.fullvicie.pojos.ForumCategory":
			et = MySQLForumCategoryDAO.getInstance().delete(request.getParameter(ForumCategory.PARAM_FORUM_CATEGORY_ID), SearchBy.ID);
			url += "#forum-categories-title";
			break;
			
		case "com.fullvicie.pojos.ForumMessage":
			et = MySQLForumMessageDAO.getInstance().delete(request.getParameter(ForumMessage.PARAM_FORUM_MESSGAE_ID), SearchBy.ID);
			url += "#forum-messages-title";
			break;
			
		case "com.fullvicie.pojos.ForumMessageLike":
			et = MySQLForumMessageLikeDAO.getInstance().delete(request.getParameter(ForumMessageLike.PARAM_FORUM_MESSAGE_LIKE_ID), SearchBy.ID);
			url += "#forum-message-likes-title";
			break;
			
		case "com.fullvicie.pojos.GamerProfile":
			et = MySQLGamerProfileDAO.getInstance().delete(request.getParameter(GamerProfile.PARAM_GAMER_PROFILE_ID), SearchBy.ID);
			url += "#gamer-profiles-title";
			break;
			
		case "com.fullvicie.pojos.Post":
			et = MySQLPostDAO.getInstance().delete(request.getParameter(Post.PARAM_POST_ID), SearchBy.ID);
			url += "#posts-title";
			break;
			
		case "com.fullvicie.pojos.PostCategory":
			et = MySQLPostCategoryDAO.getInstance().delete(request.getParameter(PostCategory.PARAM_POST_CATEGORY_ID), SearchBy.ID);
			url += "#post-categories-title";
			break;
			
		case "com.fullvicie.pojos.PostComment":
			et = MySQLPostCommentDAO.getInstance().delete(request.getParameter(PostComment.PARAM_POST_COMMENT_ID), SearchBy.ID);
			url += "#post-comments-title";
			break;
			
		case "com.fullvicie.pojos.PostCommentLike":
			et = MySQLPostCommentLikeDAO.getInstance().delete(request.getParameter(PostCommentLike.PARAM_POST_COMMENT_LIKE_ID), SearchBy.ID);
			url += "#post-comment-likes-title";
			break;
			
		case "com.fullvicie.pojos.PostLike":
			et = MySQLPostLikeDAO.getInstance().delete(request.getParameter(PostLike.PARAM_POST_LIKE_ID), SearchBy.ID);
			url += "#post-likes-title";
			break;
			
		case "com.fullvicie.pojos.Profile":
			et = MySQLPersonalInformationDAO.getInstance().delete(request.getParameter(PersonalInformation.PARAM_PERSONAL_INFORMATION_ID), SearchBy.ID);
			url += "#profiles-title";
			break;
			
		case "com.fullvicie.pojos.Report":
			et = MySQLReportDAO.getInstance().delete(request.getParameter(Report.PARAM_REPORT_ID), SearchBy.ID);
			url += "#reports-title";
			break;
		
		case "com.fullvicie.pojos.Team":
			et = MySQLTeamDAO.getInstance().delete(request.getParameter(Team.PARAM_TEAM_ID), SearchBy.ID);
			url += "#teams-title";
			break;	
			
		case "com.fullvicie.pojos.TeamInvitation":
			et = MySQLTeamInvitationDAO.getInstance().delete(request.getParameter(TeamInvitation.PARAM_TEAM_INVITATION_ID), SearchBy.ID);
			url += "#team-invitations-title";
			break;		
		
		case "com.fullvicie.pojos.User":
			et = MySQLUserDAO.getInstance().delete(request.getParameter(User.PARAM_USER_ID), SearchBy.ID);
			url += "#users-title";
			break;
		
		case "com.fullvicie.pojos.VideoGame":
			et = MySQLVideoGameDAO.getInstance().delete(request.getParameter(VideoGame.PARAM_VIDEO_GAME_ID), SearchBy.ID);
			url += "#video-games-title";
			break;
		default:
			
			break;
		}
		
		if(et != ErrorType.NO_ERROR)
			url = ActionsController.ERROR_PAGE + et;
		
		return url;
		
	}
	


}
