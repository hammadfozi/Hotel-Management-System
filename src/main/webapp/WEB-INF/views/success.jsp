<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success!</title>

    <jsp:include page="includes/head.jsp"/>

    <script>
        setTimeout("window.location.href='/';", 5 * 1000);
    </script>
</head>
<body>
<jsp:include page="includes/header.jsp"/>

<main>
    <div class="container">
        <div class="alert alert-success lead">
            ${success}
        </div>
        </br>
        <div class="text-sm-left" style="font-size: 10px">
            You'll be redirected shortly.
        </div>

		<span class="well floatRight">
			Go to <a href="<c:url value='/' />">Home</a>
		</span>
    </div>
</main>
</body>
</html>
