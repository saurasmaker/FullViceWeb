package com.fullvicie.pojos;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class User implements IPojo{
	
	
	/*
	 * Static Attributes
	 */
	public static final String PARAM_USER = "PARAM_USER", PARAM_USER_ID = "PARAM_USER_ID", PARAM_USER_USERNAME = "PARAM_USER_USERNAME",
			PARAM_USER_EMAIL = "PARAM_USER_EMAIL", PARAM_USER_PASSWORD = "PARAM_USER_PASSWORD", PARAM_USER_REPEATPASSWORD="PARAM_USER_REPEATPASSWORD",
			PARAM_USER_SIGNUPDATE = "PARAM_USER_SIGNUPDATE", PARAM_USER_SIGNUPTIME = "PARAM_USER_SIGNUPTIME",
			PARAM_USER_LASTLOGOUTDATE = "PARAM_USER_LASTLOGOUTDATE", PARAM_USER_LASTLOGOUTTIME = "PARAM_USER_LASTLOGOUTTIME",
			PARAM_USER_ISADMIN = "PARAM_USER_ISADMIN", PARAM_USER_ISMODERATOR="PARAM_USER_ISMODERATOR";
	
	public static final String ATR_USERS_LIST = "ATR_USERS_LIST", ATR_USER_LOGGED = "ATR_USER_LOGGED";
		
	
	
	/*
	 * Attributes
	 */
	private String id, username, email, passwrd;
	private Date lastLogoutDate, signUpDate;
	private Time lastLogoutTime, signUpTime;
	private Boolean moderator, admin;
	
	
	/*
	 * Constructors
	 */
	public User() {
		
	}
	
	public User(HttpServletRequest request) {
		this.id = request.getParameter(PARAM_USER_ID);
		this.username = request.getParameter(PARAM_USER_USERNAME);
		this.email = request.getParameter(PARAM_USER_EMAIL);
		this.passwrd = request.getParameter(PARAM_USER_PASSWORD);
		
		try{this.signUpDate = Date.valueOf(request.getParameter(PARAM_USER_SIGNUPDATE));}
		catch(Exception t) {this.signUpDate = Date.valueOf(LocalDate.now());}
		
		try{this.signUpTime = Time.valueOf(request.getParameter(PARAM_USER_SIGNUPTIME));}
		catch(Exception t) {this.signUpTime = Time.valueOf(LocalTime.now());}
		
		try{this.lastLogoutDate = Date.valueOf(request.getParameter(PARAM_USER_LASTLOGOUTDATE));}
		catch(Exception t) {this.lastLogoutDate = Date.valueOf(LocalDate.now());}
		
		try{this.lastLogoutTime = Time.valueOf(request.getParameter(PARAM_USER_LASTLOGOUTTIME));}
		catch(Exception t) {this.lastLogoutTime = Time.valueOf(LocalTime.now());}

		this.admin = request.getParameter(PARAM_USER_ISADMIN)!=null ? true : false;
		this.moderator = request.getParameter(PARAM_USER_ISMODERATOR)!=null ? true : false;
	}
	
	
	
	/*
	 * Methods
	 */
	@Override
	public String toJavaScriptFunction() {
		return "'" + this.id + "', '" + this.username + "', '" + this.email + "', '" + this.passwrd + "', '"  + 
				this.signUpDate + "', '" + this.signUpTime + "', '" + this.lastLogoutDate + "', '" + this.lastLogoutTime + "', '" + this.admin + "'";
	}
	
	@Override
	public JSONObject toJSONObject() {
		
		JSONObject jObject = new JSONObject();
		
		jObject.put("id", this.id);
		jObject.put("username", this.username);
		jObject.put("email", this.email);
		jObject.put("passwrd", this.passwrd);
		jObject.put("sign_up_date", this.signUpDate);
		jObject.put("sign_up_time", this.signUpTime);
		jObject.put("last_logout_date", this.lastLogoutDate);
		jObject.put("last_logout_time", this.lastLogoutTime);
		jObject.put("moderator", this.moderator);
		jObject.put("admin", this.admin);
		
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
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return passwrd;
	}
	public void setPassword(String password) {
		this.passwrd = password;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getLastLogoutDate() {
		return lastLogoutDate;
	}
	public void setLastLogoutDate(Date lastLogoutDate) {
		this.lastLogoutDate = lastLogoutDate;
	}

	public Date getSignUpDate() {
		return signUpDate;
	}
	public void setSignUpDate(Date signUpDate) {
		this.signUpDate = signUpDate;
	}
	
	public Boolean isAdmin() {
		return this.admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	
	public Boolean isModerator() {
		return this.moderator;
	}
	
	public void setModerator(Boolean moderator) {
		this.moderator = moderator;
	}
	
	public Time getSignUpTime() {
		return signUpTime;
	}



	public void setSignUpTime(Time signUpTime) {
		this.signUpTime = signUpTime;
	}



	public Time getLastLogoutTime() {
		return lastLogoutTime;
	}



	public void setLastLogoutTime(Time lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}
	
	
}
