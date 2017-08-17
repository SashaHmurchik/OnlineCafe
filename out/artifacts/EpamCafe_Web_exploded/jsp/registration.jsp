<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
,

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent" var="rb"/>

<html>
<head>
    <title>Registration</title>
</head>
<body>
<jsp:include page="/jsp/pagepart/header.jsp"/>
<form name="registerForm" method="POST" action="/controller">
    <input type="hidden" name="command" value="signup"/>
    <fmt:message key="label.registration.email" bundle="${rb}"/><br/>
    <input type="text" name="email" value=""/>
    <br/>
    <fmt:message key="label.registration.name" bundle="${rb}"/><br/>
    <input type="text" name="name" value=""/>
    <br/>
    <fmt:message key="label.registration.surname" bundle="${rb}"/><br/>
    <input type="text" name="surname" value=""/>
    <br/>
    <fmt:message key="label.registration.password" bundle="${rb}"/><br/>
    <input type="password" name="password" value=""/>
    <br/>
    <fmt:message key="label.registration.repeatpassword" bundle="${rb}"/><br/>
    <input type="password" name="repeatpassword" value=""/>
    <br/>
    <fmt:message key="label.registration.passport" bundle="${rb}"/><br/>
    <input type="text" name="passport" value=""/>
    <br/>
    <fmt:message key="label.registration.phone" bundle="${rb}"/><br/>
    <input type="text" name="phone" value=""/>
    <br/>
    ${errorSignUpMessage}
    <br/>
    ${wrongAction}
    <br/>
    ${nullPage}
    <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
