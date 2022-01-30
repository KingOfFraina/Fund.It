<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/general.css">

<nav class="navbar navbar-expand-lg navbar-light navbar-fund-it sticky-top">

    <div class="container-fluid">
        <a class="navbar-brand logo logo-style" href="${pageContext.request.contextPath}" style="color: #00AB98;">Fund.it</a>
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
                    <c:choose>
                        <c:when test="${sessionScope.utente != null}">
                            <a class="nav-link active text-black navbar-text"
                               href="${pageContext.request.contextPath}/GestioneUtenteController/visualizzaDashboard">Profilo</a>
                        </c:when>

                        <c:otherwise>
                            <a class="nav-link active text-black navbar-text"
                               href="${pageContext.request.contextPath}/AutenticazioneController/login">Profilo</a>
                        </c:otherwise>
                    </c:choose>

                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link active text-black navbar-text" href="#">In Evidenza</a>
                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link active text-black navbar-text" href="${pageContext.request.contextPath}/faq/visualizzaFAQ">FAQ</a>
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