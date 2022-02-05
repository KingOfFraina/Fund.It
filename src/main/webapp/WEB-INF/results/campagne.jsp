<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <%@include file="../components/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/campagne.css">
    <title>Fund.it</title>
</head>
<body>
<%@include file="../components/navbar.jsp" %>



<div class = "mt-4 container">
    <h5>Risultati della ricerca: </h5>
</div>

<hr class="rounded">

<div class = "container my-4">


    <div class="row text-center my-4">
<c:choose>
    <c:when test="${requestScope.errorSearch == null}">
        <c:forEach items="${requestScope.campagneList}" var = "campagna">
            <div class="col-sm my-4">
                <div class="card" style="width: 18rem;">

                    <img class="card-img-top"
                         src="${pageContext.request.contextPath}/file/${campagna.immagini.get(0).path}"
                         alt="Card image cap">
                    <div class="card-body">
                        <h5>${pageContext.request.contextPath}/file/${campagna.immagini.get(0).path}</h5>
                        <h5 class="card-title">${campagna.titolo}</h5>
                        <a href="${pageContext.request.contextPath}/GestioneCampagnaController/campagna?idCampagna=${campagna.idCampagna}" class="btn btn-primary mt-3"
                           style="background-color: #00AB98; border-color: #00AB98;">Vai alla campagna</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:when>

    <c:otherwise>
        <div class = "container text-ceter">
            <img src="${pageContext.request.contextPath}/img/undraw_void_-3-ggu.svg" style="width: 30%">
            <h5 class="mt-3">Nessun risultato trovato</h5>
        </div>
    </c:otherwise>
</c:choose>







    </div>

</div>

</body>
</html>
