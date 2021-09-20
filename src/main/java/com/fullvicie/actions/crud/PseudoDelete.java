package com.fullvicie.actions.crud;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.mysql.*;
import com.fullvicie.pojos.*;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;


public class PseudoDelete implements IAction{
		
	public static final String PARAM_PSEUDODELETE_ACTION = "PARAM_PSEUDODELETE_ACTION";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		if(!u.isAdmin() && !u.isModerator())
			return request.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR;
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String url = request.getHeader("referer");
		String objectClass = request.getParameter(ActionsController.PARAM_OBJECT_CLASS);				
		
		if(objectClass != null)
		switch(objectClass) {
			
		case "com.fullvicie.pojos.Forum":
			et = (new MySQLForumDAO()).pseudoDelete(request.getParameter(Forum.PARAM_FORUM_ID), SearchBy.ID);
			url += "#forums-title";
			break;
			
		case "com.fullvicie.pojos.ForumCategory":
			et = (new MySQLForumCategoryDAO()).pseudoDelete(request.getParameter(ForumCategory.PARAM_FORUM_CATEGORY_ID), SearchBy.ID);
			url += "#forum-categories-title";
			break;
			
		case "com.fullvicie.pojos.ForumMessage":
			et = (new MySQLForumMessageDAO()).pseudoDelete(request.getParameter(ForumMessage.PARAM_FORUM_MESSGAE_ID), SearchBy.ID);
			url += "#forum-messages-title";
			break;
			
		case "com.fullvicie.pojos.ForumMessageLike":
			et = (new MySQLForumMessageLikeDAO()).pseudoDelete(request.getParameter(ForumMessageLike.PARAM_FORUM_MESSAGE_LIKE_ID), SearchBy.ID);
			url += "#forum-message-likes-title";
			break;
			
		case "com.fullvicie.pojos.GamerProfile":
			et = (new MySQLGamerProfileDAO()).pseudoDelete(request.getParameter(GamerProfile.PARAM_GAMER_PROFILE_ID), SearchBy.ID);
			url += "#gamer-profiles-likes-title";
			break;
			
		case "com.fullvicie.pojos.Post":
			et = (new MySQLPostDAO()).pseudoDelete(request.getParameter(Post.PARAM_POST_ID), SearchBy.ID);
			url += "#posts-title";
			break;
			
		case "com.fullvicie.pojos.PostCategory":
			et = (new MySQLPostCategoryDAO()).pseudoDelete(request.getParameter(PostCategory.PARAM_POST_CATEGORY_ID), SearchBy.ID);
			url += "#post-categories-title";
			break;
			
		case "com.fullvicie.pojos.PostComment":
			et = (new MySQLPostCommentDAO()).pseudoDelete(request.getParameter(PostComment.PARAM_POST_COMMENT_ID), SearchBy.ID);
			url += "#post-comments-title";
			break;
			
		case "com.fullvicie.pojos.PostCommentLike":
			et = (new MySQLPostCommentLikeDAO()).pseudoDelete(request.getParameter(PostCommentLike.PARAM_POST_COMMENT_LIKE_ID), SearchBy.ID);
			url += "#post-comment-likes-title";
			break;
			
		case "com.fullvicie.pojos.PostLike":
			et = (new MySQLPostLikeDAO()).pseudoDelete(request.getParameter(PostLike.PARAM_POST_LIKE_ID), SearchBy.ID);
			url += "#post-likes-title";
			break;
			
		case "com.fullvicie.pojos.Profile":
			et = (new MySQLProfileDAO()).pseudoDelete(request.getParameter(PersonalInformation.PARAM_PROFILE_ID), SearchBy.ID);
			url += "#profiles-title";
			break;
			
		case "com.fullvicie.pojos.Report":
			et = (new MySQLReportDAO()).pseudoDelete(request.getParameter(Report.PARAM_REPORT_ID), SearchBy.ID);
			url += "#reports-title";
			break;
		
		case "com.fullvicie.pojos.Team":
			et = (new MySQLTeamDAO()).pseudoDelete(request.getParameter(Team.PARAM_TEAM_ID), SearchBy.ID);
			url += "#teams-title";
			break;	
			
		case "com.fullvicie.pojos.TeamInvitation":
			et = (new MySQLTeamInvitationDAO()).pseudoDelete(request.getParameter(TeamInvitation.PARAM_TEAM_INVITATION_ID), SearchBy.ID);
			url += "#team-invitations-title";
			break;	
			
		case "com.fullvicie.pojos.User":
			et = (new MySQLUserDAO()).pseudoDelete(request.getParameter(User.PARAM_USER_ID), SearchBy.ID);
			url += "#users-title";
			break;
			
		case "com.fullvicie.pojos.VideoGame":
			et = (new MySQLVideoGameDAO()).pseudoDelete(request.getParameter(VideoGame.PARAM_VIDEO_GAME_ID), SearchBy.ID);
			url += "#users-title";
			break;	
		
		default:
			
			break;
		}
		
		if(et != ErrorType.NO_ERROR)
			url = request.getContextPath() + ActionsController.ERROR_PAGE + et;
		
		return url;
		
	}
		
}
