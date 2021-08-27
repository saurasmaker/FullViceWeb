package com.fullvicie.actions.moderator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fullvicie.controllers.ActionsController;
import com.fullvicie.daos.sql.ForumCategorySqlDao;
import com.fullvicie.daos.sql.ForumMessageLikeSqlDao;
import com.fullvicie.daos.sql.ForumMessageSqlDao;
import com.fullvicie.daos.sql.ForumSqlDao;
import com.fullvicie.daos.sql.PostCategorySqlDao;
import com.fullvicie.daos.sql.PostCommentLikeSqlDao;
import com.fullvicie.daos.sql.PostCommentSqlDao;
import com.fullvicie.daos.sql.PostLikeSqlDao;
import com.fullvicie.daos.sql.PostSqlDao;
import com.fullvicie.daos.sql.ProfileSqlDao;
import com.fullvicie.daos.sql.ReportSqlDao;
import com.fullvicie.daos.sql.UserSqlDao;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IAction;
import com.fullvicie.pojos.Forum;
import com.fullvicie.pojos.ForumCategory;
import com.fullvicie.pojos.ForumMessage;
import com.fullvicie.pojos.ForumMessageLike;
import com.fullvicie.pojos.Post;
import com.fullvicie.pojos.PostCategory;
import com.fullvicie.pojos.PostComment;
import com.fullvicie.pojos.PostCommentLike;
import com.fullvicie.pojos.PostLike;
import com.fullvicie.pojos.Profile;
import com.fullvicie.pojos.Report;
import com.fullvicie.pojos.User;

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
			et = (new ForumSqlDao()).pseudoDelete(request.getParameter(Forum.PARAM_FORUM_ID), SearchBy.ID);
			url += "#forums-title";
			break;
			
		case "com.fullvicie.pojos.ForumCategory":
			et = (new ForumCategorySqlDao()).pseudoDelete(request.getParameter(ForumCategory.PARAM_FORUM_CATEGORY_ID), SearchBy.ID);
			url += "#forum-categories-title";
			break;
			
		case "com.fullvicie.pojos.ForumMessage":
			et = (new ForumMessageSqlDao()).pseudoDelete(request.getParameter(ForumMessage.PARAM_FORUM_MESSGAE_ID), SearchBy.ID);
			url += "#forum-messages-title";
			break;
			
		case "com.fullvicie.pojos.ForumMessageLike":
			et = (new ForumMessageLikeSqlDao()).pseudoDelete(request.getParameter(ForumMessageLike.PARAM_FORUM_MESSAGE_LIKE_ID), SearchBy.ID);
			url += "#forum-message-likes-title";
			break;
			
		case "com.fullvicie.pojos.Post":
			et = (new PostSqlDao()).pseudoDelete(request.getParameter(Post.PARAM_POST_ID), SearchBy.ID);
			url += "#posts-title";
			break;
			
		case "com.fullvicie.pojos.PostCategory":
			et = (new PostCategorySqlDao()).pseudoDelete(request.getParameter(PostCategory.PARAM_POST_CATEGORY_ID), SearchBy.ID);
			url += "#post-categories-title";
			break;
			
		case "com.fullvicie.pojos.PostComment":
			et = (new PostCommentSqlDao()).pseudoDelete(request.getParameter(PostComment.PARAM_POST_COMMENT_ID), SearchBy.ID);
			url += "#post-comments-title";
			break;
			
		case "com.fullvicie.pojos.PostCommentLike":
			et = (new PostCommentLikeSqlDao()).pseudoDelete(request.getParameter(PostCommentLike.PARAM_POST_COMMENT_LIKE_ID), SearchBy.ID);
			url += "#post-comment-likes-title";
			break;
			
		case "com.fullvicie.pojos.PostLike":
			et = (new PostLikeSqlDao()).pseudoDelete(request.getParameter(PostLike.PARAM_POST_LIKE_ID), SearchBy.ID);
			url += "#post-likes-title";
			break;
			
		case "com.fullvicie.pojos.Profile":
			et = (new ProfileSqlDao()).pseudoDelete(request.getParameter(Profile.PARAM_PROFILE_ID), SearchBy.ID);
			url += "#profiles-title";
			break;
			
		case "com.fullvicie.pojos.Report":
			et = (new ReportSqlDao()).pseudoDelete(request.getParameter(Report.PARAM_REPORT_ID), SearchBy.ID);
			url += "#reports-title";
			break;
		
		case "com.fullvicie.pojos.User":
			et = (new UserSqlDao()).pseudoDelete(request.getParameter(User.PARAM_USER_ID), SearchBy.ID);
			url += "#users-title";
			break;
			
		default:
			
			break;
		}
		
		if(et != ErrorType.NO_ERROR)
			url = request.getContextPath()+"/mod/error.jsp?ERROR_TYPE=" + et;
		
		return url;
		
	}
		
}
