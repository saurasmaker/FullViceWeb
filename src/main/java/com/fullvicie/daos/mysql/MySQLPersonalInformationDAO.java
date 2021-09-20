package com.fullvicie.daos.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fullvicie.database.connections.MySqlConnection;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.exceptions.DaoException;
import com.fullvicie.factories.DataBaseConnectionFactory;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.PersonalInformation;

public class MySQLPersonalInformationDAO implements IDao<PersonalInformation>{
	
	/*
	 * Static Attributes
	 */
	public static String TABLE_NAME="personal_information", ID_COLUMN="id", NAME_COLUMN="name", SURNAMES_COLUMN="surnames", BIOGRAPHY_COLUMN="biography",
			BIRTHDAY_COLUMN="birthday", ADDRESS_COLUMN="address", USER_ID_COLUMN="user_id";
	
	
	/*
	 * Singleton
	 */
	private static MySQLPersonalInformationDAO instance;
	private Connection connection;
	private MySQLPersonalInformationDAO() {
		this.connection = ((MySqlConnection) DataBaseConnectionFactory.getConnection(DataBaseConnectionFactory.MYSQL_ENGINE)).getDatabaseConnection();
	}
	public static MySQLPersonalInformationDAO getInstance() {	
		
		if(instance == null)
			instance = new MySQLPersonalInformationDAO();	
		
		return instance;
	}
		
	
	
	/*
	 * Methods
	 */
	@Override
	public ErrorType create(PersonalInformation profile) throws DaoException {

		return executeQueryWithParameters("INSERT INTO " + TABLE_NAME + " (" 
			+ NAME_COLUMN + ", " 
			+ SURNAMES_COLUMN + ", "
			+ BIOGRAPHY_COLUMN + ", " 
			+ ADDRESS_COLUMN + ", "  
			+ BIRTHDAY_COLUMN + ", "  
			+ USER_ID_COLUMN + ") VALUES (?, ?, ?, ?, ?, ?)", profile);	
	}

	
	@Override
	public PersonalInformation read(String search, SearchBy searchBy) throws DaoException {
		// Declaration of variables
		PreparedStatement stat = null;
		ResultSet rs = null;
		PersonalInformation pi = null;
		
		// Initialize query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		// Logic
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);	
			if(rs.next()) {
				pi = convert(rs);
			} else { 
				throw new DaoException("");
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new DaoException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return pi;
	}


	@Override
	public ErrorType update(String search, SearchBy searchBy, PersonalInformation pi) throws DaoException {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ NAME_COLUMN + " = ?, "
			+ SURNAMES_COLUMN + " = ?, "
			+ BIOGRAPHY_COLUMN + " = ?, " 
			+ ADDRESS_COLUMN + " = ?, " 
			+ BIRTHDAY_COLUMN + " = ?, "  
			+ USER_ID_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		et = executeQueryWithParameters(updateQuery, pi);

		return et;
	}


	@Override
	public ErrorType delete(String search, SearchBy searchBy) throws DaoException {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		deleteQuery = IDao.appendMySqlSearchBy(deleteQuery, searchBy, search);
		et = executeQueryWithParameters(deleteQuery, null);
		
		return et;
	}
	
	
	@Override
	public ArrayList<PersonalInformation> listBy(String search, SearchBy searchBy) throws DaoException {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<PersonalInformation> personalInformationsList = new ArrayList<PersonalInformation>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);
			while(rs.next()) {
				personalInformationsList.add(convert(rs));
			}	
			rs.close();
		} catch (SQLException e)  {
			throw new DaoException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return personalInformationsList;
	}
	
	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, PersonalInformation pi) throws DaoException {
		
		PersonalInformation actualPi = read(String.valueOf(pi.getId()), SearchBy.ID);
		int pos = 1;
		
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(query);
			if(pi != null) {
				if(pi.getName() != null) stat.setString(pos++, pi.getName());
				else if(actualPi!=null) stat.setString(pos++, actualPi.getName());
				else stat.setString(pos++, null);
				
				if(pi.getSurnames() != null) stat.setString(pos++, pi.getSurnames());
				else if(actualPi!=null) stat.setString(pos++, actualPi.getSurnames());
				else stat.setString(pos++, null);
				
				if(pi.getBiography() != null) stat.setString(pos++, pi.getBiography());
				else if(actualPi!=null) stat.setString(pos++, actualPi.getBiography());
				else stat.setString(pos++, null);
				
				if(pi.getAddress() != null) stat.setString(pos++, pi.getAddress());
				else if(actualPi!=null) stat.setString(pos++, actualPi.getAddress());
				else stat.setString(pos++, null);
				
				if(pi.getBirthday() != null) stat.setDate(pos++, pi.getBirthday());
				else if(actualPi!=null) stat.setDate(pos++, actualPi.getBirthday());
				else stat.setString(pos++, null);
				
				if(pi.getUserId() != 0) stat.setInt(pos++, pi.getUserId());
				else if(actualPi!=null) stat.setInt(pos++, actualPi.getUserId());
				else stat.setString(pos++, null);
			}
			stat.execute();
		} catch (SQLException e) {
			throw new DaoException("");
		} finally {
			IDao.closeMySql(null, stat);
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private PersonalInformation convert(ResultSet rs) throws SQLException {
		
		PersonalInformation profile = new PersonalInformation();
		profile.setId(rs.getInt(ID_COLUMN));
		profile.setName(rs.getString(NAME_COLUMN));
		profile.setSurnames(rs.getString(SURNAMES_COLUMN));
		profile.setBiography(rs.getString(BIOGRAPHY_COLUMN));
		profile.setAddress(rs.getString(ADDRESS_COLUMN));
		profile.setBirthday(rs.getDate(BIRTHDAY_COLUMN));
		profile.setUserId(rs.getInt(USER_ID_COLUMN));

		return profile;
	}
	

}
