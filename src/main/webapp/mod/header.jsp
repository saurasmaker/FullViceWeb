<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="com.fullvicie.pojos.User"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>


<c:if test="${empty ATR_USER_LOGGED}">
	<c:set var="disabled-forum" scope="page" value="yas bro"/>
	<c:set var="atributes-disabled" scope="page" value="tabindex='-1' aria-disabled='true'"/>
</c:if>

<header>
  <nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark" aria-label="Ninth navbar example">
    <div class="container-xl">
      <a class="navbar-brand" href="index.jsp">Full Vicie</a>
      
      <div class="collapse navbar-collapse" id="navbarsExample07XL">
        
        <ul class="navbar-nav dropdown-mousehover me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="pages/communities.jsp">Comunidades</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="pages/projects.jsp">Proyectos</a>
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle ${disabled-forum}" href="pages/forums.jsp" id="dropdown07XL" data-bs-toggle="dropdown" data-hover="dropdown" aria-expanded="false">Foros</a>
             <ul class="dropdown-menu" aria-labelledby="dropdown07XL">
             <li>${disabled-forum}</li>
                <li><a class="dropdown-item" href="#">Videojuegos</a></li>
                <li><a class="dropdown-item" href="#">Series</a></li>
                <li><a class="dropdown-item" href="#">Pel&iacute;culas</a></li>
                <li><a class="dropdown-item" href="#">Libros</a></li>
                <li><a class="dropdown-item" href="#">Juegos de Mesa</a></li>
                <li><a class="dropdown-item" href="#">E-SPORTS</a></li>
                <div class="dropdown-divider"></div>
                <li><a class="dropdown-item" href="#">Proyectos</a></li>
            </ul>
          </li>
        </ul>
        
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <div class="input-group">
            <div class="form-outline">
              <input type="search" id="form1" class="form-control" placeholder="Search"/>
            </div>
            <button type="button" class="btn btn-primary">
              <i class="fas fa-search"></i>
            </button>
          </div>
        </ul>
        
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class = "nav-item">
            <button type="button" class="btn nav-link" data-bs-toggle="modal" data-bs-target="#exampleModal">
            <i class="fas fa-user"></i> Login
            </button>
          </li>
        </ul>

      </div>

    </div>
    
  </nav>

  <jsp:include page="carousel.jsp"/>

</header>