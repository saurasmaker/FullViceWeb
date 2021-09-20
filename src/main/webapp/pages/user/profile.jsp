
<%@page import="com.fullvicie.daos.mysql.MySQLVideoGameDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<%@ page import="java.util.ArrayList" %>

<%@ page import="com.fullvicie.controllers.ActionsController,com.fullvicie.actions.crud.Update,com.fullvicie.actions.crud.Create,com.fullvicie.actions.crud.PseudoDelete,com.fullvicie.actions.user.ChangeUserPicture" %>
<%@ page import="com.fullvicie.pojos.User,com.fullvicie.pojos.PersonalInformation,com.fullvicie.pojos.GamerProfile,com.fullvicie.pojos.VideoGame" %>
<%@ page import="com.fullvicie.daos.mysql.MySQLUserDAO,com.fullvicie.daos.mysql.MySQLProfileDAO,com.fullvicie.daos.mysql.MySQLGamerProfileDAO,com.fullvicie.daos.mysql.MySQLVideoGameDAO" %>
<%@ page import="com.fullvicie.enums.*" %>
 
 <%
  // Profile JSP Logic.

               	PersonalInformation profile = null;
               	try {
               		User user = (User)session.getAttribute(User.ATR_USER_LOGGED_OBJ);
               		user = new MySQLUserDAO().read(String.valueOf(user.getId()), SearchBy.ID);
               		profile = new MySQLProfileDAO().read(String.valueOf(user.getId()), SearchBy.USER_ID); 
               		if(profile==null){
            		   	profile = new PersonalInformation();
            		   	profile.setUserId(user.getId());
            		   	(new MySQLProfileDAO()).create(profile);
            		   	profile = new MySQLProfileDAO().read(String.valueOf(user.getId()), SearchBy.USER_ID); 
               		}
               		
               		session.setAttribute(User.ATR_USER_LOGGED_OBJ, user);
               		session.setAttribute(PersonalInformation.ATTR_PROFILE_OBJ, profile);
               		pageContext.setAttribute(VideoGame.ATTR_VIDEO_GAMES_LIST, new MySQLVideoGameDAO().listBy(SearchBy.NONE, null));
               		session.setAttribute(GamerProfile.ATTR_GAMER_PROFILES_LIST, new MySQLGamerProfileDAO().listBy(SearchBy.USER_ID,String.valueOf(user.getId())));
               		
               	}
               	catch(Exception e){
               		((HttpServletResponse)response).sendRedirect(request.getContextPath() + "/pages/error.jsp?ERROR_TYPE="+ErrorType.READ_PROFILE_ERROR); 
               	}
               	
               	if(profile==null)
               		((HttpServletResponse)response).sendRedirect(request.getContextPath() + "/pages/error.jsp?ERROR_TYPE="+ErrorType.READ_PROFILE_ERROR);
  %>
 
 
<!DOCTYPE html>
<html>
	<head>
		<title>FV - ${sessionScope.ATR_USER_LOGGED_OBJ.username}'s profile</title>
		<jsp:include page="../../mod/head.jsp"/> 
	</head>
	
	<body class="dark-body">
		
		<jsp:include page="../../mod/header.jsp"/>
		<br/><br/><br/>
		<div class="container text-white rounded bg-dark">
  			
  			<h2 id="users-title">${sessionScope.ATR_USER_LOGGED_OBJ.username}'s Profile</h2>
  			<div class="row">
	  			<div id="profile-forms" class="col-8">
		  			
		  			<div id="update-user-div" class="row">
		  				<form id = "update-user-form" class = "form-group" action="<%=request.getContextPath()%>/ActionsController" method = "POST" >
				            <input id="user-input-action" type='hidden' name='<%=ActionsController.PARAM_SELECT_ACTION%>' value='<%=Update.PARAM_UPDATE_ACTION%>'/>
							<input id="user-input-object-class" type="hidden" name="<%=ActionsController.PARAM_OBJECT_CLASS%>" value="<%=User.class.getName()%>" />
							<input id="user-input-id" type = "hidden" name="<%=User.PARAM_USER_ID %>" value="${ATR_USER_LOGGED_OBJ.id}" />
	
							<label for="user-input-username"><i class="fas fa-user"></i> Username: </label>
							<span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="click here to change your username">
							  <button class="btn btn-link text-white" type="button" onclick="enableUsernameInput()"><i class="fas fa-edit"></i></button>
							</span>	
							<p><input id = "user-input-username" type = "text" class="form-control" placeholder="username" name="<%=User.PARAM_USER_USERNAME %>" value="${ATR_USER_LOGGED_OBJ.username}" disabled></p> 
				
						    <label for="user-input-email"><i class="fas fa-envelope"></i> Email: </label>
						    <span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="click here to change your email">
							  <button class="btn btn-link text-white" type="button" onclick="enableEmailInput()"><i class="fas fa-edit"></i></button>
							</span>	
							<p><input id = "user-input-email" type = "email" class="form-control" placeholder = "example@example.ex" name="<%=User.PARAM_USER_EMAIL %>" value="${ATR_USER_LOGGED_OBJ.email}" disabled></p>
							<br/>
							
							<p>
			                <input id="user-input-confirm" type="submit" class="btn btn-primary" value="Confirm" disabled/>
			               	<button id="user-btn-cancel" class="btn btn-secondary" type="button" 
			               		onclick='resetUserData("${ATR_USER_LOGGED_OBJ.username}", "${ATR_USER_LOGGED_OBJ.email}")' 
			               		disabled>Cancel</button>
							</p>
			        	</form>
		  			</div>
		  			
		  			<br/>
		  			
		  			<div id="update-personal-information-div" class="row">
		  				<h3 id="profiles-title">Personal Information</h3>
			        	<form id="update-user-form" class="form-group" action="<%=request.getContextPath()%>/ActionsController" method="POST" >
				            <input id="update-profile-input-action" type='hidden' name='<%=ActionsController.PARAM_SELECT_ACTION%>' value='<%=Update.PARAM_UPDATE_ACTION%>'/>
							<input id="update-profile-input-object-class" type="hidden" name="<%=ActionsController.PARAM_OBJECT_CLASS%>" value="<%=PersonalInformation.class.getName()%>" />
							<input id="update-profile-input-id" type = "hidden" class="form-control" name="<%=Profile.PARAM_PROFILE_ID %>" value="${ATTR_PROFILE_OBJ.id}"/>
							<input id="update-profile-input-id" type = "hidden" class="form-control" name="<%=Profile.PARAM_PROFILE_USER_ID %>" value="${ATTR_PROFILE_OBJ.userId}"/>
								
							<label for="update-profile-input-name"><i class="fas fa-address-card"></i> Name: </label>
							<span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="click here to change your name">
							  <button class="btn btn-link text-white" type="button" onclick="enableNameInput()"><i class="fas fa-edit"></i></button>
							</span>	
							<p><input id="update-profile-input-name" type = "text" class="form-control" name="<%=Profile.PARAM_PROFILE_NAME %>" value="${ATTR_PROFILE_OBJ.name}" disabled /></p>
				
						    <label for="update-profile-input-surnames"><i class="fas fa-address-card"></i> Surnames: </label>
						    <span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="click here to change your surnames">
							  <button class="btn btn-link text-white" type="button" onclick="enableSurnamesInput()"><i class="fas fa-edit"></i></button>
							</span>	
							<p><input id="update-profile-input-surnames" type = "text" class="form-control" name = "<%= Profile.PARAM_PROFILE_SURNAMES %>" value="${ATTR_PROFILE_OBJ.surnames}" disabled /></p>
			
							<label for="update-profile-input-biography"><i class="fas fa-address-card"></i> Biography: </label>
						    <span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="click here to change your biography">
							  <button class="btn btn-link text-white" type="button" onclick="enableBiographyInput()"><i class="fas fa-edit"></i></button>
							</span>	
							<p><textarea id="update-profile-input-biography" class="form-control" name = "<%=PersonalInformation.PARAM_PROFILE_BIOGRAPHY%>" disabled>${ATTR_PROFILE_OBJ.biography}</textarea></p>
							
							<label for="update-user-input-birthday"><i class="fas fa-birthday-cake"></i> Birthday: </label>
						    <span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="click here to change your birthday date">
							  <button class="btn btn-link text-white" type="button" onclick="enableBirthdayInput()"><i class="fas fa-edit"></i></button>
							</span>	
							<p><input id="update-profile-input-birthday" class="form-control" type="date" class="form-control" name="<%=Profile.PARAM_PROFILE_BIRTHDAY %>" value="${ATTR_PROFILE_OBJ.birthday}" disabled /></p>
							<br/>
							
				            <p>
				                <input id = "profile-input-confirm" type = "submit" class="btn btn-primary" value = "Confirm" disabled />
				                <button id = "profile-btn-cancel" class="btn btn-secondary smooth-scroller" type = "button" role="button" 
				                	onclick = 'resetProfileData("${ATTR_PROFILE_OBJ.name}", "${ATTR_PROFILE_OBJ.surnames}", "${ATTR_PROFILE_OBJ.biography}", "${ATTR_PROFILE_OBJ.birthday}")' 
				                	style = "margin-left: 10px;" disabled >Cancel</button>
				            </p>
			        	</form>
		  			</div>
		  			
		        </div>
		        
		        <div id="change-user-picture-div" class="col-4">
		        	<label for="user-input-picture"><i class="fas fa-image"></i> Picture: </label>
						
		        	<img  class="img-fluid rounded" src="data:image/png;base64, ${ATR_USER_LOGGED_OBJ.base64Picture}" alt="${ATR_USER_LOGGED_OBJ.username}'s picture."/>
		        	<br/>
		        	<form id = "change-user-picture-form" class = "form-group" enctype="multipart/form-data" action="<%=request.getContextPath()%>/ActionsController" method="POST">
						<input id="user-input-action" type='hidden' name='<%=ActionsController.PARAM_SELECT_ACTION%>' value='<%=ChangeUserPicture.PARAM_CHANGE_USER_PICTURE_ACTION%>'/>
				        <p><input id = "user-input-picture" type = "file" accept="image/*" class="form-control" name="<%=User.PART_USER_PICTURE %>"></p>
				        <input id="user-input-change-picture" type="submit" class="btn btn-primary" value="Change"/>
					</form>
		        
		        </div>
			</div>
			
			<br/>
			
			<div id="" class="row">
				<h3 id="gamer-profiles-title">Gamer profiles</h3>
		        <div class="col-12">			
					<table class="table table-dark table-striped">
					  	<thead>
					    	<tr>
					      		<th scope="col">Name</th>
					      		<th scope="col">Analysis page</th>
					      		<th scope="col">Video game</th>
					      		<th scope="col">Create / Edit</th>
								<th scope="col">Remove</th>
					    	</tr>
					  	</thead>
					  	
					  	<c:forEach var="videoGame" items="${ATTR_VIDEO_GAMES_LIST}">
							<c:if test="${not videoGame.deleted}">
								
								<% // Logic to check if user has the Gamer Profile of this Video Game
								ArrayList<GamerProfile> userGamerProfiles = (ArrayList<GamerProfile>) session.getAttribute(GamerProfile.ATTR_GAMER_PROFILES_LIST);
								VideoGame vg = (VideoGame) pageContext.getAttribute("videoGame");
								pageContext.setAttribute("userHasGamerProfile", false);
								for(GamerProfile gp: userGamerProfiles)
									if(gp.getVideoGameId()==vg.getId()){
										pageContext.setAttribute("userHasGamerProfile", true);
										pageContext.setAttribute("gamerProfile", gp);
										break;
									}	
								%>
					
								<tbody id="div-video-game-${videoGame.id}">
						    		<c:choose>
						    		
						    			<c:when test="${userHasGamerProfile}">
						    				<tr>
							    				<th scope="row" >${gamerProfile.nameInGame}</th>
									      		<td><a class="a-dark-mode" href="${gamerProfile.analysisPage}" target="_blank">${gamerProfile.analysisPage}</a></td>
									      		<td>${videoGame.name}</td>
									      		<td><a class="btn btn-primary" data-bs-toggle="collapse" href="#update-gamer-profile-collapse-${videoGame.id}" role="button" aria-expanded="false" aria-controls="update-gamer-profile-collapse-${videoGame.id}">Edit</a></td>
									      		<td>
									      			<form id = "remove-create-gamer-profile-form-${videoGame.id}" class = "form-group" action="<%=request.getContextPath()%>/ActionsController" method = "POST" onsubmit="return removeGamerProfile()" >
											            <input id="remove-gamer-profile-input-action-${videoGame.id}" type='hidden' name='<%=ActionsController.PARAM_SELECT_ACTION%>' value='<%=PseudoDelete.PARAM_PSEUDODELETE_ACTION%>'/>
														<input id="remove-gamer-profile-input-object-class-${videoGame.id}" type="hidden" name="<%=ActionsController.PARAM_OBJECT_CLASS%>" value="<%=GamerProfile.class.getName()%>" />
							      						<input id="remove-gamer-profile-input-id-${videoGame.id}" type="hidden" name="<%=GamerProfile.PARAM_GAMER_PROFILE_ID%>" value="${gamerProfile.id}" />
							      						<input id="remove-gamer-profile-input-confirm-${videoGame.id}" type="submit" class="btn btn-danger" value="Remove"/>
							      					</form>
									      		</td>
									      	</tr>
									      	
									      	<tr>
								      			<td colspan="5">
										      		<div id="update-gamer-profile-collapse-${videoGame.id}" class="collapse">
														<div id="update-gamer-profile-div-${videoGame.id}" class="card card-body bg-dark">
											  				<form id = "update-gamer-profile-form-${videoGame.id}" class = "form-group" action="<%=request.getContextPath()%>/ActionsController" method = "POST" >
													            <input id="update-gamer-profile-input-action-${videoGame.id}" type='hidden' name='<%=ActionsController.PARAM_SELECT_ACTION%>' value='<%=Update.PARAM_UPDATE_ACTION%>'/>
																<input id="update-gamer-profile-input-object-class-${videoGame.id}" type="hidden" name="<%=ActionsController.PARAM_OBJECT_CLASS%>" value="<%=GamerProfile.class.getName()%>" />
																<input id="update-gamer-profile-input-user-id-${videoGame.id}" type="hidden" name="<%=GamerProfile.PARAM_GAMER_PROFILE_ID %>" value="${gamerProfile.id}" />
																<input id="update-gamer-profile-input-user-id-${videoGame.id}" type="hidden" name="<%=GamerProfile.PARAM_GAMER_PROFILE_USER_ID %>" value="${ATR_USER_LOGGED_OBJ.id}" />
																<input id="update-gamer-profile-input-video-game-id-${videoGame.id}" type="hidden" name="<%=GamerProfile.PARAM_GAMER_PROFILE_VIDEO_GAME_ID%>" value="${videoGame.id}" />
																
																<label for="update-gamer-profile-input-name-${videoGame.id}"><i class="fas fa-tag"></i> Name in video game: </label>
																<p><input id = "update-gamer-profile-input-name-${videoGame.id}" type="text" class="form-control" value="${gamerProfile.nameInGame}" name="<%=GamerProfile.PARAM_GAMER_PROFILE_NAME_IN_GAME %>" required></p> 
													
															    <label for="update-gamer-profile-input-description-${videoGame.id}"><i class="fas fa-book"></i> Analysis page: </label>
																<p><input id="update-gamer-profile-input-description-${videoGame.id}" type="text" class="form-control" value="${gamerProfile.analysisPage}" name="<%=GamerProfile.PARAM_GAMER_PROFILE_ANALYSIS_PAGE %>" required/></p>							
																
																<input id="update-gamer-profile-input-confirm-${videoGame.id}" type="submit" class="btn btn-primary" value="Confirm"/>
												        		<a id="a-cancel-gamer-profile-update-${videoGame.id}" class="btn btn-secondary" data-bs-toggle="collapse" href="#update-gamer-profile-collapse-${videoGame.id}" role="button" aria-expanded="false" aria-controls="update-gamer-profile-collapse-${videoGame.id}" onclick="cancelEditGamerProfile('${videoGame.id}', '${gamerProfile.nameInGame}', '${gamerProfile.analysisPage}')">Cancel</a>
												        	</form>
										  				</div>
													</div>
												</td>
						    				</tr>
						    			</c:when>
						    			
						    			<c:otherwise>
						    				<tr>
							    				<th scope="row">-</th>
							    				<td>-</td>
							    				<td>${videoGame.name}</td>
									      		<td><a class="btn btn-primary" data-bs-toggle="collapse" href="#create-gamer-profile-collapse-${videoGame.id}" role="button" aria-expanded="false" aria-controls="create-gamer-profile-collapse-${videoGame.id}">Create</a></td>
									      		<td>-</td>
								      		</tr>
								      		
								      		<tr>
								      			<td colspan="5">
										      		<div id="create-gamer-profile-collapse-${videoGame.id}" class="collapse">
														<div id="create-gamer-profile-div-${videoGame.id}" class="card card-body bg-dark">
											  				<form id = "create-gamer-profile-form-${videoGame.id}" class = "form-group" action="<%=request.getContextPath()%>/ActionsController" method = "POST" >
													            <input id="create-gamer-profile-input-action-${videoGame.id}" type='hidden' name='<%=ActionsController.PARAM_SELECT_ACTION%>' value='<%=Create.PARAM_CREATE_ACTION%>'/>
																<input id="create-gamer-profile-input-object-class-${videoGame.id}" type="hidden" name="<%=ActionsController.PARAM_OBJECT_CLASS%>" value="<%=GamerProfile.class.getName()%>" />
																<input id="create-gamer-profile-input-user-id-${videoGame.id}" type="hidden" name="<%=GamerProfile.PARAM_GAMER_PROFILE_USER_ID %>" value="${ATR_USER_LOGGED_OBJ.id}" />
																<input id="create-gamer-profile-input-video-game-id-${videoGame.id}" type="hidden" name="<%=GamerProfile.PARAM_GAMER_PROFILE_VIDEO_GAME_ID%>" value="${videoGame.id}" />
																
																<label for="create-gamer-profile-input-name-${videoGame.id}"><i class="fas fa-tag"></i> Name in video game: </label>
																<p><input id = "create-gamer-profile-input-name-${videoGame.id}" type="text" class="form-control" placeholder="Name of the team" name="<%=GamerProfile.PARAM_GAMER_PROFILE_NAME_IN_GAME %>" required></p> 
													
															    <label for="create-gamer-profile-input-description-${videoGame.id}"><i class="fas fa-book"></i> Analysis page: </label>
																<p><input id="create-gamer-profile-input-description-${videoGame.id}" type="text" class="form-control" placeholder="Set a description of the team..." name="<%=GamerProfile.PARAM_GAMER_PROFILE_ANALYSIS_PAGE %>" required/></p>							
															
																<br/>
																
																<input id="create-gamer-profile-input-confirm-${videoGame.id}" type="submit" class="btn btn-primary" value="Confirm"/>
												        	</form>
										  				</div>
													</div>
												</td>
						    				</tr>
						    			</c:otherwise>
						    			
						    		</c:choose>
							    	
							  	</tbody>
			
							</c:if>
							
						</c:forEach>
						
					</table>
					
				</div>
				
  			</div>

	        <br/>
	        
		</div>
		
		<br/>
		
		<jsp:include page="../../mod/footer.jsp"/>
		
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/profile-tools.js"></script>
		
	</body>
</html>