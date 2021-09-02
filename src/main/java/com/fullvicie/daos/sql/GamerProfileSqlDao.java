package com.fullvicie.daos.sql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import com.fullvicie.controllers.DatabaseController;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.GamerProfile;

public class GamerProfileSqlDao implements IDao<GamerProfile>{

	public static String TABLE_NAME = "gamer_profile", ID_COLUMN="id", NAME_IN_GAME_COLUMN="name_in_game",
			ANALYSIS_PAGE_COLUMN="analysis_page", POINTS_COLUMN="points",
			DELETED_COLUMN="deleted", DELETE_DATE_COLUMN="delete_date", DELETE_TIME_COLUMN="delete_time",
			VIDEO_GAME_ID_COLUMN="video_game_id", USER_ID_COLUMN="user_id";
	
	@Override
	public ErrorType create(GamerProfile player) {
		if(player!=null)
			try {
				return executeQueryWithParameters("INSERT INTO " + TABLE_NAME + " (" 
						+ NAME_IN_GAME_COLUMN + ", " 
						+ ANALYSIS_PAGE_COLUMN + ", " 
						+ POINTS_COLUMN + ", "
						+ DELETED_COLUMN + ", " 
						+ DELETE_DATE_COLUMN + ", " 
						+ DELETE_TIME_COLUMN + ", " 
						+ VIDEO_GAME_ID_COLUMN + ", " 
						+ USER_ID_COLUMN + ") "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)", player);	
			} catch(Exception e) {
				e.printStackTrace();
				return ErrorType.CREATE_PLAYER_ERROR;
			}
			else
				return ErrorType.PLAYER_NULL_ERROR;
	}

	@Override
	public GamerProfile read(String search, SearchBy searchBy) {
		GamerProfile player = null;
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 
		try {
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);	
			if(rs.next()) { //se valida si hay resultados
				if(rs.getRow() == 1) {
					player = setPlayerAttributes(rs);
				}
			}
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
			
		return player;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, GamerProfile player) {
		if(player!=null)
			try {
				String updateQuery = "UPDATE " + TABLE_NAME + " SET " 
						+ NAME_IN_GAME_COLUMN + " = ?, "
						+ ANALYSIS_PAGE_COLUMN + " = ?, "
						+ POINTS_COLUMN + " = ?, "
						+ DELETED_COLUMN + " = ?, "
						+ DELETE_DATE_COLUMN + " = ?, "
						+ DELETE_TIME_COLUMN + " = ?, "
						+ VIDEO_GAME_ID_COLUMN + " = ?, "
						+ USER_ID_COLUMN + " = ? ";
				
				updateQuery = IDao.appendSqlSearchBy(updateQuery, searchBy, search);			
				executeQueryWithParameters(updateQuery, player);
			} catch(Exception e) {
				e.printStackTrace();
				return ErrorType.UPDATE_USER_ERROR;
			}
			else
				return ErrorType.USER_NULL_ERROR;
			
			return ErrorType.NO_ERROR;
	}

	
	
	@Override
	public ErrorType delete(String search, SearchBy searchBy) {
		String deleteQuery = "DELETE FROM " + TABLE_NAME;
		try {
			deleteQuery = IDao.appendSqlSearchBy(deleteQuery, searchBy, search);
			DatabaseController.DATABASE_STATEMENT.executeUpdate(deleteQuery);	
		} catch (SQLException e)  {
			e.printStackTrace();
			return ErrorType.DELETE_USER_ERROR;
		}	
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ErrorType pseudoDelete(String search, SearchBy searchBy) {
		try {
			// Get user
			GamerProfile player = read(search, searchBy);
			
			// Define Query
			String updateQuery = "UPDATE " + TABLE_NAME + " SET "
					+ DELETED_COLUMN + " = ?, " + DELETE_DATE_COLUMN + " = ?, " + DELETE_TIME_COLUMN + " = ? ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, SearchBy.ID, String.valueOf(player.getId()));			
			
			// Prepare & Execute Statement
			PreparedStatement preparedStatement = null;
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(updateQuery);
			preparedStatement.setBoolean(1, true);
			preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
			preparedStatement.setTime(3, Time.valueOf(LocalTime.now()));
			
			preparedStatement.execute();
			preparedStatement.close();
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.UPDATE_GAMER_PROFILE_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ArrayList<GamerProfile> listBy(SearchBy searchBy, String search) {
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 
		
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
			
		
		ResultSet rs = null;
		ArrayList<GamerProfile> playersList = new ArrayList<GamerProfile>();
		
		try {
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);					
			while(rs.next()) {
				GamerProfile player = setPlayerAttributes(rs);
				playersList.add(player);
			}	
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
		
		return playersList;
	}	
	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, GamerProfile player) {
		PreparedStatement preparedStatement = null;
		
		GamerProfile actualPlayer = read(String.valueOf(player.getId()), SearchBy.ID);
		int pos = 1;
		
		try {
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(query);
			
			if(player.getNameInGame()!=null) preparedStatement.setString(pos++, player.getNameInGame());
			else if(actualPlayer!=null) preparedStatement.setString(pos++, actualPlayer.getNameInGame());
			else preparedStatement.setString(pos++, null);
			
			if(player.getAnalysisPage()!=null) preparedStatement.setString(pos++, player.getAnalysisPage());
			else if(actualPlayer!=null) preparedStatement.setString(pos++, actualPlayer.getAnalysisPage());
			else preparedStatement.setString(pos++, null);
			
			if(player.getPoints()!=-1) preparedStatement.setInt(pos++, player.getPoints());
			else if(actualPlayer!=null) preparedStatement.setInt(pos++, actualPlayer.getPoints());
			else preparedStatement.setInt(pos++, 0);
			
			preparedStatement.setBoolean(pos++, player.isDeleted());
			
			if(player.getDeleteDate()!=null) preparedStatement.setDate(pos++, player.getDeleteDate());
			else if(actualPlayer!=null) preparedStatement.setDate(pos++, actualPlayer.getDeleteDate());
			else preparedStatement.setDate(pos++, null);
			
			if(player.getDeleteTime()!=null) preparedStatement.setTime(pos++, player.getDeleteTime());
			else if(actualPlayer!=null) preparedStatement.setTime(pos++, actualPlayer.getDeleteTime());
			else preparedStatement.setTime(pos++, null);
			
			if(player.getVideoGameId()!=-1) preparedStatement.setInt(pos++, player.getVideoGameId());
			else if(actualPlayer!=null) preparedStatement.setInt(pos++, actualPlayer.getVideoGameId());
			else preparedStatement.setInt(pos++, -1);
			
			if(player.getUserId()!=-1) preparedStatement.setInt(pos++, player.getUserId());
			else if(actualPlayer!=null) preparedStatement.setInt(pos++, actualPlayer.getUserId());
			else preparedStatement.setInt(pos++, -1);
						
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.DATABASE_STATEMENT_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private GamerProfile setPlayerAttributes(ResultSet rs) {
		GamerProfile player = null;
		try {
			player = new GamerProfile();
			player.setId(rs.getInt(ID_COLUMN));
			player.setNameInGame(rs.getString(NAME_IN_GAME_COLUMN));
			player.setAnalysisPage(rs.getString(ANALYSIS_PAGE_COLUMN));
			player.setPoints(rs.getInt(POINTS_COLUMN));
			player.setDeleted(rs.getBoolean(DELETED_COLUMN));
			player.setDeleteDate(rs.getDate(DELETE_DATE_COLUMN));
			player.setDeleteTime(rs.getTime(DELETE_TIME_COLUMN));
			player.setVideoGameId(rs.getInt(VIDEO_GAME_ID_COLUMN));
			player.setUserId(rs.getInt(USER_ID_COLUMN));	

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return player;
	}
}
