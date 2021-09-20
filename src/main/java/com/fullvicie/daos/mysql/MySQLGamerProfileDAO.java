package com.fullvicie.daos.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fullvicie.database.connections.MySqlConnection;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.exceptions.DaoException;
import com.fullvicie.factories.DataBaseConnectionFactory;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.GamerProfile;

public class MySQLGamerProfileDAO implements IDao<GamerProfile>{

	/*
	 * Static Attributes
	 */
	public static String TABLE_NAME = "gamer_profile", ID_COLUMN="id", NAME_IN_GAME_COLUMN="name_in_game",
			ANALYSIS_PAGE_COLUMN="analysis_page", POINTS_COLUMN="points",
			VIDEO_GAME_ID_COLUMN="video_game_id", USER_ID_COLUMN="user_id";
	
	
	/*
	 * Singleton
	 */
	private static MySQLGamerProfileDAO instance;
	private Connection connection;
	private MySQLGamerProfileDAO() {
		this.connection = ((MySqlConnection) DataBaseConnectionFactory.getConnection(DataBaseConnectionFactory.MYSQL_ENGINE)).getDatabaseConnection();
	}
	public static MySQLGamerProfileDAO getInstance() {	
		
		if(instance == null)
			instance = new MySQLGamerProfileDAO();	
		
		return instance;
	}
		
	
	
	/*
	 * Methods
	 */
	@Override
	public ErrorType create(GamerProfile player) throws DaoException {
		
		return executeQueryWithParameters("INSERT INTO " + TABLE_NAME + " (" 
				+ NAME_IN_GAME_COLUMN + ", " 
				+ ANALYSIS_PAGE_COLUMN + ", " 
				+ POINTS_COLUMN + ", "
				+ VIDEO_GAME_ID_COLUMN + ", " 
				+ USER_ID_COLUMN + ") VALUES (?, ?, ?, ?, ?)", player);	
	}

	@Override
	public GamerProfile read(String search, SearchBy searchBy) throws DaoException {
		
		// Declaration of variables
		PreparedStatement stat = null;
		ResultSet rs = null;
		GamerProfile gp = null;
		
		// Initialize query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		// Logic
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);	
			if(rs.next()) {
				gp = convert(rs);
			} else { 
				throw new DaoException("");
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new DaoException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return gp;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, GamerProfile gp) throws DaoException {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ NAME_IN_GAME_COLUMN + " = ?, "
			+ ANALYSIS_PAGE_COLUMN + " = ?, "
			+ POINTS_COLUMN + " = ?, "
			+ VIDEO_GAME_ID_COLUMN + " = ?, "
			+ USER_ID_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		et = executeQueryWithParameters(updateQuery, gp);

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
	public ArrayList<GamerProfile> listBy(String search, SearchBy searchBy) throws DaoException {

		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<GamerProfile> gamerProfilesList = new ArrayList<GamerProfile>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);
			while(rs.next()) {
				gamerProfilesList.add(convert(rs));
			}	
			rs.close();
		} catch (SQLException e)  {
			throw new DaoException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return gamerProfilesList;
	}	
	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, GamerProfile gp) throws DaoException {
		
		GamerProfile actualGp = read(String.valueOf(gp.getId()), SearchBy.ID);
		int pos = 1;
		
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(query);
			if(gp != null) {
				if(gp.getNameInGame() != null) stat.setString(pos++, gp.getNameInGame());
				else if(actualGp != null) stat.setString(pos++, actualGp.getNameInGame());
				else stat.setString(pos++, null);
				
				if(gp.getAnalysisPage() != null) stat.setString(pos++, gp.getAnalysisPage());
				else if(actualGp!=null) stat.setString(pos++, actualGp.getAnalysisPage());
				else stat.setString(pos++, null);
				
				if(gp.getPoints() != 0) stat.setInt(pos++, gp.getPoints());
				else if(actualGp != null) stat.setInt(pos++, actualGp.getPoints());
				else stat.setInt(pos++, 0);
								
				if(gp.getVideoGameId() != 0) stat.setInt(pos++, gp.getVideoGameId());
				else if(actualGp != null) stat.setInt(pos++, actualGp.getVideoGameId());
				else stat.setInt(pos++, 0);
				
				if(gp.getUserId() != 0) stat.setInt(pos++, gp.getUserId());
				else if(actualGp != null) stat.setInt(pos++, actualGp.getUserId());
				else stat.setInt(pos++, 0);
			}
			stat.execute();
		} catch (SQLException e) {
			throw new DaoException("");
		} finally {
			IDao.closeMySql(null, stat);
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private GamerProfile convert(ResultSet rs) throws SQLException {
		
		GamerProfile player = new GamerProfile();
		player.setId(rs.getInt(ID_COLUMN));
		player.setNameInGame(rs.getString(NAME_IN_GAME_COLUMN));
		player.setAnalysisPage(rs.getString(ANALYSIS_PAGE_COLUMN));
		player.setPoints(rs.getInt(POINTS_COLUMN));
		player.setVideoGameId(rs.getInt(VIDEO_GAME_ID_COLUMN));
		player.setUserId(rs.getInt(USER_ID_COLUMN));	
		
		return player;
	}
}
