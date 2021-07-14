package com.fullvicie.pojos;

import java.sql.Date;
import java.sql.Time;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class Forum implements IPojo{

	/*
	 * Static Attributes
	 */
	public static final String PARAM_FORUM_ID = "PARAM_FORUM_ID", PARAM_FORUM_NAME = "PARAM_FORUM_NAME", PARAM_FORUM_DESCRIPTION = "PARAM_FORUM_DESCRIPTION",
			PARAM_FORUM_TAGS = "PARAM_FORUM_TAGS", PARAM_FORUM_CREATION_DATE = "PARAM_FORUM_CREATION_DATE",
			PARAM_FORUM_CREATION_TIME = "PARAM_FORUM_CREATION_TIME", PARAM_FORUM_LATEST_ANSWER_DATE = "PARAM_FORUM_LATEST_ANSWER_DATE",
			PARAM_FORUM_LATEST_ANSWER_TIME = "PARAM_FORUM_LATEST_ANSWER_TIME", PARAM_FORUM_DELETE_DATE = "PARAM_FORUM_DELETE_DATE",
			PARAM_FORUM_DELETE_TIME = "PARAM_FORUM_DELETE_TIME", PARAM_FORUM_FORUM_CATEGORY_ID = "PARAM_FORUM_FORUM_CATEGORY_ID",
			PARAM_FORUM_USER_ID = "PARAM_FORUM_USER_ID";

	
	/*
	 * Attributes
	 */
	private String id, name, description, tags, forumCategoryId, userId;
	private Date creationDate, latestAnswerDate, deleteDate;
	private Time creationTime, latestAnswerTime, deleteTime;
	
	
	/*
	 * Constructors
	 */
	public Forum() {
		
	}
	
	public Forum(HttpServletRequest request) {
		this.id = request.getParameter(PARAM_FORUM_ID);
		this.name = request.getParameter(PARAM_FORUM_NAME);
		this.description = request.getParameter(PARAM_FORUM_DESCRIPTION);
		this.tags = request.getParameter(PARAM_FORUM_TAGS);
		
		try{this.creationDate = Date.valueOf(request.getParameter(PARAM_FORUM_CREATION_DATE));}
		catch(Exception t) {this.creationDate = Date.valueOf(LocalDate.now());}
		
		try{this.creationTime = Time.valueOf(request.getParameter(PARAM_FORUM_CREATION_TIME));}
		catch(Exception t) {this.creationTime = Time.valueOf(LocalTime.now());}
		
		try{this.latestAnswerDate = Date.valueOf(request.getParameter(PARAM_FORUM_LATEST_ANSWER_DATE));}
		catch(Exception t) {this.latestAnswerDate = Date.valueOf(LocalDate.now());}
		
		try{this.latestAnswerTime = Time.valueOf(request.getParameter(PARAM_FORUM_LATEST_ANSWER_TIME));}
		catch(Exception t) {this.latestAnswerTime = Time.valueOf(LocalTime.now());}
		
		try{this.deleteDate = Date.valueOf(request.getParameter(PARAM_FORUM_DELETE_DATE));}
		catch(Exception t) {this.deleteDate = Date.valueOf(LocalDate.now());}
		
		try{this.deleteTime = Time.valueOf(request.getParameter(PARAM_FORUM_DELETE_TIME));}
		catch(Exception t) {this.deleteTime = Time.valueOf(LocalTime.now());}
		
		this.forumCategoryId = request.getParameter(PARAM_FORUM_FORUM_CATEGORY_ID);
		this.userId = request.getParameter(PARAM_FORUM_USER_ID);
	}
	
	
	/*
	 * Methods
	 */
	@Override
	public String toJavaScriptFunction() {
		return "'" + this.id + "', '" + this.name + "', '" + this.description + "', '" + this.tags + "', '"  + 
				this.creationDate + "', '" + this.creationTime + "', '" + this.latestAnswerDate + "', '" + 
				this.latestAnswerTime + "', '" + this.deleteDate + "', '" + this.deleteTime + "'";
	}

	@Override
	public JSONObject toJSONObject() {

		JSONObject jObject = new JSONObject();
		
		jObject.put("id", id);
		jObject.put("name", this.name);
		jObject.put("description", this.description);
		jObject.put("tags", this.tags);
		jObject.put("creation_date", this.creationDate);
		jObject.put("creation_time", this.creationTime);
		jObject.put("latest_answer_date", this.latestAnswerDate);
		jObject.put("latest_answer_time", this.latestAnswerTime);
		jObject.put("delete_date", this.deleteDate);
		jObject.put("delete_time", this.deleteTime);
		
		return jObject;
		
	}

	
	/*
	 * Getters & Setters
	 */
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}

	
	public String getForumCategoryId() {
		return forumCategoryId;
	}
	public void setForumCategoryId(String forumCategoryId) {
		this.forumCategoryId = forumCategoryId;
	}

	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Date getLatestAnswerDate() {
		return latestAnswerDate;
	}
	public void setLatestAnswerDate(Date latestAnswerDate) {
		this.latestAnswerDate = latestAnswerDate;
	}

	
	public Date getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	
	public Time getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Time creationTime) {
		this.creationTime = creationTime;
	}

	
	public Time getLatestAnswerTime() {
		return latestAnswerTime;
	}
	public void setLatestAnswerTime(Time latestAnswerTime) {
		this.latestAnswerTime = latestAnswerTime;
	}

	
	public Time getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Time deleteTime) {
		this.deleteTime = deleteTime;
	}

}
