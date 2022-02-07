<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>

    <%@include file="WEB-INF/components/head.jsp" %>
    <link rel="stylesheet" href="./css/index.css">
    <title>Fund.it</title>
</head>
<body>

<!--Navbar-->


<%@include file="WEB-INF/components/navbar.jsp" %>
<div class="text-center mt-2">
    <%@include file="WEB-INF/components/toasts.jsp" %>
</div>


<!--Report-->
<c:if test="${sessionScope.titoloReport.length() > 0}">
    <input id="message" type="hidden"
           value="${sessionScope.tipoReport}+${sessionScope.titoloReport}+${sessionScope.bodyReport}">
    <script>

        Toasty();
    </script>
    ${sessionScope.tipoReport = null}
    ${sessionScope.titoloReport = null}
    ${sessionScope.bodyReport = null}
</c:if>

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

    <img src="${pageContext.request.contextPath}/img/group_selfie.svg" alt="">
    <button id="scopri" type="submit" class="btn btn-primary btn-lg mx-5 my-5"
            onclick="location.href = '${pageContext.request.contextPath}/campagna/main'">
        Scopri di pi&ugrave
    </button>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

</body>
</html>