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
import com.fullvicie.pojos.Post;

public class MySQLPostDAO implements IDao<Post>{
	
	/*
	 * Static Attributes
	 */
	public static String TABLE_NAME = "posts", ID_COLUMN="id", NAME_COLUMN = "name", DESCRIPTION_COLUMN = "description", TAGS_COLUMN = "tags",
			CREATION_DATE_COLUMN = "creation_date", CREATION_TIME_COLUMN = "creation_time", POST_CATEGORY_ID_COLUMN = "post_category_id",
			USER_ID_COLUMN = "user_id";
	
	
	/*
	 * Singleton
	 */
	private static MySQLPostDAO instance;
	private Connection connection;
	private MySQLPostDAO() {
		this.connection = ((MySqlConnection) DataBaseConnectionFactory.getConnection(DataBaseConnectionFactory.MYSQL_ENGINE)).getDatabaseConnection();
	}
	public static MySQLPostDAO getInstance() {	
		
		if(instance == null)
			instance = new MySQLPostDAO();	
		
		return instance;
	}
		
	
	
	/*
	 * Methods
	 */
	@Override
	public ErrorType create(Post p) {
		
		ErrorType et = executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "("
				+ NAME_COLUMN + ", "
				+ DESCRIPTION_COLUMN + ", "
				+ TAGS_COLUMN + ", "
				+ CREATION_DATE_COLUMN + ", "
				+ CREATION_TIME_COLUMN + ", "
				+ POST_CATEGORY_ID_COLUMN + ", "
				+ USER_ID_COLUMN + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", p);
		
		if(et == ErrorType.ERROR)
			return ErrorType.CREATE_POST_ERROR;
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public Post read(String search, SearchBy searchBy) throws SQLException {
		
		// Declaration of variables
		PreparedStatement stat = null;
		ResultSet rs = null;
		Post p = null;
		
		// Initialize query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		// Logic
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);	
			if(rs.next()) {
				p = convert(rs);
			} else { 
				throw new SQLException("");
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return p;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, Post p) {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ NAME_COLUMN + " = ?, "
			+ DESCRIPTION_COLUMN + " = ?, "
			+ TAGS_COLUMN + " = ?, "
			+ CREATION_DATE_COLUMN + " = ?, "
			+ CREATION_TIME_COLUMN + " = ?, "
			+ POST_CATEGORY_ID_COLUMN + " = ?, "
			+ USER_ID_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		if(ErrorType.ERROR ==  executeQueryWithParameters(updateQuery, p))
			et = ErrorType.UPDATE_POST_ERROR;

		return et;
	}

	@Override
	public ErrorType delete(String search, SearchBy searchBy) {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		deleteQuery = IDao.appendMySqlSearchBy(deleteQuery, searchBy, search);
		if(ErrorType.ERROR == executeQueryWithParameters(deleteQuery, null))
			et = ErrorType.DELETE_POST_ERROR;
		
		return et;
	}


	@Override
	public ArrayList<Post> listBy(String search, SearchBy searchBy) throws SQLException {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<Post> postsList = new ArrayList<Post>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);
			while(rs.next()) {
				postsList.add(convert(rs));
			}	
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return postsList;
	}


	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, Post p) {
		ErrorType et = ErrorType.NO_ERROR;
		Post actualP = null;
		try {
			actualP = read(String.valueOf(p.getId()), SearchBy.ID);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return ErrorType.ERROR;
		}
		int pos = 1;
		
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(query);
			if(p != null) {
				if(p.getName() != null) stat.setString(pos++, p.getName());
				else if(actualP != null) stat.setString(pos++, actualP.getName());
				else stat.setString(pos++, null);
				
				if(p.getDescription() != null) stat.setString(pos++, p.getDescription());
				else if(actualP != null) stat.setString(pos++, actualP.getDescription());
				else stat.setString(pos++, null);
				
				if(p.getTags() != null) stat.setString(pos++, p.getTags());
				else if(actualP != null) stat.setString(pos++, actualP.getTags());
				else stat.setString(pos++, null);
				
				if(p.getCreationDate() != null) stat.setDate(pos++, p.getCreationDate());
				else if(actualP != null) stat.setDate(pos++, actualP.getCreationDate());
				else stat.setDate(pos++, null);
				
				if(p.getCreationTime() != null) stat.setTime(pos++, p.getCreationTime());
				else if(actualP != null) stat.setTime(pos++, actualP.getCreationTime());
				else stat.setTime(pos++, null);
				
				if(p.getPostCategoryId() != 0) stat.setInt(pos++, p.getPostCategoryId());
				else if(actualP != null) stat.setInt(pos++, actualP.getPostCategoryId());
				else stat.setInt(pos++, 0);
				
				if(p.getUserId() != 0) stat.setInt(pos++, p.getUserId());
				else if(actualP != null) stat.setInt(pos++, actualP.getUserId());
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
	
	private Post convert(ResultSet rs) throws SQLException {
		
		Post post = new Post();
		post.setId(rs.getInt(ID_COLUMN));
		post.setName(rs.getString(NAME_COLUMN));
		post.setDescription(rs.getString(DESCRIPTION_COLUMN));
		post.setTags(rs.getString(TAGS_COLUMN));
		post.setCreationDate(rs.getDate(CREATION_DATE_COLUMN));
		post.setCreationTime(rs.getTime(CREATION_TIME_COLUMN));
		post.setPostCategoryId(rs.getInt(POST_CATEGORY_ID_COLUMN));
		post.setUserId(rs.getInt(USER_ID_COLUMN));
		
		return post;
	}

	
}
