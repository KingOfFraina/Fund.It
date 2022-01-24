<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profilo_utente.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Comfortaa:wght@600&display=swap" rel="stylesheet">
    <title>Fund.it</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/piggy-bank-solid.png">
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light navbar-fund-it sticky-top">

    <div class="container-fluid">
        <a class="navbar-brand logo logo-style" href="#" style="color: #00AB98;">Fund.it</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav" style="font-size: 25px">
            <ul class="navbar-nav">

                <li class="nav-item mx-4">
                    <a class="nav-link active text-black navbar-text" aria-current="page" href="#">Chi Siamo</a>
                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link active text-black navbar-text" href="${pageContext.request.contextPath}/Servlet">Profilo</a>
                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link active text-black navbar-text" href="#">In Evidenza</a>
                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link active text-black navbar-text" href="#">Contatti</a>
                </li>
            </ul>

            <c:choose>
                <c:when test="${sessionScope.utente != null}">
                    <a class="login-logout-font"
                       href="${pageContext.request.contextPath}/AutenticazioneController/logout"><i
                            class="fas fa-sign-out-alt login-logout"></i> Logout</a>
                </c:when>
                <c:otherwise>
                    <a class="login-logout-font"
                       href="${pageContext.request.contextPath}/AutenticazioneController/login"><i
                            class="fas fa-sign-in-alt login-logout"></i> Login</a>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</nav>

<div id = "div-info" class = "container text-center my-5">
    <div id = "informazioni">
        <h2>Bentornato ${utente.nome}!</h2>
        <img class = "propic" src="${pageContext.request.contextPath}/img/undraw_profile_pic_ic-5-t.svg" alt=""><br>
        <button id = "visualizza" type="submit" class="btn btn-primary pulsante mt-4 mb-3" onclick="showModifica()">Visualizza informazioni profilo</button>
    </div>


    <div id = "informazioni-modifica" hidden>
        <%@include file="dati_utente.jsp"%>
    </div>

</div>




<div class="row mt-5 text-center">
    <div class = "col div-option">
       <a href = "#"><img class = "profile-option" style="width: 50%" src="${pageContext.request.contextPath}/img/undraw_transfer_money_rywa.svg" alt=""></a>
    </div>

    <div class = "col div-option">
        <a href = "#"><img class = "profile-option" style="width: 45%;" src="${pageContext.request.contextPath}/img/undraw_personalization_re_grty.svg" alt=""></a>
    </div>

</div>

<div class="row mt-3 mb-5 text-center">
    <div class = "col">
        <h3>Le mie donazioni</h3>
    </div>

    <div class = "col">
        <h3>Le mie campagne</h3>
    </div>

</div>

</body>

<!-- Footer -->
<footer class="text-center text-lg-start bg-dark text-muted footer-fundit">

    <div class="container logo text-center">
        <h1>Fund.it</h1>
    </div>
    <!-- Section: Social media -->
    <section class="d-flex justify-content-center justify-content-lg-between p-4 border-bottom">
        <!-- Left -->
        <div class="me-5 d-none d-lg-block">
            <span>Seguici sui nostri social:</span>
        </div>
        <!-- Left -->

        <!-- Right -->
        <div>
            <a href="" class="me-4 text-reset">
                <i class="fab fa-facebook-f"></i>
            </a>
            <a href="" class="me-4 text-reset">
                <i class="fab fa-twitter"></i>
            </a>
            <a href="" class="me-4 text-reset">
                <i class="fab fa-google"></i>
            </a>
            <a href="" class="me-4 text-reset">
                <i class="fab fa-instagram"></i>
            </a>
            <a href="" class="me-4 text-reset">
                <i class="fab fa-linkedin"></i>
            </a>
            <a href="" class="me-4 text-reset">
                <i class="fab fa-github"></i>
            </a>
        </div>
        <!-- Right -->
    </section>
    <!-- Section: Social media -->

    <!-- Section: Links  -->
    <section class="">
        <div class="container text-center text-md-start mt-5">
            <!-- Grid row -->
            <div class="row mt-3">
                <!-- Grid column -->
                <div class="col-md-3 col-lg-4 col-xl-3 mx-auto mb-4">
                    <!-- Content -->
                    <h6 class="text-uppercase fw-bold mb-4">
                        <i class="fas fa-gem me-3"></i>Fund.it
                    </h6>
                    <p>
                        Here you can use rows and columns to organize your footer content. Lorem ipsum
                        dolor sit amet, consectetur adipisicing elit.
                    </p>
                </div>
                <!-- Grid column -->

                <!-- Grid column -->
                <div class="col-md-2 col-lg-2 col-xl-2 mx-auto mb-4">
                    <!-- Links -->
                    <h6 class="text-uppercase fw-bold mb-4">
                        Products
                    </h6>
                    <p>
                        <a href="#!" class="text-reset">Angular</a>
                    </p>
                    <p>
                        <a href="#!" class="text-reset">React</a>
                    </p>
                    <p>
                        <a href="#!" class="text-reset">Vue</a>
                    </p>
                    <p>
                        <a href="#!" class="text-reset">Laravel</a>
                    </p>
                </div>
                <!-- Grid column -->

                <!-- Grid column -->
                <div class="col-md-3 col-lg-2 col-xl-2 mx-auto mb-4">
                    <!-- Links -->
                    <h6 class="text-uppercase fw-bold mb-4">
                        Useful links
                    </h6>
                    <p>
                        <a href="#!" class="text-reset">Pricing</a>
                    </p>
                    <p>
                        <a href="#!" class="text-reset">Settings</a>
                    </p>
                    <p>
                        <a href="#!" class="text-reset">Orders</a>
                    </p>
                    <p>
                        <a href="#!" class="text-reset">Help</a>
                    </p>
                </div>
                <!-- Grid column -->

                <!-- Grid column -->
                <div class="col-md-4 col-lg-3 col-xl-3 mx-auto mb-md-0 mb-4">
                    <!-- Links -->
                    <h6 class="text-uppercase fw-bold mb-4">
                        Contact
                    </h6>
                    <p><i class="fas fa-home me-3"></i> Sarno, 84087, IT</p>
                    <p>
                        <i class="fas fa-envelope me-3"></i>
                        fundit@example.com
                    </p>
                    <p><i class="fas fa-phone me-3"></i> + 01 234 567 88</p>
                    <p><i class="fas fa-print me-3"></i> + 01 234 567 89</p>
                </div>
                <!-- Grid column -->
            </div>
            <!-- Grid row -->
        </div>
    </section>
    <!-- Section: Links  -->

    <!-- Copyright -->
    <div class="text-center p-4" style="background-color: rgba(0, 0, 0, 0.05);">
        © 2022 Copyright:
        <a class="text-reset fw-bold" href="#">Fund.it</a>
    </div>
    <!-- Copyright -->
</footer>
<!-- Footer -->

<script type="text/javascript" src=${pageContext.request.contextPath}/js/form-validation.js></script>
<script type="text/javascript" src=${pageContext.request.contextPath}/js/registrazione.js></script>
<script type="text/javascript" src=${pageContext.request.contextPath}/js/profilo_utente.js></script>

</html>
