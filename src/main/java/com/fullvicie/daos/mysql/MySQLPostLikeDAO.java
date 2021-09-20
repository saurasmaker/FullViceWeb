package com.fullvicie.daos.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fullvicie.database.connections.MySqlConnection;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.exceptions.DaoException;
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
	public ErrorType create(PostLike pl) throws DaoException {
		
		return executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "("
				+ DISLIKE_COLUMN + ", "
				+ POST_ID_COLUMN + ", "
				+ USER_ID_COLUMN + ") VALUES (?, ?, ?)", pl);	
	}

	@Override
	public PostLike read(String search, SearchBy searchBy) throws DaoException {
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
				throw new DaoException("");
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new DaoException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return pl;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, PostLike pl) throws DaoException {

		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET " 
			+ DISLIKE_COLUMN + " = ?, "
			+ POST_ID_COLUMN + " = ?, "
			+ USER_ID_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		et = executeQueryWithParameters(updateQuery, pl);

		return et;
	}

	@Override
	public ErrorType delete(String search, SearchBy searchBy) throws DaoException {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		deleteQuery = IDao.appendMySqlSearchBy(deleteQuery, searchBy, search);
		et = executeQueryWithParameters(deleteQuery, null);
		
		return et;
	}
	
	@Override
	public ArrayList<PostLike> listBy(String search, SearchBy searchBy) throws DaoException {
		
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
		} catch (SQLException e)  {
			throw new DaoException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return postLikesList;
	}

	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, PostLike pl) throws DaoException {

		PostLike actualPl = read(String.valueOf(pl.getId()), SearchBy.ID);
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
			throw new DaoException("");
		} finally {
			IDao.closeMySql(null, stat);
		}
		
		return ErrorType.NO_ERROR;
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
