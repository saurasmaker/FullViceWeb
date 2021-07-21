package com.fullvicie.pojos;

import java.sql.Date;
import java.sql.Time;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class ForumMessage implements IPojo{

	/*
	 * Static Attributes
	 */
	public static final String PARAM_FORUM_MESSGAE_ID = "PARAM_FORUM_MESSGAE_ID", PARAM_FORUM_MESSAGE_MESSAGE = "PARAM_FORUM_MESSAGE_MESSAGE",
			PARAM_FORUM_MESSAGE_MADE_DATE = "PARAM_FORUM_MESSAGE_MADE_DATE", PARAM_FORUM_MESSAGE_MADE_TIME = "PARAM_FORUM_MESSAGE_MADE_TIME",
			PARAM_FORUM_MESSAGE_LAST_EDIT_DATE = "PARAM_FORUM_MESSAGE_LAST_EDIT_DATE", PARAM_FORUM_MESSAGE_LAST_EDIT_TIME = "PARAM_FORUM_MESSAGE_LAST_EDIT_TIME",
			PARAM_FORUM_MESSAGE_DELETE_DATE = "PARAM_FORUM_MESSAGE_DELETE_DATE", PARAM_FORUM_MESSAGE_DELETE_TIME = "PARAM_FORUM_MESSAGE_DELETE_TIME",
			PARAM_FORUM_MESSAGE_DELETED = "PARAM_FORUM_MESSAGE_DELETED";

	public static final String ATTR_FORUM_MESSAGE_OBJ = "ATTR_FORUM_MESSAGE_OBJ";
	
	
	/*
	 * Attributes
	 */
	private int id, forumId, userId;
	private String  message;
	private Date madeDate, lastEditDate, deleteDate;
	private boolean deleted;
	private Time madeTime, lastEditTime, deleteTime;
	private int likes, dislikes;
	
	
	/*
	 * Constructors
	 */
	public ForumMessage() {
		
	}
	
	public ForumMessage(HttpServletRequest request) {
		try{this.id = Integer.parseInt(request.getParameter(PARAM_FORUM_MESSGAE_ID));}
		catch(Exception t) {this.id = -1;}
		
		this.message = request.getParameter(PARAM_FORUM_MESSAGE_MESSAGE);
		
		try {}
		catch(Exception e) {}
	}
	
	
	/*
	 * Methods
	 */
	@Override
	public String toJavaScriptFunction() {
		return "'" + this.id + "', '" + this.message + "', '" + this.madeDate + "', '" + this.madeTime
				+ "', '" + this.lastEditDate + "', '" + this.lastEditTime + "', '" + this.deleted
				+ "', '" + this.deleteDate + "', '" + this.deleteTime + "', '" + this.likes
				+ "', '" + this.dislikes + "', '" + this.forumId + "', '" + this.userId + "'";
	}

	@Override
	public JSONObject toJSONObject() {
		
		JSONObject jObject = new JSONObject();
		
		jObject.put("id", id);
		jObject.put("message", this.message);
		jObject.put("madeDate", this.madeDate);
		jObject.put("madeTime", this.madeTime);
		jObject.put("lastEditDate", this.lastEditDate);
		jObject.put("lastEditTime", this.lastEditTime);
		jObject.put("deleted", this.deleted);
		jObject.put("deleteDate", this.deleteDate);
		jObject.put("deleteTime", this.deleteTime);
		jObject.put("likes", this.likes);
		jObject.put("dislikes", this.dislikes);
		jObject.put("forumId", this.forumId);
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

	
	public int getForumId() {
		return forumId;
	}
	public void setForumId(int forumId) {
		this.forumId = forumId;
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

	
	public Date getLasEditDate() {
		return lastEditDate;
	}
	public void setLastEditDate(Date lastEditDate) {
		this.lastEditDate = lastEditDate;
	}

	
	public Date getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
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

	
	public Time getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Time deleteTime) {
		this.deleteTime = deleteTime;
	}

	
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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
	
}
