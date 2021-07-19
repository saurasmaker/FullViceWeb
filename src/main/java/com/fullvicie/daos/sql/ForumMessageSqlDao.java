package com.fullvicie.daos.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fullvicie.controllers.DatabaseController;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.ForumMessage;

public class ForumMessageSqlDao implements IDao<ForumMessage>{

	public static String TABLE_NAME = "forum_messages", ID_COLUMN="id", MESSAGE_COLUMN="message", MADE_DATE_COLUMN="made_date", MADE_TIME_COLUMN = "made_time",
			LAST_EDIT_DATE_COLUMN = "last_edit_date", LAST_EDIT_TIME_COLUMN = "last_edit_time", DELETED_COLUMN = "deleted",
			DELETE_DATE_COLUMN = "delete_date", DELETE_TIME_COLUMN = "delete_time", LIKES_COLUMN = "likes", DISLIKES_COLUMN = "dislikes",
			FORUM_ID_COLUMN = "forum_id", USER_ID_COLUMN = "user_id";
	
	@Override
	public ErrorType create(ForumMessage fml) {
		try {
			return executeQueryWithParameters("INSERT INTO " + TABLE_NAME
					+ "(" + MESSAGE_COLUMN + ", " + MADE_DATE_COLUMN + ", "  + MADE_TIME_COLUMN
					+ ", " + LAST_EDIT_DATE_COLUMN + ", " + LAST_EDIT_TIME_COLUMN
					+ ", " + DELETED_COLUMN + ", " + DELETE_DATE_COLUMN + ", " + DELETE_TIME_COLUMN
					+ ", " + LIKES_COLUMN + ", " + DISLIKES_COLUMN
					+ ", " + FORUM_ID_COLUMN + ", " + USER_ID_COLUMN
					+ ") VALUES (?, ?)", fml);	
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.CREATE_FORUM_MESSAGE_ERROR;
		}
	}

	@Override
	public ForumMessage read(String search, SearchBy searchBy) {
		ForumMessage fc = null;
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME + "WHERE "; 
		try {
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);	
			if(rs.next()) {
				if(rs.getRow() == 1) {
					fc = setForumMessageAttributes(rs);
				}
			}
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
			
		return fc;
	}
	

	@Override
	public ErrorType update(String search, SearchBy searchBy, ForumMessage fc) {
		try {
			String updateQuery = "UPDATE users SET " + MESSAGE_COLUMN + " = ?, "
				+ MADE_DATE_COLUMN + " = ?, " + MADE_TIME_COLUMN + " = ?, " + LAST_EDIT_DATE_COLUMN + " = ?, "
				+ LAST_EDIT_TIME_COLUMN + " = ?, " + DELETED_COLUMN + " = ?, " + DELETE_DATE_COLUMN + " = ?, "
				+ DELETE_TIME_COLUMN + " = ?, " + LIKES_COLUMN + " = ?, " + DISLIKES_COLUMN + " = ?, "
				+ FORUM_ID_COLUMN + " = ?, " + USER_ID_COLUMN + " = ? WHERE ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, searchBy, search);			
			executeQueryWithParameters(updateQuery, fc);
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.UPDATE_FORUM_MESSAGE_ERROR;
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
			return ErrorType.DELETE_FORUM_MESSAGE_ERROR;
		}	
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ArrayList<ForumMessage> list() {
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 		
		ResultSet rs = null;
		ArrayList<ForumMessage> ForumMessagesList = new ArrayList<ForumMessage>();
		
		try {
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);					
			while(rs.next()) {
				ForumMessage fml = setForumMessageAttributes(rs);
				ForumMessagesList.add(fml);
			}	
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
		
		return ForumMessagesList;
	}

	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, ForumMessage fml) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(query);
			preparedStatement.setString(1, fml.getMessage());
			preparedStatement.setDate(2, fml.getMadeDate());
			preparedStatement.setTime(3, fml.getMadeTime());
			preparedStatement.setDate(4, fml.getLasEditDate());
			preparedStatement.setTime(5, fml.getLastEditTime());
			preparedStatement.setBoolean(6, fml.isDeleted());
			preparedStatement.setDate(7, fml.getDeleteDate());
			preparedStatement.setTime(8, fml.getDeleteTime());
			preparedStatement.setInt(9, fml.getLikes());
			preparedStatement.setInt(10, fml.getDislikes());
			preparedStatement.setInt(11, fml.getForumId());
			preparedStatement.setInt(12, fml.getUserId());
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.DATABASE_STATEMENT_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private ForumMessage setForumMessageAttributes(ResultSet rs) {
		ForumMessage fml = null;
		try {
			fml = new ForumMessage();
			fml.setId(rs.getInt(ID_COLUMN));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return fml;
	}

}
