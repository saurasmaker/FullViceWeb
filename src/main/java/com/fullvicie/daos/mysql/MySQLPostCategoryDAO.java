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
import com.fullvicie.pojos.PostCategory;

public class MySQLPostCategoryDAO implements IDao<PostCategory>{

	/*
	 * Static Attributes
	 */
	public static String TABLE_NAME = "post_categories",
			ID_COLUMN="id", NAME_COLUMN = "name", DESCRIPTION_COLUMN = "description";
			
		
	
	/*
	 * Singleton
	 */
	private static MySQLPostCategoryDAO instance;
	private Connection connection;
	private MySQLPostCategoryDAO() {
		this.connection = ((MySqlConnection) DataBaseConnectionFactory.getConnection(DataBaseConnectionFactory.MYSQL_ENGINE)).getDatabaseConnection();
	}
	public static MySQLPostCategoryDAO getInstance() {	
		
		if(instance == null)
			instance = new MySQLPostCategoryDAO();	
		
		return instance;
	}
		
	
	
	/*
	 * Methods
	 */
	@Override
	public ErrorType create(PostCategory pc) throws DaoException {

		return executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "("
			+ NAME_COLUMN + ", "
			+ DESCRIPTION_COLUMN + ") VALUES (?, ?)", pc);	
	}

	@Override
	public PostCategory read(String search, SearchBy searchBy) throws DaoException {

		// Declaration of variables
		PreparedStatement stat = null;
		ResultSet rs = null;
		PostCategory pc = null;
		
		// Initialize query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		// Logic
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);	
			if(rs.next()) {
				pc = convert(rs);
			} else { 
				throw new DaoException("");
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new DaoException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return pc;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, PostCategory pc) throws DaoException {

		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ NAME_COLUMN + " = ?, "
			+ DESCRIPTION_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		et = executeQueryWithParameters(updateQuery, pc);

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
	public ArrayList<PostCategory> listBy(String search, SearchBy searchBy) throws DaoException {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<PostCategory> postCategoriesList = new ArrayList<PostCategory>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);
			while(rs.next()) {
				postCategoriesList.add(convert(rs));
			}	
			rs.close();
		} catch (SQLException e)  {
			throw new DaoException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return postCategoriesList;
	}
	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, PostCategory pc) throws DaoException {
		PostCategory actualPc = read(String.valueOf(pc.getId()), SearchBy.ID);
		int pos = 1;
		
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(query);
			if(pc != null) {
				if(pc.getName() != null) stat.setString(pos++, pc.getName());
				else if(actualPc != null) stat.setString(pos++, actualPc.getName());
				else stat.setString(pos++, null);
				
				if(pc.getDescription() != null) stat.setString(pos++, pc.getDescription());
				else if(actualPc != null) stat.setString(pos++, actualPc.getDescription());
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
	
	
	private PostCategory convert(ResultSet rs) throws SQLException{
		
		PostCategory pc = new PostCategory();
		pc.setId(rs.getInt(ID_COLUMN));
		pc.setName(rs.getString(NAME_COLUMN));
		pc.setDescription(rs.getString(DESCRIPTION_COLUMN));

		return pc;
	}

}
