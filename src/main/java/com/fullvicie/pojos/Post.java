package com.fullvicie.pojos;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class Post implements IPojo{

	/*
	 * Static Attributes
	 */
	public static final String PARAM_POST_ID = "PARAM_POST_ID", PARAM_POST_NAME = "PARAM_POST_NAME", PARAM_POST_DESCRIPTION = "PARAM_POST_DESCRIPTION",
			PARAM_POST_TAGS = "PARAM_POST_TAGS", PARAM_POST_CREATION_DATE = "PARAM_POST_CREATION_DATE", PARAM_POST_CREATION_TIME = "PARAM_POST_CREATION_TIME",
			PARAM_POST_DELETED = "PARAM_POST_DELETED", PARAM_POST_DELETE_DATE = "PARAM_POST_DELETE_DATE", PARAM_POST_DELETE_TIME = "PARAM_POST_DELETE_TIME",
			PARAM_POST_LIKES = "PARAM_POST_LIKES", PARAM_POST_DISLIKES = "PARAM_POST_DISLIKES", PARAM_POST_USER_ID = "PARAM_POST_USER_ID",
			PARAM_POST_POST_CATEGORY_ID = "PARAM_POST_POST_CATEGORY_ID";

	public static final String ATTR_POST_OBJECT = "ATTR_POST_OBJECT";
	
	
	/*
	 * Attributes
	 */
	private int id, likes, dislikes, userId, postCategoryId;
	private String name, description, tags;
	private Date creationDate;
	private Time creationTime;
	
	
	
	/*
	 * Constructors
	 */
	public Post() {
		
	}
	
	public Post(HttpServletRequest request) {
		try{this.id = Integer.parseInt(request.getParameter(PARAM_POST_ID));}
		catch(Exception t) {this.id = -1;}
		
		this.name = request.getParameter(PARAM_POST_NAME);
		this.description = request.getParameter(PARAM_POST_DESCRIPTION);
		this.tags = request.getParameter(PARAM_POST_TAGS);

		try{this.creationDate = Date.valueOf(request.getParameter(PARAM_POST_CREATION_DATE));}
		catch(Exception t) {this.creationDate = Date.valueOf(LocalDate.now());}
		
		try{this.creationTime = Time.valueOf(request.getParameter(PARAM_POST_CREATION_TIME));}
		catch(Exception t) {this.creationTime = Time.valueOf(LocalTime.now());}

		try{this.likes = Integer.parseInt(request.getParameter(PARAM_POST_LIKES));}
		catch(Exception t) {this.id = 0;}
		
		try{this.dislikes = Integer.parseInt(request.getParameter(PARAM_POST_DISLIKES));}
		catch(Exception t) {this.id = 0;}
		
		try{this.userId = Integer.parseInt(request.getParameter(PARAM_POST_USER_ID));}
		catch(Exception t) {this.id = -1;}
		
		try{this.postCategoryId = Integer.parseInt(request.getParameter(PARAM_POST_POST_CATEGORY_ID));}
		catch(Exception t) {this.id = -1;}
		
	}
	
	
	/*
	 * Methods
	 */
	@Override
	public JSONObject toJSONObject() {
		
		JSONObject jObject = new JSONObject();
		
		jObject.put("id", id);
		jObject.put("name", this.name);
		jObject.put("description", this.description);
		jObject.put("tags", this.tags);
		jObject.put("creationDate", this.creationDate);
		jObject.put("creationTime", this.creationTime);
		jObject.put("likes", this.likes);
		jObject.put("dislikes", this.dislikes);
		jObject.put("userId", this.userId);
		jObject.put("postCategoryId", this.postCategoryId);
		
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

	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	
	public int getPostCategoryId() {
		return postCategoryId;
	}
	public void setPostCategoryId(int postCategoryId) {
		this.postCategoryId = postCategoryId;
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

	
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	
	public Time getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Time creationTime) {
		this.creationTime = creationTime;
	}

}
