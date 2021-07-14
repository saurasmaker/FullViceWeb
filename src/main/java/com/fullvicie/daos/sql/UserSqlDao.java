package com.fullvicie.daos.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
	
	public static String ID_COLUMN="id", USERNAME_COLUMN="username", EMAIL_COLUMN="email", PASSWORD_COLUMN="passwrd",
			SIGN_UP_DATE_COLUMN="sign_up_date", SIGN_UP_TIME_COLUMN="sign_up_time",
			LAST_LOGOUT_DATE_COLUMN="last_logout_date", LAST_LOGOUT_TIME_COLUMN="last_logout_time",
			IS_MODERATOR_COLUMN="moderator", IS_ADMIN_COLUMN="admin";
	
	/*
	 * CRUD Methods
	 */
	@Override
	public ErrorType create(User user) {
		try {
			return executeQueryWithParameters("INSERT INTO "
					+ "users (" + USERNAME_COLUMN + ", " + EMAIL_COLUMN + ", " + PASSWORD_COLUMN + ", " 
						+ SIGN_UP_DATE_COLUMN + ", " + SIGN_UP_TIME_COLUMN + ", "
						+ LAST_LOGOUT_DATE_COLUMN + ", " + LAST_LOGOUT_DATE_COLUMN + ", "
						+ IS_MODERATOR_COLUMN + ", " + IS_ADMIN_COLUMN + ") "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", user);	
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.CREATE_USER_ERROR;
		}
	}

	
	@Override
	public User read(String search, SearchBy searchBy) {
		User user = null;
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM users WHERE "; 
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
		try {
			String updateQuery = "UPDATE users SET " + USERNAME_COLUMN + " = ?, "
					+ EMAIL_COLUMN + " = ?, "
					+ PASSWORD_COLUMN + " = ?, "
					+ SIGN_UP_DATE_COLUMN + " = ?, "
					+ SIGN_UP_TIME_COLUMN + " = ?, "
					+ LAST_LOGOUT_DATE_COLUMN + " = ?, "
					+ LAST_LOGOUT_TIME_COLUMN + " = ?, "
					+ IS_MODERATOR_COLUMN + " = ?, "
					+ IS_ADMIN_COLUMN + " = ? WHERE ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, searchBy, search);			
			executeQueryWithParameters(updateQuery, user);
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.UPDATE_USER_ERROR;
		}
		
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
	public ArrayList<User> list() {
		String selectQuery = "SELECT * FROM users"; 		
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
			preparedStatement.setBoolean(8, user.isModerator());
			preparedStatement.setBoolean(9, user.isAdmin());
			
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
			user.setId(rs.getString(ID_COLUMN));
			user.setUsername(rs.getString(USERNAME_COLUMN));
			user.setEmail(rs.getString(EMAIL_COLUMN));
			user.setPassword(rs.getString(PASSWORD_COLUMN));
			user.setSignUpDate(rs.getDate(SIGN_UP_DATE_COLUMN));
			user.setSignUpTime(rs.getTime(SIGN_UP_TIME_COLUMN));
			user.setLastLogoutDate(rs.getDate(LAST_LOGOUT_TIME_COLUMN));
			user.setLastLogoutTime(rs.getTime(LAST_LOGOUT_TIME_COLUMN));
			user.setAdmin(rs.getBoolean(IS_ADMIN_COLUMN));
			user.setModerator(rs.getBoolean(IS_MODERATOR_COLUMN));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
}
