<%--
  Created by IntelliJ IDEA.
  User: melqueades
  Date: 10.08.2017
  Time: 11:27
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

    <title>My orders</title>

</head>

<body>
<jsp:include page="/jsp/client/pagepart/header.jsp"/>

<div class="container theme-showcase" role="main">
        <div class="row">
            <div class="col-md-12 center">
                <c:if test="${not empty sessionScope.orderFindStatus}">
                    <div class="alert alert-success" role="alert">${sessionScope.orderFindStatus}</div>
                </c:if>
                <c:if test="${not empty sessionScope.deliveryCommandStatus}">
                    <div class="alert alert-success" role="alert">${sessionScope.deliveryCommandStatus}</div>
                </c:if>
                <c:if test="${not empty sessionScope.paidCommandStatus}">
                    <div class="alert alert-success" role="alert">${sessionScope.paidCommandStatus}</div>
                </c:if>
                <c:if test="${not empty sessionScope.orderFindStatus}">
                    <c:remove var="orderFindStatus" scope="session" />
                </c:if>
                <c:if test="${not empty sessionScope.deliveryCommandStatus}">
                    <c:remove var="deliveryCommandStatus" scope="session" />
                </c:if>
                <c:if test="${not empty sessionScope.paidCommandStatus}">
                    <c:remove var="paidCommandStatus" scope="session" />
                </c:if>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <table class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th></th>
                        <th><fmt:message key="label.order.paystatus" bundle="${rb}"/></th>
                        <th><fmt:message key="label.order.deliverystatus" bundle="${rb}"/></th>
                        <th><fmt:message key="label.order.orderdate" bundle="${rb}"/></th>
                        <th><fmt:message key="label.order.deliverydate" bundle="${rb}"/></th>
                        <th><fmt:message key="label.order.price" bundle="${rb}"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.orderMap}" var="order">
                        <tr class="first">
                            <td>
                                <div class="dropdown show">
                                    <a class="btn btn-secondary dropdown-toggle" id="${order.key.id}" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <span><fmt:message key="label.order.dishes" bundle="${rb}"/></span>
                                    </a>

                                    <div class="dropdown-menu" aria-labelledby="${order.key.id}">
                                        <table class="table table-striped table-bordered table-condensed">
                                            <thead>
                                            <tr>
                                                <th>Picture</th>
                                                <th><fmt:message key="label.foodcort.dishname" bundle="${rb}"/></th>
                                                <th><fmt:message key="label.foodcort.kitchen" bundle="${rb}"/></th>
                                                <th><fmt:message key="label.order.price" bundle="${rb}"/></th>
                                                <th><fmt:message key="label.order.amount" bundle="${rb}"/></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${order.value}" var="dish">
                                                <tr>
                                                    <th><img src="${dish.key.picture}" alt="<fmt:message key="label.foodcort.picture" bundle="${rb}"/>"></th>
                                                    <th>${dish.key.name}</th>
                                                    <th>
                                                        <div class="drop dropdown show">
                                                            <a class="btn btn-secondary dropdown-toggle" id="${dish.key.kitchen.name}" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                            <span>${dish.key.kitchen.name}<span>
                                                            </a>

                                                            <div class="dropdown-menu" aria-labelledby="${dish.key.kitchen.name}">
                                                                <table class="table table-striped table-bordered table-condensed">
                                                                    <thead>
                                                                    </thead>
                                                                    <tbody>
                                                                    <tr>
                                                                        <th><img src="${dish.key.kitchen.picture}" alt="Картинка заведения"></th>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>${dish.key.kitchen.phone}</th>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>${dish.key.kitchen.site}</th>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>${dish.key.kitchen.address}</th>
                                                                    </tr>
                                                                    <tr>
                                                                        <th>${dish.key.kitchen.email}</th>
                                                                    </tr>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </th>
                                                    <th>${dish.key.price}</th>
                                                    <th>${dish.value}</th>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <c:if test="${order.key.paid == false}">
                                    <span><fmt:message key="label.order.notpaid" bundle="${rb}"/></span>
                                </c:if>
                                <c:if test="${order.key.paid == true}">
                                    <span><fmt:message key="label.order.paid" bundle="${rb}"/></span>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${order.key.deliveryStatus == false && order.key.paid == true}">
                                    <fmt:message key="label.order.notdelivered" bundle="${rb}"/>
                                </c:if>
                                <c:if test="${order.key.paid == false}">
                                    <span><fmt:message key="label.order.notpaid" bundle="${rb}"/></span>
                                </c:if>
                                <c:if test="${order.key.deliveryStatus == true}">
                                    <span><fmt:message key="label.order.delivered" bundle="${rb}"/></span>
                                </c:if>
                            </td>
                            <td>${order.key.date}</td>
                            <td>${order.key.deliveryDate}</td>
                            <td>${order.key.price}</td>
                            <td>
                                <ctg:refuse deliveryDate="${order.key.deliveryDate}" paid="${order.key.paid}" delivery="${order.key.deliveryStatus}" orderId="${order.key.id}" buttonName="${sessionScope.locale}"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

</div> <!-- /container -->


<jsp:include page="/jsp/client/pagepart/footer.jsp"/>
</body>
</html>

