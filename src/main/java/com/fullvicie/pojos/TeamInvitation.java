package com.fullvicie.pojos;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.daos.sql.UserSqlDao;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IPojo;

public class TeamInvitation implements IPojo {

	
	/*
	 * Static Attributes
	 */
	public static final String PARAM_TEAM_INVITATION_ID="PARAM_TEAM_INVITATION_ID", PARAM_TEAM_INVITATION_TEAM_ID = "PARAM_TEAM_INVITATION_TEAM_ID",
			PARAM_TEAM_INVITATION_TRANSMITTER_USER_ID="PARAM_TEAM_INVITATION_TRANSMITTER_USER_ID", PARAM_TEAM_INVITATION_TRANSMITTER_USER_NAME="PARAM_TEAM_INVITATION_TRANSMITTER_USER_NAME",
			PARAM_TEAM_INVITATION_RECEIVER_USER_ID="PARAM_TEAM_INVITATION_RECEIVER_USER_ID", PARAM_TEAM_INVITATION_RECEIVER_USER_NAME="PARAM_TEAM_INVITATION_RECEIVER_USER_NAME",
			PARAM_TEAM_INVITATION_SENDING_DATE="PARAM_TEAM_INVITATION_SENDING_DATE", PARAM_TEAM_INVITATION_SENDING_TIME="PARAM_TEAM_INVITATION_SENDING_TIME",
			PARAM_TEAM_INVITATION_DELETED="PARAM_TEAM_INVITATION_DELETED",
			PARAM_TEAM_INVITATION_DELETE_DATE="PARAM_TEAM_INVITATION_DELETE_DATE", PARAM_TEAM_INVITATION_DELETE_TIME="PARAM_TEAM_INVITATION_DELETE_TIME";
	
	/*
	 * Attributes
	 */
	private int id, teamId, transmitterUserId, receiverUserId;
	private Date sendingDate, deleteDate;
	private Time sendingTime, deleteTime;
	private boolean deleted;
	
	
	
	/*
	 * Constructors
	 */
	public TeamInvitation() {
		
	}
	public TeamInvitation(HttpServletRequest request) {
		try{this.id = Integer.parseInt(request.getParameter(PARAM_TEAM_INVITATION_ID));}
		catch(Exception t) {this.id = -1;}
		
		try{this.teamId = Integer.parseInt(request.getParameter(PARAM_TEAM_INVITATION_TEAM_ID));}
		catch(Exception t) {this.teamId = -1;}
		
		try{this.transmitterUserId = Integer.parseInt(request.getParameter(PARAM_TEAM_INVITATION_TRANSMITTER_USER_ID));}
		catch(Exception t) {this.transmitterUserId = -1;}
		
		try{this.receiverUserId = Integer.parseInt(request.getParameter(PARAM_TEAM_INVITATION_RECEIVER_USER_ID));}
		catch(Exception t) {
			try {
				User receiverUser = new UserSqlDao().read(request.getParameter(PARAM_TEAM_INVITATION_RECEIVER_USER_NAME), SearchBy.USERNAME);
				this.receiverUserId = receiverUser.getId();
			}catch(Exception e) {this.receiverUserId = -1;}
		}
	
		try{this.sendingDate = Date.valueOf(request.getParameter(PARAM_TEAM_INVITATION_SENDING_DATE));}
		catch(Exception t) {this.sendingDate = Date.valueOf(LocalDate.now());}
		
		try{this.sendingTime = Time.valueOf(request.getParameter(PARAM_TEAM_INVITATION_SENDING_TIME));}
		catch(Exception t) {this.sendingTime = Time.valueOf(LocalTime.now());}
		
		this.deleted = request.getParameter(PARAM_TEAM_INVITATION_DELETED) != null ? true : false;
		
		try{this.deleteDate = Date.valueOf(request.getParameter(PARAM_TEAM_INVITATION_DELETE_DATE));}
		catch(Exception t) {this.deleteDate = Date.valueOf(LocalDate.now());}
		
		try{this.deleteTime = Time.valueOf(request.getParameter(PARAM_TEAM_INVITATION_DELETE_TIME));}
		catch(Exception t) {this.deleteTime = Time.valueOf(LocalTime.now());}
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
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getTransmitterUserId() {
		return transmitterUserId;
	}

	public void setTransmitterUserId(int transmitterUserId) {
		this.transmitterUserId = transmitterUserId;
	}

	public int getReceiverUserId() {
		return receiverUserId;
	}

	public void setReceiverUserId(int receiverUserId) {
		this.receiverUserId = receiverUserId;
	}

	public Date getSendingDate() {
		return sendingDate;
	}

	public void setSendingDate(Date sendingDate) {
		this.sendingDate = sendingDate;
	}

	public Time getSendingTime() {
		return sendingTime;
	}

	public void setSendingTime(Time sendingTime) {
		this.sendingTime = sendingTime;
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
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
