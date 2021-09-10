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
import com.fullvicie.pojos.TeamInvitation;

public class TeamInvitationSqlDao implements IDao<TeamInvitation> {

	
	public static String TABLE_NAME = "team_invitations", ID_COLUMN="id", TEAM_ID_COLUMN="team_id", TRANSMITTER_USER_ID_COLUMN="transmitter_user_id",
			RECEIVER_USER_ID_COLUMN="receiver_user_id", SENDING_DATE_COLUMN="sending_date", SENDING_TIME_COLUMN="sending_time",
			DELETED_COLUMN="deleted", DELETE_DATE_COLUMN="delete_date", DELETE_TIME_COLUMN="delete_time";

	
	@Override
	public ErrorType create(TeamInvitation teamInvitation) {
		try {
			return executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "(" 
				+ TEAM_ID_COLUMN + ", "
				+ TRANSMITTER_USER_ID_COLUMN + ", "
				+ RECEIVER_USER_ID_COLUMN + ", " 
				+ SENDING_DATE_COLUMN + ", " 
				+ SENDING_TIME_COLUMN + ", " 
				+ DELETED_COLUMN + ", " 
				+ DELETE_DATE_COLUMN + ", " 
				+ DELETE_TIME_COLUMN + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)", teamInvitation);	
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.CREATE_VIDEOGAME_ERROR;
		}
	}

	@Override
	public TeamInvitation read(String search, SearchBy searchBy) {
		TeamInvitation ti = null;
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 
		try {
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);	
			if(rs.next()) {
				if(rs.getRow() == 1) {
					ti = setTeamInvitationAttributes(rs);
				}
			}
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
			
		return ti;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, TeamInvitation teamInvitation) {
		try {
			String updateQuery = "UPDATE " + TABLE_NAME + " SET " 
					+ TEAM_ID_COLUMN + " = ?, "
					+ TRANSMITTER_USER_ID_COLUMN + " = ?, "
					+ RECEIVER_USER_ID_COLUMN + " = ?, " 
					+ SENDING_DATE_COLUMN + " = ?, " 
					+ SENDING_TIME_COLUMN + " = ?, "
					+ DELETED_COLUMN + " = ?, "
					+ DELETE_DATE_COLUMN + " = ?, "
					+ DELETE_TIME_COLUMN + " = ? ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, searchBy, search);			
			executeQueryWithParameters(updateQuery, teamInvitation);
		} catch(Exception e) {
			e.printStackTrace();
			return ErrorType.UPDATE_VIDEOGAME_ERROR;
		}
		
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
			return ErrorType.DELETE_VIDEOGAME_ERROR;
		}	
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ErrorType pseudoDelete(String search, SearchBy searchBy) {
		try {
			// Get user
			TeamInvitation teamInvitation = read(search, searchBy);
			
			// Define Query
			String updateQuery = "UPDATE " + TABLE_NAME + " SET "
					+ DELETED_COLUMN + " = ?, " + DELETE_DATE_COLUMN + " = ?, " + DELETE_TIME_COLUMN + " = ? ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, SearchBy.ID, String.valueOf(teamInvitation.getId()));			
			
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
	public ArrayList<TeamInvitation> listBy(SearchBy searchBy, String search) {
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 		
		ResultSet rs = null;
		ArrayList<TeamInvitation> teamInvitationsList = new ArrayList<TeamInvitation>();
		
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
		
		try {
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);					
			while(rs.next()) {
				TeamInvitation ti = setTeamInvitationAttributes(rs);
				teamInvitationsList.add(ti);
			}	
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
				
		return teamInvitationsList;
	}

	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, TeamInvitation teamInvitation) {
		PreparedStatement preparedStatement = null;
		try {
			
			TeamInvitation actualTeamInvitation = read(String.valueOf(teamInvitation.getId()), SearchBy.ID);
			
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(query);
			
			if(teamInvitation.getTeamId() > 0) preparedStatement.setInt(1, teamInvitation.getTeamId());
			else if(actualTeamInvitation!=null) preparedStatement.setInt(1, actualTeamInvitation.getTeamId());
			else preparedStatement.setInt(1, -1);
			
			if(teamInvitation.getTransmitterUserId() > 0) preparedStatement.setInt(2, teamInvitation.getTransmitterUserId());
			else if(actualTeamInvitation!=null) preparedStatement.setInt(2, actualTeamInvitation.getTransmitterUserId());
			else preparedStatement.setInt(2, -1);
			
			if(teamInvitation.getReceiverUserId() > 0) preparedStatement.setInt(3, teamInvitation.getReceiverUserId());
			else if(actualTeamInvitation!=null) preparedStatement.setInt(3, actualTeamInvitation.getReceiverUserId());
			else preparedStatement.setInt(3, -1);
			
			if(teamInvitation.getSendingDate()!=null) preparedStatement.setDate(4, teamInvitation.getSendingDate());
			else if(actualTeamInvitation!=null) preparedStatement.setDate(4, actualTeamInvitation.getSendingDate());
			else preparedStatement.setDate(4, null);
			
			if(teamInvitation.getSendingTime()!=null) preparedStatement.setTime(5, teamInvitation.getSendingTime());
			else if(actualTeamInvitation!=null)preparedStatement.setTime(5, actualTeamInvitation.getSendingTime());
			else preparedStatement.setTime(5, null);
			
			preparedStatement.setBoolean(6, teamInvitation.isDeleted());
			
			if(teamInvitation.getDeleteDate()!=null) preparedStatement.setDate(7, teamInvitation.getDeleteDate());
			else if(actualTeamInvitation!=null) preparedStatement.setDate(7, actualTeamInvitation.getDeleteDate());
			else preparedStatement.setDate(7, null);
			
			if(teamInvitation.getDeleteTime()!=null) preparedStatement.setTime(8, teamInvitation.getDeleteTime());
			else if(actualTeamInvitation!=null)preparedStatement.setTime(8, actualTeamInvitation.getDeleteTime());
			else preparedStatement.setTime(8, null);
			
			preparedStatement.execute();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.DATABASE_STATEMENT_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private TeamInvitation setTeamInvitationAttributes(ResultSet rs) {
		TeamInvitation ti = null;
		try {
			ti = new TeamInvitation();
			ti.setId(rs.getInt(ID_COLUMN));
			ti.setTeamId(rs.getInt(TEAM_ID_COLUMN));
			ti.setTransmitterUserId(rs.getInt(TRANSMITTER_USER_ID_COLUMN));
			ti.setReceiverUserId(rs.getInt(RECEIVER_USER_ID_COLUMN));
			ti.setSendingDate(rs.getDate(SENDING_DATE_COLUMN));
			ti.setSendingTime(rs.getTime(SENDING_TIME_COLUMN));
			
			
			ti.setDeleted(rs.getBoolean(DELETED_COLUMN));
			ti.setDeleteDate(rs.getDate(DELETE_DATE_COLUMN));
			ti.setDeleteTime(rs.getTime(DELETE_TIME_COLUMN));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ti;
	}
}
