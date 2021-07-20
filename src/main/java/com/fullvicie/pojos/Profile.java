package com.fullvicie.pojos;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class Profile implements IPojo{

	/*
	 * Static Attributes
	 */
	public static final String PARAM_PROFILE_ID = "PARAM_PROFILE_ID", PARAM_PROFILE_NAME = "PARAM_PROFILE_NAME", PARAM_PROFILE_SURNAMES = "PARAM_PROFILE_SURNAMES";
	
	public static final String PARAM_PROFILE_OBJ = "PARAM_PROFILE_OBJ";

	
	/*
	 * Attributes
	 */
	private int id, userId;
	private String name, surnames, biography;
	private Date birthday;
	
	/*
	 * Constructors
	 */
	public Profile() {
		
	}
	
	public Profile(HttpServletRequest request) {
		
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
	
	
	

}
