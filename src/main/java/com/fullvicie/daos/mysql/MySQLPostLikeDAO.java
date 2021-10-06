package com.fullvicie.daos.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fullvicie.database.connections.MySqlConnection;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.factories.DataBaseConnectionFactory;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.PostLike;

public class MySQLPostLikeDAO implements IDao<PostLike>{
	
	/*
	 * Static Attributes
	 */
	public static String TABLE_NAME = "forum_message_likes",
		ID_COLUMN="id", DISLIKE_COLUMN="dilike", POST_ID_COLUMN = "post_id", USER_ID_COLUMN="user_id";
	
	
	/*
	 * Singleton
	 */
	private static MySQLPostLikeDAO instance;
	private Connection connection;
	private MySQLPostLikeDAO() {
		this.connection = ((MySqlConnection) DataBaseConnectionFactory.getConnection(DataBaseConnectionFactory.MYSQL_ENGINE)).getDatabaseConnection();
	}
	public static MySQLPostLikeDAO getInstance() {	
		
		if(instance == null)
			instance = new MySQLPostLikeDAO();
		
		return instance;
	}
	
	
	
	/*
	 * Methods
	 */
	@Override
	public ErrorType create(PostLike pl) {
		
		ErrorType et = executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "("
				+ DISLIKE_COLUMN + ", "
				+ POST_ID_COLUMN + ", "
				+ USER_ID_COLUMN + ") VALUES (?, ?, ?)", pl);	
		
		if(et == ErrorType.ERROR)
			return ErrorType.CREATE_POST_LIKE_ERROR;
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public PostLike read(String search, SearchBy searchBy) throws SQLException {
		// Declaration of variables
		PreparedStatement stat = null;
		ResultSet rs = null;
		PostLike pl = null;
		
		// Initialize query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		// Logic
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);	
			if(rs.next()) {
				pl = convert(rs);
			} else { 
				throw new SQLException("");
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return pl;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, PostLike pl) {

		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET " 
			+ DISLIKE_COLUMN + " = ?, "
			+ POST_ID_COLUMN + " = ?, "
			+ USER_ID_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		if(ErrorType.ERROR == executeQueryWithParameters(updateQuery, pl))
			et = ErrorType.UPDATE_POST_LIKE_ERROR;

		return et;
	}

	@Override
	public ErrorType delete(String search, SearchBy searchBy) {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		deleteQuery = IDao.appendMySqlSearchBy(deleteQuery, searchBy, search);
		if(ErrorType.ERROR == executeQueryWithParameters(deleteQuery, null))
			et = ErrorType.DELETE_POST_LIKE_ERROR;
		
		return et;
	}
	
	@Override
	public ArrayList<PostLike> listBy(String search, SearchBy searchBy) throws SQLException {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<PostLike> postLikesList = new ArrayList<PostLike>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);
			while(rs.next()) {
				postLikesList.add(convert(rs));
			}	
			rs.close();
		} catch (Exception e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return postLikesList;
	}

	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, PostLike pl) {
		ErrorType et = ErrorType.NO_ERROR;
		PostLike actualPl = null;
		try {
			actualPl = read(String.valueOf(pl.getId()), SearchBy.ID);
		} catch (SQLException e1) {
			e1.printStackTrace();
			et = ErrorType.ERROR;
		}
		int pos = 1;
		
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(query);
			if(pl != null) {
				stat.setBoolean(pos++, pl.isDislike());
				
				if(pl.getPostId() != 0) stat.setInt(pos++, pl.getPostId());
				else if(actualPl != null) stat.setInt(pos++, actualPl.getPostId());
				else stat.setInt(pos++, 0);
				
				if(pl.getUserId() != 0) stat.setInt(pos++, pl.getUserId());
				else if(actualPl != null) stat.setInt(pos++, actualPl.getUserId());
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
	
	private PostLike convert(ResultSet rs) throws SQLException {
		
		PostLike pl = new PostLike();
		pl.setId(rs.getInt(ID_COLUMN));
		pl.setDislike(rs.getBoolean(DISLIKE_COLUMN));
		pl.setPostId(rs.getInt(POST_ID_COLUMN));
		pl.setUserId(rs.getInt(USER_ID_COLUMN));
		
		return pl;
	}
}
