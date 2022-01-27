<html>

<head>
    <%@include file="../components/main_page/head.jsp" %>
    <title>Fund.it</title>
</head>

<body>

<%@include file="../components/navbar.jsp" %>
<button type="button" id="sidebarCollapse" class="btn btn-info" onclick="showCategories()" style="margin-top: 10px; margin-left: 10px">
    <i class="fas fa-align-left"></i>
    <span>Mostra Categorie</span>
</button>

<div class="wrapper">
    <!-- Sidebar -->
    <%@include file="../components/main_page/sidebar.jsp" %>

    <!-- Page Content -->


    <div class=" container text-center mt-5">



        <h1>Trova una campagna da finanziare o creane una!</h1>

        <div style="display: block">
            <!--Searchbar-->
            <div class="navbar-text center mt-4">
                <form class="form-inline mx-2 my-2" action="##">
                    <input class="form-control mr-sm-2 mx-3" id = "searchbar" type="search" placeholder="Cerca in Fund.it">
                </form>
            </div>
        </div>


        <!--Bottone-->
        <div class="text-center" style="margin-top: 70px; margin-bottom: 150px;">
            <button id="crea" type="button" class="">Crea una campagna</button>
        </div>


        <div style="display: block">
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
                            <img class="card-img-top"
                                 src="./img/thumb2-santa-chiara-4k-church-archeological-museum-naples.jpg"
                                 alt="Card image cap">
                            <div class="card-body">
                                <h5 class="card-title">Contribuisci alla ristrutturazione del nostro museo!</h5>
                                <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
                                    eiusmod
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
                                <p class="card-text"> Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
                                    eiusmod
                                    tempor incididunt ut labore et dolore magna aliqua.</p>
                                <a href="#" class="btn btn-primary"
                                   style="background-color: #00AB98; border-color: #00AB98;">Vai alla Campagna</a>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="card" style="width: 18rem;">
                            <img class="card-img-top"
                                 src="./img/2560x1600_ash-black-cat-kitten-with-stare-look-4k-cat.jpg"
                                 alt="Card image cap">
                            <div class="card-body">
                                <h5 class="card-title">Aiuta Felix a cercare i fondi per un nuovo intervento</h5>
                                <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
                                    eiusmod
                                    tempor incididunt ut labore et dolore magna aliqua.</p>
                                <a href="#" class="btn btn-primary"
                                   style="background-color: #00AB98; border-color: #00AB98;">Vai alla campagna</a>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

    </div>


</div>


<%@include file="../components/footer.jsp" %>

<%@include file="../components/main_page/script_imports.jsp" %>
</body>

</html>