package com.fullvicie.listeners;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.fullvicie.daos.mysql.MySQLUserDAO;
import com.fullvicie.database.connections.MySqlConnection;
import com.fullvicie.enums.SearchBy;
import com.fullvicie.pojos.User;


/**
 * Application Lifecycle Listener implementation class Listener
 *
 */
@WebListener
public class Listener implements ServletContextListener, ServletRequestListener, HttpSessionListener{

    /**
     * Default constructor. 
     */
    public Listener() {
    }


    @Override
    public void requestInitialized(ServletRequestEvent arg0)  { 
   
    }


	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		
	}
    
	@Override
    public void contextInitialized(ServletContextEvent arg0)  { 
    	System.out.println(MySqlConnection.connect());
    }


	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println(MySqlConnection.disconnect());
	}

	
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println(MySqlConnection.connect());
	}


	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		try {
			User u = (User) arg0.getSession().getAttribute(User.ATR_USER_LOGGED_OBJ);
			new MySQLUserDAO().updateLastLogoutDatetime(String.valueOf(u.getId()), SearchBy.ID, Date.valueOf(LocalDate.now()), Time.valueOf(LocalTime.now()));
			arg0.getSession().removeAttribute(User.ATR_USER_LOGGED_OBJ);
		}catch(Exception e) {}
	}

	
}
