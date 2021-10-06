package com.fullvicie.daos.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.tomcat.util.codec.binary.Base64;

import com.fullvicie.database.connections.MySqlConnection;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.factories.DataBaseConnectionFactory;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.User;


public class MySQLUserDAO implements IDao<User>{
	
	/*
	 * Static Attributes
	 */	
	public static String TABLE_NAME = "users", ID_COLUMN="id", USERNAME_COLUMN="username", EMAIL_COLUMN="email", PASSWORD_COLUMN="passwrd",
			SIGN_UP_DATE_COLUMN="sign_up_date", SIGN_UP_TIME_COLUMN="sign_up_time",
			LAST_LOGOUT_DATE_COLUMN="last_logout_date", LAST_LOGOUT_TIME_COLUMN="last_logout_time",
			IS_MODERATOR_COLUMN="is_moderator", IS_ADMINISTRATOR_COLUMN="is_administrator", PICTURE_COLUMN="picture";
	
	
	/*
	 * Singleton
	 */
	private static MySQLUserDAO instance;
	private Connection connection;
	private MySQLUserDAO() {
		this.connection = ((MySqlConnection) DataBaseConnectionFactory.getConnection(DataBaseConnectionFactory.MYSQL_ENGINE)).getDatabaseConnection();
	}
	public static MySQLUserDAO getInstance() {	
		
		if(instance == null)
			instance = new MySQLUserDAO();	
		
		return instance;
	}
		
	
	
	/*
	 * Methods
	 */
	@Override
	public ErrorType create(User user) {

		ErrorType et = executeQueryWithParameters("INSERT INTO " + TABLE_NAME + " (" 
			+ USERNAME_COLUMN + ", " 
			+ EMAIL_COLUMN + ", "
			+ PASSWORD_COLUMN + ", "
			+ PICTURE_COLUMN + ", "
			+ SIGN_UP_DATE_COLUMN + ", " 
			+ SIGN_UP_TIME_COLUMN + ", "  
			+ LAST_LOGOUT_DATE_COLUMN + ", " 
			+ LAST_LOGOUT_TIME_COLUMN + ", " 
			+ IS_MODERATOR_COLUMN + ", " 
			+ IS_ADMINISTRATOR_COLUMN + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", user);
		
		if(et == ErrorType.ERROR)
			return ErrorType.CREATE_USER_ERROR;
		
		return ErrorType.NO_ERROR;
	}

	
	@Override
	public User read(String search, SearchBy searchBy) throws SQLException {
		
		// Declaration of variables
		PreparedStatement stat = null;
		ResultSet rs = null;
		User u = null;
		
		// Initialize query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		// Logic
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);	
			if(rs.next()) {
				u = convert(rs);
			} else { 
				throw new SQLException();
			} 
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return u;
	}


	@Override
	public ErrorType update(String search, SearchBy searchBy, User u) {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ USERNAME_COLUMN + " = ?, "
			+ EMAIL_COLUMN + " = ?, "
			+ PASSWORD_COLUMN + " = ?, "
			+ PICTURE_COLUMN + " = ?, "
			+ SIGN_UP_DATE_COLUMN + " = ?, "
			+ SIGN_UP_TIME_COLUMN + " = ?, "
			+ LAST_LOGOUT_DATE_COLUMN + " = ?, "
			+ LAST_LOGOUT_TIME_COLUMN + " = ?, "
			+ IS_MODERATOR_COLUMN + " = ?, "
			+ IS_ADMINISTRATOR_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		if(ErrorType.ERROR == executeQueryWithParameters(updateQuery, u))
			et = ErrorType.UPDATE_USER_ERROR;

		return et;
	}


	@Override
	public ErrorType delete(String search, SearchBy searchBy) {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		deleteQuery = IDao.appendMySqlSearchBy(deleteQuery, searchBy, search);
		if(ErrorType.ERROR == executeQueryWithParameters(deleteQuery, null))
			et = ErrorType.DELETE_USER_ERROR;
		
		return et;
	}

	
	@Override
	public ArrayList<User> listBy(String search, SearchBy searchBy) throws SQLException {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<User> usersList = new ArrayList<User>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);
			while(rs.next()) {
				usersList.add(convert(rs));
			}	
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException();
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return usersList;
	}
	

	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, User u) {
		ErrorType et = ErrorType.NO_ERROR;
		User actuarU = null;
		try {
			actuarU = read(String.valueOf(u.getId()), SearchBy.ID);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return ErrorType.ERROR;
		}
		int pos = 1;
		
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(query);
			if(u != null) {
				if(u.getUsername() != null) stat.setString(1, u.getUsername());
				else if(actuarU != null) stat.setString(1, actuarU.getUsername());
				else stat.setString(pos++, null);
				
				if(u.getEmail() != null) stat.setString(pos++, u.getEmail());
				else if(actuarU != null) stat.setString(pos++, actuarU.getEmail());
				else stat.setString(pos++, null);
				
				if(u.getPassword() != null) stat.setString(pos++, u.getPassword());
				else if(actuarU != null) stat.setString(pos++, actuarU.getPassword());
				else stat.setString(pos++, null);
				
				if(u.getBase64Picture() != null) stat.setBlob(pos++, new SerialBlob(Base64.decodeBase64(u.getBase64Picture())));
				else if(actuarU != null) stat.setBlob(pos++, new SerialBlob(Base64.decodeBase64(actuarU.getBase64Picture())));
				else stat.setString(pos++, null);
				
				if(u.getSignUpDate() != null) stat.setDate(pos++, u.getSignUpDate());
				else if(actuarU != null) stat.setDate(pos++, actuarU.getSignUpDate());
				else stat.setDate(pos++, null);
				
				if(u.getSignUpTime() != null) stat.setTime(pos++, u.getSignUpTime());
				else if(actuarU != null) stat.setTime(pos++, actuarU.getSignUpTime());
				else stat.setTime(pos++, null);
				
				if(u.getLastLogoutDate() != null) stat.setDate(pos++, u.getLastLogoutDate());
				else if(actuarU != null) stat.setDate(pos++, actuarU.getLastLogoutDate());
				else stat.setDate(pos++, null);
				
				if(u.getLastLogoutTime() != null) stat.setTime(pos++, u.getLastLogoutTime());
				else if(actuarU != null) stat.setTime(pos++, actuarU.getLastLogoutTime());
				else stat.setTime(pos++, null);
				
				stat.setBoolean(pos++, u.isModerator());
				stat.setBoolean(pos++, u.isAdmin());
			}
			stat.execute();
		} catch (SQLException e) {
			et = ErrorType.ERROR;
		} finally {
			IDao.closeMySql(null, stat);
		}
		
		return et;
	}
	
	private User convert(ResultSet rs) throws SQLException{
		
		User user = new User();
		user.setId(rs.getInt(ID_COLUMN));
		user.setUsername(rs.getString(USERNAME_COLUMN));
		user.setEmail(rs.getString(EMAIL_COLUMN));
		user.setPassword(rs.getString(PASSWORD_COLUMN));
		user.setBase64Picture(Base64.encodeBase64String(rs.getBytes(PICTURE_COLUMN)));
		user.setSignUpDate(rs.getDate(SIGN_UP_DATE_COLUMN));
		user.setSignUpTime(rs.getTime(SIGN_UP_TIME_COLUMN));
		user.setLastLogoutDate(rs.getDate(LAST_LOGOUT_DATE_COLUMN));
		user.setLastLogoutTime(rs.getTime(LAST_LOGOUT_TIME_COLUMN));
		user.setModerator(rs.getBoolean(IS_MODERATOR_COLUMN));
		user.setAdmin(rs.getBoolean(IS_ADMINISTRATOR_COLUMN));
		
		return user;
	}


	
}
