<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
    
    
<!DOCTYPE html>
<html>
	<head>
		<title>FV - Error</title>
		<jsp:include page="../mod/head.jsp"/> 
	</head>
	
	<body class="dark-body">
	
		<jsp:include page="../mod/header.jsp"/>
		<br/><br/><br/>
		
		<div class="container text-white rounded bg-dark">
			<p><%=request.getParameter("ERROR_TYPE") %></p>
		</div>
		
		<jsp:include page="../mod/footer.jsp"/>
		
	</body>
</html>