<html>

<head>
    <%@include file="../components/main_page/head.jsp" %>
    <title>Fund.it</title>
</head>

<body>

<%@include file="../components/navbar.jsp" %>
<button type="button" id="sidebarCollapse" class="btn btn-info" onclick="showCategories()"
        style="margin-top: 10px; margin-left: 10px">
    <i class="fas fa-align-left"></i>
    <span>Mostra Categorie</span>
</button>

<div class="wrapper">
    <!-- Sidebar -->
    <%@include file="../components/main_page/sidebar_categorie.jsp" %>

    <!-- Page Content -->


    <div class=" container text-center mt-5">


        <h1>Trova una campagna da finanziare o creane una!</h1>

        <div style="display: block">
            <!--Searchbar-->
            <div class="navbar-text center mt-4">
                <form class="form-inline mx-2 my-2" action="${pageContext.request.contextPath}/GestioneCampagnaController/ricerca">
                    <input class="form-control mr-sm-2 mx-3" id="searchbar" type="search" name = "searchText"
                           placeholder="Cerca in Fund.it">
                </form>
            </div>
        </div>


        <!--Bottone-->
        <div class="text-center" style="margin-top: 70px; margin-bottom: 150px;">
            <button id="crea" type="button" class="" onclick="location.href = '${pageContext.request.contextPath}/GestioneCampagnaController/creaCampagna'">Crea una campagna</button>
        </div>


        <div style="display: block">
            <!--Campagne in evidenza-->
            <div class="container tmx-2" style="margin-top: 70px;">
                <h2 class="text-black my-5">
                    Campagne in evidenza
                </h2>
            </div>


            <div class="container my-2"
                 style="background-image: url(${pageContext.request.contextPath}/img/undraw_before_dawn_re_hp4m.svg); padding-bottom: 355px; background-repeat: no-repeat;">
                <div class="row text-center my-4">



                            <c:forEach items="${applicationScope.campagneList}" var = "campagna" begin="0" end="7">
                                <div class="col-sm my-4">
                                    <div class="card" style="width: 18rem;">

                                        <img class="card-img-top"
                                             src="${pageContext.request.contextPath}/file/${campagna.immagini.get(0).path}"
                                             alt="Card image cap">
                                        <div class="card-body">
                                            <h5 class="card-title">${campagna.titolo}</h5>
                                            <a href="${pageContext.request.contextPath}/GestioneCampagnaController/campagna?idCampagna=${campagna.idCampagna}" class="btn btn-primary mt-3"
                                               style="background-color: #00AB98; border-color: #00AB98;">Vai alla campagna</a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>




                </div>
            </div>
        </div>

    </div>


</div>


<%@include file="../components/footer.jsp" %>

<%@include file="../components/main_page/script_imports.jsp" %>
</body>

</html>