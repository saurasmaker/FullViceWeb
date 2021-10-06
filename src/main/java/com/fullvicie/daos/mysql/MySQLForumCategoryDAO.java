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
import com.fullvicie.pojos.ForumCategory;

public class MySQLForumCategoryDAO implements IDao<ForumCategory>{

	/*
	 * Static Attributes
	 */
	public static String TABLE_NAME = "forum_categories",
			ID_COLUMN="id", NAME_COLUMN="name", DESCRIPTION_COLUMN="description";
	
	
			
	/*
	 * Singleton
	 */
	private static MySQLForumCategoryDAO instance;
	private Connection connection;
	private MySQLForumCategoryDAO() {
		this.connection = ((MySqlConnection) DataBaseConnectionFactory.getConnection(DataBaseConnectionFactory.MYSQL_ENGINE)).getDatabaseConnection();
	}
	public static MySQLForumCategoryDAO getInstance() {	
		
		if(instance == null)
			instance = new MySQLForumCategoryDAO();	
		
		return instance;
	}
		
	
	
	/*
	 * Methods
	 */
	@Override
	public ErrorType create(ForumCategory fc) {		
		
		ErrorType et = executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "("
			+ NAME_COLUMN + ", "
			+ DESCRIPTION_COLUMN + ") VALUES (?, ?)", fc);
		
		if(et == ErrorType.ERROR)
			return ErrorType.CREATE_FORUM_CATEGORY_ERROR;
		
		return ErrorType.NO_ERROR;
	}

	
	@Override
	public ForumCategory read(String search, SearchBy searchBy) throws SQLException {
		
		// Declaration of variables
		PreparedStatement stat = null;
		ResultSet rs = null;
		ForumCategory fc = null;
		
		// Initialize query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		// Logic
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);	
			if(rs.next()) {
				fc = convert(rs);
			} else { 
				throw new SQLException();
			} 
			rs.close();
		} catch (Exception e)  {
			throw new SQLException();
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return fc;
	}
	

	@Override
	public ErrorType update(String search, SearchBy searchBy, ForumCategory fc) {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ NAME_COLUMN + " = ?, "
			+ DESCRIPTION_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			

		if(ErrorType.ERROR == executeQueryWithParameters(updateQuery, fc));
			et = ErrorType.UPDATE_FORUM_CATEGORY_ERROR;
			
		return et;
	}

	
	@Override
	public ErrorType delete(String search, SearchBy searchBy) {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		deleteQuery = IDao.appendMySqlSearchBy(deleteQuery, searchBy, search);
		if(ErrorType.ERROR == executeQueryWithParameters(deleteQuery, null))
			et = ErrorType.DELETE_FORUM_CATEGORY_ERROR;
		
		return et;
	}

	
	@Override
	public ArrayList<ForumCategory> listBy(String search, SearchBy searchBy) throws SQLException {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<ForumCategory> forumCategoriesList = new ArrayList<ForumCategory>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);
			while(rs.next()) {
				forumCategoriesList.add(convert(rs));
			}	
			rs.close();
		} catch (Exception e)  {
			throw new SQLException();
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return forumCategoriesList;
	}

	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, ForumCategory fc) {
		ErrorType et = ErrorType.NO_ERROR;
		ForumCategory actualFc = null;
		try {
			actualFc = read(String.valueOf(fc.getId()), SearchBy.ID);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return ErrorType.ERROR;
		}
		int pos = 1;
		
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(query);
			if(fc != null) {
				if(fc.getName() != null) stat.setString(pos++, fc.getName());
				else if(actualFc != null) stat.setString(pos++, actualFc.getName());
				else stat.setString(pos++, null);
				
				if(fc.getDescription() != null) stat.setString(pos++, fc.getDescription());
				else if(actualFc != null) stat.setString(pos++, actualFc.getDescription());
				else stat.setString(pos++, null);
			}
			stat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			et = ErrorType.ERROR;
		} finally {
			IDao.closeMySql(null, stat);
		}
		
		return et;
	}
	
	private ForumCategory convert(ResultSet rs) throws SQLException {
		
		ForumCategory fc = new ForumCategory();
		fc.setId(rs.getInt(ID_COLUMN));
		fc.setName(rs.getString(NAME_COLUMN));
		fc.setDescription(rs.getString(DESCRIPTION_COLUMN));

		return fc;
	}
	
}
