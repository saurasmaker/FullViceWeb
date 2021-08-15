package com.fullvicie.actions.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.interfaces.IAction;

import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;

import com.fullvicie.daos.sql.*;
import com.fullvicie.pojos.*;


public class Delete implements IAction{
	
	public static final String PARAM_DELETE_ACTION = "PARAM_DELETE_ACTION";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User u = (User) request.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
		if(!u.isAdmin() && !u.isModerator())
			return "/pages/error.jsp?ERROR_TYPE=" + ErrorType.ACCESS_DENIED_ERROR;
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String url = request.getHeader("referer");
		String objectClass = request.getParameter(ActionsController.PARAM_OBJECT_CLASS);				
		
		if(objectClass != null)
		switch(objectClass) {
			
		case "com.fullvicie.pojos.Forum":
			et = (new ForumSqlDao()).delete(request.getParameter(Forum.PARAM_FORUM_ID), SearchBy.ID);
			url += "#forums-title";
			break;
			
		case "com.fullvicie.pojos.ForumCategory":
			et = (new ForumCategorySqlDao()).delete(request.getParameter(ForumCategory.PARAM_FORUM_CATEGORY_ID), SearchBy.ID);
			url += "#forum-categories-title";
			break;
			
		case "com.fullvicie.pojos.ForumMessage":
			et = (new ForumMessageSqlDao()).delete(request.getParameter(ForumMessage.PARAM_FORUM_MESSGAE_ID), SearchBy.ID);
			url += "#forum-messages-title";
			break;
			
		case "com.fullvicie.pojos.ForumMessageLike":
			et = (new ForumMessageLikeSqlDao()).delete(request.getParameter(ForumMessageLike.PARAM_FORUM_MESSAGE_LIKE_ID), SearchBy.ID);
			url += "#forum-message-likes-title";
			break;
			
		case "com.fullvicie.pojos.Post":
			et = (new PostSqlDao()).delete(request.getParameter(Post.PARAM_POST_ID), SearchBy.ID);
			url += "#posts-title";
			break;
			
		case "com.fullvicie.pojos.PostCategory":
			et = (new PostCategorySqlDao()).delete(request.getParameter(PostCategory.PARAM_POST_CATEGORY_ID), SearchBy.ID);
			url += "#post-categories-title";
			break;
			
		case "com.fullvicie.pojos.PostComment":
			et = (new PostCommentSqlDao()).delete(request.getParameter(PostComment.PARAM_POST_COMMENT_ID), SearchBy.ID);
			url += "#post-comments-title";
			break;
			
		case "com.fullvicie.pojos.PostCommentLike":
			et = (new PostCommentLikeSqlDao()).delete(request.getParameter(PostCommentLike.PARAM_POST_COMMENT_LIKE_ID), SearchBy.ID);
			url += "#post-comment-likes-title";
			break;
			
		case "com.fullvicie.pojos.PostLike":
			et = (new PostLikeSqlDao()).delete(request.getParameter(PostLike.PARAM_POST_LIKE_ID), SearchBy.ID);
			url += "#post-likes-title";
			break;
			
		case "com.fullvicie.pojos.Profile":
			et = (new ProfileSqlDao()).delete(request.getParameter(Profile.PARAM_PROFILE_ID), SearchBy.ID);
			url += "#profiles-title";
			break;
			
		case "com.fullvicie.pojos.Report":
			et = (new ReportSqlDao()).delete(request.getParameter(Report.PARAM_REPORT_ID), SearchBy.ID);
			url += "#reports-title";
			break;
		
		case "com.fullvicie.pojos.User":
			et = (new UserSqlDao()).delete(request.getParameter(User.PARAM_USER_ID), SearchBy.ID);
			url += "#users-title";
			break;
			
		default:
			
			break;
		}
		
		if(et != ErrorType.NO_ERROR)
			url = "/pages/error.jsp?ERROR_TYPE=" + et;
		
		return url;
		
	}
	


}
