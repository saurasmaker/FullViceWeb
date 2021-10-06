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
	public ErrorType create(GamerProfile player) {
		
		ErrorType et = executeQueryWithParameters("INSERT INTO " + TABLE_NAME + " (" 
				+ NAME_IN_GAME_COLUMN + ", " 
				+ ANALYSIS_PAGE_COLUMN + ", " 
				+ POINTS_COLUMN + ", "
				+ VIDEO_GAME_ID_COLUMN + ", " 
				+ USER_ID_COLUMN + ") VALUES (?, ?, ?, ?, ?)", player);	
		
		if(et == ErrorType.ERROR)
			return ErrorType.CREATE_GAMER_PROFILE_ERROR;
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public GamerProfile read(String search, SearchBy searchBy) throws SQLException {
		
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
				throw new SQLException();
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new SQLException();
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return gp;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, GamerProfile gp) {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ NAME_IN_GAME_COLUMN + " = ?, "
			+ ANALYSIS_PAGE_COLUMN + " = ?, "
			+ POINTS_COLUMN + " = ?, "
			+ VIDEO_GAME_ID_COLUMN + " = ?, "
			+ USER_ID_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		if(ErrorType.ERROR == executeQueryWithParameters(updateQuery, gp))
			et = ErrorType.UPDATE_GAMER_PROFILE_ERROR;

		return et;
	}

	
	
	@Override
	public ErrorType delete(String search, SearchBy searchBy) {

		ErrorType et = ErrorType.NO_ERROR;
		
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		deleteQuery = IDao.appendMySqlSearchBy(deleteQuery, searchBy, search);
		if(ErrorType.ERROR == executeQueryWithParameters(deleteQuery, null))
			et = ErrorType.DELETE_GAMER_PROFILE_ERROR;
		
		return et;
	}


	@Override
	public ArrayList<GamerProfile> listBy(String search, SearchBy searchBy) throws SQLException {

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
			throw new SQLException();
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return gamerProfilesList;
	}	
	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, GamerProfile gp) {
		ErrorType et = ErrorType.NO_ERROR;
		GamerProfile actualGp = null;
		try {
			actualGp = read(String.valueOf(gp.getId()), SearchBy.ID);
		}catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.ERROR;
		}
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
			et = ErrorType.ERROR;
		} finally {
			IDao.closeMySql(null, stat);
		}
		
		return et;
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
