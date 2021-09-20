package com.fullvicie.database.connections;

import com.fullvicie.enums.ErrorType;
import com.fullvicie.interfaces.IDataBaseConnection;

public class VoidConnection implements IDataBaseConnection{

	/*
	 * Attributes
	 */
	private static VoidConnection instance;
	
	
	/*
	 * Constructors
	 */
	private VoidConnection() {}
	
	
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
	public static VoidConnection getInstance() {
		
		if(instance == null)
			instance = new VoidConnection();
		
		return instance;
	}
}
