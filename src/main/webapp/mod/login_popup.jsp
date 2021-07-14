<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.fullvicie.controllers.ActionsController, com.fullvicie.actions.user.Login, com.fullvicie.actions.user.Signup, com.fullvicie.pojos.User" %>
       
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	
	<div class="modal-dialog modal-login">
		
		<div class="modal-content">
			
			<div class="modal-header">				
				<h4 class="modal-title" align="center">Authentication Form</h4>
			</div>
			
			<div id="login-form" class="modal-body">
				<form action="<%=request.getContextPath() %>/Controller" method="post">
					
					<input type="hidden" name="<%=ActionsController.PARAM_SELECT_ACTION %>" value="<%=Login.PARAM_ACTION %>"/>
					
					<div class="form-group">
						<i class="fa fa-user"></i>
						<input type="text" class="form-control" name="<%=User.PARAM_USER_USERNAME %>" placeholder="Username" required/>
					</div>
					<br/>
					
					<div class="form-group">
						<i class="fa fa-lock"></i>
						<input type="password" class="form-control password_input" name="<%=User.PARAM_USER_PASSWORD %>" placeholder="Password" required/>					
					</div>
					<br/>
					
					<div class="form-group">
						<input type="submit" class="btn btn-primary btn-block btn-lg" value="Login"/>
					</div>
					<br/>

				</form>
			</div>
			
			
			<div id="signup-form" class="modal-body" style="display: none">
				<form action="" method="post">
					
					<input type="hidden" name="<%=ActionsController.PARAM_SELECT_ACTION %>" value="<%=Signup.PARAM_ACTION %>"/>
					
					<div class="form-group">
						<i class="fa fa-user"></i>
						<input type="text" class="form-control" placeholder="Username" name="<%=User.PARAM_USER_USERNAME %>" required/>
					</div>
					<br/>
					
					<div class="form-group">
						<i class="fa fa-user"></i>
						<input type="text" class="form-control" placeholder="Email" name="<%=User.PARAM_USER_EMAIL %>" required/>
					</div>
					<br/>
					
					<div class="form-group">
						<i class="fa fa-lock"></i>
						<input type="password" class="form-control password_input" name="<%=User.PARAM_USER_PASSWORD %>" placeholder="Password" required/>					
					</div>
					<br/>
					
					<div class="form-group">
						<i class="fa fa-lock"></i>
						<input type="password" class="form-control password_input" name="<%=User.PARAM_USER_REPEATPASSWORD %>" placeholder="Repeat password" required/>					
					</div>
					<br/>
					
					<div class="form-group">
						<input type="submit" class="btn btn-primary btn-block btn-lg" value="Signup"/>
					</div>
					<br/>
					
				</form>
			</div>
			
			
			
			<div class="modal-footer">
				<input id="checkbox-showpassword" type = "checkbox" onclick="showPassIn('exampleModal')"/>
				<label for="checkbox-showpassword">Show password</label>
				<a href="#">Forgot Password?</a>
        		<a id="signup-login-label" href="#" value="false" onclick="showSignUpForm()">Sign Up</a>
			</div>
		</div>
	</div>
</div>     

<script src = "js/login-form.js"></script>
