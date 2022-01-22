<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/campagna.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Comfortaa:wght@600&display=swap" rel="stylesheet">
    <link rel="icon" href="${pageContext.request.contextPath}/img/piggy-bank-solid.png">
    <title>Fund.it</title>
</head>
<body>
<!--Navbar-->
<nav class="navbar navbar-expand-lg navbar-light navbar-fund-it">

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
                    <a class="nav-link active text-black navbar-text" href="#">Profilo</a>
                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link active text-black navbar-text" href="#">In Evidenza</a>
                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link active text-black navbar-text" href="#">Contatti</a>
                </li>
            </ul>

            <a class="login-logout-font" href=#><i class="fas fa-sign-in-alt login-logout"></i> Login</a>

        </div>
    </div>
</nav>

<!--Titolo-->
<div class="container my-5">
    <h1 class="text-black text-center"> Aiutaci a finanziare la nostra start-up</h1>
</div>

<!--Definizione colonne-->
<div class="container">
    <div class="row">
        <div class="col-7">
            <!--Carosello-->
            <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img src="${pageContext.request.contextPath}/img/41341476-teamwork-team-together-collaboration-meeting-brainstorming-ideas-concept.jpg"
                             class="d-block w-100" alt="...">
                    </div>
                    <div class="carousel-item">
                        <img src="${pageContext.request.contextPath}/img/team.jpg" class="d-block w-100" alt="...">
                    </div>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls"
                        data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls"
                        data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
            <!--Fine Carosello-->

            <!--Descrizione-->
            <div class="container div-campagna-info2">
                <h4>Creata il 20/01/2022</h4>
                <hr class="solid text-black">
                <p>
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla congue neque quis lobortis hendrerit.
                    Integer non blandit nulla, in molestie sem. Praesent luctus mollis maximus. Suspendisse eu est enim.
                    Ut et massa et libero condimentum posuere. Quisque quis nunc quis lorem sollicitudin porttitor.
                    Aenean hendrerit, lectus ullamcorper mattis vehicula, ligula enim blandit lorem, ut maximus eros
                    ipsum id nisi. In tristique libero ex, a eleifend sem sodales sit amet. Maecenas viverra leo sed
                    viverra tempor. Mauris faucibus placerat ante non viverra. Sed vulputate ut ligula id faucibus.

                    Sed eu accumsan nunc. Duis posuere massa eu ipsum faucibus, at gravida lacus sodales. Praesent id
                    diam ornare, dignissim quam id, malesuada nibh. Donec faucibus massa est, vehicula fermentum ipsum
                    posuere et. In hac habitasse platea dictumst. Nam diam enim, rutrum pharetra justo ut, rhoncus
                    dignissim lectus. Vivamus pretium, odio a malesuada mattis, quam leo convallis metus, eget placerat
                    nibh tortor ut mauris. Suspendisse quis imperdiet nisl, vitae maximus eros. Praesent rhoncus
                    interdum volutpat.
                </p>

            </div>


            <!--Organizzatore-->
            <div class="container my-4 text-center">
                <h3>Organizzatore</h3>
                <hr class="solid text-black">
                <a class="text-black" style="text-decoration: none; font-size: 23px"><i class="fas fa-user-alt"
                                                                                        style="font-size: 23px; color: #00AB98"></i>
                    Mario Rossi</a><br>
                <a class="text-black" style="text-decoration: none; font-size: 19px"> Organizzatore</a><br>
                <a class="text-black" style="text-decoration: none; font-size: 19px"> Milano, Italia</a>
            </div>

            <!--Commenti-->
            <div class="container my-4">
                <h3>Commenti(3)</h3>
                <div class="container commento">
                    <hr class="solid text-black">
                    <h4>Marco ha donato 50€</h4>
                    <h5>Complimenti per l'idea, non vedo l'ora di vedere il vostro progetto finito!</h5>
                    <hr class="solid text-black">
                </div>

                <div class="container commento">
                    <hr class="solid text-black">
                    <h4>Anonimo ha donato 30€</h4>
                    <h5>Grande progetto! Spero che raggiungiate il vostro obiettivo.</h5>
                    <hr class="solid text-black">
                </div>

                <div class="container commento">
                    <hr class="solid text-black">
                    <h4>Marta ha donato 500€</h4>
                    <h5>Bravissimi!</h5>
                    <hr class="solid text-black">
                </div>

                <!--Segnalazione-->
                <div class="container" style="margin-top: 120px">
                    <a style="color: black; font-size: 20px" href="#"><i class="fas fa-flag"></i> Segnala la raccolta
                        fondi</a>
                    <hr class="solid text-black">
                </div>

            </div>
        </div>

        <div class="col-4 div-campagna-info">
            <a class="text-center goal">13.456 raccolti su 20.000</a>

            <div class="progress" style="border-color: black; border-style: solid; border-width: 1px">
                <div class="progress-bar" role="progressbar" style="width: 67%; background-color: #00AB98"
                     aria-valuenow="67" aria-valuemin="0" aria-valuemax="100"></div>
            </div>

            <div class="d-grid gap-2 my-3">
                <button class="btn btn-primary pulsante" type="button">Condividi</button>
                <button class="btn btn-primary pulsante" type="button">Fai una Donazione</button>
            </div>

            <a style="font-size: 20px"><i class="fas fa-chart-line"></i> 10 persone hanno fatto una donazione a questa
                campagna</a><br>

            <div class="container my-4">
                <hr class="solid text-black">
            </div>

            <!--Ultime donazioni-->
            <div class="container text-center" style="margin-top: 30px">
                <!--TODO-->
                <h6>Anonimo ha donato 500€ <span class="badge bg-white" style="color: #00AB98;">Migliore</span></h6>
                <h6>Giovanni ha donato 10€ <span class="badge bg-white" style="color: #00AB98;">Nuovo</span></h6>
                <h6>Anonimo ha donato 20€ <span class="badge bg-white" style="color: #00AB98;">Nuovo</span></h6>
                <h6>Marco ha donato 10€ <span class="badge bg-white" style="color: #00AB98;">Nuovo</span></h6>
            </div>

            <!--Visualizza donazioni-->
            <div class="d-grid gap-2 d-md-block text-center my-5">
                <button class="btn btn-primary pulsante" type="button">Mostra tutto</button>
                <button class="btn btn-primary pulsante" type="button">Vedi le Migliori <i class="far fa-star"></i>
                </button>
            </div>

        </div>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

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
</html>
