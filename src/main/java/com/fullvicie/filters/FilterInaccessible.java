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
import com.fullvicie.controllers.ActionsController;
import com.fullvicie.enums.ErrorType;

/**
 * Servlet Filter implementation class FilterInaccessible
 */
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD


@WebFilter("/secured/*")
=======
@WebFilter("/FilterInaccessible")
>>>>>>> parent of 7294c19 (a)
=======
@WebFilter("/FilterInaccessible")
>>>>>>> parent of 7294c19 (a)
=======
@WebFilter("/FilterInaccessible")
>>>>>>> parent of 7294c19 (a)
public class FilterInaccessible implements Filter {

    /**
     * Default constructor. 
     */
    public FilterInaccessible() {
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
		((HttpServletResponse)response).sendRedirect(httpRequest.getContextPath() + ActionsController.ERROR_PAGE + ErrorType.ACCESS_DENIED_ERROR); 
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
