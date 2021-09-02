package com.fullvicie.daos.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fullvicie.controllers.DatabaseController;
import com.fullvicie.enums.*;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.Forum;

public class ForumSqlDao implements IDao<Forum>{

	public static String TABLE_NAME = "forums", ID_COLUMN="id", NAME_COLUMN = "name", DESCRIPTION_COLUMN = "description", TAGS_COLUMN = "tags",
			CREATION_DATE_COLUMN = "creation_date", CREATION_TIME_COLUMN = "creation_time", LATEST_ANSWER_DATE_COLUMN = "latest_answer_date",
			LATEST_ANSWER_TIME_COLUMN = "latest_answer_time", DELETED_COLUMN = "deleted", DELETE_DATE_COLUMN = "delete_date",
			DELETE_TIME_COLUMN = "delete_time", FORUM_CATEGORY_ID_COLUMN = "forum_category_id", USER_ID_COLUMN = "user_id";
	
	
	@Override
	public ErrorType create(Forum forum) {
		try {
			return executeQueryWithParameters("INSERT INTO " + TABLE_NAME
					+ "(" + NAME_COLUMN + ", " + DESCRIPTION_COLUMN + ", "  + TAGS_COLUMN
					+ ", " + CREATION_DATE_COLUMN + ", " + CREATION_TIME_COLUMN
					+ ", " + LATEST_ANSWER_DATE_COLUMN + ", " + LATEST_ANSWER_TIME_COLUMN + ", " + DELETED_COLUMN
					+ ", " + DELETE_DATE_COLUMN + ", " + DELETE_TIME_COLUMN
					+ ", " + FORUM_CATEGORY_ID_COLUMN + ", " + USER_ID_COLUMN
					+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", forum);	
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.CREATE_FORUM_ERROR;
		}
	}

	@Override
	public Forum read(String search, SearchBy searchBy) {
		Forum forum = null;
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 
		try {
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);	
			if(rs.next()) {
				if(rs.getRow() == 1) {
					forum = setForumAttributes(rs);
				}
			}
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
			
		return forum;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, Forum forum) {
		try {
			String updateQuery = "UPDATE " + TABLE_NAME + " SET " + NAME_COLUMN + " = ?, "
				+ DESCRIPTION_COLUMN + " = ?, " + TAGS_COLUMN + " = ?, " + CREATION_DATE_COLUMN + " = ?, "
				+ CREATION_TIME_COLUMN + " = ?, " + LATEST_ANSWER_DATE_COLUMN + " = ?, " + LATEST_ANSWER_TIME_COLUMN + " = ?, "
				+ DELETED_COLUMN + " = ?, " + DELETE_DATE_COLUMN + " = ?, " + DELETE_TIME_COLUMN + " = ?, "
				+ FORUM_CATEGORY_ID_COLUMN + " = ?, " + USER_ID_COLUMN + " = ? ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, searchBy, search);			
			executeQueryWithParameters(updateQuery, forum);
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.UPDATE_FORUM_ERROR;
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
			return ErrorType.DELETE_FORUM_ERROR;
		}	
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ErrorType pseudoDelete(String search, SearchBy searchBy) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<Forum> listBy(SearchBy searchBy, String search) {
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 		
		ResultSet rs = null;
		ArrayList<Forum> forumsList = new ArrayList<Forum>();
		
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
		
		try {
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);					
			while(rs.next()) {
				Forum forum = setForumAttributes(rs);
				forumsList.add(forum);
			}	
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
		
		return forumsList;
	}

	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, Forum forum) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(query);
			preparedStatement.setString(1, forum.getName());
			preparedStatement.setString(2, forum.getDescription());
			preparedStatement.setString(3, forum.getTags());
			preparedStatement.setDate(4, forum.getCreationDate());
			preparedStatement.setTime(5, forum.getCreationTime());
			preparedStatement.setDate(6, forum.getLatestAnswerDate());
			preparedStatement.setTime(7, forum.getLatestAnswerTime());
			preparedStatement.setBoolean(8, forum.isDeleted());
			preparedStatement.setDate(9, forum.getDeleteDate());
			preparedStatement.setTime(10, forum.getDeleteTime());
			preparedStatement.setInt(11, forum.getForumCategoryId());
			preparedStatement.setInt(12, forum.getUserId());
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.DATABASE_STATEMENT_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private Forum setForumAttributes(ResultSet rs) {
		Forum forum = null;
		try {
			forum = new Forum();
			forum.setId(rs.getInt(ID_COLUMN));
			forum.setName(rs.getString(NAME_COLUMN));
			forum.setDescription(rs.getString(DESCRIPTION_COLUMN));
			forum.setTags(rs.getString(TAGS_COLUMN));
			forum.setCreationDate(rs.getDate(CREATION_DATE_COLUMN));
			forum.setCreationTime(rs.getTime(CREATION_TIME_COLUMN));
			forum.setLatestAnswerDate(rs.getDate(LATEST_ANSWER_DATE_COLUMN));
			forum.setLatestAnswerTime(rs.getTime(LATEST_ANSWER_TIME_COLUMN));
			forum.setDeleted(rs.getBoolean(DELETED_COLUMN));
			forum.setDeleteDate(rs.getDate(DELETE_DATE_COLUMN));
			forum.setDeleteTime(rs.getTime(DELETE_TIME_COLUMN));
			forum.setForumCategoryId(rs.getInt(FORUM_CATEGORY_ID_COLUMN));
			forum.setUserId(rs.getInt(USER_ID_COLUMN));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return forum;
	}
}
