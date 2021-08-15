package com.fullvicie.daos.sql;

import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.apache.tomcat.util.codec.binary.Base64;

import com.fullvicie.controllers.DatabaseController;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.User;



public class UserSqlDao implements IDao<User>{
	
	/*
	 * STATIC ATTRIBUTES
	 */
	public static int USER_COUNT = 0;
	
	public static String TABLE_NAME = "users", ID_COLUMN="id", USERNAME_COLUMN="username", EMAIL_COLUMN="email", PASSWORD_COLUMN="passwrd",
			SIGN_UP_DATE_COLUMN="sign_up_date", SIGN_UP_TIME_COLUMN="sign_up_time",
			LAST_LOGOUT_DATE_COLUMN="last_logout_date", LAST_LOGOUT_TIME_COLUMN="last_logout_time",
			DELETED_COLUMN = "deleted", DELETE_DATE_COLUMN="delete_date", DELETE_TIME_COLUMN="delete_time",
			IS_MODERATOR_COLUMN="moderator", IS_ADMIN_COLUMN="admin", PICTURE_COLUMN="picture";
	
	/*
	 * CRUD Methods
	 */
	@Override
	public ErrorType create(User user) {
		if(user!=null)
		try {
			return executeQueryWithParameters("INSERT INTO " + TABLE_NAME + " (" 
					+ USERNAME_COLUMN + ", " 
					+ EMAIL_COLUMN + ", "
					+ SIGN_UP_DATE_COLUMN + ", " 
					+ SIGN_UP_TIME_COLUMN + ", "  
					+ PASSWORD_COLUMN + ", " 
					+ LAST_LOGOUT_DATE_COLUMN + ", " 
					+ LAST_LOGOUT_TIME_COLUMN + ", " 
					+ DELETED_COLUMN + ", "
					+ DELETE_DATE_COLUMN + ", " 
					+ DELETE_TIME_COLUMN + ", " 
					+ IS_MODERATOR_COLUMN + ", " 
					+ IS_ADMIN_COLUMN + ") "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", user);	
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.CREATE_USER_ERROR;
		}
		else
			return ErrorType.USER_NULL_ERROR;
	}

	
	@Override
	public User read(String search, SearchBy searchBy) {
		User user = null;
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "; 
		try {
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);	
			if(rs.next()) { //se valida si hay resultados
				if(rs.getRow() == 1) {
					user = setUserAttributes(rs);
				}
			}
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
			
		return user;
	}


	@Override
	public ErrorType update(String search, SearchBy searchBy, User user) {
		
		if(user!=null)
		try {
			String updateQuery = "UPDATE " + TABLE_NAME + " SET " 
					+ USERNAME_COLUMN + " = ?, "
					+ EMAIL_COLUMN + " = ?, "
					+ PASSWORD_COLUMN + " = ?, "
					+ SIGN_UP_DATE_COLUMN + " = ?, "
					+ SIGN_UP_TIME_COLUMN + " = ?, "
					+ LAST_LOGOUT_DATE_COLUMN + " = ?, "
					+ LAST_LOGOUT_TIME_COLUMN + " = ?, "
					+ DELETED_COLUMN + " = ?, "
					+ DELETE_DATE_COLUMN + " = ?, "
					+ DELETE_TIME_COLUMN + " = ?, "
					+ IS_MODERATOR_COLUMN + " = ?, "
					+ IS_ADMIN_COLUMN + " = ? WHERE ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, searchBy, search);			
			executeQueryWithParameters(updateQuery, user);
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.UPDATE_USER_ERROR;
		}
		else
			return ErrorType.USER_NULL_ERROR;
		
		return ErrorType.NO_ERROR;
	}


	@Override
	public ErrorType delete(String search, SearchBy searchBy) {
		
		String deleteQuery = "DELETE FROM users WHERE ";
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
			User user = read(search, searchBy);
			
			// Define Query
			String updateQuery = "UPDATE " + TABLE_NAME + " SET "
					+ DELETED_COLUMN + " = ?, " + DELETE_DATE_COLUMN + " = ?, " + DELETE_TIME_COLUMN + " = ? WHERE ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, SearchBy.ID, String.valueOf(user.getId()));			
			
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
			return ErrorType.UPDATE_USER_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}
	
	
	@Override
	public ArrayList<User> list() {
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 		
		ResultSet rs = null;
		ArrayList<User> usersList = new ArrayList<User>();
		
		try {
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);					
			while(rs.next()) {
				User user = setUserAttributes(rs);
				usersList.add(user);
			}	
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
		
		return usersList;
	}
	
	
	
	/*
	 * Specific Methods
	 */
	public ErrorType updateLastLogoutDatetime(String search, SearchBy searchBy, Date logoutDate, Time logoutTime) {
		
		if(logoutDate!=null && logoutTime!=null)
		try {
			// Define Query
			String updateQuery = "UPDATE " + TABLE_NAME + " SET "
					+ LAST_LOGOUT_DATE_COLUMN + " = ?, "
					+ LAST_LOGOUT_TIME_COLUMN + " = ? WHERE ";
			updateQuery = IDao.appendSqlSearchBy(updateQuery, SearchBy.ID, search);			
			
			// Prepare & Execute Statement
			PreparedStatement preparedStatement = null;
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(updateQuery);
			preparedStatement.setDate(1, logoutDate);
			preparedStatement.setTime(2, logoutTime);
			preparedStatement.execute();
			preparedStatement.close();
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.UPDATE_USER_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}

	public ErrorType updatePicture(String search, SearchBy searchBy, InputStream picture) {
		if(picture!=null)
			try {
				// Define Query
				String updateQuery = "UPDATE " + TABLE_NAME + " SET "
						+ PICTURE_COLUMN + " = ? WHERE ";
				updateQuery = IDao.appendSqlSearchBy(updateQuery, SearchBy.ID, search);			
				
				// Prepare & Execute Statement
				PreparedStatement preparedStatement = null;
				preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(updateQuery);
				preparedStatement.setBlob(1, picture);
				preparedStatement.execute();
				preparedStatement.close();
			} catch(Exception e) {
				e.printStackTrace();
				return ErrorType.UPDATE_USER_ERROR;
			}
			
			return ErrorType.NO_ERROR;
	}
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, User user) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(query);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setDate(4, user.getSignUpDate());
			preparedStatement.setTime(5, user.getSignUpTime());
			preparedStatement.setDate(6, user.getLastLogoutDate());
			preparedStatement.setTime(7, user.getLastLogoutTime());
			preparedStatement.setBoolean(8, user.getDeleted());
			preparedStatement.setDate(9, user.getDeleteDate());
			preparedStatement.setTime(10, user.getDeleteTime());
			preparedStatement.setBoolean(11, user.isModerator());
			preparedStatement.setBoolean(12, user.isAdmin());
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.DATABASE_STATEMENT_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private User setUserAttributes(ResultSet rs) {
		User user = null;
		try {
			user = new User();
			user.setId(rs.getInt(ID_COLUMN));
			user.setUsername(rs.getString(USERNAME_COLUMN));
			user.setBase64Picture(Base64.encodeBase64String(rs.getBytes(PICTURE_COLUMN)));
			user.setEmail(rs.getString(EMAIL_COLUMN));
			user.setPassword(rs.getString(PASSWORD_COLUMN));
			user.setSignUpDate(rs.getDate(SIGN_UP_DATE_COLUMN));
			user.setSignUpTime(rs.getTime(SIGN_UP_TIME_COLUMN));
			user.setLastLogoutDate(rs.getDate(LAST_LOGOUT_TIME_COLUMN));
			user.setLastLogoutTime(rs.getTime(LAST_LOGOUT_TIME_COLUMN));
			user.setDeleted(rs.getBoolean(DELETED_COLUMN));
			user.setDeleteDate(rs.getDate(DELETE_DATE_COLUMN));
			user.setDeleteTime(rs.getTime(DELETE_TIME_COLUMN));
			user.setAdmin(rs.getBoolean(IS_ADMIN_COLUMN));
			user.setModerator(rs.getBoolean(IS_MODERATOR_COLUMN));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}


	
}
