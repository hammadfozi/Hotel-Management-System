<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Create User</title>

    <jsp:include page="includes/head.jsp"/>

    <script type="text/javascript" src="<c:url value='/static/js/user-validations.js' />"></script>
</head>

<body class="background">

<jsp:include page="includes/staff_header.jsp"/>

<section id="register" style="margin-top: 5%">

    <form:form action="/admin/new/user" method="post" modelAttribute="user" id="regForm">
        <form:input type="hidden" path="id" id="id"/>
        <div>
            <h1 class="heading h1-responsive blue-grey center-div white-text card-header">CREATE USER</h1>

            <div class="form-group card card-block">
                <div>
                    <label for="firstName" data-icon="u">First name</label>
                    <form:input type="text" onchange="firstNameChecker()" path="firstName" id="firstName"
                                placeholder="John" required="required"/>
                </div>
                <div class="has-error">
                    <form:errors path="firstName" class="help-inline material-red-text "/>
                </div>
                </br>
                <div>
                    <label for="lastName" data-icon="u">Last name</label>
                    <form:input type="text" onchange="lastNameChecker()" path="lastName" id="lastName" placeholder="Doe"
                                required="required"/>
                </div>
                <div class="has-error">
                    <form:errors path="lastName" class="help-inline material-red-text "/>
                </div>
                </br>
                <div>
                    <label for="username" data-icon="e">Username</label>
                    <form:input type="text" onchange="usernameChecker()" path="username" id="username"
                                placeholder="johndoe199x" required="required"/>
                </div>
                <div class="has-error">
                    <form:errors path="username" class="help-inline material-red-text "/>
                    <span class="animated help-inline material-red-text " id="usernameerror"></span>
                </div>
                </br>
                <div>
                    <label for="email" data-icon="e">Email</label>
                    <form:input type="email" onchange="emailChecker()" path="email" id="email"
                                placeholder="johndoe_199x@mail.com" required="required"/>
                </div>
                <div class="has-error">
                    <form:errors path="email" class="help-inline material-red-text "/>
                    <span class="animated help-inline material-red-text " id="emailerror"></span>
                </div>
                </br>
                <div>
                    <label for="password" data-icon="p">Password</label>
                    <form:input type="password" onchange="passwordChecker()" path="password" id="password"
                                placeholder="password"/>
                </div>
                <div class="has-error">
                    <form:errors path="password" class="help-inline material-red-text "/>
                </div>
                </br>
                <div>
                    <label for="confirmPassword" data-icon="p">Please confirm password </label>
                    <input id="confirmPassword" onchange="checkFields()" required="required" type="password"
                           placeholder="Confirm Password"/>
                </div>
                </br>
                <label for="userProfiles">Assign Role</label>

                <div>
                    <form:select path="userProfiles" items="${roles}" itemValue="id" itemLabel="type" multiple="true"
                                 required="required" cssClass="input-block-level rgba-white-slight waves-input-wrapper"
                                 cssStyle="padding-top: 10px">
                    </form:select>
                    <div class="has-error">
                        <form:errors path="userProfiles" class="help-inline material-red-text "/>
                    </div>
                </div>
                </br>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </br>
                <div style="text-align: center">
                    <input class="btn btn-blue-grey waves-button waves-light" style="width: 240px; height: 40px"
                           type="submit" value="Create"/>
                </div>
                </br>
                <div class="change_link" style="text-align: center">
                    <a href="/admin" class="to_register">GO BACK TO ADMIN PANEL</a>
                </div>
            </div>
        </div>
    </form:form>
</section>
</body>
</html>