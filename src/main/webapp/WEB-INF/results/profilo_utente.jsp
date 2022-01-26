<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <%@include file="../components/head.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profilo_utente.css">
</head>
<body>

<!--Navbar-->
<%@include file="../components/navbar.jsp" %>
<div id="div-info" class="container text-center my-5">
    <div id="informazioni">
        <h2>Bentornato ${utente.nome}!</h2>
        <img class="propic" src="${pageContext.request.contextPath}/img/undraw_profile_pic_ic-5-t.svg" alt=""><br>
        <button id="visualizza" type="submit" class="btn btn-primary pulsante mt-4 mb-3" onclick="showModifica()">
            Visualizza informazioni profilo
        </button>
    </div>

    <div class="row mb-3" style="width: fit-content; margin-left: 10px;">
        <button id="backII" class="btn btn-primary mt-4 mb-3 pulsante"
                onclick="hideModifica()" style="display: none">
            <i class="fas fa-arrow-left"></i></button>
    </div>

    <form id="form" class="needs-validation" novalidate method="post"
          action="${pageContext.request.contextPath}/GestioneUtenteController/modificaProfilo">

        <div id="informazioni-modifica" style="display: none">
            <%@include file="../components/dati_utente.jsp" %>
            <button type="submit" class="btn btn-primary pulsante mt-4 mb-3"
                    style="background-color: #00AB98; border-color: #00AB98;">Modifica
            </button>
        </div>



    </form>

</div>

<div class="img-table">
    <div id="div-select-images">
        <%@include file="../components/user-option.jsp" %>
    </div>

    <div id="div-donation-table" style="display: none">
        <%@include file="../components/table_user_donations.jsp" %>
    </div>


</div>

<%@include file="../components/footer.jsp" %>
</body>


<script type="text/javascript" src=${pageContext.request.contextPath}/js/form-validation.js></script>
<script type="text/javascript" src=${pageContext.request.contextPath}/js/registrazione.js></script>
<script type="text/javascript" src=${pageContext.request.contextPath}/js/profilo_utente.js></script>

</html>
