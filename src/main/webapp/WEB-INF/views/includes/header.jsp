<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header style="margin-bottom: 60px">
    <nav class="navbar navbar-dark white navbar-fixed-top scrolling-navbar"
         role="navigation">
        <div class="container-fluid" style="">
            <div class="navbar-header">
                <a class="navbar-brand" href="<c:url value="/" />" style="margin-top: -12px">
                    <span class="light-blue-text" style="font-weight: 800;font-size: 38px">HYATT</span></a>
            </div>

            <div id="navigation">
                <ul class="nav navbar-nav" style="float: right;">
                    <li class="nav-item">
                        <a class="nav-link waves-effect waves-light text-muted"
                           href="<c:url value="/booking" />">BOOK ROOM</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link waves-effect waves-light text-muted"
                           href="<c:url value="/rooms" />">ROOMS</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link waves-effect waves-light text-muted"
                           href="/#services">SERVICES</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link waves-effect waves-light text-muted"
                           href="<c:url value="/about" />">ABOUT</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link waves-effect waves-light text-muted"
                           href="<c:url value="/contact" />">CONTACT</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link waves-effect waves-light text-muted"
                           href="<c:url value="/privacy" />">PRIVACY</a>
                    </li>

                    <sec:authorize access="hasRole('ADMIN')">
                        <li class="nav-item">
                            <a href="<c:url value="/admin" />">
                                <button id="admin" type="button"
                                        class="btn-danger waves-button waves-light material-red"
                                        style="font-weight: 500; font-size: 15px;">ADMIN PANEL
                                </button>
                            </a>
                        </li>
                    </sec:authorize>

                    <sec:authorize access="hasRole('MANAGER')">
                        <li class="nav-item">
                            <a href="<c:url value="/manage" />">
                                <button id="manager" type="button"
                                        class="btn-danger waves-button waves-light material-red"
                                        style="font-weight: 600; font-size: 15px;">MANAGE
                                </button>
                            </a>
                        </li>
                    </sec:authorize>

                    <sec:authorize access="isAnonymous()">
                        <li class="nav-item">
                            <a href="<c:url value="/login" />">
                                <button id="login" type="button" class="btn-primary waves-button light-blue"
                                        style="width: 120px; font-weight: 500; font-size: 15px">LOGIN
                                </button>
                            </a>
                        </li>
                    </sec:authorize>

                    <sec:authorize access="isAuthenticated()">
                        <li class="nav-item dropdown"
                            style="height: 40px; width: 40px; margin-top: -28.5px; margin-left: 30px;">
                            <a id="dropdownMenu1" data-toggle="dropdown">
                                <img src="<c:url value='/static/images/default_picture.png' /> "
                                     class="img-fluid img-circle">
                            </a>

                            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu1"
                                 data-dropdown-in="fadeIn" data-dropdown-out="fadeOut">
                                <a id="account" class="dropdown-item waves-effect waves-light"
                                   href="<c:url value="/user/profile" />">Profile</a>
                                <a id="logout" class="dropdown-item waves-effect waves-light"
                                   href="<c:url value="/logout" />">Logout</a>
                            </div>
                        </li>
                    </sec:authorize>
                </ul>
            </div>
        </div>
    </nav>
</header>
