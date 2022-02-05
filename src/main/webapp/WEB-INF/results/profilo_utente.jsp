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
        <c:choose>
            <c:when test="${sessionScope.utente.fotoProfilo.blank}">
                <img class="propic" src="${pageContext.request.contextPath}/img/undraw_profile_pic_ic-5-t.svg" alt="">
            </c:when>
            <c:otherwise>
                <img class="propic" src="${pageContext.request.contextPath}/file/${sessionScope.utente.fotoProfilo}"
                     alt="" style=" width:130px;
  height:130px;
  object-fit:cover;
  border-radius:50%;">
            </c:otherwise>
        </c:choose>

        <br>
        <button id="visualizza" type="submit" class="btn btn-primary pulsante mt-4 mb-3" onclick="showModifica()">
            Visualizza informazioni profilo
        </button>
    </div>

    <div class="row mb-3" style="width: fit-content; margin-left: 10px;">
        <button id="backII" class="btn btn-primary mt-4 mb-3 pulsante"
                onclick="hideModifica()" style="display: none; background-color: #00AB98; border-color: #00AB98;">
            <i class="fas fa-arrow-left"></i></button>
    </div>

    <form id="form" class="needs-validation" novalidate method="post" enctype="multipart/form-data"
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

    <div id="div-campagna-table" style="display: none">
        <%@include file="../components/table_user_campagne.jsp" %>
    </div>
</div>

<div class="row text-center mt-4" id = "adminOption">
    <c:if test="${sessionScope.utente.admin}">
        <a style="text-decoration: none; color: black"
           href="${pageContext.request.contextPath}/GestioneUtenteController/visualizzaDashboardAdmin"
           class="admin-panel-icon"><i class="fas fa-users-cog"></i></a>
    </c:if>
</div>

<%@include file="../components/footer.jsp" %>
</body>


<script type="text/javascript" src=${pageContext.request.contextPath}/js/form-validation.js></script>
<script type="text/javascript" src=${pageContext.request.contextPath}/js/registrazione.js></script>
<script type="text/javascript" src=${pageContext.request.contextPath}/js/profilo_utente.js></script>

</html>
