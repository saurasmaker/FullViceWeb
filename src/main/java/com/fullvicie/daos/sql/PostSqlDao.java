package com.fullvicie.daos.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fullvicie.controllers.DatabaseController;
import com.fullvicie.enums.*;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.Post;

public class PostSqlDao implements IDao<Post>{
	
	public static String TABLE_NAME = "posts", ID_COLUMN="id", NAME_COLUMN = "name", DESCRIPTION_COLUMN = "description", TAGS_COLUMN = "tags",
			CREATION_DATE_COLUMN = "creation_date", CREATION_TIME_COLUMN = "creation_time", DELETED_COLUMN = "deleted", DELETE_DATE_COLUMN = "delete_date",
			DELETE_TIME_COLUMN = "delete_time", POST_CATEGORY_ID_COLUMN = "post_category_id", USER_ID_COLUMN = "user_id";
	
	@Override
	public ErrorType create(Post post) {
		try {
			return executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "(" 
					+ NAME_COLUMN + ", " 
					+ DESCRIPTION_COLUMN + ", "  
					+ TAGS_COLUMN + ", " 
					+ CREATION_DATE_COLUMN + ", " 
					+ CREATION_TIME_COLUMN + ", " 
					+ DELETED_COLUMN + ", " 
					+ DELETE_DATE_COLUMN + ", " 
					+ DELETE_TIME_COLUMN + ", " 
					+ POST_CATEGORY_ID_COLUMN + ", " 
					+ USER_ID_COLUMN
					+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", post);	
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.CREATE_POST_ERROR;
		}
	}

	@Override
	public Post read(String search, SearchBy searchBy) {
		Post post = null;
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME + "WHERE "; 
		try {
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);	
			if(rs.next()) {
				if(rs.getRow() == 1) {
					post = setPostAttributes(rs);
				}
			}
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
			
		return post;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, Post post) {
		try {
			String updateQuery = "UPDATE " + TABLE_NAME + " SET " + NAME_COLUMN + " = ?, "
				+ DESCRIPTION_COLUMN + " = ?, " + TAGS_COLUMN + " = ?, " + CREATION_DATE_COLUMN + " = ?, "
				+ CREATION_TIME_COLUMN + " = ?, " + DELETED_COLUMN + " = ?, " + DELETE_DATE_COLUMN + " = ?, "
				+ DELETE_TIME_COLUMN + " = ?, "+ POST_CATEGORY_ID_COLUMN + " = ?, " + USER_ID_COLUMN + " = ? WHERE ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, searchBy, search);			
			executeQueryWithParameters(updateQuery, post);
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.UPDATE_POST_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ErrorType delete(String search, SearchBy searchBy) {
		String deleteQuery = "DELETE FROM " + TABLE_NAME + "WHERE ";
		try {
			deleteQuery = IDao.appendSqlSearchBy(deleteQuery, searchBy, search);
			DatabaseController.DATABASE_STATEMENT.executeUpdate(deleteQuery);	
		} catch (SQLException e)  {
			e.printStackTrace();
			return ErrorType.DELETE_POST_ERROR;
		}	
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ErrorType pseudoDelete(String search, SearchBy searchBy) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<Post> list() {
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 		
		ResultSet rs = null;
		ArrayList<Post> postsList = new ArrayList<Post>();
		
		try {
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);					
			while(rs.next()) {
				Post post = setPostAttributes(rs);
				postsList.add(post);
			}	
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
		
		return postsList;
	}


	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, Post post) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(query);
			preparedStatement.setString(1, post.getName());
			preparedStatement.setString(2, post.getDescription());
			preparedStatement.setString(3, post.getTags());
			preparedStatement.setDate(4, post.getCreationDate());
			preparedStatement.setTime(5, post.getCreationTime());
			preparedStatement.setBoolean(6, post.isDeleted());
			preparedStatement.setDate(7, post.getDeleteDate());
			preparedStatement.setTime(8, post.getDeleteTime());
			preparedStatement.setInt(9, post.getPostCategoryId());
			preparedStatement.setInt(10, post.getUserId());
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.DATABASE_STATEMENT_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private Post setPostAttributes(ResultSet rs) {
		Post post = null;
		try {
			post = new Post();
			post.setId(rs.getInt(ID_COLUMN));
			post.setName(rs.getString(NAME_COLUMN));
			post.setDescription(rs.getString(DESCRIPTION_COLUMN));
			post.setTags(rs.getString(TAGS_COLUMN));
			post.setCreationDate(rs.getDate(CREATION_DATE_COLUMN));
			post.setCreationTime(rs.getTime(CREATION_TIME_COLUMN));
			post.setDeleted(rs.getBoolean(DELETED_COLUMN));
			post.setDeleteDate(rs.getDate(DELETE_DATE_COLUMN));
			post.setDeleteTime(rs.getTime(DELETE_TIME_COLUMN));
			post.setPostCategoryId(rs.getInt(POST_CATEGORY_ID_COLUMN));
			post.setUserId(rs.getInt(USER_ID_COLUMN));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return post;
	}

	
}
