<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty sessionScope.locale}">
    <c:set var="locale" value="en_US" scope="session"  />
</c:if>

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
    <link rel="icon" href="../fonts/favicon.ico">

    <title>Login Page</title>

</head>

<body>
    <jsp:include page="/jsp/pagepart/header.jsp"/>
    <div class="container" role="main">
        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <strong>${sessionScope.statusInfo}</strong>
                <c:if test="${not empty sessionScope.statusInfo}">
                    <c:remove var="statusInfo" scope="session" />
                </c:if>
                <form class="form-signin"  name="loginForm" method="POST" action="/controller">
                    <input type="hidden" name="command" value="login" />
                    <h2 class="form-signin-heading"><fmt:message key="label.login.header" bundle="${rb}"/></h2>
                    <label for="inputEmail" class="sr-only"><fmt:message key="label.registration.email" bundle="${rb}"/></label>
                    <input type="email" name="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
                    <label for="inputPassword" class="sr-only"><fmt:message key="label.registration.password" bundle="${rb}"/></label>
                    <input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>

                    <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="label.login.login_button" bundle="${rb}" /></button>
                    <br

                    <p>${errorLoginPassMessage}</p>

                </form>
                <a href="/jsp/passwordrecovery.jsp"><fmt:message key="label.login.forgot-password" bundle="${rb}"/></a>
            </div>
        </div>
    </div>
    <div class="col-md-4"></div>
    <jsp:include page="/jsp/pagepart/footer.jsp"/>
</body>
</html>

