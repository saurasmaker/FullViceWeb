<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="com.fullvicie.controllers.ActionsController, com.fullvicie.actions.user.Logout, com.fullvicie.pojos.User" %>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>


<header>
  	<nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark">
    	<div class="container-xl">
	    	<a class="navbar-brand" href="<%=request.getContextPath() %>/index.jsp">Full Vicie</a>
	    	<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarToggler" aria-controls="navbarToggler" aria-expanded="false" aria-label="Toggle navigation">
	    		<span class="navbar-toggler-icon"></span>
	  		</button>
      
      
      		<div class="collapse navbar-collapse" id="navbarToggler">
        
        		<!-- Navbar MENU section -->
        		<ul class="navbar-nav me-auto mb-2 mb-lg-0">
          			<li class="nav-item">
            			<a class="nav-link disabled" aria-current="page" href="pages/communities.jsp">Communities</a>
          			</li>
          			<li class="nav-item">
            			<a class="nav-link disabled" href="pages/projects.jsp">Projects</a>
          			</li>
          			<li class="nav-item dropdown">
            			<a class="nav-link dropdown-toggle" href="pages/forums.jsp" id="dropdown07XL" data-bs-toggle="dropdown" data-hover="dropdown" aria-expanded="false">Forums</a>
             			<ul class="dropdown-menu" aria-labelledby="dropdown07XL">
			                <li><a class="dropdown-item" href="#">Video Games</a></li>
			                <li><a class="dropdown-item" href="#">Series</a></li>
			                <li><a class="dropdown-item" href="#">Films</a></li>
			                <li><a class="dropdown-item" href="#">Books</a></li>
			                <li><a class="dropdown-item" href="#">Table games</a></li>
			                <li><a class="dropdown-item" href="#">E-SPORTS</a></li>
			                <li class="dropdown-divider"/>
			                <li><a class="dropdown-item" href="#">Forums</a></li>
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
			             			<li><a class="dropdown-item" href="<%=request.getContextPath() %>/pages/user/user-notifications.jsp">Notifications</a></li>
			                		<li><a class="dropdown-item" href="<%=request.getContextPath() %>/pages/user/user-profile.jsp">Profile</a></li>
			                		<li><a class="dropdown-item" href="<%=request.getContextPath() %>/pages/user/user-teams.jsp">Teams</a></li>
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
		                			<a class="nav-link" href = "<%=request.getContextPath() %>/secured/admin/admin-page.jsp">Administrar<span class="sr-only">(current)</span></a>
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