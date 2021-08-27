<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<!DOCTYPE html>
<html>
	<head>
		<title>Full Vicie</title>
		
	
	
	
		<!-- My Styles -->
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/secured/styles/reset.css"/>
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/secured/styles/my-styles.css"/>
		<link type="text/css" rel="stylesheet" href="<%=request.getContextPath() %>/secured/styles/carousel.css"/>
		
		<!-- My Scripts -->		
		
		
		<jsp:include page="secured/mod/head.html"/> 

	</head>
	
	<body class="dark-body">
		
		<jsp:include page="secured/mod/cookies-advise.jsp"/> 
		<jsp:include page="secured/mod/login-popup.jsp"/>
		<jsp:include page="secured/mod/header.jsp"/>
		<jsp:include page="secured/mod/carousel.jsp"/>
		
		<div class="container text-white bg-dark">
  			
		</div>
		
		<jsp:include page="secured/mod/footer.jsp"/>
	
	</body>
</html>