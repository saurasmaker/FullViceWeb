package com.fullvicie.actions.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.interfaces.IAction;

import com.fullvicie.daos.sql.*;
import com.fullvicie.pojos.*;

public class Create implements IAction{
	
	public static final String PARAM_CREATE_ACTION = "PARAM_CREATE_ACTION";
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
ErrorType et = ErrorType.NO_ERROR;
		
		String url = "/secured/admin_page.jsp";
		String objectClass = request.getParameter(ActionsController.PARAM_OBJECT_CLASS);		
					
		
		if(objectClass != null)
		switch(objectClass) {
		
		case "com.fullvicie.pojos.Forum":
			et = (new ForumSqlDao()).create(new Forum(request));
			url += "#forums-title";
			break;
			
		case "com.fullvicie.pojos.ForumCategory":
			et = (new ForumCategorySqlDao()).create(new ForumCategory(request));
			url += "#forum-categories-title";
			break;
			
		case "com.fullvicie.pojos.ForumMessage":
			et = (new ForumMessageSqlDao()).create(new ForumMessage(request));
			url += "#forum-messages-title";
			break;
			
		case "com.fullvicie.pojos.ForumMessageLike":
			et = (new ForumMessageLikeSqlDao()).create(new ForumMessageLike(request));
			url += "#forum-message-likes-title";
			break;
			
		case "com.fullvicie.pojos.Post":
			et = (new PostSqlDao()).create(new Post(request));
			url += "#posts-title";
			break;
			
		case "com.fullvicie.pojos.PostCategory":
			et = (new PostCategorySqlDao()).create(new PostCategory(request));
			url += "#post-categories-title";
			break;
			
		case "com.fullvicie.pojos.PostComment":
			et = (new PostCommentSqlDao()).create(new PostComment(request));
			url += "#post-comments-title";
			break;
			
		case "com.fullvicie.pojos.PostCommentLike":
			et = (new PostCommentLikeSqlDao()).create(new PostCommentLike(request));
			url += "#post-comment-likes-title";
			break;
			
		case "com.fullvicie.pojos.PostLike":
			et = (new PostLikeSqlDao()).create(new PostLike(request));
			url += "#post-likes-title";
			break;
			
		case "com.fullvicie.pojos.Profile":
			et = (new ProfileSqlDao()).create(new Profile(request));
			url += "#profiles-title";
			break;
			
		case "com.fullvicie.pojos.Report":
			et = (new ReportSqlDao()).create(new Report(request));
			url += "#reports-title";
			break;
		
		case "com.fullvicie.pojos.User":
			et = (new UserSqlDao()).create(new User(request));
			url += "#forums-title";
			break;

		default:
			break;
		}
		
		
		if(et != ErrorType.NO_ERROR)
			return "/mod/error.jsp?ERROR_TYPE=" + et;
		else
			return url;
	}
	
}
