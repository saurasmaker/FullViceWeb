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
import com.fullvicie.pojos.TeamInvitation;

public class MySQLTeamInvitationDAO implements IDao<TeamInvitation> {

	
	public static String TABLE_NAME = "team_invitations", ID_COLUMN="id", TEAM_ID_COLUMN="team_id", TRANSMITTER_USER_ID_COLUMN="transmitter_user_id",
			RECEIVER_USER_ID_COLUMN="receiver_user_id", SENDING_DATE_COLUMN="sending_date", SENDING_TIME_COLUMN="sending_time";

	
	
	/*
	 * Singleton
	 */
	private static MySQLTeamInvitationDAO instance;
	private Connection connection;
	private MySQLTeamInvitationDAO() {
		this.connection = ((MySqlConnection) DataBaseConnectionFactory.getConnection(DataBaseConnectionFactory.MYSQL_ENGINE)).getDatabaseConnection();
	}
	public static MySQLTeamInvitationDAO getInstance() {	
		
		if(instance == null)
			instance = new MySQLTeamInvitationDAO();	
		
		return instance;
	}
		
	
	
	/*
	 * Methods
	 */
	@Override
	public ErrorType create(TeamInvitation ti) throws DaoException {
		
		return executeQueryWithParameters("INSERT INTO " + TABLE_NAME + "(" 
			+ TEAM_ID_COLUMN + ", "
			+ TRANSMITTER_USER_ID_COLUMN + ", "
			+ RECEIVER_USER_ID_COLUMN + ", " 
			+ SENDING_DATE_COLUMN + ", " 
			+ SENDING_TIME_COLUMN + ") VALUES (?, ?, ?, ?, ?)", ti);
	}

	@Override
	public TeamInvitation read(String search, SearchBy searchBy) throws DaoException {
		// Declaration of variables
		PreparedStatement stat = null;
		ResultSet rs = null;
		TeamInvitation ti = null;
		
		// Initialize query
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		// Logic
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);	
			if(rs.next()) {
				ti = convert(rs);
			} else { 
				throw new DaoException("");
			} 
			rs.close();
		} catch (SQLException e)  {
			throw new DaoException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
			
		return ti;
	}

	@Override
	public ErrorType update(String search, SearchBy searchBy, TeamInvitation ti) throws DaoException {
		
		ErrorType et = ErrorType.NO_ERROR;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET "
			+ TEAM_ID_COLUMN + ", "
			+ TRANSMITTER_USER_ID_COLUMN + ", "
			+ RECEIVER_USER_ID_COLUMN + ", " 
			+ SENDING_DATE_COLUMN + ", " 
			+ SENDING_TIME_COLUMN + " = ? ";
		
		updateQuery = IDao.appendMySqlSearchBy(updateQuery, searchBy, search);			
		et = executeQueryWithParameters(updateQuery, ti);

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
	public ArrayList<TeamInvitation> listBy(String search, SearchBy searchBy) throws DaoException {
		
		PreparedStatement stat = null;
		ResultSet rs = null;
		ArrayList<TeamInvitation> teamInvitationsList = new ArrayList<TeamInvitation>();
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		if(searchBy != SearchBy.NONE)
			selectQuery = IDao.appendMySqlSearchBy(selectQuery, searchBy, search);
		
		try {
			stat = connection.prepareStatement(selectQuery);
			rs = stat.executeQuery(selectQuery);
			while(rs.next()) {
				teamInvitationsList.add(convert(rs));
			}	
			rs.close();
		} catch (SQLException e)  {
			throw new DaoException("", e);
		} finally {
			IDao.closeMySql(rs, stat);
		}
		
		return teamInvitationsList;
	}

	
	
	/*
	 * Tool Methods
	 */
	private ErrorType executeQueryWithParameters(String query, TeamInvitation ti) throws DaoException {
		
		TeamInvitation actualTi = read(String.valueOf(ti.getId()), SearchBy.ID);
		int pos = 1;
		
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(query);
			if(ti != null) {
				if(ti.getTeamId() != 0) stat.setInt(pos++, ti.getTeamId());
				else if(actualTi!=null) stat.setInt(pos++, actualTi.getTeamId());
				else stat.setInt(pos++, 0);
				
				if(ti.getTransmitterUserId() != 0) stat.setInt(pos++, ti.getTransmitterUserId());
				else if(actualTi!=null) stat.setInt(pos++, actualTi.getTransmitterUserId());
				else stat.setInt(pos++, 0);
				
				if(ti.getReceiverUserId() != 0) stat.setInt(pos++, ti.getReceiverUserId());
				else if(actualTi!=null) stat.setInt(pos++, actualTi.getReceiverUserId());
				else stat.setInt(pos++, 0);
				
				if(ti.getSendingDate()!=null) stat.setDate(pos++, ti.getSendingDate());
				else if(actualTi!=null) stat.setDate(pos++, actualTi.getSendingDate());
				else stat.setDate(pos++, null);
				
				if(ti.getSendingTime()!=null) stat.setTime(pos++, ti.getSendingTime());
				else if(actualTi!=null)stat.setTime(pos++, actualTi.getSendingTime());
				else stat.setTime(pos++, null);
			}
			stat.execute();
		} catch (SQLException e) {
			throw new DaoException("");
		} finally {
			IDao.closeMySql(null, stat);
		}
		
		return ErrorType.NO_ERROR;
	}
	
	private TeamInvitation convert(ResultSet rs) throws SQLException {
		
		TeamInvitation ti = new TeamInvitation();
		ti.setId(rs.getInt(ID_COLUMN));
		ti.setTeamId(rs.getInt(TEAM_ID_COLUMN));
		ti.setTransmitterUserId(rs.getInt(TRANSMITTER_USER_ID_COLUMN));
		ti.setReceiverUserId(rs.getInt(RECEIVER_USER_ID_COLUMN));
		ti.setSendingDate(rs.getDate(SENDING_DATE_COLUMN));
		ti.setSendingTime(rs.getTime(SENDING_TIME_COLUMN));

		return ti;
	}
}
