<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Registration</title>

    <jsp:include page="includes/head.jsp"/>

    <script type="text/javascript" src="<c:url value='/static/js/user-validations.js' />"></script>
</head>

<body class="background">

<jsp:include page="includes/header.jsp"/>

<section id="register" style="margin-top: 5%">

    <form:form id="regForm" action="/register" method="post" modelAttribute="user">
        <form:input type="hidden" path="id" id="id"/>
        <div>
            <h1 class="heading h1-responsive blue center-div white-text card-header">SIGN UP</h1>

            <div class="form-group card card-block">

                <div>
                    <label for="firstName" 0data-icon="u">Your first name</label>
                    <form:input type="text" onchange="firstNameChecker()" path="firstName" id="firstName"
                                placeholder="John" required="required"/>
                </div>
                <div class="has-error">
                    <form:errors path="firstName" class="help-inline material-red-text "/>
                </div>
                </br>
                <div>
                    <label for="lastName" data-icon="u">Your last name</label>
                    <form:input type="text" onchange="lastNameChecker()" path="lastName" id="lastName" placeholder="Doe"
                                required="required"/>
                </div>
                <div class="has-error">
                    <form:errors path="lastName" class="help-inline material-red-text "/>
                </div>
                </br>
                <div>
                    <label for="username" data-icon="e">Your unique customer name</label>
                    <form:input type="text" path="username" id="username"
                                placeholder="johndoe_199x"
                                required="required"/>
                </div>
                <div class="has-error">
                    <form:errors path="username" class="help-inline material-red-text "/>
                    <span class="animated help-inline material-red-text " id="usernameerror"></span>
                </div>
                </br>
                <div>
                    <label for="email" data-icon="e">Your email</label>
                    <form:input type="email" onchange="emailChecker()" path="email" id="email"
                                placeholder="johndoe_199x@mail.com"
                                required="required"/>
                </div>
                <div class="has-error">
                    <form:errors path="email" class="help-inline material-red-text "/>
                    <span class="animated help-inline material-red-text " id="emailerror"></span>
                </div>
                </br>
                <div>
                    <label for="password" data-icon="p">Your password </label>
                    <form:input type="password" onchange="passwordChecker()" path="password" id="password"
                                placeholder="Password"/>
                </div>
                <div class="has-error">
                    <form:errors path="password" class="help-inline material-red-text "/>
                </div>
                </br>
                <div>
                    <label for="confirmPassword" data-icon="p">Please confirm your password </label>
                    <input id="confirmPassword" onchange="checkFields()" required="required" type="password"
                           placeholder="Confirm Password"/>
                </div>
                </br>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </br>
                <div style="text-align: center">
                    <input class="btn btn-primary waves-button waves-light" style="width: 240px; height: 40px"
                           type="submit" value="Sign up"/>
                </div>
                </br>
                <div class="change_link" style="text-align: center">
                    Already a member ? </br>
                    <a href="/login" class="to_register"> Login Instead </a>
                </div>
            </div>
        </div>
    </form:form>
</section>
</body>
</html>