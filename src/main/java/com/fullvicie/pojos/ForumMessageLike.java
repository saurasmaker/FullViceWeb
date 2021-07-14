package com.fullvicie.pojos;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class ForumMessageLike implements IPojo{

	public static final String PARAM_FORUM_MESSAGE_LIKE_ID = "PARAM_FORUM_MESSAGE_LIKE_ID";

	public ForumMessageLike(HttpServletRequest request) {
		
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
