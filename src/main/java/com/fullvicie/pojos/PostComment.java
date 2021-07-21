package com.fullvicie.pojos;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class PostComment implements IPojo{

	/*
	 * Static Attributes
	 */
	public static final String PARAM_POST_COMMENT_ID = "PARAM_POST_COMMENT_ID", PARAM_POST_COMMENT_MESSAGE = "PARAM_POST_COMMENT_MESSAGE",
			PARAM_POST_COMMENT_LIKES = "PARAM_POST_COMMENT_LIKES", PARAM_POST_COMMENT_DISLIKES = "PARAM_POST_COMMENT_DISLIKES",
			PARAM_POST_COMMENT_MADE_DATE  = "PARAM_POST_COMMENT_MADE_DATE", PARAM_POST_COMMENT_MADE_TIME = "PARAM_POST_COMMENT_MADE_TIME",
			PARAM_POST_COMMENT_DELETED = "PARAM_POST_COMMENT_DELETED", PARAM_POST_COMMENT_DELETE_DATE = "PARAM_POST_COMMENT_DELETE_DATE",
			PARAM_POST_COMMENT_DELETE_TIME = "PARAM_POST_COMMENT_DELETE_TIME", PARAM_POST_COMMENT_LAST_EDIT_DATE = "PARAM_POST_COMMENT_LAST_EDIT_DATE",
			PARAM_POST_COMMENT_LAST_EDIT_TIME = "PARAM_POST_COMMENT_LAST_EDIT_TIME", PARAM_POST_COMMENT_POST_ID = "PARAM_POST_COMMENT_POST_ID",
			PARAM_POST_COMMENT_USER_ID = "PARAM_POST_COMMENT_USER_ID";

	public static final String ATTR_POST_COMMENT_OBJ = "ATTR_POST_COMMENT_OBJ";
	
	
	/*
	 * Attributes
	 */
	private int id, likes, dislikes, postId, userId;
	private String message;
	private boolean deleted;
	private Date madeDate, lastEditDate, deleteDate;
	private Time madeTime, lastEditTime, deleteTime;
	
	
	
	/*
	 * Constructors
	 */
	public PostComment() {
		
	}
	public PostComment(HttpServletRequest request) {
		try{this.id = Integer.parseInt(request.getParameter(PARAM_POST_COMMENT_ID));}
		catch(Exception t) {this.id = -1;}
		
		this.message = request.getParameter(PARAM_POST_COMMENT_MESSAGE);
		
		try{this.likes = Integer.parseInt(request.getParameter(PARAM_POST_COMMENT_LIKES));}
		catch(Exception t) {this.id = 0;}
		
		try{this.dislikes = Integer.parseInt(request.getParameter(PARAM_POST_COMMENT_DISLIKES));}
		catch(Exception t) {this.id = 0;}
		
		try{this.madeDate = Date.valueOf(request.getParameter(PARAM_POST_COMMENT_MADE_DATE));}
		catch(Exception t) {this.madeDate = Date.valueOf(LocalDate.now());}
		
		try{this.madeTime = Time.valueOf(request.getParameter(PARAM_POST_COMMENT_MADE_TIME));}
		catch(Exception t) {this.madeTime = Time.valueOf(LocalTime.now());}
		
		this.deleted = request.getParameter(PARAM_POST_COMMENT_DELETED) != null ? true : false;
		
		try{this.deleteDate = Date.valueOf(request.getParameter(PARAM_POST_COMMENT_DELETE_DATE));}
		catch(Exception t) {this.deleteDate = Date.valueOf(LocalDate.now());}
		
		try{this.deleteTime = Time.valueOf(request.getParameter(PARAM_POST_COMMENT_DELETE_TIME));}
		catch(Exception t) {this.deleteTime = Time.valueOf(LocalTime.now());}
		
		try{this.lastEditDate = Date.valueOf(request.getParameter(PARAM_POST_COMMENT_LAST_EDIT_DATE));}
		catch(Exception t) {this.lastEditDate = Date.valueOf(LocalDate.now());}
		
		try{this.lastEditTime = Time.valueOf(request.getParameter(PARAM_POST_COMMENT_LAST_EDIT_TIME));}
		catch(Exception t) {this.lastEditTime = Time.valueOf(LocalTime.now());}
		
		try{this.postId = Integer.parseInt(request.getParameter(PARAM_POST_COMMENT_POST_ID));}
		catch(Exception t) {this.id = -1;}
		
		try{this.userId = Integer.parseInt(request.getParameter(PARAM_POST_COMMENT_USER_ID));}
		catch(Exception t) {this.id = -1;}
	}
	
	
	
	/*
	 * Methods
	 */
	@Override
	public String toJavaScriptFunction() {
		return "'" + this.id + "', '" + this.message + "', '" + this.likes + "', '" + this.dislikes + "', '" + this.madeDate 
			+ "', '" + this.madeTime + "', '" + this.deleted  + "', '" + this.deleteDate  + "', '" + this.deleteTime
			+ "', '" + this.lastEditDate + "', '" + this.lastEditDate + "', '" + this.lastEditTime  + "', '" + this.postId
			+ "', '" + this.userId + "'";
	}

	@Override
	public JSONObject toJSONObject() {
		
		JSONObject jObject = new JSONObject();
		
		jObject.put("id", id);
		jObject.put("message", this.message);
		jObject.put("likes", this.likes);
		jObject.put("dislikes", this.dislikes);
		jObject.put("madeDate", this.madeDate);
		jObject.put("madeTime", this.madeTime);
		jObject.put("lastEditTime", this.deleted);
		jObject.put("deleteDate", this.deleteDate);
		jObject.put("deleteTime", this.deleteTime);
		jObject.put("lastEditDate", this.lastEditDate);
		jObject.put("lastEditTime", this.lastEditTime);
		jObject.put("postId", this.postId);
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

	
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}

	
	public int getDislikes() {
		return dislikes;
	}
	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
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

	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
	public Date getMadeDate() {
		return madeDate;
	}
	public void setMadeDate(Date madeDate) {
		this.madeDate = madeDate;
	}

	
	public Date getLastEditDate() {
		return lastEditDate;
	}
	public void setLastEditDate(Date lastEditDate) {
		this.lastEditDate = lastEditDate;
	}

	
	public Time getMadeTime() {
		return madeTime;
	}
	public void setMadeTime(Time madeTime) {
		this.madeTime = madeTime;
	}

	
	public Time getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Time lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	
	
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	public Date getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}
	
	
	public Time getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Time deleteTime) {
		this.deleteTime = deleteTime;
	}
	
}
