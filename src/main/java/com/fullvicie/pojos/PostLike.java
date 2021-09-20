package com.fullvicie.pojos;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class PostLike implements IPojo{

	/*
	 * Static Attributes
	 */
	public static final String PARAM_POST_LIKE_ID = "PARAM_POST_LIKE_ID", PARAM_POST_LIKE_DISLIKE = "PARAM_POST_LIKE_DISLIKE", 
			PARAM_POST_LIKE_POST_ID = "PARAM_POST_LIKE_POST_ID", PARAM_POST_LIKE_USER_ID = "PARAM_POST_LIKE_USER_ID";

	public static final String ATTR_POST_LIKE_OBJ = "ATTR_POST_LIKE_OBJ";
	
	
	/*
	 * Attributes
	 */
	private int id, postId, userId;
	private boolean dislike;
	
	
	/*
	 * Constructors
	 */
	public PostLike() {
		
	}
	
	public PostLike(HttpServletRequest request) {
		try{this.id = Integer.parseInt(request.getParameter(PARAM_POST_LIKE_ID));}
		catch(Exception t) {this.id = -1;}
		
		this.dislike = request.getParameter(PARAM_POST_LIKE_DISLIKE)!=null ? true : false;
		
		try{this.userId = Integer.parseInt(request.getParameter(PARAM_POST_LIKE_USER_ID));}
		catch(Exception t) {this.id = -1;}
		
		try{this.postId = Integer.parseInt(request.getParameter(PARAM_POST_LIKE_POST_ID));}
		catch(Exception t) {this.id = -1;}
	}
	
	
	/*
	 * Methods
	 */
	@Override
	public JSONObject toJSONObject() {
		JSONObject jObject = new JSONObject();
		
		jObject.put("id", id);
		jObject.put("dislike", this.dislike);
		jObject.put("userId", this.userId);
		jObject.put("postId", this.postId);
		
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

	
	public int getPostId() {
		return postId;
	}
	public void setPostId(int postId) {
		this.postId = postId;
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
