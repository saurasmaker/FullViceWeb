package com.fullvicie.pojos;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;
import com.fullvicie.tools.Encryptor;

public class User implements IPojo{
	
	
	/*
	 * Static Attributes
	 */
	public static final String PARAM_USER = "PARAM_USER", PARAM_USER_ID = "PARAM_USER_ID", PARAM_USER_USERNAME = "PARAM_USER_USERNAME",
			PARAM_USER_EMAIL = "PARAM_USER_EMAIL", PARAM_USER_PASSWORD = "PARAM_USER_PASSWORD", PARAM_USER_REPEATPASSWORD="PARAM_USER_REPEATPASSWORD",
			PARAM_USER_SIGNUPDATE = "PARAM_USER_SIGNUPDATE", PARAM_USER_SIGNUPTIME = "PARAM_USER_SIGNUPTIME",
			PARAM_USER_LASTLOGOUTDATE = "PARAM_USER_LASTLOGOUTDATE", PARAM_USER_LASTLOGOUTTIME = "PARAM_USER_LASTLOGOUTTIME",
			PARAM_USER_DELETED = "PARAM_USER_DELETED", PARAM_USER_DELETE_DATE = "PARAM_USER_DELETE_DATE", PARAM_USER_DELETE_TIME = "PARAM_USER_DELETE_TIME",
			PARAM_USER_ISADMIN = "PARAM_USER_ISADMIN", PARAM_USER_ISMODERATOR="PARAM_USER_ISMODERATOR";
	
	public static final String PART_USER_PICTURE="PART_USER_PICTURE";
	
	public static final String ATR_USER_OBJ = "ATR_USER_OBJ", ATR_USERS_LIST = "ATR_USERS_LIST", ATR_USER_LOGGED_OBJ = "ATR_USER_LOGGED_OBJ";
		
	
	
	/*
	 * Attributes
	 */
	private int id;
	private String username, email, passwrd, base64Picture;
	private Date lastLogoutDate, signUpDate, deleteDate;
	private Time lastLogoutTime, signUpTime, deleteTime;
	private Boolean deleted, moderator, admin;
	
	
	/*
	 * Constructors
	 */
	public User() {
		
	}
	
	public User(HttpServletRequest request) {
		try{this.id = Integer.parseInt(request.getParameter(PARAM_USER_ID));}
		catch(Exception t) {this.id = -1;}
		
		this.username = request.getParameter(PARAM_USER_USERNAME);
		this.email = request.getParameter(PARAM_USER_EMAIL);
		this.passwrd = request.getParameter(PARAM_USER_PASSWORD);
		
		try{ this.signUpDate = Date.valueOf(request.getParameter(PARAM_USER_SIGNUPDATE)); }
		catch(Exception t) { this.signUpDate = Date.valueOf(LocalDate.now());}
		
		try{ this.signUpTime = Time.valueOf(request.getParameter(PARAM_USER_SIGNUPTIME)); }
		catch(Exception t) { this.signUpTime = Time.valueOf(LocalTime.now());}
		
		try{ this.lastLogoutDate = Date.valueOf(request.getParameter(PARAM_USER_LASTLOGOUTDATE)); }
		catch(Exception t) { this.lastLogoutDate = Date.valueOf(LocalDate.now()); }
		
		try{ this.lastLogoutTime = Time.valueOf(request.getParameter(PARAM_USER_LASTLOGOUTTIME)); }
		catch(Exception t) { this.lastLogoutTime = Time.valueOf(LocalTime.now()); }

		this.deleted = request.getParameter(PARAM_USER_DELETED) != null ? true : false;
		
		try{ this.deleteDate = Date.valueOf(request.getParameter(PARAM_USER_DELETE_DATE)); }
		catch(Exception t) { this.deleteDate = null; }
		
		try{ this.deleteTime = Time.valueOf(request.getParameter(PARAM_USER_DELETE_TIME)); }
		catch(Exception t) { this.deleteTime = null; }
		
		this.admin = request.getParameter(PARAM_USER_ISADMIN)!=null ? true : false;
		this.moderator = request.getParameter(PARAM_USER_ISMODERATOR)!=null ? true : false;
				
		// Encrypt password
		if(this.passwrd!=null)
			this.passwrd = Encryptor.encrypt(this.passwrd);
	}
	
	
	
	/*
	 * Methods
	 */
	@Override
	public String toJavaScriptFunction() {
		return "'" + this.id + "', '" + this.username + "', '" + this.email + "', '" + this.moderator  + "', '" + this.admin + "'";
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
		jObject.put("deleted", this.deleted);
		jObject.put("deleteDate", this.deleteDate);
		jObject.put("deleteTime", this.deleteTime);
		jObject.put("moderator", this.moderator);
		jObject.put("admin", this.admin);
		
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
	
	
	public String getPasswrd() {
		return passwrd;
	}
	public void setPasswrd(String passwrd) {
		this.passwrd = passwrd;
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

	
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	
	public Boolean getModerator() {
		return moderator;
	}
	public Boolean getAdmin() {
		return admin;
	}

	public String getBase64Picture() {
		return base64Picture;
	}

	public void setBase64Picture(String base64Picture) {
		this.base64Picture = base64Picture;
	}

	
}
