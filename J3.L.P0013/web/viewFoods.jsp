<%-- 
    Document   : viewFoods
    Created on : Jan 5, 2021, 6:03:45 PM
    Author     : DELL
--%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Foods Page</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="css/style.css">
    </head>
    <body>
        <style>
            .myTab li:hover {
                background-color: #007bff;
                border-radius: 5px;
            }
            .myTab li:hover a{
                color: white;
            }
        </style>
        <c:if var="checkMember" test="${sessionScope.USER != null}" scope="page">
            <jsp:include page="menu.jsp"/>
        </c:if>
        <c:if test="${!checkMember}" >
            <jsp:include page="menu.jsp"/>
        </c:if>
        <div style="margin-left: 5%;">
            <h2>Search:</h2>
            <div style="margin-left: 4%;">
                <form action="MainController" method="POST" onsubmit="return validateFormSearch();">
                    <strong>By Name:</strong><input type="text" name="txtSearchByName" class="form-control" aria-describedby="emailHelp"  style="width: 70%;margin-bottom: 1%;" placeholder="Enter name food search:" value="${param.txtSearchByName}"/>
                    <input type="hidden" name="pageIndex" value="1"/>
                    <strong>By Category:</strong>
                    <div style="display: block;margin-left: 5%;">
                        <input class="form-check-input" type="radio" name="checkRadioCategory" id="exampleRadios1" value="All" checked>
                        <label class="form-check-label" for="exampleRadios1">
                            All
                        </label><br/>
                        <c:forEach var="category" items="${applicationScope.LIST_CATEGORY}">
                            <c:if var="checkEqual" scope="page" test="${category.categoryId eq param.checkRadioCategory}">
                                <input class="form-check-input" type="radio" name="checkRadioCategory" id="exampleRadios2" value="${category.categoryId}" checked>
                                <label class="form-check-label" for="exampleRadios2">
                                    ${category.categoryName}
                                </label><br/>
                            </c:if>
                            <c:if test="${!checkEqual}" >
                                <input class="form-check-input" type="radio" name="checkRadioCategory" id="exampleRadios2" value="${category.categoryId}">
                                <label class="form-check-label" for="exampleRadios2">
                                    ${category.categoryName}
                                </label><br/>
                            </c:if>
                        </c:forEach>
                    </div>
                    <strong>By Range Money:</strong>
                    <input type="number" min="0" id="moneyFrom" name="txtSearchMoneyFrom" class="form-control" aria-describedby="emailHelp"  style="width: 70%;margin-bottom: 1%;" placeholder="Enter money from search:" value="${param.txtSearchMoneyFrom}"/>
                    <input type="number" min="0" id="moneyTo" name="txtSearchMoneyTo" class="form-control" aria-describedby="emailHelp"  style="width: 70%;margin-bottom: 1%;" placeholder="Enter money to search:" value="${param.txtSearchMoneyTo}"/>
                    <p style="color: red;font-weight: bold;text-align: center;">${requestScope.INVALID_NUMBER}</p>
                    <button type="submit" name="action" value="SearchFoods" class="btn btn-primary">Search</button>
                </form>
            </div>
        </div>
        <c:if var="checkEmpty" scope="page" test="${requestScope.LISTFOODS != null }">
            <c:if test="${not empty requestScope.LISTFOODS and requestScope.LISTFOODS.size() != 0}" var="checkList" scope="page">
                <div class="card-deck" style="padding-left: 10%;display: flex; ">
                    <c:forEach var="food" items="${requestScope.LISTFOODS}" varStatus="counter">
                        <form action="MainController" method="POST">
                            <c:url value="MainController" var="linkFoodDetail">
                                <c:param name="action" value="ViewFoodDetails"/>
                                <c:param name="foodId" value="${food.foodId}"/>
                            </c:url>
                            <c:url value="MainController" var="linkAddFood">
                                <c:param name="action" value="AddFoodToCart"/>
                                <c:param name="foodId" value="${food.foodId}"/>
                                <c:param name="pageIndex" value="${param.pageIndex}"/>
                                <c:param name="txtSearchByName" value="${param.txtSearchByName}"/>
                                <c:param name="checkRadioCategory" value="${param.checkRadioCategory}"/>
                                <c:param name="txtSearchMoneyFrom" value="${param.txtSearchMoneyFrom}"/>
                                <c:param name="txtSearchMoneyTo" value="${param.txtSearchMoneyTo}"/>
                            </c:url>
                            <c:set var="desc" value="${food.foodDescription}" scope="page"/>
                            <div class="card mt-3" style="width: 18rem;margin-top: 5%;min-height: 200px;">
                                <a href="${linkFoodDetail}"><img class="card-img-top" style="max-height: 200px;width: 200px; max-width: 100%;height: 200px;object-fit: cover;margin-left: 14%;border-radius: 5px;" src='images/${food.imageUrl}' alt="Card image cap"></a>
                                <div class="card-body">
                                    <h5 class="card-title" style="font-weight: bold;text-align: center;color: #128BE5;">${food.foodName}</h5>
                                    <fmt:setLocale value = "en_US"/>
                                    <p class="card-text" style="color: green;display: inline;"><fmt:formatNumber type="currency" value = "${food.foodPrice}"/></p><br/>
                                    <p class="card-text" style="color: green;display: inline;">${food.foodDescription}</p><br/>
                                    <br/><a href="${linkFoodDetail}" class="btn btn-primary" style="margin-left: 5%;margin-top: 5px;display: inline;">Food Details</a>
                                    <a href="${linkAddFood}" class="btn btn-primary" style="margin-top: 5px;display: inline;">Add to Cart</a>
                                </div>
                            </div>
                        </form>
                    </c:forEach>
                    <nav aria-label="Page navigation example" style="margin-left: 40%;margin-top: 2%;margin-right: 40%;">
                        <ul class="pagination" >
                            <c:forEach begin="1" end="${requestScope.ENDPAGE}" var="i">
                                <c:url value="MainController" var="linkPagningFoodsBySearch">
                                    <c:param name="action" value="SearchFoods"/>
                                    <c:param name="pageIndex" value="${i}"/>
                                    <c:param name="txtSearchByName" value="${param.txtSearchByName}"/>
                                    <c:param name="checkRadioCategory" value="${param.checkRadioCategory}"/>
                                    <c:param name="txtSearchMoneyFrom" value="${param.txtSearchMoneyFrom}"/>
                                    <c:param name="txtSearchMoneyTo" value="${param.txtSearchMoneyTo}"/>
                                </c:url>
                                <c:if var="checkPageIndex" scope="page" test="${i eq param.pageIndex}">
                                    <li class="page-item"><a class="active" href="${linkPagningFoodsBySearch}" style="font-size: 250%;">${i}</a></li>
                                    </c:if>
                                    <c:if test="${!checkPageIndex}">
                                    <li class="page-item"><a class="page-link" href="${linkPagningFoodsBySearch}" style="font-size: 250%;">${i}</a></li>
                                    </c:if>
                                </c:forEach>
                        </ul>
                    </nav>
                </div>
            </c:if>
            <c:if test="${!checkList}">
                <h2 style="text-align: center;color: red;">Can not find Product</h2>
            </c:if>
        </c:if>
        <c:if test="${!checkEmpty}">
            <h2 style="text-align: center;color: red;">Can not find Product</h2>
        </c:if>
        <jsp:include page="footer.jsp"></jsp:include>
        <c:if test="${requestScope.OUT_OF_STOCK.length() != 0 and not empty requestScope.OUT_OF_STOCK}" >
            <script>
                window.alert("Can not add food/drink to cart! Out of stock!");
            </script>
        </c:if>
        <script>
            function validateFormSearch() {
                var moneyFrom = document.getElementById("moneyFrom").value;
                var moneyTo = document.getElementById("moneyTo").value;
                if (moneyFrom.length != 0 && moneyTo.length == 0) {
                    alert("Required enter money to!");
                    document.getElementById("moneyTo").focus();
                    return false;
                } else if (moneyFrom.length == 0 && moneyTo.length != 0) {
                    alert("Required enter money from!");
                    document.getElementById("moneyFrom").focus();
                    return false;
                } else if (moneyFrom.length != 0 && moneyTo.length != 0) {
                    if (parseInt(moneyFrom, 10) >= parseInt(moneyTo, 10)) {
                        alert("Required enter money from smaller than money to!");
                        document.getElementById("moneyFrom").focus();
                        return false;
                    }
                }
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
    </body>
</html>

