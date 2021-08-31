<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.ArrayList" %>
    
    
<!DOCTYPE html>
<html>
	<head>
		<title>Full Vicie - Administer</title>
		
		<!-- My Styles -->
		<link type="text/css" rel="stylesheet" href="../../styles/reset.css" />
		<link type="text/css" rel="stylesheet" href="../../styles/my-styles.css">
		
		<!-- My Scripts -->
		
		
		
		<jsp:include page="../../mod/head.jsp"/> 
		
		
	</head>
	

	<body>
		<div class = "general container">
			<jsp:include page="administer-header.jsp" />		
			<br/><br/><br/>
			<div class = "content row">
				<div class = "col-12">
					<h3 class = "display-2 text-center">Administer</h3>
					<hr class = "mx-auto" width = "50%"/>
					<br/>
				</div>
				
				<jsp:include page="admin-users.jsp"/>
				<br/>
				<jsp:include page="admin-video-games.jsp"/>
			
			</div>

		</div>
		<jsp:include page="../../mod/footer.jsp" />
		
		<script type="text/javascript" src="../../js/admin-crud.js"></script>
	</body>
</html>