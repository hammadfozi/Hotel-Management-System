<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>About Us</title>

    <jsp:include page="includes/head.jsp"></jsp:include>
</head>
<body class="white">
<jsp:include page="includes/header.jsp"></jsp:include>

<section style="margin-top: 10%">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-2"></div>
            <div class="col-sm-8">
                <h2 class="h2-responsive font-weight-bold">About Hyatt</h2>
                <h4 class="h4-responsive">A 7-star Restaurant located in Huawei. We provide family, executive and deluxe
                    type rooms.</h4>
                <button class="btn btn-default btn-lg" onclick="window.location='/contact'">Get in Touch</button>
            </div>
            <div class="col-sm-4">
            </div>
        </div>
    </div>
    <br/>
    <div class="container-fluid bg-grey">
        <div class="row">
            <div class="col-sm-2"></div>
            <div class="col-sm-8">
                <h2 class="h2-responsive">Our Values</h2>
                <h4 class="h4-responsive"><strong>MISSION:</strong> Our mission is to make customers happy.</h4>
                <p><strong>VISION:</strong> Our vision is to provide best services.</p>
            </div>
        </div>
    </div>
</section>
</body>
</html>
