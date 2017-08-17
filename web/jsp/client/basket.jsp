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
    <link rel="icon" href="../../../bootstrap-3.3.7/docs/favicon.ico">

    <title>Basket</title>

    <!-- Bootstrap core CSS -->
    <link href="../../../bootstrap-3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="../../../bootstrap-3.3.7/dist/css/bootstrap-theme.min.css" rel="stylesheet">
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="../../../bootstrap-3.3.7/docs/assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../../../css/theme.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../../bootstrap-3.3.7/docs/assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="../../../bootstrap-3.3.7/docs/assets/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
        img {
            width: 120px;
            height: 120px;
        }
    </style>
</head>
<body>
    <jsp:include page="/jsp/client/pagepart/header.jsp"/>
    <div class="row">
        <div class="col-md-12">
            <table class="table" >
                <thead>
                <tr>
                    <th><fmt:message key="label.basket.picture" bundle="${rb}"/></th>
                    <th><fmt:message key="label.basket.name" bundle="${rb}"/></th>
                    <th><fmt:message key="label.basket.price" bundle="${rb}"/></th>
                    <th><fmt:message key="label.basket.amount" bundle="${rb}"/></th>
                    <th><fmt:message key="label.basket.kitchenname" bundle="${rb}"/></th>
                    <th><fmt:message key="label.basket.description" bundle="${rb}"/></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${user.basket}" var="dish">
                    <tr>
                        <td><img src="/${dish.key.picture}" alt="Картинка блюда"></td>
                        <td>${dish.key.name}</td>
                        <td>${dish.key.price}</td>
                        <td>${dish.value}</td>
                        <td>${dish.key.kitchen.name}</td>
                        <td>${dish.key.description}</td>
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
                <p><fmt:message key="label.basket.selectdate" bundle="${rb}"/></p>
                    <input type="hidden" name="command" value="makeorder"/>
                    <input type="date" name="calendar" value="${user.availableForDeliveryDays[1]}"
                           max="${user.availableForDeliveryDays[2]}" min="${user.availableForDeliveryDays[1]}">
                    <button type="submit" class="btn btn-success"><fmt:message key="label.basket.makeorder" bundle="${rb}"/></button>
            </form>
        </div>
        <div class="col-md-6">
            <form method="get" action="/controller">
                <input type="hidden" name="command" value="showmyorder"/>
                <button type="submit" class="btn btn-success"><fmt:message key="label.basket.myorder" bundle="${rb}"/></button>
            </form>
        </div>
    </div>

</body>
</html>
