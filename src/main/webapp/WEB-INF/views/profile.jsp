<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${user.firstName} ${user.lastName}</title>
    <jsp:include page="includes/head.jsp"/>

    <script type="text/javascript" src="<c:url value='/static/js/user-validations.js' />"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $('#success').delay(3000).fadeOut();
        });
    </script>
</head>
<body>
<jsp:include page="includes/header.jsp"/>

<div class="container">
    <div class="row" style="margin-top: 70px">
        <div class="col-sm-10" style="margin-top: 20px"><h1>${user.firstName} ${user.lastName}</h1></div>
        <div class="col-sm-2"><img title="profile image" style="width:75px; height: 75px"
                                   class="img-circle img-responsive"
                                   src="<c:url value='/static/images/default_picture.png' />">
        </div>
    </div>

    <hr>

    <div id="success">
        <c:if test="${success != null}">
            <div class="alert alert-success lead">
                    ${success}
            </div>
        </c:if>
    </div>

    <div class="row">
        <div class="col-sm-3">

            <ul class="list-group">
                <li class="list-group-item text-muted">Activity <i class="fa fa-dashboard fa-1x"></i></li>
                <li class="list-group-item text-right"><span
                        class="pull-left"><strong>Bookings</strong></span> ${totalbookings}</li>
            </ul>

            <div style="margin-top: 30px">
                <button type="button"
                        class="btn btn-sm btn-info"
                        data-toggle="modal" data-target="#changePassword">Change Password
                </button>

                <a href="/user/profile/delete" class="btn btn-sm btn-danger"
                   onclick="return confirm('Are you sure you want to delete your profile? You will have to register again in order to book rooms.')">
                    Delete Profile
                </a>

                <sec:authorize access="hasRole('UNVERIFIED')">

                    <div class="alert alert-info waves-effect" style="width: 255px">
                        <strong>Kindly check your email for confirmation.</strong>
                        <br/><br/>
                        <a class="center-div-horizontal btn-sm waves-button theme-black white-text"
                           href="/user/profile-${pageContext.request.userPrincipal.name}/resend">
                            Resend Confirmation</a>
                    </div>

                </sec:authorize>
            </div>
        </div>

        <div>
            <form:form class="form" action="/user/profile" method="post" modelAttribute="user" id="regForm">
                <form:input type="hidden" path="id" id="id"/>
                <form:input type="hidden" path="password" id="password"/>
                <form:input type="hidden" path="userProfiles" id="userProfiles"/>
                <div class="form-group">

                    <div class="col-xs-6">
                        <label for="firstName"><h4>First name</h4></label>
                        <form:input path="firstName" onchange="firstNameChecker()" type="text" class="form-control"
                                    name="firstName" id="firstName"
                                    placeholder="First name"/>
                        <div class="has-error">
                            <form:errors path="firstName" class="help-inline material-red-text "/>
                        </div>
                    </div>
                </div>
                <div class="form-group">

                    <div class="col-xs-6">
                        <label for="lastName"><h4>Last name</h4></label>
                        <form:input path="lastName" type="text" onchange="lastNameChecker()" class="form-control"
                                    name="lastName" id="lastName"
                                    placeholder="Last name"/>
                        <div class="has-error">
                            <form:errors path="lastName" class="help-inline material-red-text "/>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-xs-6">
                        <label for="username"><h4>Username</h4></label>
                        <form:input path="username" type="text" class="form-control" name="username" id="username"
                                    placeholder="Username" onchange="usernameChecker()"/>
                        <div class="has-error">
                            <form:errors path="username" class="help-inline material-red-text "/>
                            <span class="animated help-inline material-red-text " id="usernameerror"></span>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-xs-6">
                        <label for="email"><h4>Email</h4></label>
                        <form:input path="email" type="email" class="form-control" name="email" id="email"
                                    placeholder="you@email.com" onchange="emailChecker()"/>
                        <div class="has-error">
                            <form:errors path="email" class="help-inline material-red-text "/>
                            <span class="animated help-inline material-red-text " id="emailerror"></span>
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-xs-12">
                        <button class="btn btn-lg btn-primary light-blue white-text waves-button"
                                style="width: 240px;"
                                type="submit">Update
                        </button>
                    </div>
                </div>
            </form:form>
        </div>

        <br><br>
        <hr style="visibility: hidden">

        <sec:authorize access="hasAnyRole('ADMIN', 'MANAGER', 'USER')">
            <c:choose>
                <c:when test="${totalbookings > 0}">

                    <div>
                        <h2>Your Bookings</h2>

                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>Booking ID</th>
                                    <th>Room</th>
                                    <th>People</th>
                                    <th>Arrival Time</th>
                                    <th>Departure Time</th>
                                    <th>Current Status</th>
                                </tr>
                                </thead>
                                <tbody id="items">
                                <c:forEach items="${user.bookings}" var="booking">
                                    <tr>
                                        <td>${booking.id}</td>
                                        <c:choose>
                                            <c:when test="${booking.room != null}">
                                                <td>${booking.room.name}</td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>${booking.roomBooked}</td>
                                            </c:otherwise>
                                        </c:choose>
                                        <td>${booking.people}</td>
                                        <td>${booking.arrivalTime}</td>
                                        <td>${booking.departureTime}</td>
                                        <td>${booking.status}</td>
                                        <td>
                                            <c:if test="${booking.status != 'COMPLETED'}">
                                                <a href="<c:url value='/user/profile/cancel-${booking.id}+confirmed' />"
                                                   onclick="return confirm('Are you sure you want to cancel this booking? You will have to book room again to recover it.')"
                                                   class="btn material-red btn-sm table-actions-buttons">Cancel</a>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <h4 class="h4-responsive text-muted"> No room booked yet. You can <a href="/booking">book room</a>
                        or
                        <a href="/rooms">select room</a> from here.</h4>
                </c:otherwise>
            </c:choose>
        </sec:authorize>

    </div>

    <!-- Change Password Dialog -->
    <div class="modal hide fade" id="changePassword" tabindex="-1" role="dialog"
         aria-labelledby="company-about-label"
         aria-hidden="true" style="display: none;">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="completedroooms"
                        style="float: left">Change Password
                    </h4>
                </div>
                <div class="modal-body">
                    <form class="form" action="/user/profile-loggedin+edit" method="post" style="margin-top: 70px">
                        <div class="form-group">
                            <div>
                                <label for="password"><h4>Password</h4></label>
                                <input onchange="newPasswordChecker()" type="password"
                                       class="form-control" name="newpassword" id="newpassword"
                                       placeholder="New Password" required/>
                            </div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">
                            <div class="col-xs-12">
                                <button class="btn btn-lg btn-primary light-blue white-text waves-button"
                                        style="width: 240px; margin-top: 70px"
                                        type="submit">Update
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</div>

</body>
</html>
