<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>HYATT</title>

    <jsp:include page="includes/head.jsp"/>

    <style>
        img {
            width: auto;
            height: auto;
            max-width: 100%;
        }
    </style>
</head>
<body class="background">
<jsp:include page="includes/header.jsp"/>

<div id="display_carousel" class="carousel slide carousel-fade display_carousel" data-ride="carousel">
    <ol class="carousel-indicators">
        <li data-target="#display_carousel" data-slide-to="0" class=""></li>
        <li data-target="#display_carousel" data-slide-to="1" class=""></li>
        <li data-target="#display_carousel" data-slide-to="2" class="active"></li>
    </ol>

    <div class="carousel-inner" role="listbox">
        <div class="carousel-item">
            <div id="display_image1" class="flex-center animated">
                <img src="https://res.cloudinary.com/hte2zx5qx/image/upload/display1.jpg">
            </div>
        </div>

        <div class="carousel-item">
            <div id="display_image2" class="flex-center animated">
                <img src="https://res.cloudinary.com/hte2zx5qx/image/upload/display2.jpg">
            </div>
        </div>

        <div class="carousel-item active">
            <div id="display_image3" class="flex-center animated">
                <img src="https://res.cloudinary.com/hte2zx5qx/image/upload/display3.jpg">
            </div>
        </div>
    </div>

    <a class="left carousel-control" href="#display_carousel" role="button" data-slide="prev">
        <span class="icon-prev" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="right carousel-control" href="#display_carousel" role="button" data-slide="next">
        <span class="icon-next" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>

<section>
    <div class="center-div-horizontal" style="margin-bottom: 30px; margin-top: 30px">
        <div class="col-md-4">
            <button id="services_button" type="button" onclick="window.location='#services'"
                    class="waves-button col-lg-3"
                    style="width: 200px; height: 50px; font-weight: 500">OUR SERVICES
            </button>
        </div>

        <div class="col-md-4">
            <button id="add3room_button" type="button" onclick="window.location='/booking'"
                    class="btn-danger waves-button waves-light material-red"
                    style="width: 200px; height: 50px; color: white; font-weight: 400">BOOK ROOM
            </button>
        </div>

        <form action="/rooms/search" method="post" class="col-md-4">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button id="searchtext" name="searchtext" type="submit" value="Available" class="waves-button"
                    style="width: 200px; height: 50px; font-weight: 500">AVAILABLE ROOMS
            </button>
        </form>

    </div>
    <hr class="container">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <body>
    <div id="room_types" class="container">
        <h1 class="h1-responsive" style="margin-bottom: 20px">Room Types
            <small class="text-muted">Pricing</small>
        </h1>
        <hr>

        <div class="row">
            <div class="col-md-4">
                <div class="card">
                    <div class="view overlay hm-white-slight">
                        <img src="https://res.cloudinary.com/hte2zx5qx/image/upload/room1.jpg" class="img-fluid" alt="">
                    </div>

                    <div class="card-block">
                        <h4 class="card-title">Family Rooms</h4>

                        <p class="card-text">Room perfect for family vacations which includes entertainment facilities
                            like plasma TV, C.D, Stereo and DVD. These rooms provides all necessities for family.</p>

                        <div class="read-more">
                            <table>
                                <tr>
                                    <form action="/rooms/search" method="post" class="btn btn-primary">
                                        <td><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <button id="searchtext" name="searchtext" type="submit" value="Family"
                                                    class="btn btn-primary waves-button waves-light">Available Rooms
                                            </button>
                                        </td>
                                    </form>
                                </tr>
                            </table>

                            <span class="text-lg-left" style="float: right; margin-top: -50px">
                                <p id="display_room1_price"
                                   style="font-size: 30px; color: dodgerblue">$${familybase} +</p>
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card">
                    <div class="view overlay hm-white-slight">
                        <img src="https://res.cloudinary.com/hte2zx5qx/image/upload/room2.jpg" class="img-fluid" alt="">
                    </div>

                    <div class="card-block">
                        <h4 class="card-title">Deluxe Rooms</h4>

                        <p class="card-text">Hotel provides the entire world class premium services like plasma TV, C.D,
                            Stereo & DVD,work desks, phone and much more.</p>

                        <div class="read-more">
                            <table>
                                <tr>
                                    <form action="/rooms/search" method="post" class="btn btn-primary">
                                        <td><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <button id="searchtext" name="searchtext" type="submit" value="Deluxe"
                                                    class="btn btn-primary waves-button waves-light">Available Rooms
                                            </button>
                                        </td>
                                    </form>
                                </tr>
                            </table>

                            <span class="text-lg-left" style="float: right; margin-top: -50px">
                                <p id="display_room2_price"
                                   style="font-size: 30px; color: dodgerblue">$${deluxebase} +</p>
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card">
                    <div class="view overlay hm-white-slight">
                        <img src="https://res.cloudinary.com/hte2zx5qx/image/upload/room3.jpg" class="img-fluid" alt="">
                    </div>

                    <div class="card-block">
                        <h4 class="card-title">Executive Rooms</h4>

                        <p class="card-text"> This include a range of luxurious rooms suitable for
                            its discerning visitors. The different kinds of rooms and suits include the Business suits
                            and Crown Suits.</p>

                        <div class="read-more">
                            <table>
                                <tr>
                                    <form action="/rooms/search" method="post" class="btn btn-primary">
                                        <td><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <button id="searchtext" name="searchtext" type="submit" value="Executive"
                                                    class="btn btn-primary waves-button waves-light">Available Rooms
                                            </button>
                                        </td>
                                    </form>
                                </tr>
                            </table>

                            <span class="text-lg-left" style="float: right; margin-top: -50px">
                                <p id="display_room3_price" style="font-size: 30px; color: dodgerblue">$${executivebase} +</p>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
    </html>

    <hr class="container">
    <jsp:include page="services.jsp"/>

</section>
</body>
</html>
