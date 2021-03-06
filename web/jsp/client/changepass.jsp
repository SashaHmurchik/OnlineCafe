<%--
  Created by IntelliJ IDEA.
  User: melqueades
  Date: 20.08.2017
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="resources.pagecontent" var="rb" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../fonts/favicon.ico">

    <title>Change Password</title>

</head>
    <body>
        <jsp:include page="/jsp/client/pagepart/header.jsp"/>
        <div class="container theme-showcase" role="main">
            <div class="row">
                <div class="col-md-4"></div>
                <div class="col-md-4">
                    <c:if test="${not empty sessionScope.errorMessage}">
                        <div class="alert alert-danger" role="alert">${sessionScope.errorMessage}</div>
                        <c:remove var="errorMessage" scope="session" />
                    </c:if>

                    <form class="form-horizontal" action='/controller' method="POST">
                            <input type="hidden" name="command" value="changepass" />
                            <fieldset>
                                <div id="legend">
                                    <h1><fmt:message key="label.account.changepass.header" bundle="${rb}"/></h1>
                                </div>

                                <div class="control-group">
                                    <!-- New password-->
                                    <label class="control-label" for="newpassword"><fmt:message key="label.account.changepass.newpassword" bundle="${rb}"/></label>
                                    <div class="controls">
                                        <input type="password" id="newpassword" name="newPassword" placeholder="" class="form-control" required />
                                        <p class="help-block"><fmt:message key="label.registration.password.help" bundle="${rb}"/></p>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <!-- New password confirm-->
                                    <label class="control-label" for="newpasswordconfirm"><fmt:message key="label.account.changepass.newpassword.confirm" bundle="${rb}"/></label>
                                    <div class="controls">
                                        <input type="password" id="newPasswordConfirm" name="newPasswordConfirm" placeholder="" class="form-control" required />
                                        <p class="help-block"><fmt:message key="label.registration.repeatpassword.help" bundle="${rb}"/></p>
                                    </div>
                                </div>

                                <div class="control-group">
                                    <!-- Password-->
                                    <label class="control-label" for="password"><fmt:message key="label.account.changepass.oldpassword" bundle="${rb}"/></label>
                                    <div class="controls">
                                        <input type="password" id="password" name="password" class="form-control" required />
                                    </div>
                                </div>
                                <br/>

                                <div class="control-group">
                                    <!-- Button -->
                                    <div class="controls">
                                        <button class="btn btn-success" type="submit"><fmt:message key="label.account.changepass.changebutton" bundle="${rb}"/></button>
                                    </div>
                                </div>
                        </fieldset>
                    </form>
                </div>
                <div class="col-md-4"></div>
            </div>
        </div>
        <jsp:include page="/jsp/client/pagepart/footer.jsp"/>
    </body>
</html>
