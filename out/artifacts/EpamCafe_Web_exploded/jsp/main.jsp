<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent" var="rb" />

<html>
    <head><title><fmt:message key="label.main.header" bundle="${rb}"/></title></head>
    <jsp:include page="/jsp/pagepart/header.jsp"/>
    <body>
        <h3><fmt:message key="label.main.header" bundle="${rb}"/></h3>
        <hr/>
        ${sessionScope.user.name}<fmt:message key="label.main.message" bundle="${rb}"/>
        роль - ${sessionScope.user.role}

        <hr/>

        <form name="loginForm" method="POST" action="/controller">
            <input type="hidden" name="command" value="showdish" />
            <input type="submit" value="Show Dish" />
        </form>

        <form method="POST" action="/controller">
            <input type="hidden" name="command" value="logout" />
            <button type="submit"> <fmt:message key="label.main.logout_button"  bundle="${rb}"/></button>
        </form>
    </body>
</html>