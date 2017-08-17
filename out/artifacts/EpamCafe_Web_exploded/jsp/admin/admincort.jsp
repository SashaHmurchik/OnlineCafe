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

    <title>Admincort</title>

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
    <style type="text/css">
        img {
            width: 120px;
            height: 120px;
        }
    </style>
</head>

<body>
<jsp:include page="/jsp/admin/pagepart/header.jsp"/>
<div class="container theme-showcase" role="main">
    <form class="form-inline" action="/controller">
        <input type="hidden" name="command" value="FindUserBySurname" />
        <label class="sr-only" for="inlineFormInput">Surname</label>
        <input type="text" name="surname" class="form-control mb-2 mr-sm-2 mb-sm-0" id="inlineFormInput" placeholder="Vasia Pupkin">
        <button type="submit" class="btn btn-primary">Найти</button>
    </form>
    <br>
    <div class="row">
        <div class="col-md-2">
            <form class="form-inline" action="/controller">
                <input type="hidden" name="command" value="FINDUSERBYSURNAME" />
                <input type="hidden" name="surname" value="allusers" />
                <button type="submit" class="btn btn-primary">Show All Users</button>
            </form>
        </div>
        <div class="col-md-2">
            <form class="form-inline" action="/controller">
                <input type="hidden" name="command" value="SHOWORDERBYDATE" />
                <input type="date" name="startDate" value="${user.availableForDeliveryDays[0]}" />
                <input type="date" name="endDate" value="${user.availableForDeliveryDays[0]}" />
                <button type="submit" class="btn btn-primary">Show Orders</button>
            </form>
        </div>
    </div>


    <div class="row">
        <div class="col-md-12">
            <h5>${findUserStatus}</h5>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <table class="table">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Passport</th>
                    <th>Email</th>
                    <th>Orders</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${userList}" var="user">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.surname}</td>
                        <td>${user.passport}</td>
                        <td>${user.mail}</td>
                        <td>
                            <form class="form-inline" action="/controller">
                                <input type="hidden" name="command" value="showorders" />
                                <input type="hidden" name="clientId" value=${user.id} />
                                <button type="submit" class="btn btn-primary">Show Orders</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>



</div> <!-- /container -->


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
