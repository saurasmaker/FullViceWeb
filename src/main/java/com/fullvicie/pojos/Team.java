package com.fullvicie.pojos;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class Team implements IPojo{

	/*
	 * Static Attributes
	 */
	public static final String PARAM_TEAM_ID = "PARAM_TEAM_ID", PARAM_TEAM_NAME = "PARAM_TEAM_NAME", PARAM_TEAM_DESCRIPTION = "PARAM_TEAM_DESCRIPTION",
			PARAM_TEAM_LOGO = "PARAM_TEAM_LOGO", PARAM_TEAM_CREATION_DATE="PARAM_TEAM_CREATION_DATE", PARAM_TEAM_CREATION_TIME="PARAM_TEAM_CREATION_TIME",
			PARAM_TEAM_DELETED="PARAM_TEAM_DELETED", PARAM_TEAM_DELETE_DATE="PARAM_TEAM_DELETE_DATE", PARAM_TEAM_DELETE_TIME="PARAM_TEAM_DELETE_TIME",
			PARAM_TEAM_VIDEO_GAME_ID="PARAM_TEAM_VIDEO_GAME_ID", PARAM_TEAM_USER_OWNER_ID="PARAM_TEAM_USER_OWNER_ID", PARAM_TEAM_BASE64LOGO="PARAM_TEAM_BASE64LOGO",
			PARAM_TEAM_USER_CREATOR_ID="PARAM_TEAM_USER_CREATOR_ID", PARAM_TEAM_GAMER_PROFILE_ID_="PARAM_TEAM_GAMER_PROFILE_ID_";
			
	public static final String ATTR_TEAM_OBJ = "ATTR_TEAM_OBJ", ATR_TEAMS_LIST = "ATR_TEAMS_LIST";
	
	
	/*
	 * Attributes
	 */
	private int id, videoGameId, userOwnerId, userCreatorId;
	private int gamerProfiles[] = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
	private String name, description, base64Logo;
	private boolean deleted;
	private Date creationDate, deleteDate;
	private Time creationTime, deleteTime;
	
	
	
	/*
	 * Constructors
	 */
	public Team() {
		
	}
	public Team(HttpServletRequest request) {
				
		try{this.id = Integer.parseInt(request.getParameter(PARAM_TEAM_ID));}
		catch(Exception t) {this.id = -1;}
		
		this.name = request.getParameter(PARAM_TEAM_NAME);
		this.description = request.getParameter(PARAM_TEAM_DESCRIPTION);
		
		try{this.creationDate = Date.valueOf(request.getParameter(PARAM_TEAM_CREATION_DATE));}
		catch(Exception t) {this.creationDate = Date.valueOf(LocalDate.now());}
		
		try{this.creationTime = Time.valueOf(request.getParameter(PARAM_TEAM_CREATION_TIME));}
		catch(Exception t) {this.creationTime = Time.valueOf(LocalTime.now());}
		
		this.deleted = request.getParameter(PARAM_TEAM_DELETED) != null ? true : false;
		
		try{this.deleteDate = Date.valueOf(request.getParameter(PARAM_TEAM_DELETE_DATE));}
		catch(Exception t) {this.deleteDate = Date.valueOf(LocalDate.now());}
		
		try{this.deleteTime = Time.valueOf(request.getParameter(PARAM_TEAM_DELETE_TIME));}
		catch(Exception t) {this.deleteTime = Time.valueOf(LocalTime.now());}
		
		for(int i = 0; i < gamerProfiles.length; ++i)
			try{this.gamerProfiles[i] = Integer.parseInt(request.getParameter(PARAM_TEAM_GAMER_PROFILE_ID_ + i));}
			catch(Exception t) {this.gamerProfiles[i] = -1;}
		
		try{this.videoGameId = Integer.parseInt(request.getParameter(PARAM_TEAM_VIDEO_GAME_ID));}
		catch(Exception t) {this.videoGameId = -1;}
		
		try{this.userOwnerId = Integer.parseInt(request.getParameter(PARAM_TEAM_USER_OWNER_ID));}
		catch(Exception t) {this.userOwnerId = -1;}
		
		try{this.userCreatorId = Integer.parseInt(request.getParameter(PARAM_TEAM_USER_CREATOR_ID));}
		catch(Exception t) {this.userCreatorId = -1;}
	}
	
	
	/*
	 * Methods
	 */
	@Override
	public String toJavaScriptFunction() {
		return "'" + this.id + "', '" + this.name + "', '" + this.description + "', '" + this.base64Logo + "', '"  + 
				this.creationDate + "', '" + this.creationTime + "', '" + this.deleted + "', '" + 
				this.deleteDate + "', '" + this.deleteTime + "', '" + this.videoGameId  + "', '" + 
				this.userOwnerId + "', '" + this.userCreatorId + "'";
	}

	@Override
	public JSONObject toJSONObject() {
		
		JSONObject jObject = new JSONObject();
		
		jObject.put("id", id);
		jObject.put("name", this.name);
		jObject.put("description", this.description);
		jObject.put("base64Logo", this.base64Logo);
		jObject.put("creationDate", this.creationDate);
		jObject.put("creationDate", this.creationTime);
		jObject.put("deleted", this.deleted);
		jObject.put("deleteDate", this.deleteDate);
		jObject.put("deleteTime", this.deleteTime);
		jObject.put("videogameId", this.videoGameId);
		jObject.put("userOwnerId", this.userOwnerId);
		jObject.put("userCreatorId", this.userCreatorId);
		
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


	public int getVideoGameId() {
		return videoGameId;
	}
	public void setVideoGameId(int videogameId) {
		this.videoGameId = videogameId;
	}


	public int getUserCreatorId() {
		return userCreatorId;
	}
	public void setUserCreatorId(int userCreatorId) {
		this.userCreatorId = userCreatorId;
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


	public String getBase64Logo() {
		return base64Logo;
	}
	public void setBase64Logo(String base64Logo) {
		this.base64Logo = base64Logo;
	}


	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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

	
	public int getUserOwnerId() {
		return userOwnerId;
	}
	public void setUserOwnerId(int userOwnerId) {
		this.userOwnerId = userOwnerId;
	}
	

	public Time getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(Time deleteTime) {
		this.deleteTime = deleteTime;
	}
	public int[] getGamerProfiles() {
		return gamerProfiles;
	}
	public void setGamerProfiles(int players[]) {
		this.gamerProfiles = players;
	}
	

	
	
}
