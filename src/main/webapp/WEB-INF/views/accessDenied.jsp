<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>404</title>

    <jsp:include page="includes/head.jsp"/>

    <style>
        body {
            margin: 0;
            padding: 0;
            width: 100%;
            color: #B0BEC5;
            display: table;
            font-weight: 100;
        }

        .container {
            text-align: center;
            display: table-cell;
            vertical-align: middle;
        }

        .content {
            text-align: center;
            display: inline-block;
        }

        .title {
            font-size: 72px;
            margin-bottom: 40px;
        }
    </style>

    <script>

        function goBack() {
            if (history.length > 2)
                window.history.back();
            else window.location = '/';
        }
    </script>
</head>

<body>
<div class="container">
    <div class="content">
        <div class="title">Dear<strong>${loggedinuser}</strong>, You have lost your way.</div>

        <button id="login" onclick="goBack()" type="button" class="btn-primary waves-button"
                style="width: 120px; font-weight: 500">GO BACK
        </button>
    </div>
</div>
</body>
</html>