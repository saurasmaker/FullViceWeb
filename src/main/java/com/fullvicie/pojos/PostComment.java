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
			PARAM_POST_COMMENT_LAST_EDIT_DATE = "PARAM_POST_COMMENT_LAST_EDIT_DATE", PARAM_POST_COMMENT_LAST_EDIT_TIME = "PARAM_POST_COMMENT_LAST_EDIT_TIME",
			PARAM_POST_COMMENT_POST_ID = "PARAM_POST_COMMENT_POST_ID", PARAM_POST_COMMENT_USER_ID = "PARAM_POST_COMMENT_USER_ID";

	public static final String ATTR_POST_COMMENT_OBJ = "ATTR_POST_COMMENT_OBJ";
	
	
	/*
	 * Attributes
	 */
	private int id, likes, dislikes, postId, userId;
	private String message;
	private Date madeDate, lastEditDate;
	private Time madeTime, lastEditTime;
	
	
	
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
	
}
