package com.fullvicie.daos.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fullvicie.controllers.DatabaseController;
import com.fullvicie.enums.*;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.ForumMessageLike;

public class ForumMessageLikeSqlDao implements IDao<ForumMessageLike>{

	public static String TABLE_NAME = "forum_message_likes", ID_COLUMN="id", DISLIKE_COLUMN="dilike", USER_ID_COLUMN="user_id", FORUM_MESSAGE_ID_COLUMN = "forum_message_id";
	
	@Override
	public ErrorType create(ForumMessageLike fml) {
		try {
			return executeQueryWithParameters("INSERT INTO " + TABLE_NAME
					+ "(" + DISLIKE_COLUMN + ", " + USER_ID_COLUMN + ", " + FORUM_MESSAGE_ID_COLUMN + ") VALUES (?, ?, ?)", fml);	
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.CREATE_FORUM_MESSAGE_LIKE_ERROR;
		}
	}

	@Override
	public ForumMessageLike read(String search, SearchBy searchBy) {
		ForumMessageLike fc = null;
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 
		try {
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);	
			if(rs.next()) {
				if(rs.getRow() == 1) {
					fc = setForumMessageLikeAttributes(rs);
				}
			}
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
			
		return fc;
	}
	

	@Override
	public ErrorType update(String search, SearchBy searchBy, ForumMessageLike fc) {
		try {
			String updateQuery = "UPDATE " + TABLE_NAME + " SET " + DISLIKE_COLUMN + " = ?, "
					+ USER_ID_COLUMN + " = ?, " + FORUM_MESSAGE_ID_COLUMN + " = ? ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, searchBy, search);			
			executeQueryWithParameters(updateQuery, fc);
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.UPDATE_FORUM_MESSAGE_LIKE_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ErrorType delete(String search, SearchBy searchBy) {
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		try {
			deleteQuery = IDao.appendSqlSearchBy(deleteQuery, searchBy, search);
			DatabaseController.DATABASE_STATEMENT.executeUpdate(deleteQuery);	
		} catch (SQLException e)  {
			e.printStackTrace();
			return ErrorType.DELETE_FORUM_MESSAGE_LIKE_ERROR;
		}	
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ErrorType pseudoDelete(String search, SearchBy searchBy) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<ForumMessageLike> listBy(SearchBy searchBy, String search) {
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 		
		ResultSet rs = null;
		ArrayList<ForumMessageLike> forumMessageLikesList = new ArrayList<ForumMessageLike>();
		
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
		
		try {
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);					
			while(rs.next()) {
				ForumMessageLike fml = setForumMessageLikeAttributes(rs);
				forumMessageLikesList.add(fml);
			}	
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
		
		return forumMessageLikesList;
	}

	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, ForumMessageLike fml) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(query);
			preparedStatement.setBoolean(1, fml.isDislike());
			preparedStatement.setInt(2, fml.getUserId());
			preparedStatement.setInt(3, fml.getForumMessageId());
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.DATABASE_STATEMENT_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private ForumMessageLike setForumMessageLikeAttributes(ResultSet rs) {
		ForumMessageLike fml = null;
		try {
			fml = new ForumMessageLike();
			fml.setId(rs.getInt(ID_COLUMN));
			fml.setDislike(rs.getBoolean(DISLIKE_COLUMN));
			fml.setUserId(rs.getInt(USER_ID_COLUMN));
			fml.setForumMessageId(rs.getInt(FORUM_MESSAGE_ID_COLUMN));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return fml;
	}

}
