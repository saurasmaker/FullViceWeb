package com.fullvicie.interfaces;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fullvicie.enums.*;

public interface IDao <Pojo> {
	
	/*
	 * CRUD Methods
	 */	
	public abstract ErrorType create(Pojo pojo);
	public abstract Pojo read(String search, SearchBy searchBy) throws SQLException;
	public abstract ErrorType update(String search, SearchBy searchBy, Pojo pojo);
	public abstract ErrorType delete(String search, SearchBy searchBy);
	public abstract ArrayList<Pojo> listBy(String search, SearchBy searchBy) throws SQLException;

	
	/*
	 * Static methods
	 */
	public static String appendMySqlSearchBy(String s, SearchBy searchBy, String search) {
		
		s += " WHERE ";
		
		switch(searchBy) {
		
		case ID:
			s += "id = '";
			break;
			
		case NAME:
			s += "name = '";	
			break;	
		
		case USERNAME:
			s += "username = '";	
			break;
			
		case EMAIL:
			s += "email = '";	
			break;
			
		case USER_ID:
			s += "user_id = '";	
			break;
			
		case OWNER_ID:
			s += "user_owner_id = '";
			break;
		case RECEIVER_USER_ID:
			s += "receiver_user_id = '";
			break;
			
		case TRANSMITTER_USER_ID:
			s += "transmitter_user_id = '";
			break;
			
		case TEAM_ID:
			s += "team_id = '";
			break;
			
		default:
			break;
			
		}
		
		return s  + search + "'";
	}
	
	
	public static void closeMySql(ResultSet rs, PreparedStatement stat) {
		
		if(rs != null) {
			try {
				rs.close();
			} catch(SQLException ex) {
				ex.getStackTrace();
			}
		}
		
		if(stat != null) {
			try {
				stat.close();
			} catch(SQLException ex) {
				ex.getStackTrace();
			}
		}
	}
}
