package com.fullvicie.daos.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.tomcat.util.codec.binary.Base64;

import com.fullvicie.database.connections.MySqlConnection;
import com.fullvicie.enums.ErrorType;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.exceptions.DaoException;
import com.fullvicie.factories.DataBaseConnectionFactory;
import com.fullvicie.interfaces.IDao;
import com.fullvicie.pojos.Team;

public class MySQLTeamDAO implements IDao<Team>{

	
	/*
	 * Static Attributes
	 */
	public static String TABLE_NAME = "teams", ID_COLUMN="id", NAME_COLUMN="name", DESCRIPTION_COLUMN="description", LOGO_COLUMN="logo",
			CREATION_DATE_COLUMN="creation_date", CREATION_TIME_COLUMN="creation_time", VIDEO_GAME_ID_COLUMN="video_game_id",
			USER_OWNER_ID_COLUMN="user_owner_id", USER_CREATOR_ID_COLUMN="user_creator_id";
	
	
	/*
	 * Singleton
	 */
	private static MySQLTeamDAO instance;
	private Connection connection;
	private MySQLTeamDAO() {
		this.connection = ((MySqlConnection) DataBaseConnectionFactory.getConnection(DataBaseConnectionFactory.MYSQL_ENGINE)).getDatabaseConnection();
	}
	public static MySQLTeamDAO getInstance() {	
		
		if(instance == null)
			instance = new MySQLTeamDAO();	
		
		return instance;
	}
		
	
	
	/*
	 * Methods
	 */
	@Override
	public ErrorType create(Team t) throws DaoException {
		return executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "("
				+ NAME_COLUMN + ", "
				+ DESCRIPTION_COLUMN + ", "
				+ LOGO_COLUMN + ", "
				+ CREATION_DATE_COLUMN + ", "
				+ CREATION_TIME_COLUMN + ", "
				+ VIDEO_GAME_ID_COLUMN + ", "
				+ USER_OWNER_ID_COLUMN + ", "
				+ USER_CREATOR_ID_COLUMN + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)", t);	
	}

	@Override
	public Team read(String search, SearchBy searchBy) throws DaoException {
		
		// Declaration of variables
		PreparedStatement stat = null;
		ResultSet rs = null;
		Team t = null;
		
		// Initialize query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		// Logic
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);	
			if(rs.next()) {
				t = convert(rs);
			} else { 
				throw new DaoException("");
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new DaoException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return t;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, Team t) throws DaoException {
			
		ErrorType et = ErrorType.NO_ERROR;

		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ NAME_COLUMN + " = ?, "
			+ DESCRIPTION_COLUMN + " = ?, "
			+ LOGO_COLUMN + " = ?, "
			+ CREATION_DATE_COLUMN + " = ?, "
			+ CREATION_TIME_COLUMN + " = ?, "
			+ VIDEO_GAME_ID_COLUMN + " = ?, "
			+ USER_OWNER_ID_COLUMN + " = ?, "
			+ USER_CREATOR_ID_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		et = executeQueryWithParameters(updateQuery, t);

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
	public ArrayList<Team> listBy(String search, SearchBy searchBy) throws DaoException {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<Team> teamsList = new ArrayList<Team>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);
			while(rs.next()) {
				teamsList.add(convert(rs));
			}	
			rs.close();
		} catch (SQLException e)  {
			throw new DaoException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return teamsList;
	}

	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, Team t) throws DaoException {
		
		Team actualT = read(String.valueOf(t.getId()), SearchBy.ID);
		int pos = 1;
		
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(query);
			if(t != null) {
				if(t.getName() != null) stat.setString(pos++, t.getName());
				else if(actualT != null) stat.setString(pos++, actualT.getName());
				else stat.setString(pos++, null);
				
				if(t.getDescription()!=null) stat.setString(pos++, t.getDescription());
				else if(actualT != null) stat.setString(pos++, actualT.getDescription());
				else stat.setString(pos++, null);
					
				if(t.getBase64Logo() != null) stat.setBlob(pos++, new SerialBlob(Base64.decodeBase64(t.getBase64Logo())));
				else if(actualT != null) stat.setBlob(pos++, new SerialBlob(Base64.decodeBase64(actualT.getBase64Logo())));
				else stat.setString(pos++,null);
				
				if(t.getCreationDate() != null) stat.setDate(pos++, t.getCreationDate());
				else if(actualT != null) stat.setDate(pos++, actualT.getCreationDate());
				else stat.setDate(pos++, null);
				
				if(t.getCreationTime() != null) stat.setTime(pos++, t.getCreationTime());
				else if(actualT != null) stat.setTime(pos++, actualT.getCreationTime());
				else stat.setTime(pos++, null);
				
				if(t.getVideoGameId() != 0) stat.setInt(pos++, t.getVideoGameId());
				else if(actualT != null) stat.setInt(pos++, actualT.getVideoGameId());
				else stat.setInt(pos++, 0);
				
				if(t.getUserOwnerId() != 0) stat.setInt(pos++, t.getUserOwnerId());
				else if(actualT != null) stat.setInt(pos++, actualT.getUserOwnerId());
				else stat.setInt(pos++, 0);
				
				if(t.getUserCreatorId() != 0) stat.setInt(pos++, t.getUserCreatorId());
				else if(actualT != null) stat.setInt(pos++, actualT.getUserCreatorId());
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
	
	private Team convert(ResultSet rs) throws SQLException {
		Team team = new Team();
		team.setId(rs.getInt(ID_COLUMN));
		team.setName(rs.getString(NAME_COLUMN));
		team.setBase64Logo(Base64.encodeBase64String(rs.getBytes(LOGO_COLUMN)));
		team.setDescription(rs.getString(DESCRIPTION_COLUMN));
		team.setCreationDate(rs.getDate(CREATION_DATE_COLUMN));
		team.setCreationTime(rs.getTime(CREATION_TIME_COLUMN));
		team.setVideoGameId(rs.getInt(VIDEO_GAME_ID_COLUMN));
		team.setUserOwnerId(rs.getInt(USER_OWNER_ID_COLUMN));
		team.setUserCreatorId(rs.getInt(USER_CREATOR_ID_COLUMN));
		
		return team;
	}
}
