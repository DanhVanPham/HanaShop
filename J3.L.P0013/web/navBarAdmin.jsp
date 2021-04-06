<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--begin of menu-->
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="MainController?action=Manage Foods">Hana-Shop</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <c:url value="MainController" var="linkLogout">
            <c:param name= "action" value="Log out"/>
            <c:param name= "txtUsername" value="${sessionScope.USER.userId}"/>
        </c:url>
        <c:url value="MainController" var="linkViewFoods">
            <c:param name= "action" value= "Manage Foods"/>
        </c:url>
        <c:url value="MainController" var= "linkCreateFood">
            <c:param name= "action" value= "Form Create Food"/>
        </c:url>
        <c:url value="MainController" var="linkViewHistoryOrder">
            <c:param name="action" value="ViewHistoryOrder"/>
        </c:url>
        <c:if var="checkLogin" test="${not empty sessionScope.USER}" scope="page">
            <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
                <ul class="navbar-nav m-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="${linkViewFoods}">Manage Foods</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${linkViewHistoryOrder}">History Order</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${linkCreateFood}">Create Food</a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a class="btn btn-success btn-sm ml-3" href="${linkViewFoods}">
                            <i class="fa fa-user"></i> ${sessionScope.USER.fullName}
                        </a>
                    </li>
                    <li >
                        <a class="btn btn-success btn-sm ml-3" href="${linkLogout}">Logout</a>
                    </li>
                </ul>
            </div>
        </c:if>
    </div>
</nav>
