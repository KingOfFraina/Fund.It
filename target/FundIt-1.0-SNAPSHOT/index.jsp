<!doctype html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

    <link rel="stylesheet" href = "./css/index.css">

    <link href="https://fonts.googleapis.com/css2?family=Comfortaa:wght@600&display=swap" rel="stylesheet">

    <title>Fund.it</title>
</head>
<body style="background-color: black; text-align: center">

<!--Navbar-->

<nav class="navbar navbar-expand-lg navbar-dark bg-black">

    <div class="container-fluid">
        <a class="navbar-brand logo" href="${pageContext.request.contextPath}/Servlet" style="color: #00AB98;">Fund.it</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav" style="font-size: 25px">
            <ul class="navbar-nav">

                <li class="nav-item mx-4">
                    <a class="nav-link active" aria-current="page" href="#">Chi Siamo</a>
                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link active" href="#">Profilo</a>
                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link active" href="#">In Evidenza</a>
                </li>
                <li class="nav-item mx-4">
                    <a class="nav-link active" href="#">Contatti</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class = "container" style="text-align: center;">
<!-- div testo e presentazione-->

<div class="container my-5 text-center">
    <h1 class ="white-text">
        Raccolte fondi e
        finanziamenti per
        sostenere gli sforzi di
        persone
        e organizzazioni.
    </h1>

</div>

<!-- Div immagine e pulsante-->

<div id = "container" class="container my-5 mx-3">
>
    <img src="./img/group_selfie.svg" alt="">
    <button id = "scopri" type="submit" class="btn btn-primary btn-lg mx-5 my-5 text-center" onclick="location.href = '#'">
        Scopri di pi&ugrave
    </button>
</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

</body>
</html>