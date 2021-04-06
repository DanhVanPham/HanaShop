<%-- 
    Document   : viewOrderDetail
    Created on : Jan 13, 2021, 3:45:14 PM
    Author     : DELL
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Detail Page</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="../css/style.css"/>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous"><link rel="stylesheet" type="text/css" href="css/style.css"
    </head>
    <body>
        <c:if test="${not empty sessionScope.USER and sessionScope.USER.roleId eq 'ROL_2'}" >
            <jsp:include page="menu.jsp"/>
        </c:if>
        <c:if test="${not empty sessionScope.USER and sessionScope.USER.roleId eq 'ROL_1'}" >
            <jsp:include page="navBarAdmin.jsp"/>
        </c:if>
        <c:if test="${empty sessionScope.USER}">
            <jsp:include page="menu.jsp"/>
        </c:if>
        <h1 style="text-align: center;">View Order Detail:</h1>
        <form action="MainController" method="POST">
            <table class="table" border="1" style="margin-left: 10%;margin-top: 2%;width: 80%;">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col" style="text-align: center;">NO.</th>
                        <th scope="col" style="text-align: center;">IMAGE</th>
                        <th scope="col" style="text-align: center;">FOOD NAME:</th>
                        <th scope="col" style="text-align: center;">FOOD QUANTITY:</th>
                        <th scope="col" style="text-align: center;">FOOD PRICE:</th>
                        <th scope="col" style="text-align: center;">CREATE AT:</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="orderDetail" items="${requestScope.LIST_ORDER_DETAIL}" varStatus="counter">
                        <tr>
                            <th scope="row">${counter.count}</th>
                            <td><img src='images/${orderDetail.imageUrl}' alt="" height=200 width=200></td>
                            <td style="vertical-align: middle;">${orderDetail.foodName}</td>
                            <td style="vertical-align: middle;">${orderDetail.foodQuantity}</td>
                            <fmt:setLocale value = "en_US"/>
                            <th style="vertical-align: middle;"><fmt:formatNumber type="currency" value="${orderDetail.foodPrice}" /></th>
                            <td style="vertical-align: middle;"><fmt:formatDate type = "both" pattern="dd/MM/yyyy HH:mm:ss" value = "${orderDetail.createAt}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </form>
        <jsp:include page="footer.jsp"></jsp:include>
    </body>
</html>
