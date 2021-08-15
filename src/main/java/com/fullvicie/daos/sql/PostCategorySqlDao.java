package com.fullvicie.daos.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fullvicie.controllers.DatabaseController;
import com.fullvicie.enums.*;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.PostCategory;

public class PostCategorySqlDao implements IDao<PostCategory>{

	
	public static String TABLE_NAME = "post_categories", ID_COLUMN="id", NAME_COLUMN = "name", DESCRIPTION_COLUMN = "description";
			
			
	@Override
	public ErrorType create(PostCategory pc) {
		try {
			return executeQueryWithParameters("INSERT INTO " + TABLE_NAME
					+ "(" + NAME_COLUMN + ", " + DESCRIPTION_COLUMN + ") VALUES (?, ?)", pc);	
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.CREATE_POST_CATEGORY_ERROR;
		}
	}

	@Override
	public PostCategory read(String search, SearchBy searchBy) {
		PostCategory pc = null;
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME + "WHERE "; 
		try {
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);	
			if(rs.next()) {
				if(rs.getRow() == 1) {
					pc = setPostCategoryAttributes(rs);
				}
			}
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
			
		return pc;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, PostCategory pc) {
		try {
			String updateQuery = "UPDATE " + TABLE_NAME + " SET " + NAME_COLUMN + " = ?, "
				+ DESCRIPTION_COLUMN + " = ?  WHERE ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, searchBy, search);			
			executeQueryWithParameters(updateQuery, pc);
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.UPDATE_POST_CATEGORY_ERROR;
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
			return ErrorType.DELETE_POST_CATEGORY_ERROR;
		}	
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ErrorType pseudoDelete(String search, SearchBy searchBy) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<PostCategory> list() {
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 		
		ResultSet rs = null;
		ArrayList<PostCategory> postCategoriesList = new ArrayList<PostCategory>();
		
		try {
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);					
			while(rs.next()) {
				PostCategory pc = setPostCategoryAttributes(rs);
				postCategoriesList.add(pc);
			}	
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
		
		return postCategoriesList;
	}
	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, PostCategory pc) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(query);
			preparedStatement.setString(1, pc.getName());
			preparedStatement.setString(2, pc.getDescription());
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.DATABASE_STATEMENT_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private PostCategory setPostCategoryAttributes(ResultSet rs) {
		PostCategory pc = null;
		try {
			pc = new PostCategory();
			pc.setId(rs.getInt(ID_COLUMN));
			pc.setName(rs.getString(NAME_COLUMN));
			pc.setDescription(rs.getString(DESCRIPTION_COLUMN));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pc;
	}

}
