<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import = "com.fullvicie.pojos.*" %>
<%@ page import = "com.fullvicie.daos.*" %>
    
    
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="../../mod/head.html"/> 
		
		<!-- Styles -->
		<link rel = "stylesheet" href = "../../styles/mystyle.css"/>
		
		<title>Full Vicie - Administer</title>
	</head>
	

	<body>
		<div class = "general container">
			<jsp:include page="admin_header.jsp" />		
			<br/><br/><br/>
			<div class = "content row">
				<div class = "col-12">
					<h3 class = "display-2 text-center">Administer</h3>
					<hr class = "mx-auto" width = "50%"/>
					<br/>
				</div>
				
				<jsp:include page="users_admin.jsp"/>
			
			</div>

		</div>
		<jsp:include page="../../mod/footer.jsp" />
		
		<script type="text/javascript" src="../../js/adminCRUD.js"></script>
	</body>
</html>