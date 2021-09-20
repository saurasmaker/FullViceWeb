package com.fullvicie.pojos;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.fullvicie.interfaces.IPojo;

public class Report implements IPojo{

	/*
	 * Static Attributes
	 */
	public static final String PARAM_REPORT_ID = "PARAM_REPORT_ID", PARAM_REPORT_REPORT_TYPE_ID = "PARAM_REPORT_REPORT_TYPE_ID", PARAM_REPORT_DESCRIPTION = "PARAM_REPORT_DESCRIPTION",
		PARAM_REPORT_REPORT_DATE = "PARAM_REPORT_REPORT_DATE", PARAM_REPORT_REPORT_TIME = "PARAM_REPORT_REPORT_TIME", PARAM_REPORT_SOLUTION_DATE = "PARAM_REPORT_SOLUTION_DATE",
		PARAM_REPORT_SOLUTION_TIME = "PARAM_REPORT_SOLUTION_TIME", PARAM_REPORT_MODERATOR_ID = "PARAM_REPORT_MODERATOR_ID", PARAM_REPORT_ACCUSED_ID = "PARAM_REPORT_ACCUSED_ID",
		PARAM_REPORT_WHISTLEBLOWER_ID = "PARAM_REPORT_WHISTLEBOWER_ID";

	public static final String ATTR_REPORT_OBJ = "ATTR_REPORT_OBJ";
	
	
	/*
	 * Attributes
	 */
	private int id, reportTypeId, moderatorId, accusedId, whistleblowerId;
	private String description;
	private Date reportDate, solutionDate;
	private Time reportTime, solutionTime;
	
	
	/*
	 * Constructors
	 */
	public Report() {
		
	}
	
	public Report(HttpServletRequest request) {
		try{this.id = Integer.parseInt(request.getParameter(PARAM_REPORT_ID));}
		catch(Exception t) {this.id = -1;}
		
		this.description = request.getParameter(PARAM_REPORT_DESCRIPTION);

		try{this.reportDate = Date.valueOf(request.getParameter(PARAM_REPORT_REPORT_DATE));}
		catch(Exception t) {this.reportDate = Date.valueOf(LocalDate.now());}
		
		try{this.reportTime = Time.valueOf(request.getParameter(PARAM_REPORT_REPORT_TIME));}
		catch(Exception t) {this.reportTime = Time.valueOf(LocalTime.now());}
		
		try{this.solutionDate = Date.valueOf(request.getParameter(PARAM_REPORT_SOLUTION_DATE));}
		catch(Exception t) {this.solutionDate = Date.valueOf(LocalDate.now());}
		
		try{this.solutionTime = Time.valueOf(request.getParameter(PARAM_REPORT_SOLUTION_TIME));}
		catch(Exception t) {this.reportTime = Time.valueOf(LocalTime.now());}
		
		try{this.reportTypeId = Integer.parseInt(request.getParameter(PARAM_REPORT_MODERATOR_ID));}
		catch(Exception t) {this.moderatorId = -1;}
		
		try{this.moderatorId = Integer.parseInt(request.getParameter(PARAM_REPORT_REPORT_TYPE_ID));}
		catch(Exception t) {this.reportTypeId = -1;}
		
		try{this.accusedId = Integer.parseInt(request.getParameter(PARAM_REPORT_ACCUSED_ID));}
		catch(Exception t) {this.accusedId = -1;}
		
		try{this.whistleblowerId = Integer.parseInt(request.getParameter(PARAM_REPORT_WHISTLEBLOWER_ID));}
		catch(Exception t) {this.whistleblowerId = -1;}
	}
	
	
	/*
	 * Methods
	 */
	@Override
	public JSONObject toJSONObject() {
		
		JSONObject jObject = new JSONObject();
		
		jObject.put("id", id);
		jObject.put("description", this.description);
		jObject.put("reportDate", this.reportDate);
		jObject.put("reportTime", this.reportTime);
		jObject.put("solutionDate", this.solutionDate);
		jObject.put("solutionTime", this.solutionTime);
		jObject.put("reportTypeId", this.reportTypeId);
		jObject.put("moderatorId", this.moderatorId);
		jObject.put("accusedId", this.accusedId);
		jObject.put("whistleblowerId", this.whistleblowerId);
		
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

	
	public int getModeratorId() {
		return moderatorId;
	}
	public void setModeratorId(int moderatorId) {
		this.moderatorId = moderatorId;
	}

	
	public int getAccusedId() {
		return accusedId;
	}
	public void setAccusedId(int accusedId) {
		this.accusedId = accusedId;
	}

	
	public int getWhistleblowerId() {
		return whistleblowerId;
	}
	public void setWhistleblowerId(int whistleblowerId) {
		this.whistleblowerId = whistleblowerId;
	}

	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	
	public Date getSolutionDate() {
		return solutionDate;
	}
	public void setSolutionDate(Date solutionDate) {
		this.solutionDate = solutionDate;
	}

	
	public Time getReportTime() {
		return reportTime;
	}
	public void setReportTime(Time reportTime) {
		this.reportTime = reportTime;
	}
	

	public Time getSolutionTime() {
		return solutionTime;
	}
	public void setSolutionTime(Time solutionTime) {
		this.solutionTime = solutionTime;
	}

	public int getReportTypeId() {
		return reportTypeId;
	}

	public void setReportTypeId(int reportTypeId) {
		this.reportTypeId = reportTypeId;
	}
	
}
