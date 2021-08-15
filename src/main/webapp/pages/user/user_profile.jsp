<%@page import="com.fullvicie.actions.user.UpdateUserPicture"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="com.fullvicie.controllers.ActionsController,com.fullvicie.actions.admin.Update" %>
<%@ page import="com.fullvicie.pojos.User,com.fullvicie.pojos.Profile" %>
<%@ page import="com.fullvicie.enums.*" %>
<%@ page import="com.fullvicie.daos.sql.ProfileSqlDao,com.fullvicie.daos.sql.UserSqlDao" %>
 
<!DOCTYPE html>
<html>
	<head>
		<title>FV - ${sessionScope.ATR_USER_LOGGED_OBJ.username}'s profile</title>
		<jsp:include page="../../mod/head.html"/> 
		
	</head>
	
	<%
		Profile profile = null;
			try {
				User user = (User)session.getAttribute(User.ATR_USER_LOGGED_OBJ);
				user = new UserSqlDao().read(String.valueOf(user.getId()), SearchBy.ID);
				profile = new ProfileSqlDao().read(String.valueOf(user.getId()), SearchBy.USER_ID); 
				if(profile==null){
			profile = new Profile();
			profile.setUserId(user.getId());
			(new ProfileSqlDao()).create(profile);
			profile = new ProfileSqlDao().read(String.valueOf(user.getId()), SearchBy.USER_ID); 
				}
				session.setAttribute(Profile.ATTR_PROFILE_OBJ, profile);
			}
			catch(Exception e){
				((HttpServletResponse)response).sendRedirect(request.getContextPath() + "/pages/error.jsp?ERROR_TYPE="+ErrorType.READ_PROFILE_ERROR); 
			}
			
			if(profile==null)
				((HttpServletResponse)response).sendRedirect(request.getContextPath() + "/pagesd/error.jsp?ERROR_TYPE="+ErrorType.READ_PROFILE_ERROR);
		%>
	
	
	
	
	<body class="" style="background-color: #161616">
		
		<jsp:include page="../../mod/header.jsp"/>
		<br/><br/><br/>
		<div class="container text-white rounded bg-dark">
  			
  			<h2>Profile</h2>
  			<div class="row">
	  			<div class="col-8">
		  			
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
		        
		        <div class="col-4">
		        	<img  class="img-fluid rounded" src="data:image/png;base64, ${ATR_USER_LOGGED_OBJ.base64Picture}" alt="${ATR_USER_LOGGED_OBJ.username}'s picture."/>
		        
		        	<form id = "change-user-picture-form" class = "form-group" enctype="multipart/form-data" action="<%=request.getContextPath()%>/ActionsController" method="POST">
						<input id="user-input-action" type='hidden' name='<%=ActionsController.PARAM_SELECT_ACTION%>' value='<%=UpdateUserPicture.PARAM_CHANGE_USER_PICTURE_ACTION%>'/>
						
						<label for="user-input-picture"><i class="fas fa-image"></i> Picture: </label>
						<p><input id = "user-input-picture" type = "file" accept="image/*" class="form-control" name="<%=User.PART_USER_PICTURE %>"></p>												
				        
				        <input id="user-input-change-picture" type="submit" class="btn btn-primary" value="Change"/>
					</form>
		        
		        </div>
			</div>
			
			
			<div>
		        <br/><br/>
		        
		        <div>
			        <h3>Personal Information</h3>
			        <form id="update-user-form" class="form-group" action="<%= request.getContextPath() %>/ActionsController" method="POST" >
		            
			            <input id="update-profile-input-action" type='hidden' name='<%= ActionsController.PARAM_SELECT_ACTION %>' value='<%= Update.PARAM_UPDATE_ACTION %>'/>
						<input id="update-profile-input-object-class" type="hidden" name="<%=ActionsController.PARAM_OBJECT_CLASS %>" value="<%=Profile.class.getName() %>" />
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
						<p><textarea id="update-profile-input-biography" class="form-control" name = "<%=Profile.PARAM_PROFILE_BIOGRAPHY %>" disabled>${ATTR_PROFILE_OBJ.biography}</textarea></p>
						
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
  			
	        
	        <br/>
	        
		</div>
		
		<br/>
		
		<jsp:include page="../../mod/footer.jsp"/>
		
		<script type="text/javascript" src="../../js/profile_tools.js"></script>
		
	</body>
</html>