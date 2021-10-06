package com.fullvicie.database.connections;

import java.sql.*;

import com.fullvicie.enums.ErrorType;
import com.fullvicie.interfaces.IDataBaseConnection;

public class MySqlConnection implements IDataBaseConnection {

	
	/*
	 * Attributes
	 */
	private static MySqlConnection instance;
	
	private String jdbcDriver, databaseUrl, databaseName, databaseUsername, databasePassword;
	private Connection databaseConnection;
	private Statement databaseStatement;
	
	
	/*
	 * Constructors
	 */
	private MySqlConnection() {
		this.jdbcDriver = "com.mysql.jdbc.Driver";
		this.databaseUrl = "jdbc:mysql://localhost:3306/";
		this.databaseName = "fullvicie";
		this.databaseUsername = "root";
		this.databasePassword = "";
		
		this.connect();
	}
	
	
	
	/*
	 * Methods 
	 */
	@Override
	public ErrorType connect(){
		
		try {
			Class.forName(jdbcDriver);
			disconnect();			
			databaseConnection = DriverManager.getConnection(databaseUrl + databaseName, databaseUsername, databasePassword);
			databaseStatement = databaseConnection.createStatement();		
		}catch(Exception e) {
			return ErrorType.MYSQL_DATABASE_CONNECTION_ERROR;
		}
		
        return ErrorType.NO_ERROR;
	}
	
	
	@Override
	public ErrorType disconnect(){
		
		try {
			if(databaseStatement != null) {
				databaseStatement.close();
				databaseStatement = null;
				
				if(databaseConnection != null) {
					databaseConnection.close();
					databaseConnection = null;
				}	
			}
		}catch(Exception e) {
			return ErrorType.MYSQL_DATABASE_DISCONNECTION_ERROR;
		}
		
        return ErrorType.NO_ERROR;
	}


	/*
	 * Getters & Setters
	 */
	public static MySqlConnection getInstance() {
		
		if(instance == null)
			instance = new MySqlConnection();
		
		return instance;
	}

	
	public Connection getDatabaseConnection() {
		return databaseConnection;
	}
	public void setDatabaseConnection(Connection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}
	
	
	public Statement getDatabaseStatement() {
		return databaseStatement;
	}
	public void setDatabaseStatement(Statement databaseStatement) {
		this.databaseStatement = databaseStatement;
	}
	
	
}
