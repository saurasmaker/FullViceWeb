package com.fullvicie.daos.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fullvicie.controllers.DatabaseController;
import com.fullvicie.enums.*;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.PostComment;

public class PostCommentSqlDao implements IDao<PostComment>{

	public static String TABLE_NAME = "post_comments", ID_COLUMN="id", MESSAGE_COLUMN="message", LIKES_COLUMN = "likes", DISLIKES_COLUMN = "dislikes",
			MADE_DATE_COLUMN="made_date", MADE_TIME_COLUMN = "made_time", DELETED_COLUMN = "deleted", DELETE_DATE_COLUMN = "delete_date",
			DELETE_TIME_COLUMN = "delete_time", LAST_EDIT_DATE_COLUMN = "last_edit_date", LAST_EDIT_TIME_COLUMN = "last_edit_time",
			POST_ID_COLUMN = "post_id", USER_ID_COLUMN = "user_id";
	
	
	
	@Override
	public ErrorType create(PostComment pc) {
		try {
			return executeQueryWithParameters("INSERT INTO " + TABLE_NAME
					+ "(" + MESSAGE_COLUMN + ", " + LIKES_COLUMN + ", "  + DISLIKES_COLUMN
					+ ", " + MADE_DATE_COLUMN + ", " + MADE_TIME_COLUMN
					+ ", " + DELETED_COLUMN + ", " + DELETE_DATE_COLUMN + ", " + DELETE_TIME_COLUMN
					+ ", " + LAST_EDIT_DATE_COLUMN + ", " + LAST_EDIT_TIME_COLUMN
					+ ", " + POST_ID_COLUMN + ", " + USER_ID_COLUMN
					+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", pc);	
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.CREATE_POST_COMMENT_ERROR;
		}
	}

	@Override
	public PostComment read(String search, SearchBy searchBy) {
		PostComment pc = null;
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME + "WHERE "; 
		try {
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);	
			if(rs.next()) {
				if(rs.getRow() == 1) {
					pc = setPostCommentAttributes(rs);
				}
			}
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
			
		return pc;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, PostComment pc) {
		try {
			String updateQuery = "UPDATE " + TABLE_NAME + " SET " + MESSAGE_COLUMN + " = ?, "
				+ MADE_DATE_COLUMN + " = ?, " + MADE_TIME_COLUMN + " = ?, " + LAST_EDIT_DATE_COLUMN + " = ?, "
				+ LAST_EDIT_TIME_COLUMN + " = ?, " + DELETED_COLUMN + " = ?, " + DELETE_DATE_COLUMN + " = ?, "
				+ DELETE_TIME_COLUMN + " = ?, " + LIKES_COLUMN + " = ?, " + DISLIKES_COLUMN + " = ?, "
				+ POST_ID_COLUMN + " = ?, " + USER_ID_COLUMN + " = ? WHERE ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, searchBy, search);			
			executeQueryWithParameters(updateQuery, pc);
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.UPDATE_POST_COMMENT_ERROR;
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
			return ErrorType.DELETE_POST_COMMENT_ERROR;
		}	
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ArrayList<PostComment> list() {
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 		
		ResultSet rs = null;
		ArrayList<PostComment> postCommentList = new ArrayList<PostComment>();
		
		try {
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);					
			while(rs.next()) {
				PostComment pc = setPostCommentAttributes(rs);
				postCommentList.add(pc);
			}	
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
		
		return postCommentList;
	}

	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, PostComment fml) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(query);
			preparedStatement.setString(1, fml.getMessage());
			preparedStatement.setInt(2, fml.getLikes());
			preparedStatement.setInt(3, fml.getDislikes());
			preparedStatement.setDate(4, fml.getMadeDate());
			preparedStatement.setTime(5, fml.getMadeTime());
			preparedStatement.setBoolean(6, fml.isDeleted());
			preparedStatement.setDate(7, fml.getDeleteDate());
			preparedStatement.setTime(8, fml.getDeleteTime());
			preparedStatement.setDate(9, fml.getLastEditDate());
			preparedStatement.setTime(10, fml.getLastEditTime());
			preparedStatement.setInt(11, fml.getPostId());
			preparedStatement.setInt(12, fml.getUserId());
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.DATABASE_STATEMENT_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private PostComment setPostCommentAttributes(ResultSet rs) {
		PostComment pc = null;
		try {
			pc = new PostComment();
			pc.setId(rs.getInt(ID_COLUMN));
			pc.setMessage(rs.getString(MESSAGE_COLUMN));
			pc.setMadeDate(rs.getDate(MADE_DATE_COLUMN));
			pc.setMadeTime(rs.getTime(MADE_TIME_COLUMN));
			pc.setLastEditDate(rs.getDate(LAST_EDIT_DATE_COLUMN));
			pc.setLastEditTime(rs.getTime(LAST_EDIT_TIME_COLUMN));
			pc.setDeleted(rs.getBoolean(DELETED_COLUMN));
			pc.setDeleteDate(rs.getDate(DELETE_DATE_COLUMN));
			pc.setDeleteTime(rs.getTime(DELETE_TIME_COLUMN));
			pc.setLikes(rs.getInt(LIKES_COLUMN));
			pc.setDislikes(rs.getInt(DISLIKES_COLUMN));
			pc.setPostId(rs.getInt(POST_ID_COLUMN));
			pc.setUserId(rs.getInt(USER_ID_COLUMN));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pc;
	}
}
