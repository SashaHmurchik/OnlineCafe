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

    <title>Header</title>

    <!-- Bootstrap core CSS -->
    <link href="../../../css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap theme -->
    <link href="../../../css/bootstrap-theme.min.css" rel="stylesheet">
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="../../../css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="../../../css/theme.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../../js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="../../../js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<!-- Fixed navbar -->
<nav class="navbar navbar-default navbar-fixed-top">
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
                <li><a href="/jsp/admin/admincort.jsp"><fmt:message key="label.navbar.admin.main"  bundle="${rb}"/></a></li>
                <li><a href="/jsp/admin/orders.jsp"><fmt:message key="label.navbar.admin.orders"  bundle="${rb}"/></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><fmt:message key="label.navbar.admin.add"  bundle="${rb}"/><span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/controller?command=adddish"><fmt:message key="label.navbar.admin.newdish"  bundle="${rb}"/></a></li>
                        <li><a href="/jsp/admin/newcategory.jsp"><fmt:message key="label.navbar.admin.newcategory"  bundle="${rb}"/></a></li>
                        <li><a href="/jsp/admin/newkitchen.jsp"><fmt:message key="label.navbar.admin.newkitchen"  bundle="${rb}"/></a></li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><fmt:message key="label.navbar.admin.show"  bundle="${rb}"/><span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/controller?command=showdish&current_page=1"><fmt:message key="label.navbar.admin.dishes"  bundle="${rb}"/></a></li>
                        <li><a href="/controller?command=showcategory&archive_status=false"><fmt:message key="label.navbar.admin.categorys"  bundle="${rb}"/></a></li>
                        <li><a href="/controller?command=showkitchen&archive_status=false"><fmt:message key="label.navbar.admin.kitchens"  bundle="${rb}"/></a></li>
                    </ul>
                </li>
                <li><a href="/jsp/admin/myaccount.jsp"><fmt:message key="label.navbar.account"  bundle="${rb}"/></a></li>
            </ul>
            <ul class="nav navbar-nav active">
                <li>
                    <form method="POST" action="/controller">
                        <input type="hidden" name="command" value="logout" />
                        <button class="btn btn-default navbar-btn" ><fmt:message key="label.main.logout_button"  bundle="${rb}"/></button>
                    </form>
                </li>
                <li>
                    <form action="/controller">
                        <input type="hidden" name="command" value="locale">
                        <button type="submit" class="btn btn-default navbar-btn" name="locale" value="RU" >RU</button>
                    </form>
                </li>
                <li>
                    <form action="/controller">
                        <input type="hidden" name="command" value="locale">
                        <button type="submit" class="btn btn-default navbar-btn" name="locale" value="EN" >EN</button>
                    </form>
                </li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>
</body>
</html>
