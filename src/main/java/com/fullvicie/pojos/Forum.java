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
			PARAM_FORUM_LATEST_ANSWER_TIME = "PARAM_FORUM_LATEST_ANSWER_TIME", PARAM_FORUM_DELETED = "PARAM_FORUM_DELETED",
			PARAM_FORUM_DELETE_DATE = "PARAM_FORUM_DELETE_DATE", PARAM_FORUM_DELETE_TIME = "PARAM_FORUM_DELETE_TIME",
			PARAM_FORUM_FORUM_CATEGORY_ID = "PARAM_FORUM_FORUM_CATEGORY_ID", PARAM_FORUM_USER_ID = "PARAM_FORUM_USER_ID";

	public static final String ATTR_FORUM_OBJ = "ATTR_FORUM_OBJ";
	
	
	/*
	 * Attributes
	 */
	private int id, forumCategoryId, userId;
	private String name, description, tags;
	private boolean deleted;
	private Date creationDate, latestAnswerDate, deleteDate;
	private Time creationTime, latestAnswerTime, deleteTime;
	
	
	/*
	 * Constructors
	 */
	public Forum() {
		
	}
	
	public Forum(HttpServletRequest request) {
		try{this.id = Integer.parseInt(request.getParameter(PARAM_FORUM_ID));}
		catch(Exception t) {this.id = -1;}
		
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
		
		this.deleted = request.getParameter(PARAM_FORUM_DELETED) != null ? true : false;
		
		try{this.deleteDate = Date.valueOf(request.getParameter(PARAM_FORUM_DELETE_DATE));}
		catch(Exception t) {this.deleteDate = Date.valueOf(LocalDate.now());}
		
		try{this.deleteTime = Time.valueOf(request.getParameter(PARAM_FORUM_DELETE_TIME));}
		catch(Exception t) {this.deleteTime = Time.valueOf(LocalTime.now());}
		
		try{this.forumCategoryId = Integer.parseInt(request.getParameter(PARAM_FORUM_FORUM_CATEGORY_ID));}
		catch(Exception t) {this.id = -1;}
		
		try{this.userId = Integer.parseInt(request.getParameter(PARAM_FORUM_USER_ID));}
		catch(Exception t) {this.id = -1;}
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
		jObject.put("creationDate", this.creationDate);
		jObject.put("creationTime", this.creationTime);
		jObject.put("latestAnswerDate", this.latestAnswerDate);
		jObject.put("latestAnswerTime", this.latestAnswerTime);
		jObject.put("deleted", deleted);
		jObject.put("delete_date", this.deleteDate);
		jObject.put("delete_time", this.deleteTime);
		
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

	
	public int getForumCategoryId() {
		return forumCategoryId;
	}
	public void setForumCategoryId(int forumCategoryId) {
		this.forumCategoryId = forumCategoryId;
	}

	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
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
