package com.fullvicie.database.connections;

import com.fullvicie.enums.ErrorType;
import com.fullvicie.interfaces.IDataBaseConnection;

public class PostgreSqlConnection implements IDataBaseConnection{

	/*
	 * Attributes
	 */
	private static PostgreSqlConnection instance;
	
	
	/*
	 * Constructors
	 */
	private PostgreSqlConnection() {}
	
	
	
	/*
	 * Methods
	 */
	@Override
	public ErrorType connect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ErrorType disconnect() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/*
	 * Getters & Setters
	 */
	public static PostgreSqlConnection getInstance() {
		
		if(instance == null)
			instance = new PostgreSqlConnection();
		
		return instance;
	}
}
