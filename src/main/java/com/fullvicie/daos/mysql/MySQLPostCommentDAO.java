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
import com.fullvicie.pojos.PostComment;

public class MySQLPostCommentDAO implements IDao<PostComment>{

	/*
	 * Static Attributes
	 */
	public static String TABLE_NAME = "post_comments", ID_COLUMN="id", MESSAGE_COLUMN="message", LIKES_COLUMN = "likes", DISLIKES_COLUMN = "dislikes",
			MADE_DATE_COLUMN="made_date", MADE_TIME_COLUMN = "made_time", LAST_EDIT_DATE_COLUMN = "last_edit_date", LAST_EDIT_TIME_COLUMN = "last_edit_time",
			POST_ID_COLUMN = "post_id", USER_ID_COLUMN = "user_id";
	
	

	/*
	 * Singleton
	 */
	private static MySQLPostCommentDAO instance;
	private Connection connection;
	private MySQLPostCommentDAO() {
		this.connection = ((MySqlConnection) DataBaseConnectionFactory.getConnection(DataBaseConnectionFactory.MYSQL_ENGINE)).getDatabaseConnection();
	}
	public static MySQLPostCommentDAO getInstance() {	
		
		if(instance == null)
			instance = new MySQLPostCommentDAO();	
		
		return instance;
	}
		
	
	
	/*
	 * Methods
	 */
	@Override
	public ErrorType create(PostComment pc) {
		ErrorType et = executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "("
			+ MESSAGE_COLUMN + ", "
			+ LIKES_COLUMN + ", " 
			+ DISLIKES_COLUMN + ", "
			+ MADE_DATE_COLUMN + ", "
			+ MADE_TIME_COLUMN + ", "
			+ LAST_EDIT_DATE_COLUMN + ", "
			+ LAST_EDIT_TIME_COLUMN + ", "
			+ POST_ID_COLUMN + ", "
			+ USER_ID_COLUMN + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", pc);	
		
		if(et == ErrorType.ERROR)
			return ErrorType.CREATE_POST_COMMENT_ERROR;
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public PostComment read(String search, SearchBy searchBy) throws SQLException {
		
		// Declaration of variables
		PreparedStatement stat = null;
		ResultSet rs = null;
		PostComment pc = null;
		
		// Initialize query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		// Logic
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);	
			if(rs.next()) {
				pc = convert(rs);
			} else { 
				throw new SQLException("");
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return pc;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, PostComment pc) {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ MESSAGE_COLUMN + " = ?, "
			+ LIKES_COLUMN + " = ?, "
			+ DISLIKES_COLUMN + " = ?, "
			+ MADE_DATE_COLUMN + " = ?, "
			+ MADE_TIME_COLUMN + " = ?, "
			+ LAST_EDIT_DATE_COLUMN + " = ?, "
			+ LAST_EDIT_TIME_COLUMN + " = ?, "
			+ POST_ID_COLUMN + " = ?, "
			+ USER_ID_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		if(ErrorType.ERROR == executeQueryWithParameters(updateQuery, pc))
			et = ErrorType.UPDATE_POST_COMMENT_ERROR;

		return et;
	}

	@Override
	public ErrorType delete(String search, SearchBy searchBy) {

		ErrorType et = ErrorType.NO_ERROR;
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		deleteQuery = IDao.appendMySqlSearchBy(deleteQuery, searchBy, search);
		if(ErrorType.ERROR == executeQueryWithParameters(deleteQuery, null))
			et = ErrorType.DELETE_POST_COMMENT_ERROR;
		
		return et;
	}

	@Override
	public ArrayList<PostComment> listBy(String search, SearchBy searchBy) throws SQLException {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<PostComment> postCommentsList = new ArrayList<PostComment>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);
			while(rs.next()) {
				postCommentsList.add(convert(rs));
			}	
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return postCommentsList;
	}

	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, PostComment pc) {
		ErrorType et = ErrorType.NO_ERROR;
		PostComment actualPc;
		try {
			actualPc = read(String.valueOf(pc.getId()), SearchBy.ID);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return ErrorType.ERROR;
		}
		int pos = 1;
		
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(query);
			if(pc != null) {
				if(pc.getMessage() != null) stat.setString(pos++, pc.getMessage());
				else if(actualPc != null) stat.setString(pos++, actualPc.getMessage());
				else stat.setString(pos++, null);
				
				if(pc.getLikes() != 0) stat.setInt(pos++, pc.getLikes());
				else if(actualPc != null) stat.setInt(pos++, actualPc.getLikes());
				else stat.setInt(pos++, 0);
				
				if(pc.getDislikes() != 0) stat.setInt(pos++, pc.getDislikes());
				else if(actualPc != null) stat.setInt(pos++, actualPc.getDislikes());
				else stat.setInt(pos++, 0);
				
				if(pc.getMadeDate() != null) stat.setDate(pos++, pc.getMadeDate());
				else if(actualPc != null) stat.setDate(pos++, actualPc.getMadeDate());
				else stat.setDate(pos++, null);
				
				if(pc.getMadeTime() != null) stat.setTime(pos++, pc.getMadeTime());
				else if(actualPc != null) stat.setTime(pos++, actualPc.getMadeTime());
				else stat.setTime(pos++, null);
				
				if(pc.getLastEditDate() != null) stat.setDate(pos++, pc.getLastEditDate());
				else if(actualPc != null) stat.setDate(pos++, actualPc.getLastEditDate());
				else stat.setDate(pos++, null);
				
				if(pc.getLastEditTime() != null) stat.setTime(pos++, pc.getLastEditTime());
				else if(actualPc != null) stat.setTime(pos++, actualPc.getLastEditTime());
				else stat.setTime(pos++, null);
				
				if(pc.getPostId() != 0) stat.setInt(pos++, pc.getPostId());
				else if(actualPc != null) stat.setInt(pos++, actualPc.getPostId());
				else stat.setInt(pos++, 0);
				
				if(pc.getUserId() != 0) stat.setInt(pos++, pc.getUserId());
				else if(actualPc != null) stat.setInt(pos++, actualPc.getUserId());
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
	
	private PostComment convert(ResultSet rs) throws SQLException {
		
		PostComment pc = new PostComment();
		pc.setId(rs.getInt(ID_COLUMN));
		pc.setMessage(rs.getString(MESSAGE_COLUMN));
		pc.setMadeDate(rs.getDate(MADE_DATE_COLUMN));
		pc.setMadeTime(rs.getTime(MADE_TIME_COLUMN));
		pc.setLastEditDate(rs.getDate(LAST_EDIT_DATE_COLUMN));
		pc.setLastEditTime(rs.getTime(LAST_EDIT_TIME_COLUMN));
		pc.setLikes(rs.getInt(LIKES_COLUMN));
		pc.setDislikes(rs.getInt(DISLIKES_COLUMN));
		pc.setPostId(rs.getInt(POST_ID_COLUMN));
		pc.setUserId(rs.getInt(USER_ID_COLUMN));
		
		return pc;
	}
}
