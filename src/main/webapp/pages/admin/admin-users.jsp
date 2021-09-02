<%@ page import="com.fullvicie.actions.crud.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<%@ page import = "com.fullvicie.pojos.User" %>
<%@ page import = "com.fullvicie.daos.sql.UserSqlDao" %>
<%@ page import = "com.fullvicie.controllers.ActionsController" %>
<%@ page import = "com.fullvicie.enums.SearchBy" %>

	<div id = "users-title" class = "col-12">
        <h3 id="users-title" class = "display-3">Users</h3>
        <hr width = "25%" align = "left"/>
        <br/>
    </div>
	  
	<div class = "col-lg-4 col-md-6 col-sm-12">
      	<form id = "create-user-form" class = "form-group" action = "<%= request.getContextPath() %>/ActionsController" method = "POST">
			<input id="create-user-input-action" type='hidden' name='<%= ActionsController.PARAM_SELECT_ACTION %>' value='<%= Create.PARAM_CREATE_ACTION %>'/>
			<input id="create-user-input-object-class" type="hidden" name="<%=ActionsController.PARAM_OBJECT_CLASS %>" value="<%=User.class.getName() %>" />
			
			<label for="create-user-input-nickname">Nickname: </label>
			<p><input id = "create-user-input-nickname" type = "text" class="form-control" placeholder = "Introduce el Nombre del Usuario..." name = "<%=User.PARAM_USER_USERNAME %>" required></p>

		    <label for="create-user-input-email">Email: </label>
			<p><input id = "create-user-input-email" type = "email" class="form-control" placeholder = "Introduce el Correo Electrónico del Usuario..." name = "<%=User.PARAM_USER_EMAIL %>" required></p>

			<label for="create-user-input-password">Password: </label>
			<p><input id = "create-user-input-password" type = "text" class="form-control" placeholder = "Introduce la Contraseña del Usuario..." name = "<%=User.PARAM_USER_PASSWORD %>" required></p>

			<input id = "create-user-input-ismoderator" type = "checkbox" class="form-check-input" name = "<%=User.PARAM_USER_ISMODERATOR %>">
			<label for="create-user-input-ismoderator">Is Moderator</label>
			<br/><br/>
			<input id = "create-user-input-isadmin" type = "checkbox" class="form-check-input" name = "<%=User.PARAM_USER_ISADMIN %>">
			<label for="create-user-input-isadmin">Is Admin</label>
			<br/><br/>
            <p><input id = "create-user-input-submit" type = "submit" class="btn btn-primary" value = "Create"></p>
        </form>



        <form id = "update-user-form" class = "form-group" action = "<%= request.getContextPath() %>/ActionsController" method = "POST" style = "display: 'none';">
            
            <input id="update-user-input-action" type='hidden' name='<%= ActionsController.PARAM_SELECT_ACTION %>' value='<%= Update.PARAM_UPDATE_ACTION %>'/>
			<input id="update-user-input-object-class" type="hidden" name="<%=ActionsController.PARAM_OBJECT_CLASS %>" value="<%=User.class.getName() %>" />
			
			<label for="update-user-input-id">ID: </label>
			<p><input id = "update-user-input-id" type = "text" class="form-control" placeholder = "ID del Usuario" name = "<%=User.PARAM_USER_ID %>" readonly></p>
				
			<label for="update-user-input-nickname">Nickname: </label>
			<p><input id = "update-user-input-nickname" type = "text" class="form-control" placeholder = "Introduce el Nombre del Usuario..." name = "<%=User.PARAM_USER_USERNAME %>" required></p>

		    <label for="update-user-input-email">Email: </label>
			<p><input id = "update-user-input-email" type = "email" class="form-control" placeholder = "Introduce el Correo Electrónico del Usuario..." name = "<%=User.PARAM_USER_EMAIL %>" required></p>

			<label for="update-user-input-password">New Password: </label> 
			<span class="d-inline-block" tabindex="0" data-bs-toggle="popover" data-bs-trigger="hover focus" data-bs-content="Leave the input empty if you do not want to change the password. If left empty, the user will keep the password they had before the update.">
			  <button class="btn" type="button" disabled><i class="fas fa-question-circle"></i></button>
			</span>			
			<p><input id = "update-user-input-password" type = "text" class="form-control" placeholder = "Introduce la Contraseña del Usuario..." name = "<%=User.PARAM_USER_PASSWORD %>"></p>

			<input id = "update-user-input-ismoderator" type = "checkbox" class="form-check-input" name = "<%=User.PARAM_USER_ISMODERATOR %>">
			<label for="update-user-input-ismoderator">Is Moderator</label>
			<br/><br/>
			<input id = "update-user-input-isadmin" type = "checkbox" class="form-check-input" name = "<%=User.PARAM_USER_ISADMIN %>">
			<label for="update-user-input-isadmin">Is Administrator</label>
			<br/><br/>					
            <p>
                <input id = "input-edit-send" type = "submit" class="btn btn-primary" value = "Editar">
                <a id = "input-edit-send" class="btn btn-secondary smooth-scroller" type = "button" role="button" href = "#users-title" onclick = "cancelUpdateUser()" style = "margin-left: 10px;">Cancelar</a>
            </p>
        </form>
    </div>

    
	  
	<div class = "col-lg-8 col-md-6 col-sm-12">
        <div class = "table-responsive" style = " max-height: 600px !important; overflow: auto;">
            <table class="table table-striped">
               	<thead class = "thead-dark">
                  	<tr>
                     	<th scope="col">ID</th>
                     	<th scope="col">Nickname</th>
                     	<th scope="col">Email</th>
						<th scope="col">Sign up date</th>
						<th scope="col">Sign up time</th>
						<th scope="col">Last logout date</th>
						<th scope="col">Last logout time</th>
						<th scope="col">Moderator</th>
						<th scope="col">Administrator</th>
                        <th scope="col">Edit</th>
                        <th scope="col">Delete</th>
                  	</tr>
               	</thead>
               	
			   	<tbody>
			   		<% pageContext.setAttribute("usersList", new UserSqlDao().listBy(SearchBy.NONE, null)); %>
				   	<c:forEach var='user' items='${usersList}'>
				   		<c:if test="${not user.deleted}">
				   		<% User u = (User) pageContext.getAttribute("user"); %>
				   	
				   		<tr>
	                     	<td>${user.id}</td>
	                     	<td>${user.username}</td>
	                        <td>${user.email}</td>
	                        <td>${user.signUpDate}</td>
	                        <td>${user.signUpTime}</td>
	                        <td>${user.lastLogoutDate}</td>
	                        <td>${user.lastLogoutTime}</td>
	                        <td>${user.moderator}</td>
	                        <td>${user.admin}</td>
	                        
	                        <td>
	                            <button type = "submit" class="btn btn-warning" onclick = "updateUser(<%=u.toJavaScriptFunction() %>)">Edit</button>
	                        </td>
	                        <td>
								<form action = "<%= request.getContextPath() %>/ActionsController" method = "POST">
									<input type='hidden' name='<%= ActionsController.PARAM_SELECT_ACTION %>' value='<%= PseudoDelete.PARAM_PSEUDODELETE_ACTION %>'/>
	                           		<input type = "hidden" name = "<%=User.PARAM_USER_ID %>" value = '${user.id}'>
	                           		<input type = "hidden" name = "<%=ActionsController.PARAM_OBJECT_CLASS %>" value = "<%=User.class.getName() %>">
	                           		<button type = "submit" class="btn btn-danger">Delete</button>
	                        	</form>
	                        </td>
	                	</tr>
	                	</c:if>
				   	</c:forEach>
				</tbody>
            </table>
        </div>
    </div>
	
	<br/>  