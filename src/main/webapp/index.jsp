<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<!DOCTYPE html>
<html>
	<head>
		<title>Full Vicie</title>

		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/styles/carousel.css"/>

		<jsp:include page="mod/head.jsp"/> 
	</head>
	
	<body class="dark-body">
		
		<jsp:include page="mod/cookies-advise.jsp"/> 
		<jsp:include page="mod/header.jsp"/>
		<jsp:include page="mod/carousel.jsp"/>
			
		<div class="container text-white bg-dark">
		</div>
		
		<jsp:include page="mod/footer.jsp"/>
	
	</body>
</html>


	