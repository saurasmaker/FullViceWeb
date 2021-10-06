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
import com.fullvicie.pojos.ForumMessageLike;

public class MySQLForumMessageLikeDAO implements IDao<ForumMessageLike>{
	
	/*
	 * Static Attributes
	 */
	public static String TABLE_NAME = "forum_message_likes",
		ID_COLUMN="id", DISLIKE_COLUMN="dilike", FORUM_MESSAGE_ID_COLUMN = "forum_message_id", USER_ID_COLUMN="user_id";
	
	
	/*
	 * Singleton
	 */
	private static MySQLForumMessageLikeDAO instance;
	private Connection connection;
	private MySQLForumMessageLikeDAO() {
		this.connection = ((MySqlConnection) DataBaseConnectionFactory.getConnection(DataBaseConnectionFactory.MYSQL_ENGINE)).getDatabaseConnection();
	}
	public static MySQLForumMessageLikeDAO getInstance() {	
		
		if(instance == null)
			instance = new MySQLForumMessageLikeDAO();	
		
		return instance;
	}
	
	
	
	/*
	 * Methods
	 */
	@Override
	public ErrorType create(ForumMessageLike fml) {
		
		ErrorType et = executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "("
				+ DISLIKE_COLUMN + ", "
				+ FORUM_MESSAGE_ID_COLUMN + ", "
				+ USER_ID_COLUMN + ") VALUES (?, ?, ?)", fml);	
		
		if(et == ErrorType.ERROR)
			return ErrorType.CREATE_FORUM_MESSAGE_LIKE_ERROR;
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ForumMessageLike read(String search, SearchBy searchBy) throws SQLException {
		
		// Declaration of variables
		PreparedStatement stat = null;
		ResultSet rs = null;
		ForumMessageLike fml = null;
		
		// Initialize query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		// Logic
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);	
			if(rs.next()) {
				fml = convert(rs);
			} else { 
				throw new SQLException("");
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return fml;
	}
	

	@Override
	public ErrorType update(String search, SearchBy searchBy, ForumMessageLike fml) {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET " 
			+ DISLIKE_COLUMN + " = ?, "
			+ FORUM_MESSAGE_ID_COLUMN + " = ?, "
			+ USER_ID_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		if(ErrorType.ERROR == executeQueryWithParameters(updateQuery, fml))
			et = ErrorType.UPDATE_FORUM_ERROR;

		return et;
	}

	@Override
	public ErrorType delete(String search, SearchBy searchBy) {

		ErrorType et = ErrorType.NO_ERROR;
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		deleteQuery = IDao.appendMySqlSearchBy(deleteQuery, searchBy, search);
		if(ErrorType.ERROR == executeQueryWithParameters(deleteQuery, null))
			et = ErrorType.DELETE_FORUM_MESSAGE_LIKE_ERROR;
		
		return et;
	}

	
	@Override
	public ArrayList<ForumMessageLike> listBy(String search, SearchBy searchBy) throws SQLException {

		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<ForumMessageLike> forumMessageLikesList = new ArrayList<ForumMessageLike>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);
			while(rs.next()) {
				forumMessageLikesList.add(convert(rs));
			}	
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return forumMessageLikesList;
	}

	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, ForumMessageLike fml) {
		ErrorType et = ErrorType.NO_ERROR;
		ForumMessageLike actualFml;
		try {
			actualFml = read(String.valueOf(fml.getId()), SearchBy.ID);
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.ERROR;
		}
		int pos = 1;
		
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(query);
			if(fml != null) {
				stat.setBoolean(pos++, fml.isDislike());
				
				if(fml.getForumMessageId() != 0) stat.setInt(pos++, fml.getForumMessageId());
				else if(actualFml != null) stat.setInt(pos++, actualFml.getForumMessageId());
				else stat.setInt(pos++, 0);
				
				if(fml.getUserId() != 0) stat.setInt(pos++, fml.getUserId());
				else if(actualFml != null) stat.setInt(pos++, actualFml.getUserId());
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
	
	private ForumMessageLike convert(ResultSet rs) throws SQLException {
		
		ForumMessageLike fml = new ForumMessageLike();
		fml.setId(rs.getInt(ID_COLUMN));
		fml.setDislike(rs.getBoolean(DISLIKE_COLUMN));
		fml.setForumMessageId(rs.getInt(FORUM_MESSAGE_ID_COLUMN));
		fml.setUserId(rs.getInt(USER_ID_COLUMN));
		
		return fml;
	}

}
