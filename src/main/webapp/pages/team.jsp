<%@page import="com.fullvicie.actions.user.ChangeTeamOwner"%>
<%@page import="com.fullvicie.actions.crud.Delete"%>
<%@page import="com.fullvicie.daos.mysql.MySQLTeamInvitationDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="com.fullvicie.pojos.Team,com.fullvicie.pojos.User,com.fullvicie.pojos.GamerProfile,com.fullvicie.pojos.VideoGame,com.fullvicie.pojos.TeamInvitation" %>
<%@ page import="com.fullvicie.daos.mysql.MySQLTeamDAO,com.fullvicie.daos.mysql.MySQLUserDAO,com.fullvicie.daos.mysql.MySQLGamerProfileDAO,com.fullvicie.daos.mysql.MySQLVideoGameDAO" %>
<%@ page import="com.fullvicie.controllers.ActionsController" %>
<%@ page import="com.fullvicie.enums.*" %>
<%@ page import="com.fullvicie.actions.crud.Update,com.fullvicie.actions.user.ChangeTeamLogo,com.fullvicie.actions.user.InvitePlayerToTeam,com.fullvicie.actions.user.KickPlayerFromTeam,com.fullvicie.actions.user.LeaveTeam" %>

<%
Team team = new MySQLTeamDAO().read(request.getParameter(Team.PARAM_TEAM_ID), SearchBy.ID);
	
	if(team == null)
		((HttpServletResponse)response).sendRedirect(request.getContextPath() + "/pages/error.jsp?ERROR_TYPE="+ErrorType.READ_TEAM_ERROR);
	
	User sessionUser = (User) session.getAttribute(User.ATR_USER_LOGGED_OBJ);
	ArrayList<TeamInvitation> teamInvitations = new MySQLTeamInvitationDAO().listBy(SearchBy.TEAM_ID, String.valueOf(team.getId()));
	
	session.setAttribute(Team.ATTR_TEAM_OBJ, team);
	pageContext.setAttribute("videogamesList", new MySQLVideoGameDAO().listBy(SearchBy.NONE, null));
	pageContext.setAttribute("teamInvitations", teamInvitations);
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
			            <%
			            try { if (team.getUserOwnerId() == sessionUser.getId()){
			            %>
			            <input id="team-input-action" type='hidden' name='<%=ActionsController.PARAM_SELECT_ACTION%>' value='<%=Update.PARAM_UPDATE_ACTION%>'/>
						<input id="team-input-object-class" type="hidden" name="<%=ActionsController.PARAM_OBJECT_CLASS%>" value="<%=Team.class.getName()%>" />
						<input id="team-input-id" type = "hidden" name="<%=Team.PARAM_TEAM_ID %>" value="${ATTR_TEAM_OBJ.id}" />
						
						<c:forEach var="gamerProfileId" items="${ATTR_TEAM_OBJ.gamerProfiles}" varStatus="loop">
							<input id="team-input-gamer-profile-id-${loop.index}" type="hidden" name="<%=Team.PARAM_TEAM_GAMER_PROFILE_ID_ %>${loop.index}" value="${gamerProfileId}" />
						</c:forEach>
						<%
						} } catch(Exception e){ }
						%>
						
						<label for="team-input-name"><i class="fas fa-user"></i> Name: </label>
						<%
						try { if (team.getUserOwnerId() == sessionUser.getId()){
						%>
						<span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="click here to change the name of the team.">
						  <button class="btn btn-link text-white" type="button" onclick="enableNameInput()"><i class="fas fa-edit"></i></button>
						</span>	
						<%
							} } catch(Exception e){ }
							%>
						<p><input id = "team-input-name" type = "text" class="form-control" name="<%=Team.PARAM_TEAM_NAME %>" value="${ATTR_TEAM_OBJ.name}" disabled></p> 
			
					    <label for="team-input-description"><i class="fas fa-envelope"></i> Description: </label>
					    <%
					    try { if (team.getUserOwnerId() == sessionUser.getId()){
					    %>
					    <span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="click here to change the description of the team">
						  <button class="btn btn-link text-white" type="button" onclick="enableDescriptionInput()"><i class="fas fa-edit"></i></button>
						</span>	
						<%
							} } catch(Exception e){ }
							%>
						<p><textarea id = "team-input-description" class="form-control" name="<%=Team.PARAM_TEAM_DESCRIPTION%>" disabled>${ATTR_TEAM_OBJ.description}</textarea></p>
						
						<label for="team-input-video-game"><i class="fas fa-gamepad"></i> Video game: </label>
						<%
						try { if (team.getUserOwnerId() == sessionUser.getId()){
						%>
						<span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="click here to change the video game of the team.">
						  <button class="btn btn-link text-white" type="button" onclick="enableVideoGameIdInput()"><i class="fas fa-edit"></i></button>
						</span>	
						<%
							} } catch(Exception e){ }
							%>
						<select class="form-control" id="team-input-video-game" name="<%=Team.PARAM_TEAM_VIDEO_GAME_ID%>" disabled>
							<c:forEach var="videoGame" items="${videogamesList}">
								<%
								//Logic for select de videogame of the team
																										VideoGame vg = (VideoGame) pageContext.getAttribute("videoGame");
																										try{ 
																											if(team!=null && vg.getId() == team.getVideoGameId())	
																												pageContext.setAttribute("videoGameSelected", "selected");
																											else
																												pageContext.setAttribute("videoGameSelected", "");
																										} catch(Exception e){ }
								%>
								<c:if test="${not videoGame.deleted}">
							    	<option value="${videoGame.id}" ${videoGameSelected}>${videoGame.name}</option>
							    </c:if>
						    </c:forEach>
						</select>
						
						<br/>
						
						<%
												try { if (team.getUserOwnerId() == sessionUser.getId()){
												%>
						<p>
		                <input id="team-input-confirm" type="submit" class="btn btn-primary" value="Confirm" disabled/>
		               	<button id="team-btn-cancel" class="btn btn-secondary" type="button" 
		               		onclick='resetTeamData("${ATTR_TEAM_OBJ.name}", "${ATTR_TEAM_OBJ.description}", "${ATTR_TEAM_OBJ.videoGameId}")' 
		               		disabled>Cancel</button>
						</p>
						<%
						} } catch(Exception e){ }
						%>
		        	</form>
		        	
		        	<br/>
		        	
		        	<%
		        			        	try { if (team.getUserOwnerId()==sessionUser.getId() && team.getNumberOfPlayers() < team.getGamerProfiles().length) {
		        			        	%> 
						<h3>Invite Player</h3>
	    				<form id = "invite-player-form" class = "form-group" action="<%=request.getContextPath()%>/ActionsController" method = "POST" >
	    					<input id="invite-player-input-action" type='hidden' name='<%=ActionsController.PARAM_SELECT_ACTION%>' value='<%=InvitePlayerToTeam.PARAM_INVITE_PLAYER_TO_TEAM_ACTION%>'/>														
							<input id="invite-player-input-transmitter-user-id" type="hidden" name="<%=TeamInvitation.PARAM_TEAM_INVITATION_TRANSMITTER_USER_ID %>" value="${ATR_USER_LOGGED_OBJ.id}"/>
							<input id="invite-player-input-team" type="hidden" name="<%=TeamInvitation.PARAM_TEAM_INVITATION_TEAM_ID %>" value="${ATTR_TEAM_OBJ.id}" />
							
	    					<label for="invite-player-input-user-name-${loop.index}"><i class="fas fa-user"></i> User name: </label>
							<p><input id="invite-player-input-user-name-${loop.index}" type="text" class="form-control" name="<%=TeamInvitation.PARAM_TEAM_INVITATION_RECEIVER_USER_NAME %>" required></p> 
				
	    					<input id="invite-player-input-confirm-${loop.index}" type="submit" class="btn btn-primary" value="Send Invitation"/>
	    				</form>					
					<%
										} } catch (Exception t) { }
										%>
	  			</div>	
			
				<div id="change-team-logo-div" class="col-4">
		        	<label for="team-input-logo"><i class="fas fa-image"></i> Logo: </label>
					<br/>
		        	<img  class="img-fluid rounded img-fit" src="data:image/png;base64, ${ATTR_TEAM_OBJ.base64Logo}" alt="logotype of ${ATTR_TEAM_OBJ.name}."/>
		        	
		        	<%
		        			        	try { if (team.getUserOwnerId() == sessionUser.getId()){
		        			        	%>
		        	<br/>
		        	<form id="change-team-logo-form" class="form-group" enctype="multipart/form-data" action="<%=request.getContextPath()%>/ActionsController" method="POST">
						<input id="team-input-action" type='hidden' name='<%=ActionsController.PARAM_SELECT_ACTION%>' value='<%=ChangeTeamLogo.PARAM_CHANGE_TEAM_LOGO_ACTION%>'/>
				        <p><input id = "team-input-logo" type = "file" accept="image/*" class="form-control" name="<%=Team.PARAM_TEAM_LOGO%>"></p>
				        <input id="team-input-change-logo" type="submit" class="btn btn-primary" value="Change"/>
					</form>
		        	<%
		        	} } catch(Exception e){ }
		        	%>
		        </div>
			</div>
		
			<br/>
		
			
			<br/>
			
			
			<div class="row"> <!-- LIST OF PLAYERS -->
			
				<div class="col-12">
					<h3>Template</h3>			
					<table class="table table-dark table-striped">
					  	<thead>
					    	<tr>
					    		<th scope="col">Number</th>
					      		<th scope="col">Picture</th>
					      		<th scope="col">Name</th>
					      		<th scope="col">Analysis Page</th>
					      		<%
					      		try { if (team.getUserOwnerId() == sessionUser.getId()){
					      		%><th scope="col">Set as Owner </th><%
					      		}  } catch(Exception e){ }
					      		%>
								<%
								try { if (team.getUserOwnerId() == sessionUser.getId()){
								%><th scope="col">Kick</th><%
								}  } catch(Exception e){ }
								%>
					    	</tr>
					  	</thead>	
					  	
					  	<c:forEach var="gamerProfileId" items="${ATTR_TEAM_OBJ.gamerProfiles}" varStatus="loop">
					  		<%
					  		//Logic show Kick or Leave from Team				  		
					  					  					  					  		GamerProfile gamerP = null; 
					  					  					  					  		User user = null; 
					  					  					  					  		try{
					  					  					  					  			gamerP = new MySQLGamerProfileDAO().read(String.valueOf(pageContext.getAttribute("gamerProfileId")), SearchBy.ID);
					  					  					  					  			user = new MySQLUserDAO().read(String.valueOf(gamerP.getUserId()), SearchBy.ID);
					  					  					  					  			
					  					  					  						  		pageContext.setAttribute("user", user);
					  					  					  							  	pageContext.setAttribute("gamerProfile", gamerP);
					  		%>
					  		
						  			<tbody id="tbody-gamer-profile-${loop.index}">
								    	<tr>
								    		<th scope="row">${loop.index+1}</th>
								      		<td><img class="img-fluid rounded img-in-table" src="data:image/png;base64, ${user.base64Picture}" alt="${gamerProfile.nameInGame}'s logo" /></td>
								      		<td><a class="a-dark-mode" target="_blank" href="<%= request.getContextPath() %>/pages/user-profile.jsp?<%= User.PARAM_USER_ID %>=${user.id}"><%
								      		if(team.getUserOwnerId() == gamerP.getUserId()) {
								      		%> <i class="fas fa-crown"></i> <%
 }
 %> ${gamerProfile.nameInGame}</a></td>
								      		<td><a class="a-dark-mode" target="_blank" href="${gamerProfile.analysisPage}">${gamerProfile.analysisPage}</a></td>
		
								    		<%
										    		if (team!=null && user!=null && sessionUser!=null){
										    		%>
								    		
								    			<th scope="col">
								    			<%
								    			if (team.getUserOwnerId() == sessionUser.getId() && sessionUser.getId() != user.getId()){
								    			%>
								    				<form  action="<%=request.getContextPath()%>/ActionsController" method="POST" onsubmit="return changeOwner('<%=team.getName()%>', '<%=gamerP.getNameInGame()%>')">
														<input type="hidden" name="<%=ActionsController.PARAM_SELECT_ACTION%>" value="<%=ChangeTeamOwner.PARAM_CHANGE_TEAM_OWNER_ACTION%>"/>
														<input type="hidden" name="<%=Team.PARAM_TEAM_ID%>" value="<%=team.getId()%>"/>
														<input type="hidden" name="<%=GamerProfile.PARAM_GAMER_PROFILE_ID%>" value="<%=gamerP.getId()%>"/>
														<input class="btn btn-danger" type="submit" value="Set as Owner">
													</form>
								    			<%
								    			}
								    			%>
								    			</th>
								    		
								    			<th scope="col">
								    			<%
								    			if(sessionUser.getId() == gamerP.getUserId()) {
								    			%>
													<form  action="<%=request.getContextPath()%>/ActionsController" method="POST" onsubmit="return leaveTeam('<%=team.getName()%>')">
														<input type="hidden" name="<%=ActionsController.PARAM_SELECT_ACTION%>" value="<%=LeaveTeam.PARAM_LEAVE_TEAM_ACTION%>"/>
														<input type="hidden" name="<%=Team.PARAM_TEAM_ID%>" value="<%=team.getId()%>"/>
														<input class="btn btn-danger" type="submit" value="Leave">
													</form>
												<%
												} else if (team.getUserOwnerId() == sessionUser.getId()) {
												%>
													<form  action="<%=request.getContextPath()%>/ActionsController" method="POST" onsubmit="return kickPlayerFromTeam('<%=team.getName()%>', '<%=user.getUsername()%>')">
														<input type="hidden" name="<%=ActionsController.PARAM_SELECT_ACTION%>" value="<%=KickPlayerFromTeam.PARAM_KICK_PLAYER_FROM_TEAM_ACTION%>"/>
														<input type="hidden" name="<%=GamerProfile.PARAM_GAMER_PROFILE_ID%>" value="<%=gamerP.getId()%>"/>
														<input type="hidden" name="<%=Team.PARAM_TEAM_ID%>" value="<%=team.getId()%>"/>
														<input class="btn btn-danger" type="submit" value="Kick">
													</form>
												<%
												}
												%>
												</th>
												
								    		<%
																				    		}
																				    		%>
								    	</tr>			
								  	</tbody>
							<%
							} catch(Exception e){ }
							%>	
						</c:forEach>
						
						<%
												if(sessionUser!=null) if (team.getUserOwnerId() == sessionUser.getId()) {
												%>
						<c:forEach var="teamInvitation" items="${teamInvitations}" varStatus="loop">
					  		<%
					  		//Logic show Kick or Leave from Team	
					  					  		TeamInvitation teamInvitation = (TeamInvitation) pageContext.getAttribute("teamInvitation");
					  					  		User user = new MySQLUserDAO().read(String.valueOf(teamInvitation.getReceiverUserId()), SearchBy.ID);
					  					  		try{
					  						  		pageContext.setAttribute("user", user);
					  		%>
					  		
						  			<tbody id="tbody-gamer-profile-${loop.index}">
								    	<tr>
								    		<th scope="row">-</th>
								      		<td><img class="img-fluid rounded img-in-table" src="data:image/png;base64, ${user.base64Picture}" alt="${user.username}'s logo" /></td>
								      		<td><a class="a-dark-mode" target="_blank" href="<%= request.getContextPath() %>/pages/user-profile.jsp?<%= User.PARAM_USER_ID %>=${user.id}">${user.username}</a></td>
								      		<td>Waiting invitation response...</td>
		
								    		<% if (team!=null && user!=null && sessionUser!=null){ %>
								    			<th scope="col">
													<form  action="<%=request.getContextPath()%>/ActionsController" method="POST" onsubmit="return cancelInvitation('<%= team.getName() %>', '<%= user.getUsername() %>')">
														<input type="hidden" name="<%= ActionsController.PARAM_SELECT_ACTION %>" value="<%= Delete.PARAM_DELETE_ACTION %>"/>
														<input type="hidden" name="<%= ActionsController.PARAM_OBJECT_CLASS %>" value="<%= TeamInvitation.class.getName() %>"/>
														<input type="hidden" name="<%= TeamInvitation.PARAM_TEAM_INVITATION_ID %>" value="<%= teamInvitation.getId() %>"/>
														<input class="btn btn-danger" type="submit" value="Cancel">
													</form>
												</th>
								    		<% } %>
								    	</tr>			
								  	</tbody>
							<% } catch(Exception e){ } %>	
						</c:forEach>
						<% } %>
						
					</table>
				</div>
			</div>
			
			<% if(sessionUser!=null) if (team.getUserOwnerId() == sessionUser.getId()) { %>
			<div>
				<form  action="<%=request.getContextPath()%>/ActionsController" method="POST" onsubmit="return deleteTeam('<%= team.getName() %>')">
					<input type="hidden" name="<%= ActionsController.PARAM_SELECT_ACTION %>" value="<%= Delete.PARAM_DELETE_ACTION %>"/>
					<input type="hidden" name="<%= ActionsController.PARAM_OBJECT_CLASS %>" value="<%= Team.class.getName() %>"/>
					<input type="hidden" name="<%= Team.PARAM_TEAM_ID %>" value="<%= team.getId() %>"/>
					<input class="btn btn-danger" type="submit" value="Delete Team">
				</form>
			</div>
			<% } %>
			
		</div>		
				
		<br/>
		
		<jsp:include page="../mod/footer.jsp"/>
		
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/team-tools.js"></script>
		
	</body>
</html>