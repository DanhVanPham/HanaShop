<%-- 
    Document   : viewFoodDetails
    Created on : Jan 8, 2021, 11:31:37 AM
    Author     : DELL
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="css/foodDetail.css"/>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="css/style.css"/>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        <title>Food Details Page</title>
    </head>
    <body>
        <c:if test="${empty sessionScope.USER}" >
            <jsp:include page="menu.jsp"/>
        </c:if>
        <c:if test="${not empty sessionScope.USER and sessionScope.USER.roleId eq 'ROL_2'}" >
            <jsp:include page="menu.jsp"/>
        </c:if>
        <c:if var="checkAdmin" test="${not empty sessionScope.USER and sessionScope.USER.roleId eq 'ROL_1'}" scope="page">
            <jsp:include page="navBarAdmin.jsp"/>
        </c:if>
        <h1 style="text-align: center;font-size: 250%;">Food Details</h1>
        <c:set var="foodDetail" value="${requestScope.FOOD_DETAIL}" scope="page"/>
        <c:if test="${foodDetail != null}" >
            <c:if test="${not empty foodDetail}" var="checkEmpty">
                <div class="container">
                    <div class="card">
                        <div class="container-fliud">
                            <div class="wrapper row">
                                <div class="preview col-md-6">
                                    <div class="preview-pic tab-content">
                                        <div class="tab-pane active" id="pic-1"><img src='images/${foodDetail.imageUrl}' /></div>
                                    </div>
                                </div>
                                <div class="details col-md-6">
                                    <h3 class="product-title">${foodDetail.foodName}</h3>
                                    <c:forEach var="category" items="${applicationScope.LIST_CATEGORY}">
                                        <c:if test="${foodDetail.categoryId eq category.categoryId}" >
                                            <h4><strong>Category:</strong>${category.categoryName}</h4>
                                        </c:if>
                                    </c:forEach>
                                    <p class="product-description"><strong>Description:</strong> ${foodDetail.foodDescription}</p>
                                    <fmt:setLocale value = "en_US"/>
                                    <h4 class="price">current price: <span><fmt:formatNumber type="currency" value = "${foodDetail.foodPrice}"/></span></h4>
                                    <p class="vote"><strong>91%</strong> of buyers enjoyed this product! <strong>(87 votes)</strong></p>
                                    <c:if test="${checkAdmin}">
                                        <p class="product-description" style="color: red;font-weight: bold;font-size: 150%;"><strong>Create Date:</strong>&nbsp;&nbsp;<fmt:formatDate type = "both" dateStyle = "short" timeStyle = "short" value = "${foodDetail.createDate}" /></p>
                                        <c:if test="${!foodDetail.status}" var="check" scope="page">
                                            <p class="product-description" style="color: red;font-weight: bold;font-size: 150%;"><strong>Status:</strong>INACTIVE</p>
                                        </c:if>
                                        <c:if test="${!check}" >
                                            <p class="product-description" style="color: green;font-weight: bold;font-size: 150%;"><strong>Status:</strong>ACTIVE</p>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${!checkAdmin}">
                                        <div class="action">
                                            <c:if var="checkQuantity" test="${foodDetail.foodQuantity > 0}" scope="page">
                                                <form action="MainController" method="POST">
                                                    <input type="hidden" name="foodId" value="${foodDetail.foodId}"/>
                                                    <button class="add-to-cart btn btn-default" type="submit" name="action" value="AddFoodToCart">add to cart</button>
                                                </form>
                                            </c:if>
                                            <c:if test="${!checkQuantity}">
                                                <button class="btn btn-danger" type="submit" name="action" value="AddFoodToCart">Out of stock</button>
                                            </c:if>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${sessionScope.USER.roleId eq 'ROL_2' or empty sessionScope.USER}">
        <h2 style="margin-left: 10%;margin-top: 2%;width: 80%;border-bottom-style: solid;">List foods related:</h2>
        <c:if var="checkEmptyRelated" scope="page" test="${empty requestScope.LIST_RELATED or requestScope.LIST_RELATED.size() == 0}">
            <h2 style="margin-left: 10%;margin-top: 2%;width: 80%;text-align: center;color: red;">NOT FOUND</h2>
        </c:if>
        <c:if test="${!checkEmptyRelated}" >
            <form>
                <table class="table" border="1" style="width: 90%;margin-left: 5%;">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col" style="text-align: center;">NO.</th>
                            <th scope="col" style="text-align: center;">IMAGE:</th>
                            <th scope="col" style="text-align: center;">Food Name:</th>
                            <th scope="col" style="text-align: center;">Price($):</th>
                            <th scope="col" style="text-align: center;">Category:</th>
                            <th scope="col" style="text-align: center;">Action:</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="food" items="${requestScope.LIST_RELATED}" varStatus="counter">
                            <c:url value="MainController" var="linkFoodDetail">
                                <c:param name="action" value="ViewFoodDetails"/>
                                <c:param name="foodId" value="${food.foodId}"/>
                            </c:url>
                            <c:url value="MainController" var="linkAddFood">
                                <c:param name="action" value="AddFoodToCart"/>
                                <c:param name="foodId" value="${food.foodId}"/>
                            </c:url>
                            <tr>
                                <th scope="row">${counter.count}</th>
                                <td><img src='images/${food.imageUrl}' alt="" height=200 width=200></td>
                                <td>${food.foodName}</td>
                                <td>${food.foodPrice}</td>
                                <td >
                                    <c:forEach var="category" items="${applicationScope.LIST_CATEGORY}">
                                        <c:if var="testEqual" scope="page" test="${food.categoryId eq category.categoryId}">
                                            ${category.categoryName}
                                        </c:if>
                                        <c:if test="${!testEqual}">
                                            ${category.categoryName}
                                        </c:if>
                                    </c:forEach>
                                    </select>
                                </td>
                                <td>
                                    <a class="btn btn-primary" href="${linkFoodDetail}">View Food Details</a>
                                    <a href="${linkAddFood}" class="btn btn-success" >Add to cart</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
        </c:if>
    </c:if>
</c:if>
<c:if test="${!checkEmpty}" >
    <h2 style="color:red;">Can not find Food Details</h2>
</c:if>
</c:if>
<jsp:include page="footer.jsp"></jsp:include>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
</body>
</html>
