<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Contact Us</title>

    <jsp:include page="includes/head.jsp"/>
</head>
<body class="background">
<jsp:include page="includes/header.jsp"/>


<section id="contact">
    <div class="main-wrapper">
        <div>
            <div id="wrapper" class="center-div">
                <div id="register_div" class="card card-block span7 animate form"
                     style="padding-top: 20px; padding-bottom: 20px; min-height: 100%">
                    <form:form action="/contact" method="post" modelAttribute="feedback" autocomplete="on"
                               style="min-width: 400px;">
                        <h2 class="center-div">CONTACT US</h2>

                        </br></br>
                        <div>
                            <label for="name" 0data-icon="u">Your name</label>
                            <form:input type="text" path="name" id="name"
                                        placeholder="John" required="required"/>
                        </div>
                        <div class="has-error">
                            <form:errors path="name" class="help-inline material-red-text "/>
                        </div>

                        <div>
                            <label for="email" data-icon="e">Your email</label>
                            <form:input type="email" path="email" id="email"
                                        placeholder="johndoe_199x@mail.com"
                                        required="required"/>
                        </div>
                        <div class="has-error">
                            <form:errors path="email" class="help-inline material-red-text "/>
                            <span class="animated help-inline material-red-text " id="emailerror"></span>
                        </div>

                        <div class="controls">
                            <form:textarea path="comment" name="comment" class="md-textarea" id="comment"
                                           required="true"/>
                            <label for="comment">Enter your comment</label>
                        </div>

                        <sec:authorize access="isAuthenticated()">
                            <div>
                                <label for="rating" data-icon="e">Rating</label>
                                <form:input type="number" path="rating" id="rating" min="0" max="5"/>
                            </div>
                            <div class="has-error">
                                <form:errors path="rating" class="help-inline material-red-text "/>
                            </div>
                        </sec:authorize>
                        <sec:authorize access="isAnonymous()">
                            <input type="hidden" name="rating" id="rating" value=""/>
                        </sec:authorize>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <p>
                            <input class="btn btn-primary waves-button waves-light center-div-horizontal"
                                   style="width: 240px; height: 40px"
                                   type="submit" value="Send"/>
                        </p>
                    </form:form>
                </div>

            </div>
        </div>
    </div>
</section>
</body>
</html>
