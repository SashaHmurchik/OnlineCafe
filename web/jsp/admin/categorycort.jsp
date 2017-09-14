<%--
  Created by IntelliJ IDEA.
  User: melqueades
  Date: 26.08.2017
  Time: 13:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="customtags"%>

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

    <title>Categorycort</title>
</head>

<jsp:include page="/jsp/admin/pagepart/header.jsp"/>

<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <c:if test="${not empty sessionScope.changeArchiveStatusResult}">
                <div class="alert alert-success" role="alert">${sessionScope.changeArchiveStatusResult}</div>
                <c:remove var="changeArchiveStatusResult" scope="session" />
            </c:if>
            <form method="get" action="/controller">
                <input type="hidden" name="command" value="showcategory"/>
                <fmt:message key="label.foodcort.archive" bundle="${rb}"/>
                <br/>
                <select name="archive_status" class="form-control" >
                    <option selected value="false"><fmt:message key="label.foodcort.notfromarchive" bundle="${rb}"/></option>
                    <option value="true"><fmt:message key="label.foodcort.fromarchive" bundle="${rb}"/></option>
                </select>
                <br/>
                <input type="submit" value=<fmt:message key="label.foodcort.show_button" bundle="${rb}"/> />
            </form>
        </div>
        <div class="col-md-4"></div>
        <div class="row">
            <div class="col-md-12">
                <br/>
                <table class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th><fmt:message key="label.category.name" bundle="${rb}"/>e</th>
                        <th>
                            <ctg:to-archive statusMarker="${requestScope.archiveStatusMarker}" locale="${sessionScope.locale}" entityMarker="category_archive"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${sessionScope.categoryList}" var="category">
                        <tr>
                            <td>${category.name}</td>
                            <td>
                                <div class="checkbox">
                                    <label><input type="checkbox" form="archive" name="checkedCategoryId" value="${category.id}"></label>
                                </div>
                                <c:if test="${category.archiveStatus eq false}">
                                    <fmt:message key="label.foodcort.notfromarchive" bundle="${rb}"/>
                                </c:if>
                                <c:if test="${category.archiveStatus eq true}">
                                    <fmt:message key="label.foodcort.fromarchive" bundle="${rb}"/>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/jsp/admin/pagepart/footer.jsp"/>
</body>
</html>
