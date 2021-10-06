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
import com.fullvicie.pojos.PostCommentLike;

public class MySQLPostCommentLikeDAO implements IDao<PostCommentLike>{

	/*
	 * Static Attributes
	 */
	public static String TABLE_NAME = "post_categories",
			ID_COLUMN="id", DISLIKE_COLUMN = "dislike", POST_COMMENT_ID_COLUMN = "post_comment_id", USER_ID_COLUMN = "user_id";
	
	
	/*
	 * Singleton
	 */
	private static MySQLPostCommentLikeDAO instance;
	private Connection connection;
	private MySQLPostCommentLikeDAO() {
		this.connection = ((MySqlConnection) DataBaseConnectionFactory.getConnection(DataBaseConnectionFactory.MYSQL_ENGINE)).getDatabaseConnection();
	}
	public static MySQLPostCommentLikeDAO getInstance() {	
		
		if(instance == null)
			instance = new MySQLPostCommentLikeDAO();	
		
		return instance;
	}
		
	
	
	/*
	 * Methods
	 */
	@Override
	public ErrorType create(PostCommentLike pcl) {
		
		ErrorType et = executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "("
			+ DISLIKE_COLUMN + ", "
			+ POST_COMMENT_ID_COLUMN + ", "
			+ USER_ID_COLUMN + ") VALUES (?, ?, ?)", pcl);
		
		if(et == ErrorType.ERROR)
			return ErrorType.CREATE_POST_COMMENT_LIKE_ERROR;
		
		return ErrorType.NO_ERROR;
	}

	
	@Override
	public PostCommentLike read(String search, SearchBy searchBy) throws SQLException {
		
		// Declaration of variables
		PreparedStatement stat = null;
		ResultSet rs = null;
		PostCommentLike pcl = null;
		
		// Initialize query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		// Logic
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);	
			if(rs.next()) {
				pcl = convert(rs);
			} else { 
				throw new SQLException("");
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return pcl;
	}

	
	@Override
	public ErrorType update(String search, SearchBy searchBy, PostCommentLike pcl) {

		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ DISLIKE_COLUMN + " = ?, "
			+ POST_COMMENT_ID_COLUMN + " = ?, "
			+ USER_ID_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		if(ErrorType.ERROR == executeQueryWithParameters(updateQuery, pcl))
			et = ErrorType.UPDATE_POST_COMMENT_LIKE_ERROR;

		return et;
	}

	
	@Override
	public ErrorType delete(String search, SearchBy searchBy) {

		ErrorType et = ErrorType.NO_ERROR;
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		deleteQuery = IDao.appendMySqlSearchBy(deleteQuery, searchBy, search);
		if(ErrorType.ERROR == executeQueryWithParameters(deleteQuery, null))
			et = ErrorType.DELETE_POST_COMMENT_LIKE_ERROR;
		
		return et;
	}

	
	@Override
	public ArrayList<PostCommentLike> listBy(String search, SearchBy searchBy) throws SQLException {

		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<PostCommentLike> postCommentLikesList = new ArrayList<PostCommentLike>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);
			while(rs.next()) {
				postCommentLikesList.add(convert(rs));
			}	
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return postCommentLikesList;
	}
	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, PostCommentLike pcl) {
		ErrorType et = ErrorType.NO_ERROR;
		PostCommentLike actualPcl = null;
		try {
			actualPcl = read(String.valueOf(pcl.getId()), SearchBy.ID);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return ErrorType.ERROR;
		}
		int pos = 1;
		
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(query);
			if(pcl != null) {
				stat.setBoolean(pos++, pcl.isDislike());
				
				if(pcl.getPostCommentId() != 0) stat.setInt(pos++, pcl.getPostCommentId());
				else if(actualPcl != null) stat.setInt(pos++, actualPcl.getPostCommentId());
				else stat.setInt(pos++, 0);
				
				if(pcl.getUserId() != 0) stat.setInt(pos++, pcl.getUserId());
				else if(actualPcl != null) stat.setInt(pos++, actualPcl.getUserId());
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
	
	
	private PostCommentLike convert(ResultSet rs) throws SQLException {
		PostCommentLike pcl = new PostCommentLike();
		pcl.setId(rs.getInt(ID_COLUMN));
		pcl.setDislike(rs.getBoolean(DISLIKE_COLUMN));
		pcl.setPostCommentId(rs.getInt(POST_COMMENT_ID_COLUMN));
		pcl.setUserId(rs.getInt(USER_ID_COLUMN));

		return pcl;
	}

}
