<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head>

    <%@include file="../components/head.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main_page.css">

</head>

<body>

<!--Navbar-->

<%@include file="../components/navbar.jsp" %>

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
    <div class="row text-center">
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
        <div class="col">
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
        <div class="col">
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

<%@include file="../components/footer.jsp" %>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

</body>


</html>