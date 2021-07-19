package com.fullvicie.pojos;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class ForumMessageLike implements IPojo{

	/*
	 * Static Attributes
	 */
	public static final String PARAM_FORUM_MESSAGE_LIKE_ID = "PARAM_FORUM_MESSAGE_LIKE_ID", PARAM_FORUM_MESSAGE_LIKE_DISLIKE = "PARAM_FORUM_MESSAGE_LIKE_DISLIKE",
			PARAM_FORUM_MESSAGE_LIKE_USER_ID = "PARAM_FORUM_MESSAGE_LIKE_USER_ID", PARAM_FORUM_MESSAGE_LIKE_FORUM_MESSAGE_ID = "PARAM_FORUM_MESSAGE_LIKE_FORUM_MESSAGE_ID";

	
	/*
	 * Attributes
	 */
	private int id, userId, forumMessageId;
	private boolean dislike;
	
	
	/*
	 * Constructors
	 */
	public ForumMessageLike() {
		
	}
	
	public ForumMessageLike(HttpServletRequest request) {
		try{this.id = Integer.parseInt(request.getParameter(PARAM_FORUM_MESSAGE_LIKE_ID));}
		catch(Exception t) {this.id = -1;}
		
		this.dislike = request.getParameter(PARAM_FORUM_MESSAGE_LIKE_DISLIKE)!=null ? true : false;
		
		try{this.userId = Integer.parseInt(request.getParameter(PARAM_FORUM_MESSAGE_LIKE_USER_ID));}
		catch(Exception t) {this.id = -1;}
		
		try{this.forumMessageId = Integer.parseInt(request.getParameter(PARAM_FORUM_MESSAGE_LIKE_FORUM_MESSAGE_ID));}
		catch(Exception t) {this.id = -1;}
	}
	
	
	/*
	 * Methods
	 */
	@Override
	public String toJavaScriptFunction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject toJSONObject() {
		// TODO Auto-generated method stub
		return null;
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

	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	
	public int getForumMessageId() {
		return forumMessageId;
	}
	public void setForumMessageId(int forumMessageId) {
		this.forumMessageId = forumMessageId;
	}

	
	public boolean isDislike() {
		return dislike;
	}
	public void setDislike(boolean dislike) {
		this.dislike = dislike;
	}
	
}
