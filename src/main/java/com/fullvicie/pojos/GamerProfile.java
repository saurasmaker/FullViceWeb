package com.fullvicie.pojos;

import java.sql.Date;
import java.sql.Time;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class GamerProfile implements IPojo{

	
	/*
	 * Static Attributes
	 */
	public static String PARAM_GAMER_PROFILE_ID="PARAM_GAMER_PROFILE_ID", PARAM_GAMER_PROFILE_NAME_IN_GAME="PARAM_GAMER_PROFILE_NAME_IN_GAME", 
			PARAM_GAMER_PROFILE_ANALYSIS_PAGE="PARAM_GAMER_PROFILE_ANALYSIS_PAGE",
			PARAM_GAMER_PROFILE_POINTS="PARAM_GAMER_PROFILE_POINTS", PARAM_GAMER_PROFILE_DELETED="PARAM_GAMER_PROFILE_DELETED",
			PARAM_GAMER_PROFILE_DELETE_DATE="PARAM_GAMER_PROFILE_DELETE_DATE", PARAM_GAMER_PROFILE_DELETE_TIME="PARAM_GAMER_PROFILE_DELETE_TIME",
			PARAM_GAMER_PROFILE_VIDEO_GAME_ID="PARAM_GAMER_PROFILE_VIDEO_GAME_ID", PARAM_GAMER_PROFILE_USER_ID="PARAM_GAMER_PROFILE_USER_ID";
	
	public static String ATTR_GAMER_PROFILE_OBJ="ATTR_GAMER_PROFILE_OBJ", ATTR_GAMER_PROFILES_LIST="ATTR_GAMER_PROFILES_LIST";
	
	
	/*
	 * Attributes
	 */
	private int id, points, videoGameId, userId;
	private String nameInGame, analysisPage; 
	private boolean deleted;
	private Date deleteDate;
	private Time deleteTime;


	
	/*
	 * Constructors
	 */
	public GamerProfile() {
		
	}
	public GamerProfile(HttpServletRequest request) {
		try{this.id = Integer.parseInt(request.getParameter(PARAM_GAMER_PROFILE_ID));}
		catch(Exception t) {this.id = -1;}
		
		this.nameInGame = request.getParameter(PARAM_GAMER_PROFILE_NAME_IN_GAME);
		
		this.analysisPage = request.getParameter(PARAM_GAMER_PROFILE_ANALYSIS_PAGE);
		
		try{this.points = Integer.parseInt(request.getParameter(PARAM_GAMER_PROFILE_POINTS));}
		catch(Exception t) {this.points = 0;}
		
		this.deleted = request.getParameter(PARAM_GAMER_PROFILE_DELETED) != null ? true : false;
		
		try{ this.setDeleteDate(Date.valueOf(request.getParameter(PARAM_GAMER_PROFILE_DELETE_DATE))); }
		catch(Exception t) { this.setDeleteDate(null); }
		
		try{ this.deleteTime = Time.valueOf(request.getParameter(PARAM_GAMER_PROFILE_DELETE_TIME)); }
		catch(Exception t) { this.deleteTime = null; }
		
		try{this.videoGameId = Integer.parseInt(request.getParameter(PARAM_GAMER_PROFILE_VIDEO_GAME_ID));}
		catch(Exception t) {this.videoGameId = -1;}
		
		try{this.userId = Integer.parseInt(request.getParameter(PARAM_GAMER_PROFILE_USER_ID));}
		catch(Exception t) {this.userId = -1;}
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
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getNameInGame() {
		return nameInGame;
	}

	public void setNameInGame(String nameInGame) {
		this.nameInGame = nameInGame;
	}

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

	public int getVideoGameId() {
		return videoGameId;
	}

	public void setVideoGameId(int gameId) {
		this.videoGameId = gameId;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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
	public String getAnalysisPage() {
		return analysisPage;
	}
	public void setAnalysisPage(String analysisPage) {
		this.analysisPage = analysisPage;
	}

}
