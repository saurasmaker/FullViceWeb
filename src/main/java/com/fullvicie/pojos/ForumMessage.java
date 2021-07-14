package com.fullvicie.pojos;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class ForumMessage implements IPojo{

	public static final String PARAM_FORUM_MESSGAE_ID = "PARAM_FORUM_MESSGAE_ID";

	public ForumMessage(HttpServletRequest request) {
		
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
