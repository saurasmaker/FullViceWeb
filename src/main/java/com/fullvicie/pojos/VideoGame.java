package com.fullvicie.pojos;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class VideoGame implements IPojo{

	
	/*
	 * Static Attributes
	 */
	public static String PARAM_VIDEO_GAME_ID="PARAM_VIDEO_GAME_ID", PARAM_VIDEO_GAME_NAME = "PARAM_VIDEO_GAME_NAME",
			PARAM_VIDEO_GAME_DESCRIPTION="PARAM_VIDEO_GAME_DESCRIPTION", PARAM_VIDEO_GAME_DELETED="PARAM_VIDEO_GAME_DELETED",
			PARAM_VIDEO_GAME_DELETE_DATE="PARAM_VIDEO_GAME_DELETE_DATE", PARAM_VIDEO_GAME_DELETE_TIME="PARAM_VIDEO_GAME_DELETE_TIME";
	
	public static String ATTR_VIDEO_GAME_OBJ="ATTR_VIDEO_GAME_OBJ", ATTR_VIDEO_GAMES_LIST="ATTR_VIDEO_GAMES_LIST";
	
	/*
	 * Attributes
	 */
	private int id;
	private String name, description;
	
	
	
	/*
	 * Constructors
	 */
	public VideoGame() {
		
	}
	public VideoGame(HttpServletRequest request) {
		
		try{this.id = Integer.parseInt(request.getParameter(PARAM_VIDEO_GAME_ID));}
		catch(Exception t) {this.id = -1;}
		
		this.name = request.getParameter(PARAM_VIDEO_GAME_NAME);
		this.description = request.getParameter(PARAM_VIDEO_GAME_DESCRIPTION);
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
