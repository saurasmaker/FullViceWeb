package com.fullvicie.interfaces;

import java.util.ArrayList;

import com.fullvicie.enums.*;


public interface IDao <Pojo> {
	
	/*
	 * CRUD Methods
	 */	
	public ErrorType create(Pojo pojo);
	public Pojo read(String search, SearchBy searchBy);
	public ErrorType update(String search, SearchBy searchBy, Pojo pojo);
	public ErrorType delete(String search, SearchBy searchBy);
	public ErrorType pseudoDelete(String search, SearchBy searchBy);
	public ArrayList<Pojo> list();
	
	/*
	 * Static methods
	 */
	public static String appendSqlSearchBy(String s, SearchBy searchBy, String search) {
		
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
			
		default:
			break;
			
		}
		
		return s  + search + "'";
	}
	
}
