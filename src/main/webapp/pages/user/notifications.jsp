<%@page import="com.fullvicie.actions.user.RejectTeamInvitation"%>
<%@page import="com.fullvicie.actions.user.AcceptTeamInvitation"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.fullvicie.pojos.*,com.fullvicie.daos.mysql.*, com.fullvicie.enums.*, com.fullvicie.actions.*, com.fullvicie.controllers.*" %>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<%
User userSession = (User) session.getAttribute(User.ATR_USER_LOGGED_OBJ);
	ArrayList<TeamInvitation> teamInvitations = new MySQLTeamInvitationDAO().listBy(SearchBy.RECEIVER_USER_ID, String.valueOf(userSession.getId()));
	
	pageContext.setAttribute("teamInvitations", teamInvitations);
%>


<!DOCTYPE html>
<html>

	<head>
		<title>FV - ${sessionScope.ATR_USER_LOGGED_OBJ.username}'s notifications</title>
		<jsp:include page="../../mod/head.jsp"/> 
	</head>
	
	<body class="dark-body">
			
		<jsp:include page="../../mod/header.jsp"/>
		<br/><br/><br/>
		
		<div class="container text-white rounded bg-dark">
			<h2>${sessionScope.ATR_USER_LOGGED_OBJ.username}'s Notifications</h2>
 			<div class="row">			
				<div class="col-12">
					<h3>Invitations</h3>			
					<table class="table table-dark table-striped">
					  	<thead>
					    	<tr>
					    		<th scope="col">Number</th>
					      		<th scope="col">Resume</th>
					      		<th scope="col">Sender</th>
					      		<th scope="col">Accept</th>
								<th scope="col">Reject</th>
					    	</tr>
					  	</thead>	
					  	
					  	<tbody>
					  		<c:forEach var="teamInvitation" items="${teamInvitations}" varStatus="loop">
					  			<%
					  			//Setting necesary variables
					  						  						  						  			TeamInvitation teamInvitation = (TeamInvitation)pageContext.getAttribute("teamInvitation");
					  						  						  						  			User transmitter = new MySQLUserDAO().read(String.valueOf(teamInvitation.getTransmitterUserId()), SearchBy.ID);
					  						  						  						  			Team team = new MySQLTeamDAO().read(String.valueOf(teamInvitation.getTeamId()), SearchBy.ID);
					  						  						  						  			VideoGame videoGame = new MySQLVideoGameDAO().read(String.valueOf(team.getVideoGameId()), SearchBy.ID);
					  						  						  						  			pageContext.setAttribute("transmitter", transmitter);
					  						  						  						  			pageContext.setAttribute("team", team);
					  						  						  						  			pageContext.setAttribute("videoGame", videoGame);
					  			%>
					  			<tr>
						  			<th scope="col">${loop.index}</th>
						      		<th scope="col"> ${transmitter.username} invited you to join in a team of ${videoGame.name} called ${team.name} </th>
						      		<th scope="col"> ${transmitter.username} </th>
						      		
						      		<th scope="col">
						      			<form  action="<%=request.getContextPath()%>/ActionsController" method="POST">
						      				<input type="hidden" name="<%= ActionsController.PARAM_SELECT_ACTION %>" value="<%= AcceptTeamInvitation.PARAM_ACCEPT_TEAM_INVITATION_ACTION %>"/>
											<input type="hidden" name="<%= TeamInvitation.PARAM_TEAM_INVITATION_ID %>" value="${teamInvitation.id}" />
											<input class="btn btn-primary" type="submit" value="Accept">
										</form>
									</th>
									
									<th scope="col">
										<form  action="<%=request.getContextPath()%>/ActionsController" method="POST">
						      				<input type="hidden" name="<%= ActionsController.PARAM_SELECT_ACTION %>" value="<%= RejectTeamInvitation.PARAM_REJECT_TEAM_INVITATION_ACTION %>"/>	
											<input type="hidden" name="<%= TeamInvitation.PARAM_TEAM_INVITATION_ID %>" value="${teamInvitation.id}" />
											<input class="btn btn-danger" type="submit" value="Reject">
										</form>
									</th>
					  			</tr>
							</c:forEach>
					  	</tbody>
					  	
					</table>
				</div>
			</div>
 			
 		</div>
 		
		<br/>
		
		<jsp:include page="../../mod/footer.jsp"/>
	</body>
	
</html>