<%-- 
    Document   : viewHistoryOrder
    Created on : Jan 13, 2021, 11:19:46 AM
    Author     : DELL
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View History Order</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    </head>
    <body>
        <c:if test="${not empty sessionScope.USER and sessionScope.USER.roleId eq 'ROL_2'}" >
            <jsp:include page="menu.jsp"/>
        </c:if>
        <c:if test="${not empty sessionScope.USER and sessionScope.USER.roleId eq 'ROL_1'}" >
            <jsp:include page="navBarAdmin.jsp"/>
        </c:if>
        <c:if test="${empty sessionScope.USER}" >
            <jsp:include page="menu.jsp"/>
        </c:if>
        <h1 style="text-align: center;">View History Orders</h1>
        <form style="width: 100%;margin-left: 10%;margin-top: 2%;" action="MainController" method="POST">
            <div class="form-group"  >
                <input type="text" name="txtSearchByFoodName" class="form-control" aria-describedby="emailHelp"  style="width: 80%;margin-bottom: 1%;" placeholder="Enter name of food to search:" value="${param.txtSearchByFoodName}"/>
                <input type="hidden" name="pageIndex" value="1"/>
                <input type="date" name="txtSearchByDate" class="form-control" aria-describedby="emailHelp"  style="width: 80%;margin-bottom: 1%;" value="${param.txtSearchByDate}"/>
                <button type="submit" name="action" value="ViewHistoryOrder" style="width: 15%;" class="btn btn-primary">Search</button>
            </div>
        </form>
        <c:if var="checkExisted" scope="page" test="${not empty requestScope.LIST_HISTORY_ORDERS and requestScope.LIST_HISTORY_ORDERS.size() != 0}">
            <form action="MainController" method="POST">
                <table class="table" border="1" style="margin-left: 10%;margin-top: 2%;width: 80%;">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col" style="text-align: center;">NO.</th>
                            <th scope="col" style="text-align: center;">ORDER ID:</th>
                                <c:if test="${sessionScope.USER.roleId eq 'ROL_1'}" >
                                <th scope="col" style="text-align: center;">User Id:</th>
                                </c:if>
                            <th scope="col" style="text-align: center;">CREATE AT: </th>
                            <th scope="col" style="text-align: center;">ORDER TOTAL: </th>
                            <th style="text-align: center;">ACTION:</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${requestScope.LIST_HISTORY_ORDERS}" varStatus="counter">
                            <c:url value="MainController" var="linkOrderDetail">
                                <c:param name="action" value="ViewOrderDetail"/>
                                <c:param name="orderId" value="${order.orderId}"/>
                            </c:url>
                            <tr>
                                <th scope="row">${counter.count}</th>
                                <td style="vertical-align: middle;">${order.orderId}</td>
                                <c:if test="${sessionScope.USER.roleId eq 'ROL_1'}" >
                                    <td style="vertical-align: middle;">${order.userId}</td>
                                </c:if>
                                <td style="vertical-align: middle;"><fmt:formatDate type = "both" pattern="dd/MM/yyyy HH:mm:ss" value = "${order.createAt}" /></td>
                                <fmt:setLocale value = "en_US"/>
                                <th style="vertical-align: middle;"><fmt:formatNumber type="currency" value="${order.orderTotal}" /></th>
                                <td style="text-align: center;">
                                    <div class="form-group">
                                        <a href="${linkOrderDetail}" class="btn btn-primary" style="margin-right: 5%;margin-top: 10%;">Order Details</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
        </c:if>
        <c:if test="${!checkExisted}" >
            <h2 style="text-align: center;color: red;">Can not find history order!</h2>
        </c:if>
        <jsp:include page="footer.jsp"></jsp:include>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
    </body>
</html>
