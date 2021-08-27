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
				<form name="loginForm" action="<%=request.getContextPath() %>/ActionsController" method="post" onsubmit="return doCaptcha()">
					
					<input type="hidden" name="<%=ActionsController.PARAM_SELECT_ACTION %>" value="<%=Login.PARAM_LOGIN_ACTION %>"/>
					
					<div class="form-group">
						<i class="fa fa-user"></i>
						<input type="text" id="input-username-login-form" class="form-control" name="<%=User.PARAM_USER_USERNAME %>" placeholder="Username" required/>
					</div>
					<br/>
					
					<div class="form-group">
						<i class="fa fa-lock"></i>
						<input type="password" id="input-password-login-form" class="form-control password_input" name="<%=User.PARAM_USER_PASSWORD %>" placeholder="Password" required/>					
					</div>
					<br/>
					<div align="center" class="form-group">
						<input type="submit" class="btn btn-primary btn-block btn-lg" value="Login"/>
					</div>

				</form>
			</div>
			
			
			<div id="signup-form" class="modal-body" style="display: none">
				<form name="signupForm" action="<%=request.getContextPath() %>/ActionsController" method="post" onsubmit="return doCaptcha()">
					
					<input type="hidden" name="<%=ActionsController.PARAM_SELECT_ACTION %>" value="<%=Signup.PARAM_SIGNUP_ACTION %>"/>
					
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
					<div align="center" class="form-group">
						<input type="submit" class="btn btn-primary btn-block btn-lg" value="Signup"/>
					</div>
					
				</form>
			</div>
			
			<br/>
			<div id="status" align="center" style="color: red;"></div>	
			<br/>
			<div align="center" class="g-recaptcha" data-sitekey="6LfiutUbAAAAAHFGeKgtXQi2q1fyOYKNGKk3JRuo"></div>
			
			<br/>
			<div class="modal-footer">
				<input id="checkbox-showpassword" type = "checkbox" onclick="showPassIn('exampleModal')"/>
				<label for="checkbox-showpassword">Show password</label>
				<a href="#">Forgot Password?</a>
        		<a id="signup-login-label" href="#" value="false" onclick="showSignUpForm()">Sign Up</a>
			</div>
		</div>
	</div>
</div>     

<<<<<<< HEAD:src/main/webapp/mod/login-popup.jsp
<script src = "<%=request.getContextPath() %>/js/login-form.js"></script>
=======
<script src = "js/login-form.js"></script>
>>>>>>> parent of 7294c19 (a):src/main/webapp/mod/login_popup.jsp
