<%--
  Created by IntelliJ IDEA.
  User: melqueades
  Date: 26.08.2017
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags"%>

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

    <title>Admin foodcort</title>
</head>

<jsp:include page="/jsp/admin/pagepart/header.jsp"/>

<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <c:if test="${not empty sessionScope.changeArchiveStatusResult}">
                <div class="alert alert-success" role="alert">${sessionScope.changeArchiveStatusResult}</div>
            </c:if>
            <c:if test="${not empty sessionScope.changeArchiveStatusResult}">
                <c:remove var="changeArchiveStatusResult" scope="session" />
            </c:if>
            <form method="get" action="/controller">
                <input type="hidden" name="command" value="showdish"/>
                <fmt:message key="label.foodcort.category" bundle="${rb}"/>
                <br/>
                <select name="category" class="form-control">
                    <option selected value="x"><fmt:message key="label.foodcort.any" bundle="${rb}"/></option>
                    <c:forEach items="${sessionScope.categoryList}" var="category">
                        <option value="${category.id}">${category.name}</option>
                    </c:forEach>
                </select>
                <br/>
                <fmt:message key="label.foodcort.kitchen" bundle="${rb}"/>
                <br/>
                <select name="kitchen" class="form-control" >
                    <option selected value="x"><fmt:message key="label.foodcort.any" bundle="${rb}"/></option>
                    <c:forEach items="${sessionScope.kitchenList}" var="kitchen">
                        <option value="${kitchen.id}">${kitchen.name}</option>
                    </c:forEach>
                </select>
                <br/>
                <fmt:message key="label.foodcort.archive" bundle="${rb}"/>
                <br/>
                <select name="archive_status" class="form-control" >
                    <option selected value="false"><fmt:message key="label.foodcort.notfromarchive" bundle="${rb}"/></option>
                    <option value="true"><fmt:message key="label.foodcort.fromarchive" bundle="${rb}"/></option>
                </select>
                <br/>
                <input type="submit" value=<fmt:message key="label.foodcort.show_button" bundle="${rb}"/> />
            </form>
        </div>
        <div class="col-md-4"></div>
        <div class="row">
            <div class="col-md-12">
                <br/>

                <table class="table table-striped table-bordered table-condensed">
                    <thead>
                        <tr>
                            <th><fmt:message key="label.foodcort.picture" bundle="${rb}"/></th>
                            <th><fmt:message key="label.foodcort.dishname" bundle="${rb}"/></th>
                            <th><fmt:message key="label.foodcort.category" bundle="${rb}"/></th>
                            <th><fmt:message key="label.foodcort.kitchen" bundle="${rb}"/></th>
                            <th><fmt:message key="label.foodcort.description" bundle="${rb}"/></th>
                            <th><fmt:message key="label.foodcort.amount" bundle="${rb}"/></th>
                            <th><fmt:message key="label.foodcort.price" bundle="${rb}"/></th>
                            <th>
                                <ctg:to-archive statusMarker="${requestScope.archiveStatusMarker}" locale="${sessionScope.locale}" entityMarker="dish_archive"/>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.dishList}" var="dish">
                            <tr>
                                <td><img src="${dish.picture}" alt="Картинка блюда"></td>
                                <td>${dish.name}</td>
                                <td>${dish.category.name}</td>
                                <td>
                                    <div class="drop dropdown">
                                        <a class="btn btn-secondary dropdown-toggle" id="${dish.kitchen.name}" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <span>${dish.kitchen.name}<span>
                                        </a>

                                        <div class="dropdown-menu" aria-labelledby="${dish.kitchen.name}">
                                            <table class="table table-striped table-bordered table-condensed">
                                                <thead>
                                                </thead>
                                                <tbody>
                                                    <tr>
                                                        <th><img src="${dish.kitchen.picture}" alt="Картинка заведения"></th>
                                                    </tr>
                                                    <tr>
                                                        <th>${dish.kitchen.phone}</th>
                                                    </tr>
                                                    <tr>
                                                        <th>${dish.kitchen.site}</th>
                                                    </tr>
                                                    <tr>
                                                        <th>${dish.kitchen.address}</th>
                                                    </tr>
                                                    <tr>
                                                        <th>${dish.kitchen.email}</th>
                                                    </tr>
                                                </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </td>
                                    <td>${dish.description}</td>
                                    <td>${dish.amount}</td>
                                    <td>${dish.price}</td>
                                    <td>
                                        <div class="checkbox">
                                            <label><input type="checkbox" form="archive" name="checkedDishId" value="${dish.id}"></label>
                                        </div>
                                        <c:if test="${dish.archiveStatus eq false}">
                                            <fmt:message key="label.foodcort.notfromarchive" bundle="${rb}"/>
                                        </c:if>
                                        <c:if test="${dish.archiveStatus eq true}">
                                            <fmt:message key="label.foodcort.fromarchive" bundle="${rb}"/>
                                        </c:if>
                                    </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>



<jsp:include page="/jsp/admin/pagepart/footer.jsp"/>
</body>
</html>
