<%@ page import="com.fullvicie.actions.crud.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<%@ page import = "com.fullvicie.pojos.VideoGame" %>
<%@ page import = "com.fullvicie.daos.sql.VideoGameSqlDao" %>
<%@ page import = "com.fullvicie.controllers.ActionsController" %>


	<div id = "video-games-title" class = "col-12">
        <h3 id="video-games-title" class = "display-3">Video Games</h3>
        <hr width = "25%" align = "left"/>
        <br/>
    </div>
	  
	<div class = "col-lg-4 col-md-6 col-sm-12">
      	<form id = "create-video-game-form" class = "form-group" action = "<%=request.getContextPath()%>/ActionsController" method = "POST">
			
			<input id="create-video-game-input-action" type='hidden' name='<%=ActionsController.PARAM_SELECT_ACTION%>' value='<%=Create.PARAM_CREATE_ACTION%>'/>
			<input id="create-video-game-input-object-class" type="hidden" name="<%=ActionsController.PARAM_OBJECT_CLASS%>" value="<%=VideoGame.class.getName()%>" />
			
			<label for="create-video-game-input-name">Name: </label>
			<p><input id = "create-video-game-input-name" type = "text" class="form-control" placeholder = "name..." name = "<%=VideoGame.PARAM_VIDEO_GAME_NAME%>" required></p>

		    <label for="create-video-game-input-description">Description: </label>
			<p><textarea id = "create-video-game-input-description" class="form-control" placeholder = "description..." name = "<%=VideoGame.PARAM_VIDEO_GAME_DESCRIPTION%>"></textarea></p>

			<br/><br/>
			
            <p><input id = "create-video-game-input-submit" type = "submit" class="btn btn-primary" value = "Create"></p>
        </form>



        <form id = "update-video-game-form" class = "form-group" action = "<%=request.getContextPath()%>/ActionsController" method = "POST" style = "display: 'none';">
            
            <input id="update-video-game-input-action" type='hidden' name='<%=ActionsController.PARAM_SELECT_ACTION%>' value='<%=Update.PARAM_UPDATE_ACTION%>'/>
			<input id="update-video-game-input-object-class" type="hidden" name="<%=ActionsController.PARAM_OBJECT_CLASS%>" value="<%=VideoGame.class.getName()%>" />
			
			<label for="update-video-game-input-id">ID: </label>
			<p><input id = "update-video-game-input-id" type = "text" class="form-control" placeholder="id of videogame" name="<%=VideoGame.PARAM_VIDEO_GAME_ID%>" readonly></p>
				
			<label for="update-video-game-input-name">Name: </label>
			<p><input id = "update-video-game-input-name" type = "text" class="form-control" placeholder = "name of videogame..." name = "<%=VideoGame.PARAM_VIDEO_GAME_NAME%>" required></p>

		    <label for="update-video-game-input-description">Description: </label>
			<p><textarea id = "update-video-game-input-description" class="form-control" placeholder = "Introduce el Correo Electrónico del Usuario..." name = "<%=VideoGame.PARAM_VIDEO_GAME_DESCRIPTION%>"></textarea></p>

			<br/><br/>	
							
            <p>
                <input id = "update-video-game-input-submit" type = "submit" class="btn btn-primary" value = "Editar">
                <a id = "cancel-update-video-game-button" class="btn btn-secondary smooth-scroller" type = "button" role="button" href = "#videogames-title" onclick = "cancelUpdateVideoGame()" style = "margin-left: 10px;">Cancelar</a>
            </p>
        </form>
    </div>

    
	  
	<div class = "col-lg-8 col-md-6 col-sm-12">
        <div class = "table-responsive" style = " max-height: 600px !important; overflow: auto;">
            <table class="table table-striped">
               	<thead class = "thead-dark">
                  	<tr>
                     	<th scope="col">ID</th>
                     	<th scope="col">Name</th>
                     	<th scope="col">Description</th>
                        <th scope="col">Edit</th>
                        <th scope="col">Delete</th>
                  	</tr>
               	</thead>
               	
			   	<tbody>
			   		<% pageContext.setAttribute("videoGamesList", new VideoGameSqlDao().list()); %>
				   	<c:forEach var='videoGame' items='${videoGamesList}'>
				   		<%
				   		VideoGame v = (VideoGame) pageContext.getAttribute("videoGame");
				   		%>
				   		<c:if test="${not videoGame.deleted}">				   	
				   		<tr>
	                     	<td>${videoGame.id}</td>
	                     	<td>${videoGame.name}</td>
	                        <td>${videoGame.description}</td>
	                        
	                        <td>
	                            <button type = "submit" class="btn btn-warning" onclick = "updateVideoGame(<%=v.toJavaScriptFunction()%>)">Edit</button>
	                        </td>
	                        <td>
								<form action = "<%=request.getContextPath()%>/ActionsController" method = "POST">
									<input type="hidden" name="<%=ActionsController.PARAM_SELECT_ACTION%>" value="<%=PseudoDelete.PARAM_PSEUDODELETE_ACTION%>"/>
	                           		<input type="hidden" name = "<%=VideoGame.PARAM_VIDEO_GAME_ID %>" value = '${videoGame.id}'>
	                           		<input type="hidden" name = "<%=ActionsController.PARAM_OBJECT_CLASS%>" value = "<%=VideoGame.class.getName()%>">
	                           		<button type="submit" class="btn btn-danger">Delete</button>
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