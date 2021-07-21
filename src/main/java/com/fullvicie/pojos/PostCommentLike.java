package com.fullvicie.pojos;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class PostCommentLike implements IPojo{

	/*
	 * Static Attributes
	 */
	public static final String PARAM_POST_COMMENT_LIKE_ID = "PARAM_POST_COMMENT_LIKE_ID", PARAM_POST_COMMENT_LIKE_DISLIKE = "PARAM_POST_COMMENT_LIKE_DISLIKE",
			PARAM_POST_COMMENT_LIKE_POST_COMMENT_ID = "PARAM-POST_COMMENT_LIKE_POST_COMMENT_ID", PARAM_POST_COMMENT_LIKE_USER_ID = "PARAM_POST_COMMENT_LIKE_USER_ID";

	public static final String ATTR_POST_COMMENT_LIKE_OBJ = "ATTR_POST_COMMENT_LIKE_OBJ";
	
	
	/*
	 * Attributes
	 */
	private int id, postCommentId, userId;
	private boolean dislike;
	
	
	/*
	 * Constructors
	 */
	public PostCommentLike(HttpServletRequest request) {
		try{this.id = Integer.parseInt(request.getParameter(PARAM_POST_COMMENT_LIKE_ID));}
		catch(Exception t) {this.id = -1;}
		
		this.dislike = request.getParameter(PARAM_POST_COMMENT_LIKE_DISLIKE)!=null ? true : false;
		
		try{this.postCommentId = Integer.parseInt(request.getParameter(PARAM_POST_COMMENT_LIKE_POST_COMMENT_ID));}
		catch(Exception t) {this.id = -1;}
		
		try{this.userId = Integer.parseInt(request.getParameter(PARAM_POST_COMMENT_LIKE_USER_ID));}
		catch(Exception t) {this.id = -1;}
	}
	
	
	/*
	 * Methods
	 */
	@Override
	public String toJavaScriptFunction() {
		return "'" + this.id + "', '" + this.dislike + "', '" + this.postCommentId + "', '" + this.userId + "'";
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jObject = new JSONObject();
		
		jObject.put("id", id);
		jObject.put("dislike", this.dislike);
		jObject.put("postCommentId", this.postCommentId);
		jObject.put("userId", this.userId);
		
		return jObject;
	}

	
	/*
	 * Getters & Setters
	 */
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public int getPostCommentId() {
		return postCommentId;
	}
	public void setPostCommentId(int postCommentId) {
		this.postCommentId = postCommentId;
	}

	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}


	public boolean isDislike() {
		return dislike;
	}
	public void setDislike(boolean dislike) {
		this.dislike = dislike;
	}
	
	

}
