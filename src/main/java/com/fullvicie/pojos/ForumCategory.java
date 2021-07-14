package com.fullvicie.pojos;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class ForumCategory implements IPojo{

	
	/*
	 * Static Attributes
	 */
	public static final String PARAM_FORUM_CATEGORY_ID = "PARAM_FORUM_CATEGORY_ID", PARAM_FORUM_CATEGORY_NAME = "PARAM_FORUM_CATEGORY_NAME",
			PARAM_FORUM_CATEGORY_DESCRIPTION = "PARAM_FORUM_CATEGORY_DESCRIPTION";

	
	/*
	 * Attributes
	 */
	private String id, name, description;
	

	/*
	 * Constructors
	 */
	public ForumCategory() {
		
	}
	
	public ForumCategory(HttpServletRequest request){
		this.id = request.getParameter(PARAM_FORUM_CATEGORY_ID);
		this.name = request.getParameter(PARAM_FORUM_CATEGORY_NAME);
		this.description = request.getParameter(PARAM_FORUM_CATEGORY_DESCRIPTION);
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
