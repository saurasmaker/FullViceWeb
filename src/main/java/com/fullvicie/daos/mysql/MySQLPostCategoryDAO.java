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
	public ErrorType create(PostCategory pc) {

		ErrorType et = executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "("
			+ NAME_COLUMN + ", "
			+ DESCRIPTION_COLUMN + ") VALUES (?, ?)", pc);
		
		if(et == ErrorType.ERROR)
			return ErrorType.CREATE_POST_CATEGORY_ERROR;
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public PostCategory read(String search, SearchBy searchBy) throws SQLException {

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
				throw new SQLException("");
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return pc;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, PostCategory pc) {

		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ NAME_COLUMN + " = ?, "
			+ DESCRIPTION_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		if(ErrorType.ERROR == executeQueryWithParameters(updateQuery, pc))
			et = ErrorType.UPDATE_POST_CATEGORY_ERROR;

		return et;
	}

	@Override
	public ErrorType delete(String search, SearchBy searchBy) {

		ErrorType et = ErrorType.NO_ERROR;
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		deleteQuery = IDao.appendMySqlSearchBy(deleteQuery, searchBy, search);
		if(ErrorType.ERROR == executeQueryWithParameters(deleteQuery, null))
			et = ErrorType.DELETE_POST_CATEGORY_ERROR;
		
		return et;
	}


	@Override
	public ArrayList<PostCategory> listBy(String search, SearchBy searchBy) throws SQLException {
		
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
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return postCategoriesList;
	}
	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, PostCategory pc) {
		ErrorType et = ErrorType.NO_ERROR;
		PostCategory actualPc = null;
		try {
			actualPc = read(String.valueOf(pc.getId()), SearchBy.ID);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return ErrorType.ERROR;
		}
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
			et = ErrorType.ERROR;
		} finally {
			IDao.closeMySql(null, stat);
		}
		
		return et;
	}
	
	
	private PostCategory convert(ResultSet rs) throws SQLException{
		
		PostCategory pc = new PostCategory();
		pc.setId(rs.getInt(ID_COLUMN));
		pc.setName(rs.getString(NAME_COLUMN));
		pc.setDescription(rs.getString(DESCRIPTION_COLUMN));

		return pc;
	}

}
