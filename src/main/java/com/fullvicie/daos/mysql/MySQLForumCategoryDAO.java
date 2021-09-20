package com.fullvicie.daos.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fullvicie.database.connections.MySqlConnection;
import com.fullvicie.enums.*;
import com.fullvicie.exceptions.DaoException;
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
	public ErrorType create(ForumCategory fc) throws DaoException {		
		
		return executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "("
			+ NAME_COLUMN + ", "
			+ DESCRIPTION_COLUMN + ") VALUES (?, ?)", fc);	
	}

	
	@Override
	public ForumCategory read(String search, SearchBy searchBy) throws DaoException {
		
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
				throw new DaoException("");
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new DaoException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return fc;
	}
	

	@Override
	public ErrorType update(String search, SearchBy searchBy, ForumCategory fc) throws DaoException{
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ NAME_COLUMN + " = ?, "
			+ DESCRIPTION_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		et = executeQueryWithParameters(updateQuery, fc);

		return et;
	}

	
	@Override
	public ErrorType delete(String search, SearchBy searchBy) throws DaoException {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		deleteQuery = IDao.appendMySqlSearchBy(deleteQuery, searchBy, search);
		et = executeQueryWithParameters(deleteQuery, null);
		
		return et;
	}

	
	@Override
	public ArrayList<ForumCategory> listBy(String search, SearchBy searchBy) throws DaoException {
		
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
		} catch (SQLException e)  {
			throw new DaoException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return forumCategoriesList;
	}

	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, ForumCategory fc) throws DaoException {
		
		ForumCategory actualFc = read(String.valueOf(fc.getId()), SearchBy.ID);
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
			throw new DaoException("");
		} finally {
			IDao.closeMySql(null, stat);
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private ForumCategory convert(ResultSet rs) throws SQLException {
		
		ForumCategory fc = new ForumCategory();
		fc.setId(rs.getInt(ID_COLUMN));
		fc.setName(rs.getString(NAME_COLUMN));
		fc.setDescription(rs.getString(DESCRIPTION_COLUMN));

		return fc;
	}
	
}
