package com.fullvicie.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fullvicie.enums.ErrorType;
import com.fullvicie.pojos.User;

/**
 * Servlet Filter implementation class FilterSecured
 */
@WebFilter("/pages/admin/*")
public class FilterAdmin implements Filter {

	
	
    /**
     * Default constructor. 
     */
    public FilterAdmin() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpSession httpSession = httpRequest.getSession();
		
		User user = (User) httpSession.getAttribute(User.ATR_USER_LOGGED_OBJ);

		/*
		 * Doing filter
		 */
		if(user != null && user.isAdmin()){
			chain.doFilter(request, response);
		}
		else {
			((HttpServletResponse)response).sendRedirect(httpRequest.getContextPath() + "/mod/error.jsp?ERROR_TYPE="+ErrorType.ACCESS_DENIED_ERROR); 
		}				
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
