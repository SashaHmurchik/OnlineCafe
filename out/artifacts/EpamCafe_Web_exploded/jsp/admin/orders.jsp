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

    <title>Orders</title>

</head>

<body>

<jsp:include page="/jsp/admin/pagepart/header.jsp"/>

<div class="container theme-showcase" role="main">

    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <c:if test="${not empty sessionScope.orderFindStatus}">
                <div class="alert alert-success" role="alert">${sessionScope.orderFindStatus}</div>
                <c:remove var="orderFindStatus" scope="session" />
            </c:if>
            <c:if test="${not empty requestScope.orderFindStatus}">
                <div class="alert alert-success" role="alert">${requestScope.orderFindStatus}</div>
                <c:remove var="orderFindStatus" scope="request" />
            </c:if>
            <c:if test="${not empty sessionScope.deliveryCommandStatus}">
                <div class="alert alert-success" role="alert">${sessionScope.deliveryCommandStatus}</div>
                <c:remove var="deliveryCommandStatus" scope="session" />
            </c:if>
            <c:if test="${not empty sessionScope.paidCommandStatus}">
                <div class="alert alert-success" role="alert">${sessionScope.paidCommandStatus}</div>
                <c:remove var="paidCommandStatus" scope="session" />
            </c:if>

        </div>
        <div class="col-md-4"></div>
    </div>

    <div class="row">

        <div clas="col-md-12">
            <div class="center">
                <form class="form-inline" action="/controller">
                    <input type="hidden" name="command" value="showorderbydate" />
                    <label for="from"><fmt:message key="label.order.from" bundle="${rb}"/></label>
                    <input type="date" id="from" name="startDate" value="${user.availableForDeliveryDays[0]}" />
                    <label for="to"><fmt:message key="label.order.to" bundle="${rb}"/></label>
                    <input type="date" id="to" name="endDate" value="${user.availableForDeliveryDays[0]}" />
                    <button type="submit" class="btn btn-primary"><fmt:message key="label.foodcort.show_button" bundle="${rb}"/></button>
                </form>
            </div>
            <div class="center">
                <form class="form-inline form-sort" method="get" action="/controller">
                    <input type="hidden" name="command" value="sortordermap"/>
                    <select class="form-control mb-2 mr-sm-2 mb-sm-0" name="sort" >
                        <option selected value="paid"><fmt:message key="label.order.paystatus" bundle="${rb}"/>
                        <option value ="del_date"><fmt:message key="label.order.deliverydate" bundle="${rb}"/>
                    </select>
                    <input type="submit" class="btn btn-primary" value="<fmt:message key="label.foodcort.sort" bundle="${rb}"/>">
                </form>
            </div>

        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th><fmt:message key="label.order.client" bundle="${rb}"/></th>
                    <th><fmt:message key="label.order.paystatus" bundle="${rb}"/></th>
                    <th><fmt:message key="label.order.deliverystatus" bundle="${rb}"/></th>
                    <th><fmt:message key="label.order.orderdate" bundle="${rb}"/></th>
                    <th><fmt:message key="label.order.deliverydate" bundle="${rb}"/></th>
                    <th><fmt:message key="label.order.price" bundle="${rb}"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sessionScope.orderMap}" var="order">
                    <tr class="first">
                        <td>
                            <div class="dropdown show">
                                <a class="btn btn-secondary dropdown-toggle" id="${order.key.user.name}" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <strong>${order.key.user.name}</strong>
                                </a>

                                <div class="dropdown-menu" aria-labelledby="${order.key.user.name}">
                                    <table class="table table-striped table-bordered table-condensed">
                                        <thead>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <th><fmt:message key="label.registration.surname" bundle="${rb}"/></th>
                                                <th>${order.key.user.surname}</th>
                                            </tr>
                                            <tr>
                                                <th><fmt:message key="label.registratiion.loyaltypoints" bundle="${rb}"/></th>
                                                <th>
                                                    ${order.key.user.loyaltyPoint}
                                                    <form method="get" action="/controller">
                                                        <input type="hidden" name="command" value="updateloyaltypoint">
                                                        <input type="hidden" name="user_id" value=${order.key.user.id} />
                                                        <button type="submit" class="btn btn-success" name="loyalty_status" value="+" onclick="return confirm('<fmt:message key="label.message.areyousure" bundle="${rb}"/>')">+</button>
                                                        <button type="submit" class="btn btn-success" name="loyalty_status" value="-" onclick="return confirm('<fmt:message key="label.message.areyousure" bundle="${rb}"/>')">-</button>
                                                    </form>
                                                </th>
                                            </tr>
                                            <tr>
                                                <th><fmt:message key="label.registration.email" bundle="${rb}"/></th>
                                                <th>${order.key.user.mail}</th>
                                            </tr>
                                            <tr>
                                                <th><fmt:message key="label.registration.phone" bundle="${rb}"/></th>
                                                <th>${order.key.user.phone}</th>
                                            </tr>
                                            <tr>
                                                <th><fmt:message key="label.registration.passport" bundle="${rb}"/></th>
                                                <th>${order.key.user.passport}</th>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </td>
                        <td>
                            <c:if test="${order.key.paid == false}">
                                <form class="form-inline" action="/controller">
                                    <input type="hidden" name="command" value="payadmin" />
                                    <input type="hidden" name="orderId" value=${order.key.id} />
                                    <button type="submit" class="btn btn-success" onclick="return confirm('<fmt:message key="label.message.areyousure" bundle="${rb}"/>')"><fmt:message key="label.order.pay" bundle="${rb}"/></button>
                                </form>
                            </c:if>
                            <c:if test="${order.key.paid == true}">
                                <span><fmt:message key="label.order.paid" bundle="${rb}"/></span>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${order.key.deliveryStatus == false && order.key.paid == true}">
                                <form class="form-inline" action="/controller">
                                    <input type="hidden" name="command" value="deliver" />
                                    <input type="hidden" name="orderId" value=${order.key.id} />
                                    <button type="submit" class="btn btn-success" onclick="return confirm('<fmt:message key="label.message.areyousure" bundle="${rb}"/>')"><fmt:message key="label.order.deliver" bundle="${rb}"/></button>
                                </form>
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

                    </tr>
                    <tr class="second">
                        <td>

                            <div class="dropdown show">
                                <a class="btn btn-secondary dropdown-toggle" id="${order.key.id}" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <span><fmt:message key="label.order.dishes" bundle="${rb}"/></span>
                                </a>

                                <div class="dropdown-menu" aria-labelledby="${order.key.id}">
                                    <table class="table table-striped table-bordered table-condensed">
                                        <thead>
                                        <tr>
                                            <th><fmt:message key="label.foodcort.picture" bundle="${rb}"/></th>
                                            <th><fmt:message key="label.foodcort.dishname" bundle="${rb}"/></th>
                                            <th><fmt:message key="label.foodcort.kitchen" bundle="${rb}"/></th>
                                            <th><fmt:message key="label.foodcort.price" bundle="${rb}"/></th>
                                            <th><fmt:message key="label.order.amount" bundle="${rb}"/></th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${order.value}" var="dish">
                                            <tr>
                                                <th><img src="${dish.key.picture}" alt="Картинка блюда"></th>
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
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>


</div>
    <jsp:include page="/jsp/admin/pagepart/footer.jsp"/>
</body>
</html>

