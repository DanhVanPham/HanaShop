<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--begin of menu-->
<nav class="navbar navbar-expand-md navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="MainController?action=ViewFoodsByName">Hana-Shop</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <c:url value="MainController" var="linkLogout">
            <c:param name="action" value="Log out"/>
            <c:param name="txtUsername" value="${sessionScope.USER.userId}"/>
        </c:url>
        <c:url value="MainController" var="linkViewCart">
            <c:param name="action" value="ViewCart"/>
        </c:url>
        <c:url value="MainController" var="linkViewHistoryOrder">
            <c:param name="action" value="ViewHistoryOrder"/>
        </c:url>
        <c:if var="checkLogin" test="${not empty sessionScope.USER}" scope="page">
            <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
                <ul class="navbar-nav m-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="MainController?action=SearchFoods">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${linkViewHistoryOrder}">History Shopping</a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a class="btn btn-success btn-sm ml-3" href="${linkViewCart}">
                            <i class="fa fa-user"></i> ${sessionScope.USER.fullName}
                        </a>
                    </li>
                    <li><a class="btn btn-success btn-sm ml-3" href="${linkViewCart}">
                            <i class="fa fa-shopping-cart"></i> Cart
                            <c:if var="checkExisted" test="${sessionScope.cart != null}" scope="page">
                                <span class="badge badge-light">${sessionScope.cart.cart.size()}</span>
                            </c:if>
                            <c:if test="!checkExisted" >
                                <span class="badge badge-light">0</span>
                            </c:if>
                        </a>
                    </li>
                    <li >
                        <a class="btn btn-success btn-sm ml-3" href="${linkLogout}">Logout</a>
                    </li>
                </ul>
            </div>
        </c:if>
        <c:if test="${!checkLogin}" >
            <div class="collapse navbar-collapse justify-content-end" id="navbarsExampleDefault">
                <ul class="navbar-nav m-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="MainController?action=SearchFoods">Home</a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a class="btn btn-success btn-sm ml-3" href="${linkViewCart}">
                            <i class="fa fa-user"></i>
                        </a>
                    </li>
                    <li><a class="btn btn-success btn-sm ml-3" href="${linkViewCart}">
                            <i class="fa fa-shopping-cart"></i> Cart
                            <c:if var="checkExisted" test="${sessionScope.cart != null}" scope="page">
                                <span class="badge badge-light">${sessionScope.cart.cart.size()}</span>
                            </c:if>
                            <c:if test="!checkExisted">
                                <span class="badge badge-light">0</span>
                            </c:if>
                        </a>
                    </li>
                    <li >
                        <a class="btn btn-success btn-sm ml-3" href="login.jsp">Login</a>
                    </li>
                </ul>
            </div>
        </c:if>
    </div>
</nav>
<section class="jumbotron text-center">
    <div class="container">
        <h1 class="jumbotron-heading">Welcome to Shop Foods: </h1>
        <p class="lead text-muted mb-0">Uy tín, Chất lượng, "vị ngon trên từng ngón tay"</p>
    </div>
</section>
<!--end of menu-->
