package com.fullvicie.database.connections;

import com.fullvicie.enums.ErrorType;
import com.fullvicie.interfaces.IDataBaseConnection;

public class SqlServerConnection implements IDataBaseConnection{

	/*
	 * Attributes
	 */
	private static SqlServerConnection instance;
	
	
	/*
	 * Constructors
	 */
	private SqlServerConnection() {}
	
	
	
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
	public static SqlServerConnection getInstance() {
		
		if(instance == null)
			instance = new SqlServerConnection();
		
		return instance;
	}
}
