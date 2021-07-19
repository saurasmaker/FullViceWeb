package com.fullvicie.daos.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fullvicie.controllers.DatabaseController;
import com.fullvicie.enums.*;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.ForumCategory;

public class ForumCategorySqlDao implements IDao<ForumCategory>{

	
	public static String TABLE_NAME = "forum_categories", ID_COLUMN="id", NAME_COLUMN="name", DESCRIPTION_COLUMN="description";
	
	@Override
	public ErrorType create(ForumCategory fc) {
		try {
			return executeQueryWithParameters("INSERT INTO " + TABLE_NAME
					+ "(" + NAME_COLUMN + ", " + DESCRIPTION_COLUMN + ") VALUES (?, ?)", fc);	
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.CREATE_FORUM_CATEGORY_ERROR;
		}
	}

	@Override
	public ForumCategory read(String search, SearchBy searchBy) {
		ForumCategory fc = null;
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME + "WHERE "; 
		try {
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);	
			if(rs.next()) {
				if(rs.getRow() == 1) {
					fc = setForumCategoryAttributes(rs);
				}
			}
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
			
		return fc;
	}
	

	@Override
	public ErrorType update(String search, SearchBy searchBy, ForumCategory fc) {
		try {
			String updateQuery = "UPDATE users SET " + NAME_COLUMN + " = ?, "
					+ DESCRIPTION_COLUMN + " = ? WHERE ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, searchBy, search);			
			executeQueryWithParameters(updateQuery, fc);
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.UPDATE_FORUM_CATEGORY_ERROR;
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
			return ErrorType.DELETE_FORUM_CATEGORY_ERROR;
		}	
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ArrayList<ForumCategory> list() {
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 		
		ResultSet rs = null;
		ArrayList<ForumCategory> forumCategoriesList = new ArrayList<ForumCategory>();
		
		try {
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);					
			while(rs.next()) {
				ForumCategory fc = setForumCategoryAttributes(rs);
				forumCategoriesList.add(fc);
			}	
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
		
		return forumCategoriesList;
	}

	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, ForumCategory fc) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(query);
			preparedStatement.setString(1, fc.getName());
			preparedStatement.setString(2, fc.getDescription());
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.DATABASE_STATEMENT_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private ForumCategory setForumCategoryAttributes(ResultSet rs) {
		ForumCategory fc = null;
		try {
			fc = new ForumCategory();
			fc.setId(rs.getInt(ID_COLUMN));
			fc.setName(rs.getString(NAME_COLUMN));
			fc.setDescription(rs.getString(DESCRIPTION_COLUMN));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return fc;
	}
}
