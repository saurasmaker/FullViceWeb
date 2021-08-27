package com.fullvicie.pojos;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class Profile implements IPojo{

	/*
	 * Static Attributes
	 */
	public static final String PARAM_PROFILE_ID = "PARAM_PROFILE_ID", PARAM_PROFILE_NAME = "PARAM_PROFILE_NAME", PARAM_PROFILE_SURNAMES = "PARAM_PROFILE_SURNAMES",
			PARAM_PROFILE_BIOGRAPHY = "PARAM_PROFILE_BIOGRAPHY", PARAM_PROFILE_BIRTHDAY = "PARAM_PROFILE_BIRTHDAY", PARAM_PROFILE_BASE64_PICTURE = "PARAM_PROFILE_BASE64_PICTURE",
			PARAM_PROFILE_USER_ID = "PARAM_PROFILE_USER_ID";
	
	public static final String ATTR_PROFILE_OBJ = "ATTR_PROFILE_OBJ";

	
	/*
	 * Attributes
	 */
	private int id, userId;
	private String name, surnames, biography, base64Picture;
	private Date birthday;
	
	
	/*
	 * Constructors
	 */
	public Profile() {
		
	}
	
	public Profile(HttpServletRequest request) {
		try{this.id = Integer.parseInt(request.getParameter(PARAM_PROFILE_ID));}
		catch(Exception t) {this.id = -1;}
		
		this.name = request.getParameter(PARAM_PROFILE_NAME);
		this.surnames = request.getParameter(PARAM_PROFILE_SURNAMES);
		this.biography = request.getParameter(PARAM_PROFILE_BIOGRAPHY);
		
		try{this.userId = Integer.parseInt(request.getParameter(PARAM_PROFILE_USER_ID));}
		catch(Exception t) {this.userId = -1;}
		
	}
	
	
	/*
	 * Methods
	 */
	@Override
	public String toJavaScriptFunction() {
		return "'" + this.id + "', '" + this.name + "', '" + this.surnames + "', '" + this.biography
			+ "', '" + this.birthday  + "', '" + this.base64Picture  + "', '" + this.userId + "'";
	}

	@Override
	public JSONObject toJSONObject() {
		
		JSONObject jObject = new JSONObject();
		
		jObject.put("id", id);
		jObject.put("name", this.name);
		jObject.put("surnames", this.surnames);
		jObject.put("biography", this.biography);
		jObject.put("birthday", this.birthday);
		jObject.put("base64Picture", this.base64Picture);
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


	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getSurnames() {
		return surnames;
	}
	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}


	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}


	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	
	public String getPicture() {
		return base64Picture;
	}
	public void setPicture(String base64Picture) {
		this.base64Picture = base64Picture;
	}
	
	
	

}
