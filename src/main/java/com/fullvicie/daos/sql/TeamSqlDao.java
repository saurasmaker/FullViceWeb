package com.fullvicie.daos.sql;

import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.apache.tomcat.util.codec.binary.Base64;

import com.fullvicie.controllers.DatabaseController;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.GamerProfile;
import com.fullvicie.pojos.Team;
import com.fullvicie.pojos.User;

public class TeamSqlDao implements IDao<Team>{

	public static String TABLE_NAME = "teams", ID_COLUMN="id", NAME_COLUMN="name", DESCRIPTION_COLUMN="description", LOGO_COLUMN="logo",
			CREATION_DATE_COLUMN="creation_date", CREATION_TIME_COLUMN="creation_time",
			DELETED_COLUMN="deleted", DELETE_DATE_COLUMN="delete_date", DELETE_TIME_COLUMN="delete_time",
			GAMER_PROFILE_ID_COLUMN = "gamer_profile_id_",
			VIDEO_GAME_ID_COLUMN="video_game_id", USER_OWNER_ID_COLUMN="user_owner_id", USER_CREATOR_ID_COLUMN="user_creator_id";
	
	@Override
	public ErrorType create(Team team) {
		if(team!=null)
			try {
				String selectQuery = "INSERT INTO " + TABLE_NAME + " (" 
						+ NAME_COLUMN + ", " 
						+ DESCRIPTION_COLUMN + ", "
						+ CREATION_DATE_COLUMN + ", " 
						+ CREATION_TIME_COLUMN + ", "  
						+ DELETED_COLUMN + ", " 
						+ DELETE_DATE_COLUMN + ", " 
						+ DELETE_TIME_COLUMN + ", ";
				
				for	(int i = 0; i < new Team().getGamerProfiles().length; ++i)
					selectQuery += ""
						+ GAMER_PROFILE_ID_COLUMN + i + ", ";
				
				selectQuery += ""
						+ VIDEO_GAME_ID_COLUMN + ", " 
						+ USER_OWNER_ID_COLUMN + ", " 
						+ USER_CREATOR_ID_COLUMN + ") "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?,";
				
				for	(int i = 0; i < new Team().getGamerProfiles().length; ++i)
					selectQuery += ""
						+ "?, ";
				
				selectQuery += "?, ?, ?)";
				
				return executeQueryWithParameters(selectQuery, team);	
			} catch(Exception e) {
				e.printStackTrace();
				return ErrorType.CREATE_TEAM_ERROR;
			}
			else
				return ErrorType.TEAM_NULL_ERROR;
	}

	@Override
	public Team read(String search, SearchBy searchBy) {
		Team team = null;
		ResultSet rs = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 
		try {
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);	
			if(rs.next()) { //se valida si hay resultados
				if(rs.getRow() == 1) {
					team = setTeamAttributes(rs);
				}
			}
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
			
		return team;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, Team team) {
		if(team!=null)
			try {
				String updateQuery = "UPDATE " + TABLE_NAME + " SET " 
						+ NAME_COLUMN + " = ?, "
						+ DESCRIPTION_COLUMN + " = ?, "
						+ CREATION_DATE_COLUMN + " = ?, "
						+ CREATION_TIME_COLUMN + " = ?, "
						+ DELETED_COLUMN + " = ?, "
						+ DELETE_DATE_COLUMN + " = ?, "
						+ DELETE_TIME_COLUMN + " = ?, ";
				
				for	(int i = 0; i < new Team().getGamerProfiles().length; ++i)
					updateQuery += ""
						+ GAMER_PROFILE_ID_COLUMN + i + " = ?, ";
				
				updateQuery += ""
						+ VIDEO_GAME_ID_COLUMN + " = ?, "
						+ USER_OWNER_ID_COLUMN + " = ?, "
						+ USER_CREATOR_ID_COLUMN + " = ? ";
				
				updateQuery = IDao.appendSqlSearchBy(updateQuery, searchBy, search);			
				executeQueryWithParameters(updateQuery, team);
			} catch(Exception e) {
				e.printStackTrace();
				return ErrorType.UPDATE_TEAM_ERROR;
			}
			else
				return ErrorType.TEAM_NULL_ERROR;
			
			return ErrorType.NO_ERROR;
	}

	public ErrorType updatePicture(String search, SearchBy searchBy, InputStream picture) {
		if(picture!=null)
			try {
				// Define Query
				String updateQuery = "UPDATE " + TABLE_NAME + " SET "
						+ LOGO_COLUMN + " = ? ";
				updateQuery = IDao.appendSqlSearchBy(updateQuery, SearchBy.ID, search);			
				
				// Prepare & Execute Statement
				PreparedStatement preparedStatement = null;
				
				preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(updateQuery);
				preparedStatement.setBlob(1, picture);
				
				preparedStatement.execute();
				preparedStatement.close();
			} catch(Exception e) {
				e.printStackTrace();
				return ErrorType.UPDATE_TEAM_ERROR;
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
			return ErrorType.DELETE_TEAM_ERROR;
		}	
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ErrorType pseudoDelete(String search, SearchBy searchBy) {
		try {
			// Get user
			Team team = read(search, searchBy);
			
			// Define Query
			String updateQuery = "UPDATE " + TABLE_NAME + " SET "
					+ DELETED_COLUMN + " = ?, " + DELETE_DATE_COLUMN + " = ?, " + DELETE_TIME_COLUMN + " = ? ";
			
			updateQuery = IDao.appendSqlSearchBy(updateQuery, SearchBy.ID, String.valueOf(team.getId()));			
			
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
			return ErrorType.UPDATE_TEAM_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}

	@Override
	public ArrayList<Team> listBy(SearchBy searchBy, String search) {
		String selectQuery = "SELECT * FROM " + TABLE_NAME; 		
		ResultSet rs = null;
		ArrayList<Team> teamsList = new ArrayList<Team>();
		
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendSqlSearchBy(selectQuery, searchBy, search);
		
		try {
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);					
			while(rs.next()) {
				Team team = setTeamAttributes(rs);
				teamsList.add(team);
			}	
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
		
		return teamsList;
	}

	
	
	public ArrayList<Team> listByMemberId(String memberId) {
		
		User user = new UserSqlDao().read(memberId, SearchBy.ID);
		ArrayList<GamerProfile> gpList = new GamerProfileSqlDao().listBy(SearchBy.USER_ID, String.valueOf(user.getId()));
		
		String OR = " OR ";
		String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE ";
		
		for(int i = 0; i < new Team().getGamerProfiles().length; ++i) 
			for(GamerProfile gp: gpList) 
				selectQuery += GAMER_PROFILE_ID_COLUMN + i + "='" + gp.getId() + "'" + OR;

		selectQuery = selectQuery.substring(0, selectQuery.length()-OR.length());
				
		ResultSet rs = null;
		ArrayList<Team> teamsList = new ArrayList<Team>();
		
		try {
			rs = DatabaseController.DATABASE_STATEMENT.executeQuery(selectQuery);					
			while(rs.next()) {
				Team team = setTeamAttributes(rs);
				teamsList.add(team);
			}	
			rs.close();
		} catch (SQLException e)  {
			e.printStackTrace();
		}	
		
		return teamsList;
	}
	
	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, Team team) {
		PreparedStatement preparedStatement = null;
		
		Team actualTeam = read(String.valueOf(team.getId()), SearchBy.ID);
		int pos = 1;
		
		try {
			preparedStatement = DatabaseController.DATABASE_CONNECTION.prepareStatement(query);
			
			if(team.getName()!=null) preparedStatement.setString(pos++, team.getName());
			else if(actualTeam!=null) preparedStatement.setString(pos++, actualTeam.getName());
			else preparedStatement.setString(pos++, null);
			
			if(team.getDescription()!=null) preparedStatement.setString(pos++, team.getDescription());
			else if(actualTeam!=null) preparedStatement.setString(pos++, actualTeam.getDescription());
			else preparedStatement.setString(pos++, null);
				
			if(team.getCreationDate()!=null) preparedStatement.setDate(pos++, team.getCreationDate());
			else if(actualTeam!=null) preparedStatement.setDate(pos++, actualTeam.getCreationDate());
			else preparedStatement.setDate(pos++, null);
			
			if(team.getCreationTime()!=null) preparedStatement.setTime(pos++, team.getCreationTime());
			else if(actualTeam!=null) preparedStatement.setTime(pos++, actualTeam.getCreationTime());
			else preparedStatement.setTime(pos++, null);
			
			preparedStatement.setBoolean(pos++, team.isDeleted());
			
			if(team.getDeleteDate()!=null) preparedStatement.setDate(pos++, team.getDeleteDate());
			else if(actualTeam!=null) preparedStatement.setDate(pos++, actualTeam.getDeleteDate());
			else preparedStatement.setDate(pos++, null);
			
			if(team.getDeleteTime()!=null) preparedStatement.setTime(pos++, team.getDeleteTime());
			else if(actualTeam!=null) preparedStatement.setTime(pos++, actualTeam.getDeleteTime());
			else preparedStatement.setTime(pos++, null);
			
			int i = 0;
			for(int player: team.getGamerProfiles()) {
				preparedStatement.setInt(pos + i++, player);
			}
			pos+=i;
			
			if(team.getVideoGameId()!=-1) preparedStatement.setInt(pos++, team.getVideoGameId());
			else if(actualTeam!=null) preparedStatement.setInt(pos++, actualTeam.getVideoGameId());
			else preparedStatement.setInt(pos++, -1);
			
			if(team.getUserOwnerId()!=-1) preparedStatement.setInt(pos++, team.getUserOwnerId());
			else if(actualTeam!=null) preparedStatement.setInt(pos++, actualTeam.getUserOwnerId());
			else preparedStatement.setInt(pos++, -1);
			
			if(team.getUserCreatorId()!=-1) preparedStatement.setInt(pos++, team.getUserCreatorId());
			else if(actualTeam!=null) preparedStatement.setInt(pos++, actualTeam.getUserCreatorId());
			else preparedStatement.setInt(pos++, -1);
						
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return ErrorType.DATABASE_STATEMENT_ERROR;
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private Team setTeamAttributes(ResultSet rs) {
		Team team = null;
		try {
			team = new Team();
			team.setId(rs.getInt(ID_COLUMN));
			team.setName(rs.getString(NAME_COLUMN));
			team.setBase64Logo(Base64.encodeBase64String(rs.getBytes(LOGO_COLUMN)));
			team.setDescription(rs.getString(DESCRIPTION_COLUMN));
			team.setCreationDate(rs.getDate(CREATION_DATE_COLUMN));
			team.setCreationTime(rs.getTime(CREATION_TIME_COLUMN));
			team.setDeleted(rs.getBoolean(DELETED_COLUMN));
			team.setDeleteDate(rs.getDate(DELETE_DATE_COLUMN));
			team.setDeleteTime(rs.getTime(DELETE_TIME_COLUMN));
			
			for(int i = 0; i < team.getGamerProfiles().length; ++i)
				team.getGamerProfiles()[i] = rs.getInt(GAMER_PROFILE_ID_COLUMN + i);
			
			team.setVideoGameId(rs.getInt(VIDEO_GAME_ID_COLUMN));
			team.setUserOwnerId(rs.getInt(USER_OWNER_ID_COLUMN));
			team.setUserCreatorId(rs.getInt(USER_CREATOR_ID_COLUMN));

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return team;
	}
}
