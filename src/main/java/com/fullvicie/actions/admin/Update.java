package com.fullvicie.actions.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;

import com.fullvicie.daos.sql.*;
import com.fullvicie.enums.*;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.*;


public class Update implements IAction{
	
	public static final String PARAM_UPDATE_ACTION = "PARAM_UPDATE_ACTION";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ErrorType et = ErrorType.NO_ERROR;
		
		String url = "/secured/admin_page.jsp";
		String objectClass = request.getParameter(ActionsController.PARAM_OBJECT_CLASS);				
		
		if(objectClass != null)
		switch(objectClass) {
			
		case "com.fullvicie.pojos.Forum":
			et = (new ForumSqlDao()).update(request.getParameter(Forum.PARAM_FORUM_ID), SearchBy.ID, new Forum(request));
			url += "#forums-title";
			break;
			
		case "com.fullvicie.pojos.ForumCategory":
			et = (new ForumCategorySqlDao()).update(request.getParameter(ForumCategory.PARAM_FORUM_CATEGORY_ID), SearchBy.ID, new ForumCategory(request));
			url += "#forum-categories-title";
			break;
			
		case "com.fullvicie.pojos.ForumMessage":
			et = (new ForumMessageSqlDao()).update(request.getParameter(ForumMessage.PARAM_FORUM_MESSGAE_ID), SearchBy.ID, new ForumMessage(request));
			url += "#forum-messages-title";
			break;
			
		case "com.fullvicie.pojos.ForumMessageLike":
			et = (new ForumMessageLikeSqlDao()).update(request.getParameter(ForumMessageLike.PARAM_FORUM_MESSAGE_LIKE_ID), SearchBy.ID, new ForumMessageLike(request));
			url += "#forum-message-likes-title";
			break;
			
		case "com.fullvicie.pojos.Post":
			et = (new PostSqlDao()).update(request.getParameter(Post.PARAM_POST_ID), SearchBy.ID, new Post(request));
			url += "#posts-title";
			break;
			
		case "com.fullvicie.pojos.PostCategory":
			et = (new PostCategorySqlDao()).update(request.getParameter(PostCategory.PARAM_POST_CATEGORY_ID), SearchBy.ID, new PostCategory(request));
			url += "#post-categories-title";
			break;
			
		case "com.fullvicie.pojos.PostComment":
			et = (new PostCommentSqlDao()).update(request.getParameter(PostComment.PARAM_POST_COMMENT_ID), SearchBy.ID, new PostComment(request));
			url += "#post-comments-title";
			break;
			
		case "com.fullvicie.pojos.PostCommentLike":
			et = (new PostCommentLikeSqlDao()).update(request.getParameter(PostCommentLike.PARAM_POST_COMMENT_LIKE_ID), SearchBy.ID, new PostCommentLike(request));
			url += "#post-comment-likes-title";
			break;
			
		case "com.fullvicie.pojos.PostLike":
			et = (new PostLikeSqlDao()).update(request.getParameter(PostLike.PARAM_POST_LIKE_ID), SearchBy.ID, new PostLike(request));
			url += "#post-likes-title";
			break;
			
		case "com.fullvicie.pojos.Profile":
			et = (new ProfileSqlDao()).update(request.getParameter(Profile.PARAM_PROFILE_ID), SearchBy.ID, new Profile(request)) ;
			url += "#profiles-title";
			break;
			
		case "com.fullvicie.pojos.Report":
			et = (new ReportSqlDao()).update(request.getParameter(Report.PARAM_REPORT_ID), SearchBy.ID, new Report(request));
			url += "#reports-title";
			break;
		
		case "com.fullvicie.pojos.User":
			et = (new UserSqlDao()).update(request.getParameter(User.PARAM_USER_ID), SearchBy.ID, new User(request));
			url += "#users-title";
			break;
			
		default:
			
			break;
		}
		
		if(et != ErrorType.NO_ERROR)
			url = "/mod/error.jsp?ERROR_TYPE=" + et;
		
		return url;
	}
	
}
