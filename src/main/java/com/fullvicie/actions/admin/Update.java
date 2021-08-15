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
			
			if(PermissionType.NO_PERMISSION == ActionsController.checkPermissions(sessionUser, forum.getUserId(),
					PermissionType.ADMINISTRATOR_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.OWNER_PERMISSION))
					return request.getContextPath() + "/pages/error.jsp?ERROR_TYPE=" + ErrorType.ACCESS_DENIED_ERROR;
			
			et = (new ForumSqlDao()).update(request.getParameter(Forum.PARAM_FORUM_ID), SearchBy.ID, forum);
			url += "#forums-title";
			break;
			
			
			
		/*
		 * FORUM CATEGORY
		 */
		case "com.fullvicie.pojos.ForumCategory":
			
			if(PermissionType.NO_PERMISSION == ActionsController.checkPermissions(sessionUser, NO_OWNER,
					PermissionType.ADMINISTRATOR_PERMISSION, PermissionType.MODERATOR_PERMISSION))
					return request.getContextPath() + "/pages/error.jsp?ERROR_TYPE=" + ErrorType.ACCESS_DENIED_ERROR;
			
			et = (new ForumCategorySqlDao()).update(request.getParameter(ForumCategory.PARAM_FORUM_CATEGORY_ID), SearchBy.ID, new ForumCategory(request));
			url += "#forum-categories-title";
			break;
			
			
			
		/*
		 * FORUM MESSAGE
		 */
		case "com.fullvicie.pojos.ForumMessage":
			
			ForumMessage forumMessage =  new ForumMessage(request);
			
			if(PermissionType.NO_PERMISSION == ActionsController.checkPermissions(sessionUser, forumMessage.getUserId(),
					PermissionType.ADMINISTRATOR_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.OWNER_PERMISSION))
					return request.getContextPath() + "/pages/error.jsp?ERROR_TYPE=" + ErrorType.ACCESS_DENIED_ERROR;
			
			et = (new ForumMessageSqlDao()).update(request.getParameter(ForumMessage.PARAM_FORUM_MESSGAE_ID), SearchBy.ID, forumMessage);
			url += "#forum-messages-title";
			break;
			
			
			
		/*
		 * FORUM MESSAGE LIKE
		 */
		case "com.fullvicie.pojos.ForumMessageLike":
			et = (new ForumMessageLikeSqlDao()).update(request.getParameter(ForumMessageLike.PARAM_FORUM_MESSAGE_LIKE_ID), SearchBy.ID, new ForumMessageLike(request));
			url += "#forum-message-likes-title";
			break;
		
			
			
		/*
		 * POST
		 */
		case "com.fullvicie.pojos.Post":
			
			Post post =  new Post(request);
			
			if(PermissionType.NO_PERMISSION == ActionsController.checkPermissions(sessionUser, post.getUserId(),
					PermissionType.ADMINISTRATOR_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.OWNER_PERMISSION))
					return request.getContextPath() + "/pages/error.jsp?ERROR_TYPE=" + ErrorType.ACCESS_DENIED_ERROR;
			
			et = (new PostSqlDao()).update(request.getParameter(Post.PARAM_POST_ID), SearchBy.ID, post);
			url += "#posts-title";
			break;
			
			
		
		/*
		 * POST CATEGORY
		 */
		case "com.fullvicie.pojos.PostCategory":
						
			if(PermissionType.NO_PERMISSION == ActionsController.checkPermissions(sessionUser, NO_OWNER,
					PermissionType.ADMINISTRATOR_PERMISSION, PermissionType.MODERATOR_PERMISSION))
					return request.getContextPath() + "/pages/error.jsp?ERROR_TYPE=" + ErrorType.ACCESS_DENIED_ERROR;
			
			et = (new PostCategorySqlDao()).update(request.getParameter(PostCategory.PARAM_POST_CATEGORY_ID), SearchBy.ID, new PostCategory(request));
			url += "#post-categories-title";
			break;
			
			
			
		/*
		 * POST COMMENT
		 */
		case "com.fullvicie.pojos.PostComment":
			
			PostComment postComment =  new PostComment(request);
			
			if(PermissionType.NO_PERMISSION == ActionsController.checkPermissions(sessionUser, postComment.getUserId(),
					PermissionType.ADMINISTRATOR_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.OWNER_PERMISSION))
					return request.getContextPath() + "/pages/error.jsp?ERROR_TYPE=" + ErrorType.ACCESS_DENIED_ERROR;
			
			et = (new PostCommentSqlDao()).update(request.getParameter(PostComment.PARAM_POST_COMMENT_ID), SearchBy.ID, postComment);
			url += "#post-comments-title";
			break;
			
			
			
		/*
		 * POST COMMENT LIKE
		 */
		case "com.fullvicie.pojos.PostCommentLike":
			et = (new PostCommentLikeSqlDao()).update(request.getParameter(PostCommentLike.PARAM_POST_COMMENT_LIKE_ID), SearchBy.ID, new PostCommentLike(request));
			url += "#post-comment-likes-title";
			break;
			
			
			
		/*
		 * POST LIKE
		 */
		case "com.fullvicie.pojos.PostLike":
			et = (new PostLikeSqlDao()).update(request.getParameter(PostLike.PARAM_POST_LIKE_ID), SearchBy.ID, new PostLike(request));
			url += "#post-likes-title";
			break;
		
			
			
		/*
		 * PROFILE
		 */
		case "com.fullvicie.pojos.Profile":
			Profile profile = new Profile(request);
			ProfileSqlDao psd = new ProfileSqlDao();
			
			if(PermissionType.NO_PERMISSION == ActionsController.checkPermissions(sessionUser, profile.getUserId(),
					PermissionType.ADMINISTRATOR_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.OWNER_PERMISSION)) {
				return request.getContextPath() + "/pages/error.jsp?ERROR_TYPE=" + ErrorType.ACCESS_DENIED_ERROR;
			}
			

			et = (new ProfileSqlDao()).update(request.getParameter(Profile.PARAM_PROFILE_ID), SearchBy.ID, profile) ;
			
			if(!request.getHeader("referer").contains("moderator") && !request.getHeader("referer").contains("admin")) {
				profile = psd.read(String.valueOf(profile.getId()), SearchBy.ID); 
				request.getSession().setAttribute(Profile.ATTR_PROFILE_OBJ, profile);
			}
			
			url += "#profiles-title";
			break;
		
			
			
		/*
		 * REPORT
		 */
		case "com.fullvicie.pojos.Report":
			
			if(PermissionType.NO_PERMISSION == ActionsController.checkPermissions(sessionUser, NO_OWNER,
					PermissionType.ADMINISTRATOR_PERMISSION, PermissionType.MODERATOR_PERMISSION))
				return request.getContextPath() + "/pages/error.jsp?ERROR_TYPE=" + ErrorType.ACCESS_DENIED_ERROR;
			
			et = (new ReportSqlDao()).update(request.getParameter(Report.PARAM_REPORT_ID), SearchBy.ID, new Report(request));
			url += "#reports-title";
			break;
		
			
		/*
		 * USER
		 */
		case "com.fullvicie.pojos.User":
			User user = new User(request);
			UserSqlDao usd = new UserSqlDao();

			if(PermissionType.NO_PERMISSION == ActionsController.checkPermissions(sessionUser, user.getId(),
					PermissionType.ADMINISTRATOR_PERMISSION, PermissionType.MODERATOR_PERMISSION, PermissionType.OWNER_PERMISSION))
				return request.getContextPath() + "/pages/error.jsp?ERROR_TYPE=" + ErrorType.ACCESS_DENIED_ERROR;
			
			
			
			et = usd.update(request.getParameter(User.PARAM_USER_ID), SearchBy.ID, user);
			
			if(!request.getHeader("referer").contains("moderator") && !request.getHeader("referer").contains("admin")) {
				user = usd.read(String.valueOf(user.getId()), SearchBy.ID); 
				request.getSession().setAttribute(User.ATR_USER_LOGGED_OBJ, user);
			}
			
			url += "#users-title";
			break;
			
			
		//ERROR NO EXISTS THAT UPDATE ACTION
		default:
			
			break;
		}
		
		if(et != ErrorType.NO_ERROR)
			url = request.getContextPath() + "/pages/error.jsp?ERROR_TYPE=" + et;
		
		return url;
	}
	
	
}
