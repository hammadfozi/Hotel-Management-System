<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Rooms</title>

    <jsp:include page="includes/head.jsp"/>
</head>
<body>
<jsp:include page="includes/header.jsp"/>


<div class="animated" style="height: 220px; position: relative; overflow-x: hidden; overflow-y: hidden">
    <img src="http://res.cloudinary.com/hte2zx5qx/image/upload/roombanner.jpg" class="img-fluid banner" alt=""
         style="position: absolute"/>

    <c:choose>
        <c:when test="${search}">
            <h1 class="white-text container banner-text" style="position: absolute">Search Results</h1>
        </c:when>
        <c:otherwise>
            <h1 class="white-text container banner-text" style="position: absolute">Rooms</h1>
        </c:otherwise>
    </c:choose>

    <div style="position: absolute; top: 60%; left: 70%; box-shadow: darkgrey">
        <form action="/rooms/search" method="post" class="input-light-bg form-horizontal"
              style="width: 420px; margin-left: 10px; margin-right: 10px;">
            <div class="col-md-8" style="text-align: right; margin-right: -10px">
                <div class="form-group">
                    <div class="white card card-header-pills waves-effect waves-light">
                        <c:choose>
                            <c:when test="${search}">
                                <input id="searchtext" name="searchtext" type="text"
                                       class="navbar-search input-light-bg text-lg-left text-muted"
                                       style="font-size: 16px;width: 320px; margin-left: 10px; margin-right: 10px"
                                       placeholder="Search/Filter Rooms" value="${searchtext}">
                            </c:when>
                            <c:otherwise>
                                <input id="searchtext" name="searchtext" type="text"
                                       class="navbar-search input-light-bg text-lg-left text-muted"
                                       style="font-size: 18px;width: 320px; margin-left: 10px; margin-right: 10px"
                                       placeholder="Search/Filter Rooms">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="col-md-1" style="text-align: right;">
                <div class="form-group">
                    <button class="btn btn-primary waves-light" type="submit" style="margin-top: -1px;">
                        <img src="<c:url value="../static/images/icons/search.png" />"
                             style="width: 20px; height: 22px;"/>
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<div style="padding-left: 200px; padding-right: 200px;">
    <div class="row">
        <c:choose>
            <c:when test="${fn:length(rooms) > 0}">
                <c:forEach items="${rooms}" var="room">
                    <div class="col-md-3" style="max-height: 380px; overflow: auto">
                        <div class="card card-cascade narrower">
                            <div class="view overlay hm-white-slight">
                                <c:choose>
                                    <c:when test="${room.booking != null}">
                                        <c:choose>
                                            <c:when test="${fn:length(room.images) > 0}">
                                                <img src="${room.images[0].url}"
                                                     class="img-fluid" alt=""
                                                     style="-webkit-filter: sepia(90%) hue-rotate(90deg) brightness(50%);;height: 150px; max-width: 100%; object-fit: cover; -o-object-fit: cover;"/>
                                            </c:when>
                                            <c:otherwise>
                                                <img src="https://res.cloudinary.com/hte2zx5qx/image/upload/dummy.jpg"
                                                     class="img-fluid"
                                                     alt=""
                                                     style="height: 150px; max-width: 100%; object-fit: cover; -o-object-fit: cover"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${fn:length(room.images) > 0}">
                                                <img src="${room.images[0].url}"
                                                class="img-fluid" alt=""
                                                style="height: 150px; max-width: 100%; object-fit: cover; -o-object-fit: cover;"/>
                                            </c:when>
                                            <c:otherwise>
                                                <img src="https://res.cloudinary.com/hte2zx5qx/image/upload/dummy.jpg"
                                                     class="img-fluid"
                                                     alt=""
                                                     style="height: 150px; max-width: 100%; object-fit: cover; -o-object-fit: cover"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>

                                <a>
                                    <div class="mask waves-effect waves-light"></div>
                                </a>
                            </div>
                            <div class="card-block">
                                <h5 class="red-text"> ${room.type.type}</h5>
                                <c:choose>
                                    <c:when test="${room.booking != null}">
                                        <h5 class="text-muted text-sm-right" style="float: right; margin-top:-33px">
                                            Booked</h5>
                                    </c:when>
                                    <c:otherwise>
                                        <h5 class="light-green-text text-sm-right"
                                            style="float: right; margin-top:-33px"> Available</h5>
                                    </c:otherwise>
                                </c:choose>

                                <h4 class="card-title">${room.name}</h4>
                                <hr>

                                <span style="float: right; font-size: 18px;">$ ${room.price}</span>

                                <a id="view" data-toggle="modal" data-target="#${room.id}"
                                   class="light-blue-text waves-button"
                                   style="width: 40px; height: 15px; font-size: 12px;">VIEW</a>

                                <c:choose>
                                    <c:when test="${room.booking == null}">
                                        <a id="book" class="light-blue-text waves-button" href="/booking-${room.id}"
                                           style="width: 40px; height: 15px; font-size: 12px;">BOOK</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a id="book" aria-disabled="true" class="text-muted waves-button"
                                           style="width: 40px; height: 15px; font-size: 12px;">BOOK</a>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>
                    </div>
                    <div class="modal hide fade" id="${room.id}" tabindex="-1" role="dialog"
                         aria-labelledby="company-about-label"
                         aria-hidden="true" style="display: none;">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"
                                            style="font-size: 12px;">CLOSE
                                    </button>
                                    <h3 class="modal-title" id="name" style="float: left">${room.name} - <span
                                            class="material-red-text"
                                            style="font-weight: 400">${room.type.type} CLASS</span>
                                    </h3>
                                    <h3 class="modal-title light-blue-text" id="price"
                                        style="float: right; margin-right: 30px; font-weight: 400">$ ${room.price}</h3>
                                </div>

                                <!-- Room Details Dialog -->
                                <div class="modal-body">
                                    <c:choose>
                                        <c:when test="${room.bath == true}">
                                            <p>Bath: Included</p>
                                        </c:when>
                                        <c:otherwise>
                                            <p>Bath: Not Included</p>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${room.internet == true}">
                                            <p>Internet: Provided</p>
                                        </c:when>
                                        <c:otherwise>
                                            <p>Internet: Not Provided</p>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:if test="${room.description != null}">
                                        <p>Extras: ${room.description}</p>
                                    </c:if>
                                    <p>Beds : ${room.bed} and Room for ${room.capacity} people</p>
                                    <div>
                                        <c:if test="${fn:length(room.images) > 0}">
                                            <c:forEach items="${room.images}" var="image">
                                                <img src="${image.url}" class="roomImages"
                                                     style="height: 400px;width: 100%;object-fit: cover; -o-object-fit: cover"/>
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${search}">
                        <h2 class="h2-responsive text-muted">No rooms found.</h2>
                    </c:when>
                    <c:otherwise>
                        <h2 class="h2-responsive text-muted">There are currently no rooms available.</h2>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>