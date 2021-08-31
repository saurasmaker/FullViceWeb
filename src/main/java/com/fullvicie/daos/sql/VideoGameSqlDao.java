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
import com.fullvicie.pojos.VideoGame;

public class VideoGameSqlDao implements IDao<VideoGame> {

	public static String TABLE_NAME = "video_games", ID_COLUMN="id", NAME_COLUMN="name", DESCRIPTION_COLUMN="description",
			DELETED_COLUMN="deleted", DELETE_DATE_COLUMN="delete_date", DELETE_TIME_COLUMN="delete_time";
	
	@Override
	public ErrorType create(VideoGame videogame) {
		try {
			return executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "(" 
				+ NAME_COLUMN + ", "
				+ DESCRIPTION_COLUMN + ", "
				+ DELETED_COLUMN + ", " 
				+ DELETE_DATE_COLUMN + ", " 
				+ DELETE_TIME_COLUMN + ") VALUES (?, ?, ?, ?, ?)", videogame);	
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.CREATE_VIDEOGAME_ERROR;
		}
	}

	@Override
	public VideoGame read(String search, SearchBy searchBy) {
		VideoGame v = null;
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "; 
		try {
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);	
			if(rs.next()) {
				if(rs.getRow() == 1) {
					v = setVideogameAttributes(rs);
				}
			}
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
			
		return v;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, VideoGame videogame) {
		try {
			String updateQuery = "UPDATE " + TABLE_NAME + " SET " 
					+ NAME_COLUMN + " = ?, "
					+ DESCRIPTION_COLUMN + " = ?, "
					+ DELETED_COLUMN + " = ?, " 
					+ DELETE_DATE_COLUMN + " = ?, " 
					+ DELETE_TIME_COLUMN + " = ? WHERE ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, searchBy, search);			
			executeQueryWithParameters(updateQuery, videogame);
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.UPDATE_VIDEOGAME_ERROR;
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
			return ErrorType.DELETE_VIDEOGAME_ERROR;
		}	
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ErrorType pseudoDelete(String search, SearchBy searchBy) {
		try {
			// Get user
			VideoGame videoGame = read(search, searchBy);
			
			// Define Query
			String updateQuery = "UPDATE " + TABLE_NAME + " SET "
					+ DELETED_COLUMN + " = ?, " + DELETE_DATE_COLUMN + " = ?, " + DELETE_TIME_COLUMN + " = ? WHERE ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, SearchBy.ID, String.valueOf(videoGame.getId()));			
			
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
			return ErrorType.UPDATE_VIDEOGAME_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ArrayList<VideoGame> list() {
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 		
		ResultSet rs = null;
		ArrayList<VideoGame> videoGamesList = new ArrayList<VideoGame>();
		
		try {
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);					
			while(rs.next()) {
				VideoGame v = setVideogameAttributes(rs);
				videoGamesList.add(v);
			}	
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
		
		return videoGamesList;
	}

	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, VideoGame videogame) {
		PreparedStatement preparedStatement = null;
		try {
			
			VideoGame actualVideogame = read(String.valueOf(videogame.getId()), SearchBy.ID);
			
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(query);
			
			if(videogame.getName()!=null) preparedStatement.setString(1, videogame.getName());
			else if(actualVideogame!=null) preparedStatement.setString(1, actualVideogame.getName());
			else preparedStatement.setString(1, null);
			
			if(videogame.getDescription()!=null) preparedStatement.setString(2, videogame.getDescription());
			else if(actualVideogame!=null) preparedStatement.setString(2, actualVideogame.getDescription());
			else preparedStatement.setString(2, null);
			
			preparedStatement.setBoolean(3, videogame.isDeleted());
			
			if(videogame.getDeleteDate()!=null) preparedStatement.setDate(4, videogame.getDeleteDate());
			else if(actualVideogame!=null) preparedStatement.setDate(4, actualVideogame.getDeleteDate());
			else preparedStatement.setDate(4, null);
			
			if(videogame.getDeleteTime()!=null) preparedStatement.setTime(5, videogame.getDeleteTime());
			else if(actualVideogame!=null)preparedStatement.setTime(5, actualVideogame.getDeleteTime());
			else preparedStatement.setTime(5, null);
			
			preparedStatement.execute();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.DATABASE_STATEMENT_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private VideoGame setVideogameAttributes(ResultSet rs) {
		VideoGame v = null;
		try {
			v = new VideoGame();
			v.setId(rs.getInt(ID_COLUMN));
			v.setName(rs.getString(NAME_COLUMN));
			v.setDescription(rs.getString(DESCRIPTION_COLUMN));
			v.setDeleted(rs.getBoolean(DELETED_COLUMN));
			v.setDeleteDate(rs.getDate(DELETE_DATE_COLUMN));
			v.setDeleteTime(rs.getTime(DELETE_TIME_COLUMN));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return v;
	}
}
