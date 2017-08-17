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
    <link rel="icon" href="../../bootstrap-3.3.7/docs/favicon.ico">

    <title>Orders</title>

    <!-- Bootstrap core CSS -->
    <link href="../../bootstrap-3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="../../bootstrap-3.3.7/dist/css/bootstrap-theme.min.css" rel="stylesheet">
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="../../bootstrap-3.3.7/docs/assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../../css/theme.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../bootstrap-3.3.7/docs/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="../../bootstrap-3.3.7/docs/assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style>
        .sort {
            margin: auto;
            text-align: center;
            padding: 20px;
        }

    </style>

</head>

<body>

<jsp:include page="/jsp/admin/pagepart/header.jsp"/>

<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-12">
            <h3>${orderFindStatus}</h3>
            <h3>${deliveryCommandStatus}</h3>
            <h3>${paidCommandStatus}</h3>
        </div>
    </div>
    <c:if test="${not empty sessionScope.orderFindStatus}">
        <c:remove var="orderFindStatus" scope="session" />
    </c:if>
    <c:if test="${not empty sessionScope.deliveryCommandStatus}">
        <c:remove var="deliveryCommandStatus" scope="session" />
    </c:if>
    <c:if test="${not empty sessionScope.paidCommandStatus}">
        <c:remove var="paidCommandStatus" scope="session" />
    </c:if>
    <div class="row">
        <div clas="col-md-12">
            <div class="sort">
                <form class="form-inline form-sort">
                    <input type="hidden" name="command" value="SORTORDERMAP"/>
                    <select class="form-control mb-2 mr-sm-2 mb-sm-0" name="sort" >
                        <option value="paid">Pay status
                        <option value ="del_date">Delivery Date
                    </select>
                    <input type="submit" class="btn btn-primary" value="Sort">
                </form>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-striped table-bordered table-condensed" >
                <thead>
                <tr>
                    <th>Client</th>
                    <th>Pay Status</th>
                    <th>Delivery Status</th>
                    <th>Date</th>
                    <th>DeliveryDate</th>
                    <th>Price</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orderMap}" var="order">
                    <tr class="first">
                        <td>
                            <div class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                    ${order.key.user.name} <span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu">
                                    <li>Surname - ${order.key.user.surname}</li>
                                    <li>Loyalty points - ${order.key.user.loyaltyPoint}</li>
                                    <li>Email - ${order.key.user.mail}</li>
                                    <li>Phone - ${order.key.user.phone}</li>
                                    <li>Passport - ${order.key.user.passport}</li>
                                </ul>

                            </div>
                        </td>
                        <td>
                            <c:if test="${order.key.paid == false}">
                                <form class="form-inline" action="/controller">
                                    <input type="hidden" name="command" value="payadmin" />
                                    <input type="hidden" name="orderId" value=${order.key.id} />
                                    <button type="submit" class="btn btn-success" onclick="return confirm('Are you sure yau want to pay?')">Pay</button>
                                </form>
                            </c:if>
                            <c:if test="${order.key.paid == true}">
                                <span>Paid</span>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${order.key.deliveryStatus == false && order.key.paid == true}">
                                <form class="form-inline" action="/controller">
                                    <input type="hidden" name="command" value="deliver" />
                                    <input type="hidden" name="orderId" value=${order.key.id} />
                                    <button type="submit" class="btn btn-success" onclick="return confirm('Are you sure yau want to delivery?')">Deliver</button>
                                </form>
                            </c:if>
                            <c:if test="${order.key.paid == false}">
                                <span>Not paid</span>
                            </c:if>
                            <c:if test="${order.key.deliveryStatus == true}">
                                <span>Delivered</span>
                            </c:if>
                        </td>
                        <td>${order.key.date}</td>
                        <td>${order.key.deliveryDate}</td>
                        <td>${order.key.price}</td>

                    </tr>
                    <tr class="second">
                        <td>

                            <div class="dropdown show">
                                <a class="btn btn-secondary dropdown-toggle" id=${order.key.id} data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    Dishes
                                </a>

                                <div class="dropdown-menu" aria-labelledby=${order.key.id}>
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th>Picture</th>
                                            <th>Name</th>
                                            <th>Kitchen Name</th>
                                            <th>Price</th>
                                            <th>Amount</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${order.value}" var="dish">
                                            <tr>
                                                <th><img src="/${dish.key.picture}" alt="Картинка блюда"></th>
                                                <th>${dish.key.name}</th>
                                                <th>${dish.key.kitchen.name}</th>
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










<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="../../bootstrap-3.3.7/dist/js/bootstrap.min.js"></script>
<script src="../../bootstrap-3.3.7/docs/assets/js/docs.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../bootstrap-3.3.7/docs/assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>

