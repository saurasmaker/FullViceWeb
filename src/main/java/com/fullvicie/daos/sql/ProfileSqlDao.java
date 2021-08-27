package com.fullvicie.daos.sql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.fullvicie.controllers.DatabaseController;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.Profile;

public class ProfileSqlDao implements IDao<Profile>{

public static int USER_COUNT = 0;
	
	public static String TABLE_NAME="profiles", ID_COLUMN="id", NAME_COLUMN="name", SURNAMES_COLUMN="surnames", BIOGRAPHY_COLUMN="biography",
			BIRTHDAY_COLUMN="birthday", USER_ID_COLUMN="user_id";
	
	/*
	 * CRUD Methods
	 */
	@Override
	public ErrorType create(Profile profile) {
		if(profile!=null)
		try {
			return executeQueryWithParameters("INSERT INTO " + TABLE_NAME + " (" 
					+ NAME_COLUMN + ", " 
					+ SURNAMES_COLUMN + ", "
					+ BIOGRAPHY_COLUMN + ", " 
					+ BIRTHDAY_COLUMN + ", "  
					+ USER_ID_COLUMN + ") VALUES (?, ?, ?, ?, ?)", profile);	
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.CREATE_PROFILE_ERROR;
		}
		else
			return ErrorType.PROFILE_NULL_ERROR;
	}

	
	@Override
	public Profile read(String search, SearchBy searchBy) {
		Profile profile = null;
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM profiles WHERE "; 
		try {
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);	
			if(rs.next()) { //se valida si hay resultados
				if(rs.getRow() == 1) {
					profile = setProfileAttributes(rs);
				}
			}
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
			
		return profile;
	}


	@Override
	public ErrorType update(String search, SearchBy searchBy, Profile profile) {
		
		if(profile!=null)
		try {
			String updateQuery = "UPDATE " + TABLE_NAME + " SET " 
					+ NAME_COLUMN + " = ?, "
					+ SURNAMES_COLUMN + " = ?, "
					+ BIOGRAPHY_COLUMN + " = ?, "
					+ BIRTHDAY_COLUMN + " = ?, "
					+ USER_ID_COLUMN + " = ? WHERE ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, searchBy, search);			
			executeQueryWithParameters(updateQuery, profile);
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.UPDATE_PROFILE_ERROR;
		}
		else
			return ErrorType.PROFILE_NULL_ERROR;
		
		return ErrorType.NO_ERROR;
	}


	@Override
	public ErrorType delete(String search, SearchBy searchBy) {
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE ";
		try {
			deleteQuery = IDao.appendSqlSearchBy(deleteQuery, searchBy, search);
			DatabaseController.DATABASE_STATEMENT.executeUpdate(deleteQuery);	
		} catch (SQLException e)  {
			e.printStackTrace();
			return ErrorType.DELETE_USER_ERROR;
		}	
		
		return ErrorType.NO_ERROR;
	}

	
	@Override
	public ErrorType pseudoDelete(String search, SearchBy searchBy) {
		try {
			// Get user
			Profile profile = read(search, searchBy);
			
			// Define Query
			String updateQuery = "UPDATE users SET "
					+ NAME_COLUMN + " = ?, "
					+ SURNAMES_COLUMN + " = ?, " 
					+ BIOGRAPHY_COLUMN + " = ? "
					+ BIRTHDAY_COLUMN + " = ? "
					+ USER_ID_COLUMN + " = ? "
					+ "WHERE ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, SearchBy.ID, String.valueOf(profile.getId()));			
			
			// Prepare & Execute Statement
			PreparedStatement preparedStatement = null;
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(updateQuery);
			preparedStatement.setBoolean(1, true);
			preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
			preparedStatement.setTime(3, Time.valueOf(LocalTime.now()));
			preparedStatement.execute();
			preparedStatement.close();
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.UPDATE_PROFILE_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}
	
	
	@Override
	public ArrayList<Profile> list() {
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 		
		ResultSet rs = null;
		ArrayList<Profile> profilesList = new ArrayList<Profile>();
		
		try {
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);					
			while(rs.next()) {
				Profile profile = setProfileAttributes(rs);
				profilesList.add(profile);
			}	
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
		
		return profilesList;
	}
	
	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, Profile profile) {
		PreparedStatement preparedStatement = null;
		Profile actualProfile = read(String.valueOf(profile.getId()), SearchBy.ID);
		try {
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(query);
			
			if(profile.getName() != null) preparedStatement.setString(1, profile.getName());
			else preparedStatement.setString(1, actualProfile.getName());
				
			if(profile.getSurnames() != null) preparedStatement.setString(2, profile.getSurnames());
			else preparedStatement.setString(2, actualProfile.getSurnames());
			
			if(profile.getBiography() != null) preparedStatement.setString(3, profile.getBiography());
			else preparedStatement.setString(3, actualProfile.getBiography());
			
			if(profile.getBirthday() != null) preparedStatement.setDate(4, profile.getBirthday());
			else preparedStatement.setDate(4, actualProfile.getBirthday());
			
			if(profile.getUserId() != -1) preparedStatement.setInt(5, profile.getUserId());
			else preparedStatement.setInt(5, actualProfile.getUserId());

			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.DATABASE_STATEMENT_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private Profile setProfileAttributes(ResultSet rs) {
		Profile profile = null;
		try {
			profile = new Profile();
			profile.setId(rs.getInt(ID_COLUMN));
			profile.setName(rs.getString(NAME_COLUMN));
			profile.setSurnames(rs.getString(SURNAMES_COLUMN));
			profile.setBiography(rs.getString(BIOGRAPHY_COLUMN));
			profile.setBirthday(rs.getDate(BIRTHDAY_COLUMN));
			profile.setUserId(rs.getInt(USER_ID_COLUMN));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return profile;
	}
	

}
