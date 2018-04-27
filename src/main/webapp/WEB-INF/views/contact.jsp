<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <form action="" method="post" autocomplete="on" style="min-width: 400px;">
                        <h2 class="center-div">CONTACT US</h2>

                        </br></br>
                        <p>
                            <label for="name" data-icon="u"> Name </label>
                            <input id="name" name="name" required="required" type="text"
                                   placeholder="Full Name"/>
                        </p>

                        <p>
                            <label for="email" data-icon="u"> Your email </label>
                            <input id="email" name="email" required="required" type="email"
                                   placeholder="johndoe_199x@mail.com"/>
                        </p>

                        <p>
                            <textarea class="md-textarea" placeholder="Your message"></textarea>
                        </p>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <p>
                            <input class="btn btn-primary waves-button waves-light center-div-horizontal"
                                   style="width: 240px; height: 40px"
                                   type="submit" value="Send"/>
                        </p>
                    </form>
                </div>

            </div>
        </div>
    </div>
</section>
</body>
</html>
