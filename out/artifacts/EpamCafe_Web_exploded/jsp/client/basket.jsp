<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
,

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent" var="rb"/>'

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../fonts/favicon.ico">

    <title>Basket</title>

</head>
<body>
    <jsp:include page="/jsp/client/pagepart/header.jsp"/>
    <div class="container theme-showcase" role="main">
        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <c:if test="${not empty sessionScope.makeOrderResult}">
                    <div class="alert alert-success" role="alert">${sessionScope.makeOrderResult}</div>
                </c:if>
                <c:if test="${not empty requestScope.makeOrderResult}">
                    <div class="alert alert-danger" role="alert">${requestScope.makeOrderResult}</div>
                </c:if>
                <c:if test="${not empty sessionScope.makeOrderResult}">
                    <c:remove var="makeOrderResult" scope="session" />
                </c:if>
                <c:if test="${not empty requestScope.makeOrderResult}">
                    <c:remove var="changeArchiveStatusResult" scope="request" />
                </c:if>
            </div>
            <div class="col-md-4"></div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <table class="table" >
                    <thead>
                    <tr>
                        <th><fmt:message key="label.foodcort.picture" bundle="${rb}"/></th>
                        <th><fmt:message key="label.foodcort.dishname" bundle="${rb}"/></th>
                        <th><fmt:message key="label.foodcort.category" bundle="${rb}"/></th>
                        <th><fmt:message key="label.foodcort.kitchen" bundle="${rb}"/></th>
                        <th><fmt:message key="label.foodcort.description" bundle="${rb}"/></th>
                        <th><fmt:message key="label.foodcort.price" bundle="${rb}"/></th>
                        <th><fmt:message key="label.basket.amount" bundle="${rb}"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${sessionScope.user.basket}" var="dish">
                        <tr>
                            <td><img src="${dish.key.picture}" alt="Картинка блюда"></td>
                            <td>${dish.key.name}</td>
                            <td>${dish.key.category.name}</td>
                            <td>
                                <div class="drop dropdown">
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
                            </td>
                            <td>${dish.key.description}</td>
                            <td>${dish.key.price}</td>
                            <td>${dish.value}</td>
                            <td>
                                <form method="get" action="/controller">
                                    <input type="hidden" name="command" value="removefrombasket"/>
                                    <input type="hidden" name="dish" value="${dish.key.id}"/>
                                    <button type="submit" class="btn btn-success"><fmt:message key="label.foodcort.removefrombasket" bundle="${rb}"/></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td><strong><fmt:message key="label.basket.totalprice" bundle="${rb}"/> - ${user.basketPrice}</strong></td>
                    </tr>
                    </tbody>
                </table>
            </div>

        </div>
        <hr/>
        <div class="row">
            <div class="col-md-6">
                <form method="get" action="/controller">
                    <label for="date" ><fmt:message key="label.basket.selectdate" bundle="${rb}"/></label>
                        <input type="hidden" name="command" value="makeorder"/>
                        <input type="date" id="date" name="delivery_date" value="${sessionScope.user.availableForDeliveryDays[1]}"
                               max="${sessionScope.user.availableForDeliveryDays[2]}" min="${sessionScope.user.availableForDeliveryDays[1]}">
                        <button type="submit" class="btn btn-success" onclick="return confirm('<fmt:message key="label.message.areyousure" bundle="${rb}"/>')"><fmt:message key="label.basket.makeorder" bundle="${rb}"/></button>
                </form>
            </div>
            <div class="col-md-6">
                <form method="get" action="/controller">
                    <input type="hidden" name="command" value="showmyorder"/>
                    <button type="submit" class="btn btn-success"><fmt:message key="label.basket.myorder" bundle="${rb}"/></button>
                </form>
            </div>
        </div>
    </div>
    <jsp:include page="/jsp/client/pagepart/footer.jsp"/>
</body>
</html>
