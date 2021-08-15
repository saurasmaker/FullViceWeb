<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<!DOCTYPE html>
<html>
	<head>
		<title>Full Vicie</title>
		<jsp:include page="mod/head.html"/> 
		
		<!-- Recaptcha Google -->
		<script src="https://www.google.com/recaptcha/api.js" async defer></script>
		<!-- Anuncios Google -->
		<script data-ad-client="ca-pub-5731524801665513" async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
		
		<!-- My Scripts -->
		
	
	
	
		<!-- My Styles -->
		<link type="text/css" rel="stylesheet" href = "styles/show-login-form.css"/>
		<link type="text/css" rel="stylesheet" href = "styles/carousel.css"/>
		
	</head>
	
	<body style="background-color: #161616">
		
		<jsp:include page="mod/cookies_advise.html"/> 
		<jsp:include page="mod/login_popup.jsp"/>
		<jsp:include page="mod/header.jsp"/>
		<jsp:include page="mod/carousel.jsp"/>
		
		<div class="container text-white bg-dark">
  			
		</div>
		
		
		
		<jsp:include page="mod/footer.jsp"/>
	
	</body>
</html>