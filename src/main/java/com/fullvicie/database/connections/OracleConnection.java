package com.fullvicie.database.connections;

import com.fullvicie.enums.ErrorType;
import com.fullvicie.interfaces.IDataBaseConnection;

public class OracleConnection implements IDataBaseConnection{

	/*
	 * Attributes
	 */
	private static OracleConnection instance;
	
	
	
	/*
	 * Constructors
	 */
	private OracleConnection() {}
	
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
	public static OracleConnection getInstance() {
		
		if(instance == null)
			instance = new OracleConnection();
		
		return instance;
	}
}
