<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent" var="rb" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../fonts/favicon.ico">

    <title>New Kitchen</title>
</head>
    <body>
        <jsp:include page="/jsp/admin/pagepart/header.jsp"/>
        <div class="container theme-showcase" role="main">
            <div class="row">
                <div class="col-md-4"></div>
                <div class="col-md-4">
                    <c:if test="${not empty sessionScope.addStatusPositive}">
                        <div class="alert alert-success" role="alert">${sessionScope.addStatusPositive}</div>
                        <c:remove var="addStatusPositive" scope="session" />
                    </c:if>
                    <c:if test="${not empty sessionScope.addStatusNegative}">
                        <div class="alert alert-warning" role="alert">${sessionScope.addStatusNegative}</div>
                        <c:remove var="addStatusNegative" scope="session" />
                    </c:if>

                    <p><span class="required">*</span> - <fmt:message key="label.form.requiredfilds" bundle="${rb}"/></p>
                    <form method="post" action="/uploadcontroller" enctype="multipart/form-data">
                        <input type="hidden" name="command" value="adddish"/>

                        <div class="control-group">
                            <!-- Name -->
                            <label class="control-label"  for="dish_name"><fmt:message key="label.foodcort.dishname" bundle="${rb}"/><span class="required">*</span></label>
                            <div class="controls">
                                <input type="text" class="form-control" id="dish_name" name="dish_name"  required="" pattern="[А-Яа-я\w\s.,?!-+#%_()]{2,70}" />
                                <p class="help-block"><fmt:message key="label.add.dishname.help" bundle="${rb}"/></p>
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Category -->
                            <label class="control-label"  for="category_id"><fmt:message key="label.foodcort.category" bundle="${rb}"/><span class="required">*</span></label>
                            <div class="controls">
                                <select name="category_id" id="category_id" required class="form-control">
                                    <c:forEach items="${sessionScope.categoryList}" var="category">
                                        <option value="${category.id}">${category.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Kitchen -->
                            <label class="control-label"  for="kitchen_name"><fmt:message key="label.foodcort.kitchen" bundle="${rb}"/><span class="required">*</span></label>
                            <div class="controls">
                                <select name="kitchen_id" id="kitchen_name" class="form-control" >
                                    <c:forEach items="${sessionScope.kitchenList}" var="kitchen">
                                        <option value="${kitchen.id}" >${kitchen.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Description -->
                            <label class="control-label"  for="dish_description"><fmt:message key="label.foodcort.description" bundle="${rb}"/><span class="required">*</span></label>
                            <div class="controls">
                                <textarea class="form-control" rows="5" id="dish_description" name="dish_description"></textarea>
                            </div>
                            <p class="help-block"><fmt:message key="label.add.description.help" bundle="${rb}"/></p>
                        </div>

                        <div class="control-group">
                            <!-- Amount -->
                            <label class="control-label"  for="dish_amount"><fmt:message key="label.foodcort.amount" bundle="${rb}"/><span class="required">*</span></label>
                            <div class="controls">
                                <input type="text" class="form-control" id="dish_amount" name="dish_amount"  required="" pattern="[\d\.?\d?]{1,5}(\/[\d]{1,3}){0,3}\s[a-zа-я]{1,3}" />
                                <p class="help-block"><fmt:message key="label.add.amount.help" bundle="${rb}"/></p>
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Price -->
                            <label class="control-label"  for="dish_price"><fmt:message key="label.foodcort.price" bundle="${rb}"/><span class="required">*</span></label>
                            <div class="controls">
                                <input type="text" class="form-control" id="dish_price" name="dish_price"  required="" pattern="[\d]{0,3}\.[\d]{0,2}" />
                                <p class="help-block"><fmt:message key="label.add.price.help" bundle="${rb}"/></p>
                            </div>
                        </div>

                        <div class="control-group">
                            <!-- Photo-->
                            <label class="btn btn-primary" for="my-file-selector">
                                <input id="my-file-selector" required type="file" name="file_field" style="display:none;" pattern="([^\s]+(\.(?i)(jpg|png|gif|bmp))$)">
                                <fmt:message key="label.add.selectfile" bundle="${rb}"/><span class="required">*</span>
                            </label>
                            <p class="help-block"><fmt:message key="label.add.selectfile.help" bundle="${rb}"/></p>
                        </div>
                        <br>

                        <div class="control-group">
                            <!-- Button -->
                            <div class="controls">
                                <button class="btn btn-success" type="submit"><fmt:message key="label.add.add" bundle="${rb}"/></button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-md-4"></div>
            </div>
        </div>
        <jsp:include page="/jsp/admin/pagepart/footer.jsp"/>
    </body>
</html>