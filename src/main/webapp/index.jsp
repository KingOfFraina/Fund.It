<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=0">


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <link rel="stylesheet" href="./css/index.css">
    <link rel="stylesheet" href="./css/general.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

    <link href="https://fonts.googleapis.com/css2?family=Comfortaa:wght@600&display=swap" rel="stylesheet">

    <title>Fund.it</title>
</head>
<body>

<!--Navbar-->

<!--Navbar-->

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
                    <a class="nav-link active text-black navbar-text" href="#">Profilo</a>
                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link active text-black navbar-text" href="#">In Evidenza</a>
                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link active text-black navbar-text" href="#">Contatti</a>
                </li>
            </ul>

            <a class="login-logout-font" href="${pageContext.request.contextPath}/AutenticazioneController/login"><i class="fas fa-sign-in-alt login-logout"></i> Login</a>

        </div>
    </div>
</nav>

<!-- div testo e presentazione-->

<div class="container my-5 text-center">
    <h1 id="paragraph" class="text-black">
        Raccolte fondi e
        finanziamenti per
        sostenere gli sforzi di
        persone
        e organizzazioni.
    </h1>

</div>

<!-- Div immagine e pulsante-->

<div id="container" class="container my-5 mx-3">

    <img src="./img/group_selfie.svg" alt="">
    <button id="scopri" type="submit" class="btn btn-primary btn-lg mx-5 my-5"
            onclick="location.href = '${pageContext.request.contextPath}/AutenticazioneController/login'">
        Scopri di pi&ugrave
    </button>
</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

</body>
</html>