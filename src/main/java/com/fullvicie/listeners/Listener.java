package com.fullvicie.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

import com.fullvicie.controllers.DatabaseController;
import com.fullvicie.daos.sql.UserSqlDao;
import com.fullvicie.pojos.User;


/**
 * Application Lifecycle Listener implementation class Listener
 *
 */
@WebListener
public class Listener implements ServletContextListener, ServletRequestListener {

    /**
     * Default constructor. 
     */
    public Listener() {
    }


    @Override
    public void requestInitialized(ServletRequestEvent arg0)  { 
    	if(DatabaseController.DATABASE_CONNECTION == null) {
    		System.out.println(DatabaseController.connect());
    		System.out.println(initializeLists(arg0.getServletContext()));
    	}
    }


	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		
	}
    
	@Override
    public void contextInitialized(ServletContextEvent arg0)  { 
    	if(DatabaseController.DATABASE_CONNECTION == null) {
    		System.out.println(DatabaseController.connect());
    		System.out.println(initializeLists(arg0.getServletContext()));
    	}
    }


	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println(DatabaseController.disconnect());
	}
	
	private String initializeLists(ServletContext sc) {
		try {
			sc.setAttribute(User.ATR_USERS_LIST, (new UserSqlDao()).list());
			
			return "LISTS INITIALIZATED";
		}catch(Exception t) {
			return "ERROR INITIALIZATING LISTS";
		}
	}

	
}
