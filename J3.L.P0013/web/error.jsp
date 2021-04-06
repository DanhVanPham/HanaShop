<%-- 
    Document   : error.jsp
    Created on : Jan 13, 2021, 11:28:49 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="../css/style.css"/>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous"><link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Error Page</title>
    </head>
    <body>
        <c:if test="${not empty sessionScope.USER and sessionScope.USER.roleId eq 'ROL_2'}">
            <jsp:include page="menu.jsp"/>
        </c:if>
        <c:if test="${not empty sessionScope.USER and sessionScope.USER.roleId eq 'ROL_1'}">
            <jsp:include page="navBarAdmin.jsp"/>
        </c:if>
        <c:if test="${empty sessionScope.USER}">
            <jsp:include page="menu.jsp"/>
        </c:if>
        <h1 style="margin-left: 10%;margin-top: 2%;width: 80%;text-align: center;">Error Page</h1>
        <h2 style="margin-left: 10%;margin-top: 2%;width: 80%;text-align: center;color: red;">Something is wrong</h2>
        <h2 style="margin-left: 10%;margin-top: 2%;width: 80%;text-align: center;color: red;">${requestScope.ERROR}</h2>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
