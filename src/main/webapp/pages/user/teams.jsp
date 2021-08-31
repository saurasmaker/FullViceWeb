<%@ page import="com.fullvicie.daos.sql.TeamSqlDao"%>
<%@ page import="com.fullvicie.pojos.Team, com.fullvicie.pojos.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.fullvicie.controllers.ActionsController, com.fullvicie.actions.crud.Create, com.fullvicie.actions.user.ChangeTeamLogo" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<%
	User sessionUser = (User) session.getAttribute(User.ATR_USER_LOGGED_OBJ);
	request.setAttribute(Team.ATR_TEAMS_LIST, new TeamSqlDao().listByPlayerId(sessionUser.getId()));	
%>
    
<!DOCTYPE html>
<html>

	<head>
		<title>FV - ${sessionScope.ATR_USER_LOGGED_OBJ.username}'s teams</title>
		<jsp:include page="../../mod/head.jsp"/> 
	</head>
	
	<body class="dark-body">
			
		<jsp:include page="../../mod/header.jsp"/>
		
		<br/><br/><br/>
		
		<div class="container text-white rounded bg-dark">
		
			<h2>Teams</h2>
			
			<c:forEach var="team" items="${ATR_TEAMS_LIST}">
				<c:if test="${not team.deleted}">
					<div class="row" id="div-team-${team.id}">
						<div class="col-4">
							<img class="img-fluid rounded" src="data:image/png;base64, ${team.base64Logo}" alt="${team.name}'s logo"/>
						</div>
						
						<div class="col-4">
							<h3>${team.name}</h3>
						</div>
						
						<div class="col-4">
							<a class="btn btn-primary" href="<%= request.getContextPath() %>/pages/team?<%= Team.PARAM_TEAM_ID %>=${team.id}">See</a>
						</div>
					</div>
				</c:if>
			</c:forEach>
			
			
			<div class="row">
	  			
	  			<div id="team-forms" class="col-12">
		  			<a class="btn btn-primary" data-toggle="collapse" href="#create-team-collapse" role="button" aria-expanded="false" aria-controls="create-team-collapse">
						Create Team
					</a>
					<div id="create-team-collapse" class="collapse">
						<div id="create-team-div">
			  				<form id = "create-team-form" class = "form-group" action="<%=request.getContextPath()%>/ActionsController" method = "POST" >
					            <input id="team-input-action" type='hidden' name='<%=ActionsController.PARAM_SELECT_ACTION%>' value='<%=Create.PARAM_CREATE_ACTION%>'/>
								<input id="team-input-object-class" type="hidden" name="<%=ActionsController.PARAM_OBJECT_CLASS%>" value="<%=Team.class.getName()%>" />
								
								<input id="team-input-owner-id" type = "hidden" name="<%=Team.PARAM_TEAM_USER_OWNER_ID %>" value="${ATR_USER_LOGGED_OBJ.id}" />
								<input id="team-input-creator-id" type = "hidden" name="<%=Team.PARAM_TEAM_USER_CREATOR_ID %>" value="${ATR_USER_LOGGED_OBJ.id}" />
		
								<label for="team-input-name"><i class="fas fa-user"></i> Name: </label>
								<p><input id = "team-input-name" type = "text" class="form-control" placeholder="Name of the team" name="<%=Team.PARAM_TEAM_NAME %>" required></p> 
					
							    <label for="team-input-description"><i class="fas fa-envelope"></i> Description: </label>
								<p><textarea id = "team-input-description" class="form-control" placeholder="Set a description of the team..." name="<%=Team.PARAM_TEAM_DESCRIPTION %>"></textarea></p>
								<br/>

								<p><input id="user-input-confirm" type="submit" class="btn btn-primary" value="Confirm"/></p>
				        	</form>
		  				</div>
					</div>
		  			
		        
		        	<!--  
			        <div id="change-user-picture-div" class="col-4">
			        	<label for="user-input-picture"><i class="fas fa-image"></i> Picture: </label>
							
			        	<img  class="img-fluid rounded" src="data:image/png;base64, ${ATR_USER_LOGGED_OBJ.base64Picture}" alt="${ATTR_TEAM_OBJ.username}'s picture."/>
			        	<br/>
			        	<form id = "change-user-picture-form" class = "form-group" enctype="multipart/form-data" action="<%=//request.getContextPath()%>/ActionsController" method="POST">
							<input id="user-input-action" type='hidden' name='<%=//ActionsController.PARAM_SELECT_ACTION%>' value='<%=//ChangeTeamLogo.PARAM_CHANGE_TEAM_LOGO_ACTION %>'/>
					        <p><input id = "user-input-picture" type = "file" accept="image/*" class="form-control" name="<%=//User.PART_USER_PICTURE %>"></p>
					        <input id="user-input-change-picture" type="submit" class="btn btn-primary" value="Change"/>
						</form>
			        
			        </div>
			        -->
				</div>
			
			</div>
			
		</div>
		
		<br/>
		
		<jsp:include page="../../mod/footer.jsp"/>
	</body>
	
</html>