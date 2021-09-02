<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="com.fullvicie.pojos.Team, com.fullvicie.pojos.User, com.fullvicie.pojos.Player, com.fullvicie.pojos.VideoGame" %>
<%@ page import="com.fullvicie.daos.sql.TeamSqlDao, com.fullvicie.daos.sql.UserSqlDao, com.fullvicie.daos.sql.PlayerSqlDao, com.fullvicie.daos.sql.VideoGameSqlDao" %>
<%@ page import="com.fullvicie.controllers.ActionsController, com.fullvicie.actions.crud.*, com.fullvicie.actions.user.ChangeTeamLogo" %>
<%@ page import="com.fullvicie.enums.*" %>

<%
	Team team = new TeamSqlDao().read(request.getParameter(Team.PARAM_TEAM_ID), SearchBy.ID);
	
	if(team == null)
		((HttpServletResponse)response).sendRedirect(request.getContextPath() + "/pages/error.jsp?ERROR_TYPE="+ErrorType.READ_TEAM_ERROR);
	
	User user = (User) session.getAttribute(User.ATR_USER_LOGGED_OBJ);
	session.setAttribute(Team.ATTR_TEAM_OBJ, team);
	pageContext.setAttribute(VideoGame.ATTR_VIDEO_GAMES_LIST, new VideoGameSqlDao().listBy(SearchBy.NONE, null));
%>
    
    
<!DOCTYPE html>
<html>
	<head>
		<title>FV - Team ${ATTR_TEAM_OBJ.name}</title>
		<jsp:include page="../mod/head.jsp"/> 
	</head>
	
	<body class="dark-body">
			
		<jsp:include page="../mod/header.jsp"/>
		
		<br/><br/><br/>
		
		<div class="container text-white rounded bg-dark">
		
			<h2>Team ${ATTR_TEAM_OBJ.name}</h2>
			
			<div class="row">		
	  			<div id="update-team-div" class="col-8">
	  				<form id = "update-team-form" class = "form-group" action="<%=request.getContextPath()%>/ActionsController" method = "POST" >
			            <% if (user!=null && team.getUserOwnerId() == user.getId()){ %>
			            <input id="team-input-action" type='hidden' name='<%=ActionsController.PARAM_SELECT_ACTION%>' value='<%=Update.PARAM_UPDATE_ACTION%>'/>
						<input id="team-input-object-class" type="hidden" name="<%=ActionsController.PARAM_OBJECT_CLASS%>" value="<%=Team.class.getName()%>" />
						<input id="team-input-id" type = "hidden" name="<%=Team.PARAM_TEAM_ID %>" value="${ATTR_TEAM_OBJ.id}" />
						<% } %>
						
						<label for="team-input-name"><i class="fas fa-user"></i> Name: </label>
						<% if (user!=null && team.getUserOwnerId() == user.getId()){ %>
						<span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="click here to change the name of the team.">
						  <button class="btn btn-link text-white" type="button" onclick="enableNameInput()"><i class="fas fa-edit"></i></button>
						</span>	
						<% } %>
						<p><input id = "team-input-name" type = "text" class="form-control" name="<%=Team.PARAM_TEAM_NAME %>" value="${ATTR_TEAM_OBJ.name}" disabled></p> 
			
					    <label for="team-input-description"><i class="fas fa-envelope"></i> Description: </label>
					    <% if (user!=null && team.getUserOwnerId() == user.getId()){ %>
					    <span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="click here to change the description of the team">
						  <button class="btn btn-link text-white" type="button" onclick="enableDescriptionInput()"><i class="fas fa-edit"></i></button>
						</span>	
						<% } %>
						<p><textarea id = "team-input-description" class="form-control" name="<%=Team.PARAM_TEAM_DESCRIPTION %>" disabled>${ATTR_TEAM_OBJ.description}</textarea></p>
						
						<label for="team-input-video-game"><i class="fas fa-gamepad"></i> Video game: </label>
						<% if (user!=null && team.getUserOwnerId() == user.getId()){ %>
						<span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="click here to change the video game of the team.">
						  <button class="btn btn-link text-white" type="button" onclick="enableVideoGameIdInput()"><i class="fas fa-edit"></i></button>
						</span>	
						<% } %>
						<select class="form-control" id="team-input-video-game" name="<%=Team.PARAM_TEAM_VIDEO_GAME_ID %>" disabled>
							<c:forEach var="videoGame" items="${ATTR_VIDEO_GAMES_LIST}">
								<% //Logic for select de videogame of the team
									VideoGame vg = (VideoGame) pageContext.getAttribute("videoGame");
									if(team!=null && vg.getId() == team.getVideoGameId())	
										pageContext.setAttribute("videoGameSelected", "selected");
									else
										pageContext.setAttribute("videoGameSelected", "");
								%>
								<c:if test="${not videoGame.deleted}">
							    	<option value="${videoGame.id}" ${videoGameSelected}>${videoGame.name}</option>
							    </c:if>
						    </c:forEach>
						</select>
						
						<br/>
						
						<% if (user!=null && team.getUserOwnerId() == user.getId()){ %>
						<p>
		                <input id="team-input-confirm" type="submit" class="btn btn-primary" value="Confirm" disabled/>
		               	<button id="team-btn-cancel" class="btn btn-secondary" type="button" 
		               		onclick='resetTeamData("${ATTR_TEAM_OBJ.name}", "${ATTR_TEAM_OBJ.description}", "${ATTR_TEAM_OBJ.videoGameId}")' 
		               		disabled>Cancel</button>
						</p>
						<% } %>
		        	</form>
	  			</div>	
			
				<div id="change-team-logo-div" class="col-4">
		        	<label for="team-input-logo"><i class="fas fa-image"></i> Logo: </label>
					<br/>
		        	<img  class="img-fluid rounded img-fit" src="data:image/png;base64, ${ATTR_TEAM_OBJ.base64Logo}" alt="logotype of ${ATTR_TEAM_OBJ.name}."/>
		        	
		        	<% if (user!=null && team.getUserOwnerId() == user.getId()){ %>
		        	<br/>
		        	<form id = "change-team-logo-form" class = "form-group" enctype="multipart/form-data" action="<%=request.getContextPath()%>/ActionsController" method="POST">
						<input id="team-input-action" type='hidden' name='<%=ActionsController.PARAM_SELECT_ACTION%>' value='<%=ChangeTeamLogo.PARAM_CHANGE_TEAM_LOGO_ACTION%>'/>
				        <p><input id = "team-input-logo" type = "file" accept="image/*" class="form-control" name="<%=Team.PARAM_TEAM_LOGO %>"></p>
				        <input id="team-input-change-logo" type="submit" class="btn btn-primary" value="Change"/>
					</form>
		        	<% } %>
		        </div>
			</div>
		
			<br/>
		
			<div class="row">
			
				<div class="col-12">
				<h3>Template</h3>			
				<table class="table table-dark table-striped">
				  	<thead>
				    	<tr>
				      		<th scope="col">Picture</th>
				      		<th scope="col">Name</th>
				      		<th scope="col">League of Graphs</th>
				      		<th scope="col">See</th>
							<% if (user!=null && team.getUserOwnerId() == user.getId()){ %><th scope="col">Remove</th><% } %>
				    	</tr>
				  	</thead>
				  	
				  	<c:forEach var="playerId" items="${team.players}">
				  		<% //Get User and User's "player profile"
				  		pageContext.setAttribute("user", new UserSqlDao().read(String.valueOf(pageContext.getAttribute("playerId")), SearchBy.ID)); 
				  		ArrayList<Player> samePlayerProfiles = new PlayerSqlDao().listBy(SearchBy.USER_ID, String.valueOf(user.getId()));
				  		for(Player p: samePlayerProfiles)
					  		if(p.getVideoGameId()==team.getVideoGameId()){
					  			pageContext.setAttribute("player", p);
					  			break;
					  		}	
				  		%>
						<c:if test="${not player.deleted}">
							<tbody id="tbody-player-${player.id}">
						    	<tr>
						      		<th scope="row"><img class="img-fluid rounded" src="data:image/png;base64, ${user.base64Picture}" alt="${player.nameInGame}'s logo"/></th>
						      		<td>${player.nameInGame}</td>
						      		<td><a href="${player.analysisPage}">${player.analysisPage}</a></td>
						      		<td><a class="btn btn-primary" href="<%= request.getContextPath() %>/pages/user.jsp?<%= User.PARAM_USER_ID %>=${user.id}">See</a></td>
						    		<% if (user!=null && team.getUserOwnerId() == user.getId()){ %>
						    			<th scope="col">
											<form>
												<input type="submit" value="remove">
											</form>
										</th>
						    		<% } %>
						    	</tr>
		
						  	</tbody>
						</c:if>
					</c:forEach>
				</table>
				</div>
			
			</div>
			
		</div>		
				
		<br/>
		
		<jsp:include page="../mod/footer.jsp"/>
		
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/team-tools.js"></script>
		
	</body>
</html>