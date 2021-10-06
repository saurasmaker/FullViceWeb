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
import com.fullvicie.pojos.Forum;

public class MySQLForumDAO implements IDao<Forum>{

	/*
	 * Static Attributes
	 */
	public static String TABLE_NAME = "forums", ID_COLUMN="id", NAME_COLUMN = "name", DESCRIPTION_COLUMN = "description", TAGS_COLUMN = "tags",
			CREATION_DATE_COLUMN = "creation_date", CREATION_TIME_COLUMN = "creation_time", LATEST_ANSWER_DATE_COLUMN = "latest_answer_date",
			LATEST_ANSWER_TIME_COLUMN = "latest_answer_time", FORUM_CATEGORY_ID_COLUMN = "forum_category_id", USER_ID_COLUMN = "user_id";
	
	
	/*
	 * Singleton
	 */
	private static MySQLForumDAO instance;
	private Connection connection;
	private MySQLForumDAO() {
		this.connection = ((MySqlConnection) DataBaseConnectionFactory.getConnection(DataBaseConnectionFactory.MYSQL_ENGINE)).getDatabaseConnection();
	}
	public static MySQLForumDAO getInstance() {	
		
		if(instance == null)
			instance = new MySQLForumDAO();	
		
		return instance;
	}
	
	@Override
	public ErrorType create(Forum forum) {

		ErrorType et = executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "("
			+ NAME_COLUMN + ", "
			+ DESCRIPTION_COLUMN + ", "
			+ TAGS_COLUMN + ", "
			+ CREATION_DATE_COLUMN + ", "
			+ CREATION_TIME_COLUMN + ", "
			+ LATEST_ANSWER_DATE_COLUMN + ", "
			+ LATEST_ANSWER_TIME_COLUMN + ", "
			+ FORUM_CATEGORY_ID_COLUMN + ", "
			+ USER_ID_COLUMN + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", forum);
		
		if(et == ErrorType.ERROR)
			return ErrorType.CREATE_FORUM_ERROR;
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public Forum read(String search, SearchBy searchBy) throws SQLException {
		// Declaration of variables
		PreparedStatement stat = null;
		ResultSet rs = null;
		Forum f = null;
		
		// Initialize query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		// Logic
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);	
			if(rs.next()) {
				f = convert(rs);
			} else { 
				throw new SQLException();
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException();
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return f;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, Forum forum) {

		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ NAME_COLUMN + " = ?, "
			+ DESCRIPTION_COLUMN + " = ?, "
			+ TAGS_COLUMN + " = ?, "
			+ CREATION_DATE_COLUMN + " = ?, "
			+ CREATION_TIME_COLUMN + " = ?, "
			+ LATEST_ANSWER_DATE_COLUMN + " = ?, "
			+ LATEST_ANSWER_TIME_COLUMN + " = ?, "
			+ FORUM_CATEGORY_ID_COLUMN + " = ?, "
			+ DESCRIPTION_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		if(ErrorType.ERROR == executeQueryWithParameters(updateQuery, forum));
			et = ErrorType.UPDATE_FORUM_ERROR;
			
		return et;
	}

	@Override
	public ErrorType delete(String search, SearchBy searchBy) {

		ErrorType et = ErrorType.NO_ERROR;
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		deleteQuery = IDao.appendMySqlSearchBy(deleteQuery, searchBy, search);
		if(ErrorType.ERROR == executeQueryWithParameters(deleteQuery, null));
			et = ErrorType.DELETE_FORUM_ERROR;
			
		return et;
	}

	
	@Override
	public ArrayList<Forum> listBy(String search, SearchBy searchBy) throws SQLException {

		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<Forum> forumsList = new ArrayList<Forum>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);
			while(rs.next()) {
				forumsList.add(convert(rs));
			}	
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException();
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return forumsList;
	}

	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, Forum f) {
		ErrorType et = ErrorType.NO_ERROR;
		Forum actualF = null;
		try {
			actualF = read(String.valueOf(f.getId()), SearchBy.ID);
		}catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.ERROR;
		}
		int pos = 1;
		
		
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(query);
			if(f != null) {
				if(f.getName() != null) stat.setString(pos++, f.getName());
				else if(actualF != null) stat.setString(pos++, actualF.getName());
				else stat.setString(pos++,  null);
				
				if(f.getDescription() != null) stat.setString(pos++, f.getDescription());
				else if(actualF != null) stat.setString(pos++, actualF.getDescription());
				else stat.setString(pos++, null);
				
				if(f.getTags() != null) stat.setString(pos++, f.getTags());
				else if(actualF != null) stat.setString(pos++, actualF.getTags());
				else stat.setString(pos++, null);
				
				if(f.getCreationDate() != null) stat.setDate(pos++, f.getCreationDate());
				else if(actualF != null) stat.setDate(pos++, actualF.getCreationDate());
				else stat.setDate(pos++, null);
				
				if(f.getCreationTime() != null) stat.setTime(pos++, f.getCreationTime());
				else if (actualF != null) stat.setTime(pos++, actualF.getCreationTime());
				else stat.setTime(pos++, null);
				
				if(f.getLatestAnswerDate() != null) stat.setDate(pos++, f.getLatestAnswerDate());
				else if (actualF != null) stat.setDate(pos++, actualF.getLatestAnswerDate());
				else stat.setDate(pos++, null);
				
				if(f.getLatestAnswerTime() != null) stat.setTime(pos++, f.getLatestAnswerTime());
				else if (actualF != null) stat.setTime(pos++, actualF.getLatestAnswerTime());
				else stat.setTime(pos++, null);
				
				if(f.getForumCategoryId() != 0) stat.setInt(pos++, f.getForumCategoryId());
				else if(actualF != null) stat.setInt(pos++, actualF.getForumCategoryId());
				else stat.setInt(pos++, 0);
				
				if(f.getUserId() != 0) stat.setInt(pos++, f.getUserId());
				else if(actualF != null) stat.setInt(pos++, actualF.getUserId());
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
	
	private Forum convert(ResultSet rs) throws SQLException {
		
		Forum forum = new Forum();
		forum.setId(rs.getInt(ID_COLUMN));
		forum.setName(rs.getString(NAME_COLUMN));
		forum.setDescription(rs.getString(DESCRIPTION_COLUMN));
		forum.setTags(rs.getString(TAGS_COLUMN));
		forum.setCreationDate(rs.getDate(CREATION_DATE_COLUMN));
		forum.setCreationTime(rs.getTime(CREATION_TIME_COLUMN));
		forum.setLatestAnswerDate(rs.getDate(LATEST_ANSWER_DATE_COLUMN));
		forum.setLatestAnswerTime(rs.getTime(LATEST_ANSWER_TIME_COLUMN));
		forum.setForumCategoryId(rs.getInt(FORUM_CATEGORY_ID_COLUMN));
		forum.setUserId(rs.getInt(USER_ID_COLUMN));

		
		return forum;
	}
}
