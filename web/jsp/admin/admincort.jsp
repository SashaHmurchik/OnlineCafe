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

    <title>Admincort</title>

</head>

<body>
<jsp:include page="/jsp/admin/pagepart/header.jsp"/>
    <div class="container theme-showcase" role="main">

        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <c:if test="${not empty sessionScope.findUserStatus}">
                    <div class="alert alert-success" role="alert">${sessionScope.findUserStatus}</div>
                    <c:remove var="findUserStatus" scope="session" />
                </c:if>
                <c:if test="${not empty requestScope.findUserStatus}">
                    <div class="alert alert-danger" role="alert">${requestScope.findUserStatus}</div>
                    <c:remove var="findUserStatus" scope="request" />
                </c:if>
                <form class="form-inline" action="/controller">
                    <input type="hidden" name="command" value="finduserbysurname" />
                    <input type="hidden" name="surname" value="allusers" />
                    <button type="submit" class="btn btn-primary"><fmt:message key="label.admimcort.showallusers" bundle="${rb}"/></button>
                </form>
                <br/>
                <form class="form-inline" action="/controller">
                    <input type="hidden" name="command" value="finduserbysurname" />
                    <label class="sr-only" for="inlineFormInput"><fmt:message key="label.registration.surname" bundle="${rb}"/></label>
                    <input type="text" name="surname" class="form-control" id="inlineFormInput" required placeholder="Блинов" pattern="^[А-ЯA-Z][a-яa-z]{2,24}(-[А-ЯA-Z][a-яa-z]{2,12})?"/>
                    <p class="help-block"><fmt:message key="label.user.find.by.surname.help" bundle="${rb}"/></p>
                    <button type="submit" class="btn btn-primary"><fmt:message key="label.foodcort.show_button" bundle="${rb}"/></button>
                </form>
            </div>
            <div class="col-md-4"></div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <table class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th><fmt:message key="label.registration.name" bundle="${rb}"/></th>
                        <th><fmt:message key="label.registration.surname" bundle="${rb}"/></th>
                        <th><fmt:message key="label.registratiion.loyaltypoints" bundle="${rb}"/></th>
                        <th><fmt:message key="label.registration.passport" bundle="${rb}"/></th>
                        <th><fmt:message key="label.registration.email" bundle="${rb}"/></th>
                        <th><fmt:message key="label.registration.phone" bundle="${rb}"/></th>
                        <th><fmt:message key="label.navbar.admin.orders" bundle="${rb}"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${userList}" var="user">
                        <tr>
                            <td>${user.name}</td>
                            <td>${user.surname}</td>
                            <th>
                                <div class="dropdown show">
                                    <a class="btn btn-secondary dropdown-toggle" id="${user.id}" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <strong>${user.loyaltyPoint}</strong>
                                    </a>
                                    <div class="dropdown-menu" aria-labelledby="${user.id}">
                                        <form method="get" action="/controller">
                                            <input type="hidden" name="command" value="updateloyaltypoint">
                                            <input type="hidden" name="user_id" value=${user.id} />
                                            <button type="submit" class="btn btn-success" name="loyalty_status" value="+" onclick="return confirm('Are you sure?')">+</button>
                                            <button type="submit" class="btn btn-success" name="loyalty_status" value="-" onclick="return confirm('Are you sure?')">-</button>
                                        </form>
                                    </div>
                                </div>
                            </th>
                            <td>${user.passport}</td>
                            <td>${user.mail}</td>
                            <td>${user.phone}</td>
                            <td>
                                <form class="form-inline" action="/controller">
                                    <input type="hidden" name="command" value="showorders" />
                                    <input type="hidden" name="clientId" value=${user.id} />
                                    <button type="submit" class="btn btn-primary"><fmt:message key="label.navbar.admin.orders" bundle="${rb}"/></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div> <!-- /container -->
<jsp:include page="/jsp/admin/pagepart/footer.jsp"/>
</body>
</html>
