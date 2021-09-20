package com.fullvicie.factories;

import com.fullvicie.database.connections.*;
import com.fullvicie.interfaces.IDataBaseConnection;

public class DataBaseConnectionFactory {

	/*
	 * 
	 */
	public static final String MYSQL_ENGINE="MYSQL_ENGINE", ORACLE_ENGINE="ORACLE_ENGINE",
			POSTGRESQL_ENGINE="POSTGRESQL_ENGINE", SQLSERVER_ENGINE="SQLSERVER_ENGINE";
	
	
	
	/*
	 * Constructors
	 */
	public DataBaseConnectionFactory() {
		
	}
	
	
	/*
	 * Methods 
	 */
	public static IDataBaseConnection getConnection(String engine) {
		
		switch(engine) {
		
		case MYSQL_ENGINE:
			return MySqlConnection.getInstance();
		
		case ORACLE_ENGINE:
			return OracleConnection.getInstance();
			
		case POSTGRESQL_ENGINE:
			return PostgreSqlConnection.getInstance();

		}		
		
		return VoidConnection.getInstance();
	}
		
	
}
