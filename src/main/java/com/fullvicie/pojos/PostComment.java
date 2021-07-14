package com.fullvicie.pojos;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class PostComment implements IPojo{

	public static final String PARAM_POST_COMMENT_ID = "PARAM_POST_COMMENT_ID";

	public PostComment(HttpServletRequest request) {
		
	}
	
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

}
