<%-- 
    Document   : manageFoods.jsp
    Created on : Jan 6, 2021, 4:14:35 PM
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
        <link rel="stylesheet" type="text/css" href="../css/style.css"/>
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous"><link rel="stylesheet" type="text/css" href="css/style.css"/>
        <title>Manage Foods Page</title>
    </head>
    <body>
        <c:if test="${not empty sessionScope.USER and sessionScope.USER != null}">
            <jsp:include page="navBarAdmin.jsp"/>     
            <c:if test="${requestScope.LISTFOODS == null}" >
                <h1 style="color: red; text-align: center;">Can not find List Food</h1>
            </c:if>
            <c:if test="${requestScope.LISTFOODS != null}" >
                <h3 style="text-align: center;font-size: 250%;text-transform: uppercase;font-weight: bold;margin-top: 3%;margin-bottom: 3%;">Manage Foods</h3>
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
                                    <c:if var="checkEqual" test="${category.categoryId eq param.checkRadioCategory}" scope="page">
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
                            <input type="number" min="0" name="txtSearchMoneyFrom" id = "txtMoneyFrom" class="form-control" aria-describedby="emailHelp"  style="width: 70%;margin-bottom: 1%;" placeholder="Enter money from search:" value="${param.txtSearchMoneyFrom}"/>
                            <input type="number" min="0" name="txtSearchMoneyTo" id="txtMoneyTo" class="form-control" aria-describedby="emailHelp"  style="width: 70%;margin-bottom: 1%;" placeholder="Enter money to search:" value="${param.txtSearchMoneyTo}"/>
                            <p style="color: red;font-weight: bold;text-align: center;">${requestScope.INVALID_NUMBER}</p>
                            <button type="submit" name="action" value="Manage Foods" class="btn btn-primary">Search</button>
                        </form>
                    </div>
                </div>
                <form action="MainController" method="POST">
                    <button type="submit" class="btn btn-primary" style="margin-left: 45%;margin-bottom: 1%;width: 14%;" name="action" value="Form Create Food">Create Food</button>
                </form>
                <c:if test="${not empty requestScope.LISTFOODS}" var="checkList" scope="page">
                    <c:if test="${param.txtSearch.length() != 0}" var="checkSearchName">
                        <c:set var="txtSearch" value="${param.txtSearch}" scope="request"/>
                    </c:if>
                    <form>
                        <table class="table" border="1" style="width: 90%;margin-left: 5%;">
                            <thead class="thead-dark">
                                <tr>
                                    <th scope="col" style="text-align: center;">NO.</th>
                                    <th scope="col" style="text-align: center;">Food Name:</th>
                                    <th scope="col" style="text-align: center;">Price($):</th>
                                    <th scope="col" style="text-align: center;">Quantity:</th>
                                    <th scope="col" style="text-align: center;">Create Date:</th>
                                    <th scope="col" style="text-align: center;">Category:</th>
                                    <th scope="col" style="text-align: center;">Food Status</th>
                                    <th scope="col" style="text-align: center;">Action:</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="dto" items="${requestScope.LISTFOODS}" varStatus="counter">
                                    <c:url value="MainController" var="linkFoodDetail" >
                                        <c:param name="action" value="ViewFoodDetails"/>
                                        <c:param name="foodId" value="${dto.foodId}"/>
                                    </c:url>
                                    <c:url value="MainController" var="linkUpdateFood">
                                        <c:param name="action" value="FormUpdateFood"/>
                                        <c:param name="foodId" value="${dto.foodId}"/>
                                    </c:url>
                                    <c:url value="MainController" var="linkRemoveFood">
                                        <c:param name="action" value="RemoveFoodByAdmin"/>
                                        <c:param name="foodId" value="${dto.foodId}"/>
                                    </c:url>
                                    <tr>
                                        <th scope="row">${counter.count}</th>
                                        <td>${dto.foodName}</td>
                                        <td>${dto.foodPrice}</td>
                                        <td>${dto.foodQuantity}</td>
                                        <td><fmt:formatDate type = "both" pattern="dd/MM/yyyy HH:mm:ss" value = "${dto.createDate}" /></td>
                                        <td >
                                            <select class="custom-select custom-select-sm mb-3" name="categories" >
                                                <c:forEach var="category" items="${applicationScope.LIST_CATEGORY}">
                                                    <c:if var="testEqual" test="${dto.categoryId eq category.categoryId}" scope="page">
                                                        <option value="${itemCategory.categoryId}" selected>${category.categoryName}</option>
                                                    </c:if>
                                                    <c:if test="${!testEqual}">
                                                        <option value="${itemCategory.categoryId}">${category.categoryName}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </td>
                                        <td>
                                            <select class="custom-select custom-select-sm mb-3" name="categories" >
                                                <c:if var="testStatus" test="${dto.status}" scope="page">
                                                    <option value="${dto.status}" selected>Active</option>
                                                    <option >Inactive</option>
                                                </c:if>
                                                <c:if test="${!testStatus}" >
                                                    <option value="${dto.status}" selected>Inactive</option>
                                                    <option >Active</option>
                                                </c:if>
                                            </select>
                                        </td>
                                        <c:if test="${testStatus}" >
                                            <td>
                                                <a class="btn btn-primary" href="${linkFoodDetail}">View Food Details</a>
                                                <a href="${linkUpdateFood}" class="btn btn-success" >Update Food</a>
                                                <a href="${linkRemoveFood}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this item?');">Remove Food</a>
                                            </td>
                                        </c:if>
                                        <c:if test="${!testStatus}">
                                            <td style="text-align: center;">
                                                <a href="${linkFoodDetail}" class="btn btn-primary" >View Food Details</a>
                                            </td>
                                        </c:if>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </form>
                    <nav aria-label="Page navigation example" style="margin-left: 42%;margin-top: 2%;margin-right: 42%;">
                        <ul class="pagination pagination-sm" >
                            <c:forEach begin="1" end="${requestScope.ENDPAGE}" var="i">
                                <c:url value="MainController" var="linkPagningFood">
                                    <c:param name="action" value="Manage Foods"/>
                                    <c:param name="pageIndex" value="${i}"/>
                                    <c:param name="txtSearchByName" value="${param.txtSearchByName}"/>
                                    <c:param name="checkRadioCategory" value="${param.checkRadioCategory}"/>
                                    <c:param name="txtSearchMoneyFrom" value="${param.txtSearchMoneyFrom}"/>
                                    <c:param name="txtSearchMoneyTo" value="${param.txtSearchMoneyTo}"/>
                                </c:url>
                                <c:if var="checkPageIndex" scope="page" test="${i eq param.pageIndex}">
                                    <li class="page-item"><a class="active" href="${linkPagningFood}" style="font-size: 250%;">${i}</a></li>
                                    </c:if>
                                    <c:if test="${!checkPageIndex}">
                                    <li class="page-item"><a class="page-link" href="${linkPagningFood}" style="font-size: 250%;">${i}</a></li>
                                    </c:if>
                                </c:forEach>
                        </ul>
                    </nav>
                </c:if>
                <c:if test="${!checkList}">
                    <h1 style="color: red;text-align: center;">No record found</h1>
                </c:if>
            </c:if>
        </c:if>
        <jsp:include page="footer.jsp"></jsp:include>
        <script>
            function validateFormSearch() {
                var moneyFrom = document.getElementById("txtMoneyFrom").value;
                var moneyTo = document.getElementById("txtMoneyTo").value;
                if (moneyFrom.length == 0 && moneyTo.length != 0) {
                    alert("Required enter money from!");
                    document.getElementById("txtMoneyFrom").focus();
                    return false;
                } else if (moneyFrom.length != 0 && moneyTo.length == 0) {
                    alert("Required enter money to!");
                    document.getElementById("txtMoneyTo").focus();
                    return false;
                } else if (moneyFrom.length != 0 && moneyTo.length != 0) {
                    if (parseInt(moneyFrom, 10) >= parseInt(moneyTo, 10)) {
                        alert("Required enter money from smaller than money to!");
                        document.getElementById("txtMoneyFrom").focus();
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
