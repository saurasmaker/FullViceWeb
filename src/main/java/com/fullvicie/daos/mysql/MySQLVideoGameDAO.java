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
import com.fullvicie.pojos.VideoGame;

public class MySQLVideoGameDAO implements IDao<VideoGame> {

	/*
	 * Static Attributes
	 */
	public static String TABLE_NAME = "video_games",
		ID_COLUMN="id", NAME_COLUMN="name", DESCRIPTION_COLUMN="description";
	
	
	
	/*
	 * Singleton
	 */
	private static MySQLVideoGameDAO instance;
	private Connection connection;
	private MySQLVideoGameDAO() {
		this.connection = ((MySqlConnection) DataBaseConnectionFactory.getConnection(DataBaseConnectionFactory.MYSQL_ENGINE)).getDatabaseConnection();
	}
	public static MySQLVideoGameDAO getInstance() {	
		
		if(instance == null)
			instance = new MySQLVideoGameDAO();	
		
		return instance;
	}
		
	
	
	/*
	 * Methods
	 */
	@Override
	public ErrorType create(VideoGame vg) {

		ErrorType et = executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "(" 
			+ NAME_COLUMN + ", "
			+ DESCRIPTION_COLUMN + ") VALUES (?, ?)", vg);
		
		if(et == ErrorType.ERROR)
			return ErrorType.CREATE_VIDEO_GAME_ERROR;
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public VideoGame read(String search, SearchBy searchBy) throws SQLException {
		
		// Declaration of variables
		PreparedStatement stat = null;
		ResultSet rs = null;
		VideoGame vg = null;
		
		// Initialize query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		// Logic
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);	
			if(rs.next()) {
				vg = convert(rs);
			} else { 
				throw new SQLException("");
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return vg;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, VideoGame vg) {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ NAME_COLUMN + " = ?, "
			+ DESCRIPTION_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		if(ErrorType.ERROR == executeQueryWithParameters(updateQuery, vg))
			et = ErrorType.UPDATE_VIDEO_GAME_ERROR;

		return et;
	}

	@Override
	public ErrorType delete(String search, SearchBy searchBy) {

		ErrorType et = ErrorType.NO_ERROR;
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		deleteQuery = IDao.appendMySqlSearchBy(deleteQuery, searchBy, search);
		if(ErrorType.ERROR == executeQueryWithParameters(deleteQuery, null))
			et = ErrorType.DELETE_VIDEO_GAME_ERROR;
		
		return et;
	}
	

	@Override
	public ArrayList<VideoGame> listBy(String search, SearchBy searchBy) throws SQLException {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<VideoGame> videoGamesList = new ArrayList<VideoGame>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);
			while(rs.next()) {
				videoGamesList.add(convert(rs));
			}	
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return videoGamesList;
	}

	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, VideoGame vg) {
		ErrorType et = ErrorType.NO_ERROR;
		VideoGame actualVg = null;
		try {
			actualVg = read(String.valueOf(vg.getId()), SearchBy.ID);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return ErrorType.ERROR;
		}
		int pos = 1;
		
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(query);
			if(vg != null) {
				if(vg.getName() != null) stat.setString(pos++, vg.getName());
				else if(actualVg != null) stat.setString(pos++, actualVg.getName());
				else stat.setString(pos++, null);
				
				if(vg.getDescription() != null) stat.setString(pos++, vg.getDescription());
				else if(actualVg != null) stat.setString(pos++, actualVg.getDescription());
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
	
	private VideoGame convert(ResultSet rs) throws SQLException {
		
		VideoGame vg = new VideoGame();
		vg.setId(rs.getInt(ID_COLUMN));
		vg.setName(rs.getString(NAME_COLUMN));
		vg.setDescription(rs.getString(DESCRIPTION_COLUMN));

		return vg;
	}
}
