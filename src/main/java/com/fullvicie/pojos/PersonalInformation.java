package com.fullvicie.pojos;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class PersonalInformation implements IPojo{

	/*
	 * Static Attributes
	 */
	public static final String PARAM_PERSONAL_INFORMATION_ID = "PARAM_PERSONAL_INFORMATION_ID", PARAM_PERSONAL_INFORMATION_NAME = "PARAM_PERSONAL_INFORMATION_NAME", PARAM_PERSONAL_INFORMATION_SURNAMES = "PARAM_PERSONAL_INFORMATION_SURNAMES",
			PARAM_PERSONAL_INFORMATION_BIOGRAPHY = "PARAM_PERSONAL_INFORMATION_BIOGRAPHY", PARAM_PERSONAL_INFORMATION_BIRTHDAY = "PARAM_PERSONAL_INFORMATION_BIRTHDAY",
			PARAM_PERSONAL_INFORMATION_ADDRESS = "PARAM_PERSONAL_INFORMATION_ADDRESS", PARAM_PERSONAL_INFORMATION_USER_ID = "PARAM_PERSONAL_INFORMATION_USER_ID";
	
	public static final String ATTR_PERSONAL_INFORMATION_OBJ = "ATTR_PERSONAL_INFORMATION_OBJ";

	
	/*
	 * Attributes
	 */
	private int id, userId;
	private String name, surnames, biography, address;
	private Date birthday;
	
	
	/*
	 * Constructors
	 */
	public PersonalInformation() {
		
	}
	
	public PersonalInformation(HttpServletRequest request) {
		try{this.id = Integer.parseInt(request.getParameter(PARAM_PERSONAL_INFORMATION_ID));}
		catch(Exception t) {this.id = -1;}
		
		this.name = request.getParameter(PARAM_PERSONAL_INFORMATION_NAME);
		this.surnames = request.getParameter(PARAM_PERSONAL_INFORMATION_SURNAMES);
		this.biography = request.getParameter(PARAM_PERSONAL_INFORMATION_BIOGRAPHY);
		this.address = request.getParameter(PARAM_PERSONAL_INFORMATION_ADDRESS);
		
		try{ this.birthday = Date.valueOf(request.getParameter(PARAM_PERSONAL_INFORMATION_BIRTHDAY)); }
		catch(Exception t) { this.birthday = null; }
		
		try{this.userId = Integer.parseInt(request.getParameter(PARAM_PERSONAL_INFORMATION_USER_ID));}
		catch(Exception t) {this.userId = -1;}
		
	}
	
	
	/*
	 * Methods
	 */
	@Override
	public JSONObject toJSONObject() {
		
		JSONObject jObject = new JSONObject();
		
		jObject.put("id", id);
		jObject.put("name", this.name);
		jObject.put("surnames", this.surnames);
		jObject.put("biography", this.biography);
		jObject.put("birthday", this.birthday);
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

	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
