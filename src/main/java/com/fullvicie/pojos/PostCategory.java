package com.fullvicie.pojos;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class PostCategory implements IPojo{

	/*
	 * Static Attributes
	 */
	public static final String PARAM_POST_CATEGORY_ID = "PARAM_POST_CATEGORY_ID", PARAM_POST_CATEGORY_NAME = "PARAM_POST_CATEGORY_NAME",
			PARAM_POST_CATEGORY_DESCRIPTION = "PARAM_POST_CATEGORY_DESCRIPTION";

	public static final String ATTR_POST_CATEGORY_OBJ = "ATTR_POST_CATEGORY_OBJ";
	
	
	
	/*
	 * Attributes
	 */
	private int id;
	private String name, description;

	
	/*
	 * Constructors
	 */
	public PostCategory() {
		
	}
	public PostCategory(HttpServletRequest request) {
		try{this.id = Integer.parseInt(request.getParameter(PARAM_POST_CATEGORY_ID));}
		catch(Exception t) {this.id = -1;}
		
		this.name = request.getParameter(PARAM_POST_CATEGORY_NAME);
		this.description = request.getParameter(PARAM_POST_CATEGORY_DESCRIPTION);
	}
	
	
	/*
	 * Methods
	 */
	@Override
	public String toJavaScriptFunction() {
		return "'" + this.id + "', '" + this.name + "', '" + this.description + "'";
	}

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

	
	
	
	
	
	
}
