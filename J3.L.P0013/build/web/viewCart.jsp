<%-- 
    Document   : viewCart
    Created on : Jan 8, 2021, 5:58:55 PM
    Author     : DELL
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <title>View Cart Page</title>
    </head>
    <body>
        <style>
            .my-alert{
                position: fixed;
                left: 50%;
                top: 50%;
                transform: translate(-50%, -50%);
                background-color: lightblue;
            }
        </style>
        <c:if test="${not empty sessionScope.USER and sessionScope.USER.roleId eq 'ROL_2'}" >
            <jsp:include page="menu.jsp"/>
        </c:if>
        <c:if test="${empty sessionScope.USER}" >
            <jsp:include page="menu.jsp"/>
        </c:if>
        <c:if test="${not empty requestScope.INVALID}" >
            <script>
                alert("Required input quantity is number greater than 0!");
            </script>
        </c:if>
        <c:if var="testCartExisted" test="${sessionScope.cart != null and sessionScope.cart.cart.size() != 0}" scope="page">
            <h1 style="text-align: center;">View To Cart</h1>
            <form action="MainController" method="POST">
                <table class="table" border="1" style="margin-left: 10%;margin-top: 2%;width: 80%;">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col" style="text-align: center;">NO.</th>
                            <th scope="col" style="text-align: center;">IMAGE:</th>
                            <th scope="col" style="text-align: center;">FOOD NAME: </th>
                            <th scope="col" style="text-align: center;">AMOUNT: </th>
                            <th scope="col" style="text-align: center;">PRICE: </th>
                            <th scope="col" style="text-align: center;">TOTAL: </th>
                            <th style="text-align: center;">ACTION:</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="foodDto" items="${sessionScope.cart.getCart().values()}" varStatus="counter">
                            <c:url value="MainController" var="linkFoodDetail">
                                <c:param name="action" value="ViewFoodDetails"/>
                                <c:param name="foodId" value="${foodDto.foodId}"/>
                            </c:url>
                            <c:url value="MainController" var="linkRemoveFood">
                                <c:param name="action" value="RemoveFoodFromCart"/>
                                <c:param name="foodId" value="${foodDto.foodId}"/>
                            </c:url>
                        <input type="hidden" name="foodId" value="${foodDto.foodId}"/>
                        <tr>
                            <th scope="row">${counter.count}</th>
                            <td><img src='images/${foodDto.imageUrl}' alt="" height=200 width=200></td>
                            <td style="vertical-align: middle;">${foodDto.foodName}</td>
                            <td style="vertical-align: middle;"><input type="number" name="foodQuantity" value="${foodDto.foodQuantity}" min="1" /></td>
                            <fmt:setLocale value = "en_US"/>
                            <td style="vertical-align: middle;"><fmt:formatNumber type="currency" value = "${foodDto.foodPrice}"/></td>
                            <th style="vertical-align: middle;"><fmt:formatNumber type="currency" value="${foodDto.foodQuantity * foodDto.foodPrice}" /></th>
                            <td style="text-align: center;">
                                <div class="form-group">
                                    <a href="${linkRemoveFood}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this item?');" value="RemoveFoodFromCart" style="margin-right: 5%;margin-top: 28%;margin-bottom: 5%;">Remove Food</a>
                                    <a href="${linkFoodDetail}" class="btn btn-primary" style="margin-right: 5%;margin-top: 28%;margin-bottom: 5%;">Food Details</a>
                                    <c:if test="${requestScope.OUT_OF_STOCK.containsKey(foodDto.foodId)}" >
                                        <div class="alert alert-danger" role="alert">
                                            Product is out stock!Quantity of product is ${requestScope.OUT_OF_STOCK.get(foodDto.foodId).foodQuantity}
                                        </div>
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td style="font-weight: bold;">Total Order:</td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <th><fmt:formatNumber type="currency" value="${sessionScope.cart.getTotalOrder()}" /></th>
                        <td style="text-align: center;"><button type="submit" name="action" class="btn btn-success" value="UpdateFoodFromCart" style="margin-right: 5%;">Update Food</button></td>
                    </tr>
                    </tbody>
                </table>
                <div class="form-group" style="margin-left: 10%;margin-top: 2%;width: 80%;">
                    <h2 style="margin-left: 10%;margin-top: 2%;width: 80%;border-bottom-style: solid;">Payment Methods:</h2>
                    <div class="form-check" style="margin-left: 10%;margin-top: 2%;width: 80%;">
                        <input class="form-check-input" type="radio" name="checkRadio" id="exampleRadios1" value="Cash Payment" checked>
                        <label class="form-check-label" for="exampleRadios1">
                            Cash Payment
                        </label>
                    </div>
                    <div class="form-check" style="margin-left: 10%;margin-top: 2%; margin-bottom: 2%;width: 80%;border-bottom-style: solid;">
                        <input class="form-check-input" type="radio" name="checkRadio" id="exampleRadios2" value="PayPal Payment">
                        <label class="form-check-label" for="exampleRadios2">
                            PayPal Payment
                        </label>
                    </div>
                    <input type="submit" name="action" class="btn btn-success" value="ProceedWithPayment"
                           style="margin-left: 46%;"/><br/>
                </div>
            </form>
        </c:if>
            <c:if test="${!testCartExisted}" >
            <h2 style="color: red;text-align: center;text-transform: uppercase;margin-top: 10%; ">Can not find food in your cart!</h2>
        </c:if>
        <a href="MainController?action=SearchFoods" class="btn bg-light" style="margin-left: 40%;font-size: 200%;font-weight: bold;">Continue to shopping</a>
        <c:if test="${not empty sessionScope.USER}" >
            <h2 style="margin-left: 10%;margin-top: 2%;width: 80%;border-bottom-style: solid;">Your favorite:</h2>
            <c:if var="checkNotFound" test="${requestScope.LIST_FAVORITE.size() == 0 || empty requestScope.LIST_FAVORITE}" scope="page">
                <h2 style="margin-left: 10%;margin-top: 2%;width: 80%;text-align: center;color: red;">NOT FOUND</h2>
            </c:if>
            <c:if test="${!checkNotFound}" >
                <section class="jumbotron text-center" style="background-color: lightgreen;height: ">
                    <div class="container">
                        <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <c:set var="isFirst" value="true" scope="page"/>
                                <c:forEach var="index" begin="1" end="${requestScope.LIST_FAVORITE.size()}">
                                    <c:if test="${isFirst}" >
                                        <li data-target="#carouselExampleIndicators" data-slide-to="${index}" class="active"></li>
                                        </c:if>
                                        <c:if test="${!isFirst}" >
                                        <li data-target="#carouselExampleIndicators" data-slide-to="${index}"></li>
                                        </c:if>
                                        <c:set var="isFirst" value="false" />
                                    </c:forEach>
                            </ol>
                            <div class="carousel-inner">

                                <c:set var="isFirst" value="true" scope="page"/>
                                <c:forEach var="food" items="${requestScope.LIST_FAVORITE}">
                                    <c:url var="linkFoodDetail" value="MainController">
                                        <c:param name="action" value="ViewFoodDetails"/>
                                        <c:param name="foodId" value="${food.foodId}"/>
                                    </c:url>
                                    <c:if test="${isFirst}" >
                                        <div class="carousel-item active" style="width: 68%;margin-left: 18%;">
                                            <h2 style="margin-left: 10%;margin-top: 2%;width: 80%;font-size: 300%; color: white;">${food.foodName}</h2>
                                            <a href="${linkFoodDetail}"><img class="d-block w-100" height=500 width=200 src='images/${food.imageUrl}' alt="${food.foodName}"></a>
                                        </div>
                                    </c:if>
                                    <c:if test="${!isFirst}">
                                        <div class="carousel-item" style="width: 68%;margin-left: 18%;">
                                            <h2 style="margin-left: 10%;margin-top: 2%;width: 80%;font-size: 300%; color: white;">${food.foodName}</h2>
                                            <a href="${linkFoodDetail}"><img class="d-block w-100" height=500 width=200 src='images/${food.imageUrl}' alt="${food.foodName}"></a>
                                        </div>
                                    </c:if>
                                    <c:set var="isFirst" value="false"/>
                                </c:forEach>
                            </div>
                            <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </div>
                    </div>
                </section>
            </c:if>
        </c:if>
        <jsp:include page="footer.jsp"></jsp:include>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
                integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
                integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
        crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"
                integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s"
        crossorigin="anonymous"></script>
    </body>
</html>
