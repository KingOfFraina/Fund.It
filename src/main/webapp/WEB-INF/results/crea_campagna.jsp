<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login-registrazione.css">
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

<!--Definizione colonne-->
<div class="container">
    <div class="row">
        <div class="col-6">
            <div id="form-div" class="container my-5 div-login">
                <div class="row mb-3" style="width: fit-content; margin-left: 10px">
                    <button id="back" class="btn btn-primary mt-4 mb-3"
                            onclick="location.href = '${pageContext.request.contextPath}/AutenticazioneController/login'">
                        <i class="fas fa-arrow-left"></i></button>
                </div>

                <form id="form" class="needs-validation" novalidate method="post"
                      action="${pageContext.request.contextPath}/AutenticazioneController/registrazione">

                    <!--Titolo della campagna-->
                    <div class="col">
                        <label for="inputTitolo" class="form-label">Titolo</label>
                        <input name="titolo" type="text" class="form-control" id="inputTitolo" placeholder="Dai un titolo alla tua campagna!" required>
                        <div class=invalid-feedback>
                            Formato nome non corretto
                        </div>
                    </div>

                </form>
            </div>
        </div>
        <div class="col-6 mt-5">
            <img src="${pageContext.request.contextPath}/img/undraw_virtual_assistant_jjo2.svg" alt="" style="width: 110%; position: sticky; top:15px">
        </div>
    </div>
</div>


<script type="text/javascript" src=${pageContext.request.contextPath}/js/form-validation.js></script>
<script type="text/javascript" src=${pageContext.request.contextPath}/js/registrazione.js></script>

</body>
</html>
