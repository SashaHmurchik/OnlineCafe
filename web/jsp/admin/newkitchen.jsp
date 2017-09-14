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

    <title>New Kitchen</title>
</head>
    <body>
        <jsp:include page="/jsp/admin/pagepart/header.jsp"/>
        <div class="container theme-showcase" role="main">
            <div class="row">
                <div class="col-md-4"></div>
                <div class="col-md-4">
                    <c:if test="${not empty sessionScope.addStatusPositive}">
                        <div class="alert alert-success" role="alert">${sessionScope.addStatusPositive}</div>
                        <c:remove var="addStatusPositive" scope="session" />
                    </c:if>
                    <c:if test="${not empty sessionScope.addStatusNegative}">
                        <div class="alert alert-warning" role="alert">${sessionScope.addStatusNegative}</div>
                        <c:remove var="addStatusNegative" scope="session" />
                    </c:if>
                    <p><span class="required">*</span> - <fmt:message key="label.form.requiredfilds" bundle="${rb}"/></p>
                    <form name="uploadForm" action='/uploadcontroller' method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="command" value="addkitchen" />
                        <fieldset>

                            <div class="control-group">
                                <!-- Name -->
                                <label class="control-label"  for="name"><fmt:message key="label.foodcort.kitchen" bundle="${rb}"/><span class="required">*</span></label>
                                <div class="controls">
                                    <input type="text" class="form-control" id="name" name="kitchen_name"  required=""  />
                                    <p class="help-block"><fmt:message key="label.add.kitchen.help" bundle="${rb}"/></p>
                                </div>
                            </div>

                            <div class="control-group">
                                <!-- E-mail -->
                                <label class="control-label" for="email"><fmt:message key="label.kitchen.email" bundle="${rb}"/><span class="required">*</span></label>
                                <div class="controls">
                                    <input type="email" id="email" name="kitchen_email" required="" class="form-control"/>
                                    <p class="help-block"><fmt:message key="label.registration.email.help" bundle="${rb}"/></p>
                                </div>
                            </div>

                            <div class="control-group">
                                <!-- Address -->
                                <label class="control-label"  for="address"><fmt:message key="label.kitchen.adress" bundle="${rb}"/><span class="required">*</span></label>
                                <div class="controls">
                                    <input type="text" class="form-control" id="address" name="kitchen_address"  required="" pattern="[А-Яа-я\w\s.,?!-+#%_()]{5,70}"/>
                                    <p class="help-block"></p>
                                </div>
                            </div>


                            <div class="control-group">
                                <!-- Phone -->
                                <label class="control-label"  for="phone"><fmt:message key="label.kitchen.phone" bundle="${rb}"/><span class="required">*</span></label>
                                <div class="controls">
                                    <input type="text" class="form-control bfh-phone" id="phone" name="kitchen_phone"  required="" data-format="+375 (dd) ddd-dd-dd" pattern="(^[+]{1}[\d]{3}[\ ][(]{1}[\d]{2}[)]{1}[\ ][\d]{3}[-]{1}[\d]{2}[-]{1}[\d]{2}$)" />
                                    <p class="help-block"><fmt:message key="label.registration.phone.help" bundle="${rb}"/></p>
                                </div>
                            </div>

                            <div class="control-group">
                                <!-- Site -->
                                <label class="control-label"  for="site"><fmt:message key="label.kitchen.site" bundle="${rb}"/><span class="required">*</span></label>
                                <div class="controls">
                                    <input type="text" class="form-control" id="site" name="kitchen_site"  required="" pattern="^(https?:\/\/)?([\w\.]+)\.([a-z]{2,6}\.?)(\/[\w\.]*)*\/?$"/>
                                    <p class="help-block"></p>
                                </div>
                            </div>

                            <div class="control-group">
                                <!-- Photo-->
                                <label class="btn btn-primary" for="my-file-selector">
                                    <input id="my-file-selector" required type="file" name="file_field" style="display:none;">
                                    <fmt:message key="label.add.selectfile" bundle="${rb}"/><span class="required">*</span>
                                </label>
                                <p class="help-block"><fmt:message key="label.add.selectfile.help" bundle="${rb}"/></p>
                            </div>
                            <br>

                            <div class="control-group">
                                <!-- Button -->
                                <div class="controls">
                                    <button class="btn btn-success" type="submit"><fmt:message key="label.add.add" bundle="${rb}"/></button>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                    <br>

                </div>
                <div class="col-md-4"></div>
            </div>
        </div>
        <jsp:include page="/jsp/admin/pagepart/footer.jsp"/>
    </body>
</html>
