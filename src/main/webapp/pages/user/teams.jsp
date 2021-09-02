<%@ page import="com.fullvicie.daos.sql.TeamSqlDao, com.fullvicie.daos.sql.VideoGameSqlDao"%>
<%@ page import="com.fullvicie.pojos.Team, com.fullvicie.pojos.User, com.fullvicie.pojos.VideoGame" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.fullvicie.controllers.ActionsController, com.fullvicie.actions.crud.Create, com.fullvicie.actions.user.ChangeTeamLogo" %>
<%@ page import="com.fullvicie.enums.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<%
	User sessionUser = (User) session.getAttribute(User.ATR_USER_LOGGED_OBJ);
	pageContext.setAttribute(Team.ATR_TEAMS_LIST, new TeamSqlDao().listByPlayerId(sessionUser.getId()));	
	pageContext.setAttribute(VideoGame.ATTR_VIDEO_GAMES_LIST, new VideoGameSqlDao().listBy(SearchBy.NONE, null));
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
		
			<h2>${sessionScope.ATR_USER_LOGGED_OBJ.username}'s Teams</h2>
			
			<div class="row">
			
				<div class="col-12">			
				<table class="table table-dark table-striped">
				  	<thead>
				    	<tr>
				      		<th scope="col">Logo</th>
				      		<th scope="col">Name</th>
				      		<th scope="col">Video game</th>
				      		<th scope="col">See</th>
				    	</tr>
				  	</thead>
				  	
				  	<c:forEach var="team" items="${ATR_TEAMS_LIST}">
						<c:if test="${not team.deleted}">
							<c:set var="teamVideoGameId" value="${team.videoGameId}" scope="request" />			
							<% request.setAttribute(VideoGame.ATTR_VIDEO_GAME_OBJ, new VideoGameSqlDao().read(String.valueOf(request.getAttribute("teamVideoGameId")), SearchBy.ID)); %>
							<tbody id="div-team-${team.id}">
						    	<tr>
						      		<th scope="row"><img class="img-fluid rounded" src="data:image/png;base64, ${team.base64Logo}" alt="${team.name}'s logo"/></th>
						      		<td>${team.name}</td>
						      		<td>${ATTR_VIDEO_GAME_OBJ.name}</td>
						      		<td><a class="btn btn-primary" href="<%= request.getContextPath() %>/pages/team.jsp?<%= Team.PARAM_TEAM_ID %>=${team.id}">See</a></td>
						    	</tr>
		
						  	</tbody>
		
						</c:if>
					</c:forEach>
				</table>
				</div>
			</div>
			
			
			
			<div class="row">
	  			
	  			<div id="team-forms" class="col-12">
	  				
	  				<div class="d-flex justify-content-center"><p>
			  			<a class="btn btn-primary" data-bs-toggle="collapse" href="#create-team-collapse" role="button" aria-expanded="false" aria-controls="create-team-collapse">
							Create Team
						</a>
					</p></div>
										
					<div id="create-team-collapse" class="collapse">
						<div id="create-team-div" class="card card-body bg-dark">
			  				<form id = "create-team-form" class = "form-group" action="<%=request.getContextPath()%>/ActionsController" method = "POST" >
					            <input id="team-input-action" type='hidden' name='<%=ActionsController.PARAM_SELECT_ACTION%>' value='<%=Create.PARAM_CREATE_ACTION%>'/>
								<input id="team-input-object-class" type="hidden" name="<%=ActionsController.PARAM_OBJECT_CLASS%>" value="<%=Team.class.getName()%>" />
								
								<input id="team-input-owner-id" type="hidden" name="<%=Team.PARAM_TEAM_USER_OWNER_ID %>" value="${ATR_USER_LOGGED_OBJ.id}" />
								<input id="team-input-creator-id" type="hidden" name="<%=Team.PARAM_TEAM_USER_CREATOR_ID %>" value="${ATR_USER_LOGGED_OBJ.id}" />
								<input id="team-input-gamer-profile-0-id" type="hidden" name="<%=Team.PARAM_TEAM_GAMER_PROFILE_ID_ %>0" value="${ATR_USER_LOGGED_OBJ.id}" />
								
								<label for="team-input-name"><i class="fas fa-tag"></i> Name: </label>
								<p><input id = "team-input-name" type="text" class="form-control" placeholder="Name of the team" name="<%=Team.PARAM_TEAM_NAME %>" required></p> 
					
							    <label for="team-input-description"><i class="fas fa-book"></i> Description: </label>
								<p><textarea id = "team-input-description" class="form-control" placeholder="Set a description of the team..." name="<%=Team.PARAM_TEAM_DESCRIPTION %>"></textarea></p>							

								<label for="team-input-video-game"><i class="fas fa-gamepad"></i> Video game: </label>
								<select class="form-control" id="team-input-video-game" name="<%=Team.PARAM_TEAM_VIDEO_GAME_ID %>">
								    <c:forEach var="videoGame" items="${ATTR_VIDEO_GAMES_LIST}">
									    <c:if test="${not videoGame.deleted}">
									    	<option value="${videoGame.id}">${videoGame.name}</option>
									    </c:if>
								    </c:forEach>
								</select>
							
								<br/>
								
								<input id="user-input-confirm" type="submit" class="btn btn-primary" value="Confirm"/>
				        	</form>
		  				</div>
					</div>
		  			
		  			<br/>
		        
				</div>
			
			</div>
			
		</div>
		
		<br/>
		
		<jsp:include page="../../mod/footer.jsp"/>
	</body>
	
</html>