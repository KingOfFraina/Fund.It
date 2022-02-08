<html>
<head>
    <%@include file="../components/head.jsp" %>
    <title>Fund.it</title>
</head>
<body>
<div class="container mt-5 text-center py-3">

    <div class="container text-center">
        <h5>Lascia un commento se ti va!</h5>
    </div>


    <form method="post" action="${pageContext.request.contextPath}/donazione/scriviCommento"
          class="text-center needs-validation" novalidate>
        <input type="hidden" name="idCampagna" value="${requestScope.idCampagna}">


        <div class="form-group text-center">

            <textarea placeholder="Commenta qui!" class="form-control" id="exampleFormControlTextarea1" rows="3" name="commento" minlength="2"
                      maxlength="150" required></textarea>
        </div>

        <div class="mt-2 text-center">
            <div style="float: left">
                <input class="form-check-input" type="checkbox" value="true" id="anonimo" name="anonimo"
                       formnovalidate="formnovalidate">
                <label class="form-check-label" for="anonimo">
                    Anonimo
                </label>
            </div>
        </div>

        <button type="submit" class="pulsante btn-primary btn mt-3"
                style="background-color: #00AB98; border-color: #00AB98; color: white">
            Commenta
        </button>
    </form>

</div>
<div class = "container">

        <input type="hidden" name="idCampagna" value="${requestScope.idCampagna}">
        <button class="pulsante btn-secondary btn mt-3" onclick="location.href = '${pageContext.request.contextPath}/campagna/campagna?idCampagna=${requestScope.idCampagna}'">
            Prosegui senza commentare
        </button>

</div>


<script type="text/javascript" src=${pageContext.request.contextPath}/js/form-validation.js></script>
</body>
</html>
