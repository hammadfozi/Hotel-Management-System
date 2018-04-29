<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>

    <jsp:include page="includes/head.jsp"/>

    <script type="text/javascript" src="<c:url value='/static/js/user-validations.js' />"></script>
</head>

<body class="background">
<jsp:include page="includes/header.jsp"/>

<section id="login" style="margin-top: 9%" class="center-div center-div-horizontal center-div-vertical">
    <form action="/login" method="post" autocomplete="on">
        <div>
            <h1 class="heading h1-responsive center-div light-blue white-text card-header">LOGIN</h1>

            <div class="form-group card card-block">

                <c:if test="${param.error != null}">
                    <div class="alert alert-danger">
                        <p>Invalid email and password.</p>
                    </div>
                </c:if>

                <p>
                    <label for="username" data-icon="u"> Email </label>
                    <input id="username" onchange="usernameChecker()" name="username" required="required" type="email"
                           placeholder="Enter email"/>
                </p>

                <p>
                    <label for="password" data-icon="p"> Your password </label>
                    <input id="password" onchange="passwordChecker()" name="password" required="required"
                           type="password" placeholder="Enter Password"/>
                </p>

                <p>
                    <label><input type="checkbox" id="rememberme" name="remember-me" class="checkbox"
                                  style="float: left; width: 15px">
                        &nbsp;&nbsp;&nbsp;Remember Me</label>
                </p>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <p class="flex-center">
                    <input class="btn btn-primary waves-button waves-light light-blue"
                           style="width: 240px; height: 40px" type="submit" value="Login"/>
                </p>

                <p class="change_link" style="text-align: center">
                    Not a member yet ? </br>
                    <a href="/register" class="to_register">Join us</a>
                </p>
            </div>
        </div>
    </form>

    <form action="/auth/facebook" method="POST" style="position: absolute; margin-top: 27%; margin-left: 15%">
        <button type="submit">Sign in with Facebook</button>
        <input type="hidden" name="scope" value="email,public_profile,user_birthday"/>
    </form>
</section>
</body>
</html>