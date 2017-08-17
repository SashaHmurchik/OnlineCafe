<%--
  Created by IntelliJ IDEA.
  User: melqueades
  Date: 26.07.2017
  Time: 23:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent" var="rb" />

<html>
<head>
    <title>FoodCort</title>
    <style type="text/css">
        img {
            width: 120px;
            height: 120px;
        }
    </style>
</head>
<body>
    <jsp:include page="/jsp/pagepart/header.jsp"/>


    <br/>
    <a href="/jsp/client/basket.jsp"><fmt:message key="label.foodcort.gotobasket" bundle="${rb}"/></a>
    <br/>
    ${dishAddStatus}
    <c:if test="${not empty sessionScope.dishAddStatus}">
        <c:remove var="dishAddStatus" scope="session" />
    </c:if>


    <form method="get" action="/controller">
        <input type="hidden" name="command" value="showdish"/>
        <fmt:message key="label.foodcort.category" bundle="${rb}"/>
        <br/>
        <select name="category">
            <option selected value="x"><fmt:message key="label.foodcort.any" bundle="${rb}"/></option>
            <c:forEach items="${categoryList}" var="category">
                <option value="${category.id}">${category.name}</option>
            </c:forEach>
        </select>
        <br/>
        <fmt:message key="label.foodcort.kitchen" bundle="${rb}"/>
        <br/>
        <select name="kitchen" >
            <option selected value="x"><fmt:message key="label.foodcort.any" bundle="${rb}"/></option>
            <c:forEach items="${kitchenList}" var="kitchen">
                <option value="${kitchen.id}">${kitchen.name}</option>
            </c:forEach>
        </select>
        <br/>
        <input type="submit" value=<fmt:message key="label.foodcort.show_button" bundle="${rb}"/> >
    </form>

    <table>
        <tr>
            <th>Category</th>
            <th>Name</th>
            <th>Price</th>
            <th>Kitchen</th>
            <th>Picture</th>
            <th></th>
        </tr>
        <c:forEach items="${dishList}" var="dish">
        <tr>
            <td>${dish.category.name}</td>
            <td>${dish.name}</td>
            <td>${dish.price} р.</td>
            <td>${dish.kitchen.name}</td>
            <td><img src="/${dish.picture}" alt="Картинка блюда"></td>
            <td>
                <form method="get" action="/controller">
                    <input type="hidden"  name="command" value="addtobasket" />
                    <input type="hidden"  name="dish" value="${dish.id}" />
                    <input type="submit" value="<fmt:message key="label.foodcort.addtobasket" bundle="${rb}"/>" >
                </form>
            </td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>
