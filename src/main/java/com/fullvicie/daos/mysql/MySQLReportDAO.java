package com.fullvicie.daos.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fullvicie.database.connections.MySqlConnection;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.factories.DataBaseConnectionFactory;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.Report;

public class MySQLReportDAO implements IDao<Report>{

	/*
	 * Static Attributes
	 */
	public static String TABLE_NAME = "forum_categories",
			ID_COLUMN="id", REPORT_TYPE_ID_COLUMN="report_type_id", DESCRIPTION_COLUMN="description", REPORT_DATE_COLUMN="report_date", REPORT_TIME_COLUMN="report_time",
			SOLUTION_DATE_COLUMN="solution_date", SOLUTION_TIME_COLUMN="solution_time", MODERATOR_ID_COLUMN="moderator_id",
			ACCUSED_ID_COLUMN="accused_id", WHISTLEBLOWER_ID_COLUMN="whistleblower_id";
	
	
	/*
	 * Singleton
	 */
	private static MySQLReportDAO instance;
	private Connection connection;
	private MySQLReportDAO() {
		this.connection = ((MySqlConnection) DataBaseConnectionFactory.getConnection(DataBaseConnectionFactory.MYSQL_ENGINE)).getDatabaseConnection();
	}
	public static MySQLReportDAO getInstance() {	
		
		if(instance == null)
			instance = new MySQLReportDAO();	
		
		return instance;
	}
		
	
	
	/*
	 * Methods
	 */
	@Override
	public ErrorType create(Report r) {

		ErrorType et = executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "("
			+ DESCRIPTION_COLUMN + ", "
			+ REPORT_DATE_COLUMN + ", "
			+ REPORT_TIME_COLUMN + ", "
			+ SOLUTION_DATE_COLUMN + ", "
			+ SOLUTION_TIME_COLUMN + ", "
			+ REPORT_TYPE_ID_COLUMN + ", "
			+ MODERATOR_ID_COLUMN + ", "
			+ ACCUSED_ID_COLUMN + ", "
			+ WHISTLEBLOWER_ID_COLUMN + ") VALUES (?, ?)", r);	
		
		if(et == ErrorType.ERROR)
			return ErrorType.CREATE_REPORT_ERROR;
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public Report read(String search, SearchBy searchBy) throws SQLException {
		// Declaration of variables
		PreparedStatement stat = null;
		ResultSet rs = null;
		Report r = null;
		
		// Initialize query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		// Logic
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);	
			if(rs.next()) {
				r = convert(rs);
			} else { 
				throw new SQLException("");
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return r;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, Report r) {

		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ DESCRIPTION_COLUMN + " = ?, "
			+ REPORT_DATE_COLUMN + " = ?, "
			+ REPORT_TIME_COLUMN + " = ?, "
			+ SOLUTION_DATE_COLUMN + " = ?, "
			+ SOLUTION_TIME_COLUMN + " = ?, "
			+ REPORT_TYPE_ID_COLUMN + " = ?, "
			+ MODERATOR_ID_COLUMN + " = ?, "
			+ ACCUSED_ID_COLUMN + " = ?, "
			+ WHISTLEBLOWER_ID_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		et = executeQueryWithParameters(updateQuery, r);

		return et;
	}

	@Override
	public ErrorType delete(String search, SearchBy searchBy) {

		ErrorType et = ErrorType.NO_ERROR;
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		deleteQuery = IDao.appendMySqlSearchBy(deleteQuery, searchBy, search);
		et = executeQueryWithParameters(deleteQuery, null);
		
		return et;
	}
	
	@Override
	public ArrayList<Report> listBy(String search, SearchBy searchBy) throws SQLException {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<Report> reportsList = new ArrayList<Report>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);
			while(rs.next()) {
				reportsList.add(convert(rs));
			}	
			rs.close();
		} catch (Exception e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return reportsList;
	}

	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, Report r) {
		ErrorType et = ErrorType.NO_ERROR;
		Report actualR = null;
		try {
			actualR = read(String.valueOf(r.getId()), SearchBy.ID);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return ErrorType.ERROR;
		}
		int pos = 1;
		
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(query);
			if(r != null) {
				if(r.getDescription() != null) stat.setString(pos++, r.getDescription());
				else if(actualR != null) stat.setString(pos++, actualR.getDescription());
				else stat.setString(pos++, null);
				
				if(r.getReportDate() != null) stat.setDate(pos++, r.getReportDate());
				else if(actualR != null) stat.setDate(pos++, actualR.getReportDate());
				else stat.setDate(pos++, null);
				
				if(r.getReportTime() != null) stat.setTime(pos++, r.getReportTime());
				else if(actualR != null) stat.setTime(pos++, actualR.getReportTime());
				else stat.setTime(pos++, null);
				
				if(r.getSolutionDate() != null) stat.setDate(pos++, r.getSolutionDate());
				else if(actualR != null) stat.setDate(pos++, actualR.getSolutionDate());
				else stat.setDate(pos++, null);
				
				if(r.getSolutionTime() != null) stat.setTime(pos++, r.getSolutionTime());
				else if(actualR != null) stat.setTime(pos++, actualR.getSolutionTime());
				else stat.setTime(pos++, null);
				
				if(r.getReportTypeId() != 0) stat.setInt(pos++, r.getReportTypeId());
				else if(actualR != null) stat.setInt(pos++, actualR.getReportTypeId());
				else stat.setInt(pos++, 0);
				
				if(r.getModeratorId() != 0) stat.setInt(pos++, r.getModeratorId());
				else if(actualR != null) stat.setInt(pos++, actualR.getModeratorId());
				else stat.setInt(pos++, 0);
				
				if(r.getAccusedId() != 0) stat.setInt(pos++, r.getAccusedId());
				else if(actualR != null) stat.setInt(pos++, actualR.getAccusedId());
				else stat.setInt(pos++, 0);
				
				if(r.getWhistleblowerId() != 0) stat.setInt(pos++, r.getWhistleblowerId());
				else if(actualR != null) stat.setInt(pos++, actualR.getWhistleblowerId());
				else stat.setInt(pos++, 0);
			}
			stat.execute();
		} catch (SQLException e) {
			et = ErrorType.ERROR;
		} finally {
			IDao.closeMySql(null, stat);
		}
		
		return et;
	}
	
	private Report convert(ResultSet rs) throws SQLException {
		
		Report r = new Report();
		r.setId(rs.getInt(ID_COLUMN));
		r.setDescription(rs.getString(DESCRIPTION_COLUMN));
		r.setReportDate(rs.getDate(REPORT_DATE_COLUMN));
		r.setReportTime(rs.getTime(REPORT_TIME_COLUMN));
		r.setSolutionDate(rs.getDate(SOLUTION_DATE_COLUMN));
		r.setSolutionTime(rs.getTime(SOLUTION_TIME_COLUMN));
		r.setReportTypeId(rs.getInt(REPORT_TYPE_ID_COLUMN));
		r.setModeratorId(rs.getInt(MODERATOR_ID_COLUMN));
		r.setAccusedId(rs.getInt(ACCUSED_ID_COLUMN));
		r.setWhistleblowerId(rs.getInt(WHISTLEBLOWER_ID_COLUMN));
		
		return r;
	}
}
