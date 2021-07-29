package com.fullvicie.daos.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fullvicie.controllers.DatabaseController;
import com.fullvicie.enums.*;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.PostCommentLike;

public class PostCommentLikeSqlDao implements IDao<PostCommentLike>{

	public static String TABLE_NAME = "post_categories", ID_COLUMN="id", DISLIKE_COLUMN = "dislike", POST_COMMENT_ID_COLUMN = "post_comment_id", USER_ID_COLUMN = "user_id";
	
	@Override
	public ErrorType create(PostCommentLike pcl) {
		try {
			return executeQueryWithParameters("INSERT INTO " + TABLE_NAME
					+ "(" + DISLIKE_COLUMN + ", " + POST_COMMENT_ID_COLUMN + ", " + USER_ID_COLUMN + ") VALUES (?, ?, ?)", pcl);	
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.CREATE_POST_COMMENT_LIKE_ERROR;
		}
	}

	@Override
	public PostCommentLike read(String search, SearchBy searchBy) {
		PostCommentLike pc = null;
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME + "WHERE "; 
		try {
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);	
			if(rs.next()) {
				if(rs.getRow() == 1) {
					pc = setPostCommentLikeAttributes(rs);
				}
			}
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
			
		return pc;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, PostCommentLike pcl) {
		try {
			String updateQuery = "UPDATE " + TABLE_NAME + " SET " + DISLIKE_COLUMN + " = ?, "  + POST_COMMENT_ID_COLUMN + " = ?, "
				+ USER_ID_COLUMN + " = ?  WHERE ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, searchBy, search);			
			executeQueryWithParameters(updateQuery, pcl);
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.UPDATE_POST_COMMENT_LIKE_ERROR;
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
			return ErrorType.DELETE_POST_COMMENT_LIKE_ERROR;
		}	
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ArrayList<PostCommentLike> list() {
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 		
		ResultSet rs = null;
		ArrayList<PostCommentLike> postCommentLikesList = new ArrayList<PostCommentLike>();
		
		try {
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);					
			while(rs.next()) {
				PostCommentLike pc = setPostCommentLikeAttributes(rs);
				postCommentLikesList.add(pc);
			}	
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
		
		return postCommentLikesList;
	}
	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, PostCommentLike pcl) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(query);
			preparedStatement.setBoolean(1, pcl.isDislike());
			preparedStatement.setInt(2, pcl.getPostCommentId());
			preparedStatement.setInt(3, pcl.getUserId());
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.DATABASE_STATEMENT_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private PostCommentLike setPostCommentLikeAttributes(ResultSet rs) {
		PostCommentLike pcl = null;
		try {
			pcl = new PostCommentLike();
			pcl.setId(rs.getInt(ID_COLUMN));
			pcl.setDislike(rs.getBoolean(DISLIKE_COLUMN));
			pcl.setPostCommentId(rs.getInt(POST_COMMENT_ID_COLUMN));
			pcl.setUserId(rs.getInt(USER_ID_COLUMN));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pcl;
	}

}
