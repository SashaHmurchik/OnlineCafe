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
    <link rel="icon" href="../../../bootstrap-3.3.7/docs/favicon.ico">

    <title>Theme Template for Bootstrap</title>

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
        .active {
            float: right;
        }
    </style>
</head>
<body>
<!-- Fixed navbar -->
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="admincort.jsp">Main</a></li>
                <li><a href="orders.jsp">Orders</a></li>

                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li role="separator" class="divider"></li>
                        <li class="dropdown-header">Nav header</li>
                        <li><a href="#">Separated link</a></li>
                        <li><a href="#">One more separated link</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav navbar-nav active">
                <li>
                    <form method="POST" action="/controller">
                        <input type="hidden" name="command" value="logout" />
                        <button type="submit" ><fmt:message key="label.main.logout_button"  bundle="${rb}"/></button>
                    </form>
                </li>
                <li>
                    <form action="/controller">
                        <input type="hidden" name="command" value="locale">
                        <input type="submit" name="locale" value="RU" >
                        <input type="submit" name="locale" value="EN">
                    </form>
                </li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>
</body>
</html>