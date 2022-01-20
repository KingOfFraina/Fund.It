<%--
  User: Francesco
  Date: 19/01/2022
  Time: 21:53
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <link href="https://fonts.googleapis.com/css2?family=Comfortaa:wght@600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main_page.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

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

<!--slogan-->
<div class="container text-center slogan">
    <h1 class="text-black">
        Trova una campagna da finanziare o creane una!
    </h1>

</div>

<div class="container wrapper">

    <!--Searchbar-->
    <div class="navbar-text center">
        <form class="form-inline mx-2 my-2" action="##">
            <input class="form-control mr-sm-2 mx-3 searchbar" type="search" placeholder="Cerca in Fund.it">
        </form>
    </div>
</div>

<div class="container central-container">

    <div class="container text-center" style="margin-top: 70px;">
        <h2 class="text-black">
            Filtra per categoria
        </h2>

    </div>


    <!--Categorie-->
    <div class="container wrapper" style="margin-top: 120px;">
        <div class="container">

            <div class="row">
                <div class="col">
                    <a href="#"><i class="far fa-hospital categoria"></i></a>
                </div>
                <div class="col">
                    <a href="#"><i class="far fa-lightbulb categoria"></i></a>
                </div>
                <div class="col">
                    <a href="#"><i class="fas fa-hands-helping categoria"></i></a>
                </div>
            </div>

            <div class="row targa">
                <div class="col">
                    <a>Spese Mediche</a>
                </div>
                <div class="col">
                    <a>Creatività</a>
                </div>
                <div class="col">
                    <a>Non-Profit</a>
                </div>
            </div>

            <div class="row" style="margin-top: 120px;">
                <div class="col">
                    <a href="#"><i class="far fa-calendar-alt categoria"></i></a>
                </div>
                <div class="col">
                    <a href="#"><i class="fas fa-paw categoria"></i></a>
                </div>
                <div class="col">
                    <a href="#"><i class="fas fa-leaf categoria"></i></a>
                </div>
            </div>

            <div class="row targa">
                <div class="col">
                    <a>Eventi</a>
                </div>
                <div class="col">
                    <a>Animali</a>
                </div>
                <div class="col">
                    <a>Ambiente</a>
                </div>
            </div>

        </div>
    </div>
</div>

<!--Bottone-->
<div class="container wrapper" style="margin-top: 100px; margin-bottom: 150px;">
    <button id="crea" type="button" class="btn btn-primary btn-lg my-3">Crea una campagna</button>
</div>

<!--Campagne in evidenza-->
<div class="container tmx-2" style="margin-top: 70px;">
    <h2 class="text-black my-5">
        Campagne in evidenza
    </h2>
</div>

<!--TODO: rendere dinamico-->

<div class="container my-2"
     style="background-image: url(./img/undraw_before_dawn_re_hp4m.svg); padding-bottom: 355px; background-repeat: no-repeat;">
    <div class="row">
        <div class="col-sm">
            <div class="card" style="width: 18rem;">
                <img class="card-img-top" src="./img/thumb2-santa-chiara-4k-church-archeological-museum-naples.jpg"
                     alt="Card image cap">
                <div class="card-body">
                    <h5 class="card-title">Contribuisci alla ristrutturazione del nostro museo!</h5>
                    <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
                        tempor incididunt ut labore et dolore magna aliqua.</p>
                    <a href="#" class="btn btn-primary"
                       style="background-color: #00AB98; border-color: #00AB98;">Vai alla campagna</a>
                </div>
            </div>
        </div>
        <div class="col-sm">
            <div class="card" style="width: 18rem;">
                <img class="card-img-top" src="./img/team.jpg" alt="Card image cap">
                <div class="card-body">
                    <h5 class="card-title">Finanzia un nuovo progetto Open-Source</h5>
                    <p class="card-text"> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
                        tempor incididunt ut labore et dolore magna aliqua.</p>
                    <a href="#" class="btn btn-primary"
                       style="background-color: #00AB98; border-color: #00AB98;">Vai alla Campagna</a>
                </div>
            </div>
        </div>
        <div class="col-sm">
            <div class="card" style="width: 18rem;">
                <img class="card-img-top" src="./img/2560x1600_ash-black-cat-kitten-with-stare-look-4k-cat.jpg"
                     alt="Card image cap">
                <div class="card-body">
                    <h5 class="card-title">Aiuta Felix a cercare i fondi per un nuovo intervento</h5>
                    <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
                        tempor incididunt ut labore et dolore magna aliqua.</p>
                    <a href="#" class="btn btn-primary"
                       style="background-color: #00AB98; border-color: #00AB98;">Vai alla campagna</a>
                </div>
            </div>
        </div>

    </div>
</div>


<!-- Footer -->
<footer class="text-center text-lg-start bg-dark text-muted" style="margin-top: 0px;">
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


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

</body>


</html>