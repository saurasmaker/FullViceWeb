package com.fullvicie.daos.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fullvicie.database.connections.MySqlConnection;
import com.fullvicie.enums.*;
import com.fullvicie.factories.DataBaseConnectionFactory;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.ForumMessage;

public class MySQLForumMessageDAO implements IDao<ForumMessage>{

	/*
	 * Static Attributes
	 */
	public static String TABLE_NAME = "forum_messages", ID_COLUMN="id", MESSAGE_COLUMN="message", MADE_DATE_COLUMN="made_date", MADE_TIME_COLUMN = "made_time",
			LAST_EDIT_DATE_COLUMN = "last_edit_date", LAST_EDIT_TIME_COLUMN = "last_edit_time", LIKES_COLUMN = "likes", DISLIKES_COLUMN = "dislikes",
			FORUM_ID_COLUMN = "forum_id", USER_ID_COLUMN = "user_id";
	

	/*
	 * Singleton
	 */
	private static MySQLForumMessageDAO instance;
	private Connection connection;
	private MySQLForumMessageDAO() {
		this.connection = ((MySqlConnection) DataBaseConnectionFactory.getConnection(DataBaseConnectionFactory.MYSQL_ENGINE)).getDatabaseConnection();
	}
	public static MySQLForumMessageDAO getInstance() {	
		
		if(instance == null)
			instance = new MySQLForumMessageDAO();	
		
		return instance;
	}
		
	
	
	/*
	 * Methods
	 */
	@Override
	public ErrorType create(ForumMessage fm) {
	
		ErrorType et = executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "(" 
			+ MESSAGE_COLUMN + ", "
			+ MADE_DATE_COLUMN + ", " 
			+ MADE_TIME_COLUMN + ", "
			+ LAST_EDIT_DATE_COLUMN + ", "
			+ LAST_EDIT_TIME_COLUMN + ", "
			+ LIKES_COLUMN + ", "
			+ DISLIKES_COLUMN + ", "
			+ FORUM_ID_COLUMN + ", "
			+ USER_ID_COLUMN + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", fm);	
		
		if(et == ErrorType.ERROR)
			return ErrorType.CREATE_FORUM_ERROR;
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ForumMessage read(String search, SearchBy searchBy) throws SQLException {
		// Declaration of variables
		PreparedStatement stat = null;
		ResultSet rs = null;
		ForumMessage fm = null;
		
		// Initialize query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		// Logic
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);	
			if(rs.next()) {
				fm = convert(rs);
			} else { 
				throw new SQLException("");
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return fm;
	}
	

	@Override
	public ErrorType update(String search, SearchBy searchBy, ForumMessage fm) {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ MESSAGE_COLUMN + " = ?, "
			+ MADE_DATE_COLUMN + " = ?, "
			+ MADE_TIME_COLUMN + " = ?, "
			+ LAST_EDIT_DATE_COLUMN + " = ?, "
			+ LAST_EDIT_TIME_COLUMN + " = ?, "
			+ LIKES_COLUMN + " = ?, "
			+ DISLIKES_COLUMN + " = ?, "
			+ FORUM_ID_COLUMN + " = ?, "
			+ USER_ID_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		if(ErrorType.ERROR == executeQueryWithParameters(updateQuery, fm))
			et =ErrorType.UPDATE_FORUM_MESSAGE_ERROR;

		return et;
	}

	@Override
	public ErrorType delete(String search, SearchBy searchBy) {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		deleteQuery = IDao.appendMySqlSearchBy(deleteQuery, searchBy, search);
		if(ErrorType.ERROR == executeQueryWithParameters(deleteQuery, null))
			et = ErrorType.DELETE_FORUM_MESSAGE_ERROR;
		
		return et;
	}

	
	@Override
	public ArrayList<ForumMessage> listBy(String search, SearchBy searchBy) throws SQLException {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<ForumMessage> forumMessagesList = new ArrayList<ForumMessage>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);
			while(rs.next()) {
				forumMessagesList.add(convert(rs));
			}	
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return forumMessagesList;
	}

	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, ForumMessage fm) {
		ErrorType et = ErrorType.NO_ERROR;
		ForumMessage actualFm;
		try {
			actualFm = read(String.valueOf(fm.getId()), SearchBy.ID);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return ErrorType.ERROR;
		}
		int pos = 1;
		
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(query);
			if(fm != null) {
				if(fm.getMessage() != null) stat.setString(pos++, fm.getMessage());
				else if(actualFm != null) stat.setString(pos++, actualFm.getMessage());
				else stat.setString(pos++, null);
				
				if(fm.getMadeDate() != null) stat.setDate(pos++, fm.getMadeDate());
				else if(actualFm != null) stat.setDate(pos++, actualFm.getMadeDate());
				else stat.setString(pos++, null);
				
				if(fm.getMadeTime() != null) stat.setTime(pos++, fm.getMadeTime());
				else if(actualFm != null) stat.setTime(pos++, actualFm.getMadeTime());
				else stat.setString(pos++, null);
				
				if(fm.getLastEditDate() != null) stat.setDate(pos++, fm.getLastEditDate());
				else if(actualFm != null) stat.setDate(pos++, actualFm.getLastEditDate());
				else stat.setString(pos++, null); 
				
				if(fm.getLastEditTime() != null) stat.setTime(pos++, fm.getLastEditTime());
				else if(actualFm != null) stat.setTime(pos++, actualFm.getLastEditTime());
				else stat.setString(pos++, null); 
				
				if(fm.getLikes() != 0) stat.setInt(pos++, fm.getLikes());
				else if(actualFm != null) stat.setInt(pos++, actualFm.getLikes());
				else stat.setInt(pos++, 0);
				
				if(fm.getDislikes() != 0) stat.setInt(pos++, fm.getDislikes());
				else if(actualFm != null) stat.setInt(pos++, actualFm.getDislikes());
				else stat.setInt(pos++, 0);
				
				if(fm.getForumId() != 0) stat.setInt(pos++, fm.getForumId());
				else if(actualFm != null) stat.setInt(pos++, actualFm.getForumId());
				else stat.setInt(pos++, 0);
				
				if(fm.getUserId() != 0) stat.setInt(pos++, fm.getUserId());
				else if(actualFm != null) stat.setInt(pos++, actualFm.getUserId());
				else stat.setInt(pos++, 0);
			}
			stat.execute();
		} catch (SQLException e) {
			et = ErrorType.ERROR;
		} finally {
			IDao.closeMySql(null, stat);
		}
		
		return et;
	}
	
	private ForumMessage convert(ResultSet rs) throws SQLException {
		
		ForumMessage fml = new ForumMessage();
		fml.setId(rs.getInt(ID_COLUMN));
		fml.setMessage(rs.getString(MESSAGE_COLUMN));
		fml.setMadeDate(rs.getDate(MADE_DATE_COLUMN));
		fml.setMadeTime(rs.getTime(MADE_TIME_COLUMN));
		fml.setLastEditDate(rs.getDate(LAST_EDIT_DATE_COLUMN));
		fml.setLastEditTime(rs.getTime(LAST_EDIT_TIME_COLUMN));
		fml.setLikes(rs.getInt(LIKES_COLUMN));
		fml.setDislikes(rs.getInt(DISLIKES_COLUMN));
		fml.setForumId(rs.getInt(FORUM_ID_COLUMN));
		fml.setUserId(rs.getInt(USER_ID_COLUMN));
		
		return fml;
	}

}
