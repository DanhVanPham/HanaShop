<%-- 
    Document   : formUpdateFood
    Created on : Jan 11, 2021, 5:51:13 PM
    Author     : DELL
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
        <title>Update Food</title>
    </head>
    <body>
        <style>
            #upload {
                opacity: 0;
            }

            #upload-label {
                position: absolute;
                top: 50%;
                left: 1rem;
                transform: translateY(-50%);
            }

            .image-area {
                border: 2px dashed rgba(255, 255, 255, 0.7);
                padding: 1rem;
                position: relative;
            }

            .image-area::before {
                content: 'Uploaded image result';
                color: #fff;
                font-weight: bold;
                text-transform: uppercase;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                font-size: 0.8rem;
                z-index: 1;
            }

            .image-area img {
                z-index: 2;
                position: relative;
            }
        </style>
        <c:if test="${not empty sessionScope.USER}">
            <jsp:include page="navBarAdmin.jsp"/>
            <h3 style="text-align: center;font-size: 250%;text-transform: uppercase;font-weight: bold;margin-top: 3%;">Update Food</h3>   
            <c:if test="${requestScope.FOOD != null}" >
                <c:set var="food" value="${requestScope.FOOD}" scope="page"/>
                <form action="UpdateFoodFromAdminController" method="POST" name="myForm" enctype="multipart/form-data" onsubmit="return validateForm();">
                    <div class="row py-4">
                        <div class="col-lg-6 mx-auto">
                            <div class="input-group mb-3 px-2 py-2 rounded-pill bg-white shadow-sm">
                                <input type="hidden" name="txtImageUrl" value="${food.imageUrl}"/>
                                <input id="upload" name="txtImageUrl" type="file" onchange="readURL(this);" class="form-control border-0">
                                <label id="upload-label" for="upload" class="font-weight-light text-muted">Choose file</label>
                                <div class="input-group-append">
                                    <label for="upload" class="btn btn-light m-0 rounded-pill px-4"> <i class="fa fa-cloud-upload mr-2 text-muted"></i><small class="text-uppercase font-weight-bold text-muted">Choose file</small></label>
                                </div>
                            </div>
                            <div class="image-area mt-4"><img id="imageResult" src='images/${food.imageUrl}' alt="" class="img-fluid rounded shadow-sm mx-auto d-block"></div>
                        </div>
                    </div>
                    <div class="form-group" style="margin-left: 20%;">
                        <label for="exampleInputEmail1">Food Name:</label>
                        <input type="text" name="txtFoodName" class="form-control" id="foodName" placeholder="Enter Food Name:" aria-describedby="emailHelp" value="${food.foodName}" required style="width: 70%;"/>
                        <font color="red">${requestScope.INVALID.foodNameError}</font>
                    </div>
                    <div class="form-group" style="margin-left: 20%;">
                        <label for="exampleInputEmail1">Food Description:</label>
                        <input type="text" name="txtFoodDescription" class="form-control" id="foodDescription" aria-describedby="emailHelp" placeholder="Enter Food Description" value="${food.foodDescription}" required style="width: 70%;"/>
                        <font color="red">${requestScope.INVALID.foodDescriptionError}</font>
                    </div>
                    <div class="form-group" style="margin-left: 20%;">
                        <label for="exampleInputEmail1">Quantity: </label>
                        <input type="number" name="txtQuantity" class="form-control" id="foodQuantity" required aria-describedby="emailHelp" placeholder="Enter Food Quantity" value="${food.foodQuantity}" required style="width: 70%;" min="1"/>
                        <font color="red">${requestScope.INVALID.foodQuantityError}</font>
                    </div>
                    <div class="form-group" style="margin-left: 20%;">
                        <label for="exampleInputPrice">Price: </label>
                        <input type="number" name="txtPrice" class="form-control" id="foodPrice" required aria-describedby="emailHelp" placeholder="Enter Food Price" value="${food.foodPrice}" required style="width: 70%;" min="0"/>
                        <font color="red">${requestScope.INVALID.foodPriceError}</font>
                    </div>
                    <div class="form-group" style="margin-left: 20%;">
                        <label for="exampleSelectCategory">Category:</label>
                    </div>
                    <select class="custom-select custom-select-sm mb-3" name="categories" id="category" style="width: 56%; margin-left: 20%;">
                        <option value="" selected>(Please select:)</option>
                        <c:forEach var="itemCategory" items="${applicationScope.LIST_CATEGORY}" varStatus="counter">
                            <c:if var="checkSelected" test="${food.categoryId eq itemCategory.categoryId}" scope="page">
                                <option value="${itemCategory.categoryId}" selected>${itemCategory.categoryName}</option>
                            </c:if>
                            <c:if test="${!checkSelected}" >
                                <option value="${itemCategory.categoryId}">${itemCategory.categoryName}</option>
                            </c:if>
                        </c:forEach>
                    </select><br/>
                    <font color="red" style="margin-left:20%">${requestScope.INVALID.categoryError}</font>
                    <br/>
                    <input type="hidden" name="foodId" value="${food.foodId}"/>
                    <button type="submit" name="action" value="UpdateFoodFromAdmin" class="btn btn-primary mb-5 mt-3" style="margin-left: 45%;">Update Food</button>
                    <font color="red">${requestScope.ERROR}</font>
                </form>
            </c:if> 
        </c:if>
        <script>
            function validateForm() {
                var imageUrl = document.getElementById("upload").value.trim();
                var foodName = document.getElementById("foodName").value.trim();
                var foodDescription = document.getElementById("foodDescription").value.trim();
                var foodQuantity = document.getElementById("foodQuantity").value;
                var foodPrice = document.getElementById("foodPrice").value;
                var category = document.getElementById("category").value;
                if (imageUrl.length != 0 && !imageUrl.endsWith(".jpg") && !imageUrl.endsWith(".png") && !imageUrl.endsWith(".jpeg")) {
                    alert("Image must be format .jpg or .jpeg or .png!");
                    document.getElementById("upload").focus();
                    return false;
                } else if (foodName == "" || foodName == null) {
                    alert("Food name is not null!");
                    document.getElementById("foodName").focus();
                    return false;
                } else if (foodDescription == "" || foodDescription == null) {
                    alert("Food description is not null!");
                    document.getElementById("foodDescription").focus();
                    return false;
                } else if (isNaN(foodQuantity) ||foodQuantity <= 0 || foodQuantity == null) {
                    alert("Food quantity must be greater than 0!");
                    document.getElementById("foodQuantity").focus();
                    return false;
                } else if (isNaN(foodPrice) ||foodPrice <= 0 || foodPrice == null) {
                    alert("Food price must be greater than 0!");
                    document.getElementById("foodPrice").focus();
                    return false;
                } else if (category == null || category.length == 0) {
                    alert("Required select the category!");
                    document.getElementById("category").focus();
                    return false;
                }
            }
            function readURL(input) {
                if (input.files && input.files[0]) {
                    var reader = new FileReader();

                    reader.onload = function (e) {
                        $('#imageResult')
                                .attr('src', e.target.result);
                    };
                    reader.readAsDataURL(input.files[0]);
                }
            }

            $(function () {
                $('#upload').on('change', function () {
                    readURL(input);
                });
            });

            /*  ==========================================
             SHOW UPLOADED IMAGE NAME
             * ========================================== */
            var input = document.getElementById('upload');
            var infoArea = document.getElementById('upload-label');

            input.addEventListener('change', showFileName);
            function showFileName(event) {
                var input = event.srcElement;
                var fileName = input.files[0].name;
                infoArea.textContent = 'File name: ' + fileName;
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
    </body>
</html>
