<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="com.fullvicie.controllers.ActionsController, com.fullvicie.actions.user.Logout, com.fullvicie.pojos.User" %>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>


<header>
  	<nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark">
    	<div class="container-xl">
	    	<a class="navbar-brand" href="../../index.jsp">Full Vicie</a>
	    	<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarToggler" aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
	    		<span class="navbar-toggler-icon"></span>
	  		</button>
      
      
      		<div class="collapse navbar-collapse" id="navbarToggler">
        
        
        		<!-- Navbar MENU section -->
        		<ul class="navbar-nav me-auto mb-2 mb-lg-0">
          			<li class="nav-item dropdown">
            			<a class="nav-link dropdown-toggle" href="pages/forums.jsp" id="dropdown07XL" data-bs-toggle="dropdown" data-hover="dropdown" aria-expanded="false">Index</a>
             			<ul class="dropdown-menu" aria-labelledby="dropdown07XL">
			                <li><a class="dropdown-item" href="#events-title">Events</a></li>
			                <li><a class="dropdown-item" href="#featured-covers-title">Featured Covers</a></li>
			                <li><a class="dropdown-item" href="#forums-title">Forums</a></li>
			                <li><a class="dropdown-item" href="#forum-categories-title">Forum Categories</a></li>
			                <li><a class="dropdown-item" href="#forum-messages-title">Forum Messages</a></li>
			                <li><a class="dropdown-item" href="#posts-title">Posts</a></li>
			                <li><a class="dropdown-item" href="#post-categories-title">Post Categories</a></li>
			                <li><a class="dropdown-item" href="#post-comments-title">Post Comments</a></li>
			                <li><a class="dropdown-item" href="#profiles-title">Profiles</a></li>
			                <li><a class="dropdown-item" href="#reports-title">Reports</a></li>
			                <li><a class="dropdown-item" href="#teams-title">Teams</a></li>
			                <li><a class="dropdown-item" href="#users-title">Users</a></li>
            			</ul>
          			</li>
        		</ul>
        		<!-- END Navbar MENU section -->



		        <!-- Navbar SEARCH section -->
		        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
	    			<li class="input-group">
	        			<div class="form-outline">
	              			<input type="search" id="form1" class="form-control" placeholder="Search"/>
	            		</div>
	            		<button type="button" class="btn btn-primary">
	              			<i class="fas fa-search"></i>
	            		</button>
	     			</li>
				</ul>
				<!-- END Navbar SEARCH section -->
		
		
		
				<!-- Navbar USER section -->
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<c:choose>
	        			<c:when test="${empty ATR_USER_LOGGED_OBJ}">
			          		<li class = "nav-item">
				            	<button type="button" class="btn nav-link" data-bs-toggle="modal" data-bs-target="#exampleModal">
				            		<i class="fas fa-user"></i> Login
				            	</button>
			          		</li>
	        			</c:when>
	        	
	        			<c:otherwise>  
			        		<li class="nav-item dropdown">
			            		<a class="nav-link dropdown-toggle" href="pages/forums.jsp" id="dropdown07XL" data-bs-toggle="dropdown" data-hover="dropdown" aria-expanded="false">
									${sessionScope.ATR_USER_LOGGED_OBJ.username}
								</a>
			             		<ul class="dropdown-menu" aria-labelledby="dropdown07XL">
			             			<li><a class="dropdown-item" href="<%=request.getContextPath() %>/pages/user/user_notifications.jsp">Notifications</a></li>
			                		<li><a class="dropdown-item" href="<%=request.getContextPath() %>/pages/user/user_profile.jsp">Profile</a></li>
			                		<li><a class="dropdown-item" href="<%=request.getContextPath() %>/pages/user/user_teams.jsp">Teams</a></li>
			                		<li class="dropdown-divider"></li>
			                		<li>
										<form action= "<%= request.getContextPath() %>/ActionsController" method="post">
			                    			<input type='hidden' name='<%= ActionsController.PARAM_SELECT_ACTION %>' value='<%= Logout.PARAM_LOGOUT_ACTION %>'/>
			                    			<input type = "submit" value = "Logout" class="dropdown-item">
		                    			</form>	
									</li>
			            		</ul>
			          		</li>
			            

			            	<c:if test='${sessionScope.ATR_USER_LOGGED_OBJ.admin == true}'>
			            		<li class="nav-item">
		                			<a class="nav-link" href = "<%=request.getContextPath() %>/pages/admin/admin-page.jsp">Administer<span class="sr-only">(current)</span></a>
		            			</li>
			            	</c:if>
	        			</c:otherwise>
	        		</c:choose>
        		</ul>
				<!-- END Navbar USER section -->
		
      		</div>

    	</div>
    
  	</nav>

</header>