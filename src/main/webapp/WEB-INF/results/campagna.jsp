<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../components/head.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/campagna.css">
</head>
<body  onload="percentage(${campagna.sommaTarget}, ${campagna.sommaRaccolta})">

<div>

    <!--Navbar-->
    <%@include file="../components/navbar.jsp" %>

    <!--Titolo-->
    <div class="container my-5">
        <h1 class="text-black text-center"> ${campagna.titolo}</h1>
    </div>
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
                <h4>Descrizione</h4>
                <hr class="solid text-black">
                <p>
                    ${campagna.descrizione}
                </p>

            </div>


            <!--Organizzatore-->
            <div class="container my-4 text-center">
                <h3>Organizzatore</h3>
                <hr class="solid text-black">
                <a class="text-black" style="text-decoration: none; font-size: 23px">
                    <i class="fas fa-user-alt" style="font-size: 23px; color: #00AB98"></i>
                    ${campagna.utente.nome} ${campagna.utente.cognome}</a><br>
                <a class="text-black" style="text-decoration: none; font-size: 19px"> Organizzatore</a><br>
                <a class="text-black" style="text-decoration: none; font-size: 19px"> ${campagna.utente.citta},
                    Italia</a>
            </div>

            <!--Commenti-->
            <div class="container my-4">
                <h3>Commenti</h3>

                <c:forEach items="${campagna.donazioni}" var="don">
                    <c:if test="${don.commento != null}">
                        <div class="container commento">
                            <hr class="solid text-black">
                            <h4>${don.utente.nome} ${don.utente.cognome} ha donato  <fmt:formatNumber type="number" maxFractionDigits="2" value="${don.sommaDonata}"/>&euro;</h4>
                            <h5>"${don.commento}"</h5>
                            <hr class="solid text-black">
                        </div>
                    </c:if>
                </c:forEach>

                <!--Segnalazione-->
                <div class="container" style="margin-top: 120px">
                    <a data-bs-toggle="modal" data-bs-target="#modalSegnalazioni" style="color: black; font-size: 20px"><i class="fas fa-flag"></i> Segnala la raccolta
                        fondi</a>
                    <hr class="solid text-black">
                </div>

            </div>
        </div>


        <div class="col-4 div-campagna-info" style="top:15px">
            <a class="text-center goal"><fmt:formatNumber type="number" maxFractionDigits="2"
                                                          value="${campagna.sommaRaccolta}"/>&euro; raccolti su
                <fmt:formatNumber type="number" maxFractionDigits="2" value="${campagna.sommaTarget}"/>&euro;</a>

            <div class="progress" style="border-color: black; border-style: solid; border-width: 1px">
                <div id="progressbar" class="progress-bar" role="progressbar" style=" background-color: #00AB98"
                     aria-valuenow="67" aria-valuemin="0" aria-valuemax="100"></div>
            </div>

            <div class="d-grid gap-2 my-3">
                <button class="btn btn-primary pulsante" type="button"
                        style=" background-color: #00AB98; border-color: #00AB98">Condividi
                </button>
                <c:choose>
                    <c:when test="${sessionScope.utente != null}">
                        <a class="btn btn-primary pulsante" type="button" href="http://localhost:8080/FundPay-1.0-SNAPSHOT/fundPay/paga?idCampagna=${campagna.idCampagna}&idCliente=${sessionScope.utente.idUtente}"
                           style=" background-color: #00AB98; border-color: #00AB98" >Fai una Donazione
                        </a>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-primary pulsante" type="button" onclick="alert('Effettua prima il login!')"
                                style=" background-color: #00AB98; border-color: #00AB98" >Fai una Donazione
                        </button>
                    </c:otherwise>
                </c:choose>
            </div>

            <a id="donation-count" style="font-size: 20px" )><i
                    class="fas fa-chart-line"></i> ${fn:length(campagna.donazioni)} persone hanno donato a questa
                campagna</a><br>

            <div class="container my-4">
                <hr class="solid text-black">
            </div>

            <!--Ultime donazioni-->
            <div class="container text-center" style="margin-top: 30px">

                <c:choose>
                    <c:when test="${(campagna.donazioni.size()-3) >= 0}">
                        <c:forEach items="${campagna.donazioni}" begin = "${campagna.donazioni.size()-3}" var="don">
                            <h6>${don.utente.nome} ha donato ${don.sommaDonata}&euro; <span class="badge bg-white" style="color: #00AB98;">Nuovo</span></h6>
                        </c:forEach>
                    </c:when>

                    <c:otherwise>
                        <c:forEach items="${campagna.donazioni}" var="don">
                            <h6>${don.utente.nome} ha donato ${don.sommaDonata}&euro; <span class="badge bg-white" style="color: #00AB98;">Nuovo</span></h6>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>



            </div>


            <!--Visualizza donazioni-->
            <div class="d-grid gap-2 d-md-block text-center my-5">
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal"
                        style=" background-color: #00AB98; border-color: #00AB98">Mostra tutto
                </button>
                <button class="btn btn-primary pulsante" type="button"
                        style=" background-color: #00AB98; border-color: #00AB98">Vedi le Migliori <i
                        class="far fa-star"></i>
                </button>
            </div>

        </div>

    </div>
</div>

<%@include file="../components/footer.jsp" %>

<%@include file="../components/modal_donazioni.jsp" %>
<%@include file="../components/modal_segnalazioni.jsp" %>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>

<script type="text/javascript" src=${pageContext.request.contextPath}/js/campagna.js></script>

</body>

</html>
