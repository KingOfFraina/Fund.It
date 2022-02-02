<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@include file="../components/head.jsp" %>
    <title>Fund.it</title>
    <link rel="stylesheet" type="text/css" href=${pageContext.request.contextPath}/css/faq.css>
</head>
<body>
<%@include file="../components/navbar.jsp" %>

<div class="container text-center mt-4">
    <h1>FAQ</h1>
</div>

<c:forEach items="${requestScope.faqList}" var="faq">
    <div class="container mt-5">
        <div class="container container-faq my-3">
            <div class="container">
                <h5>D: ${faq.domanda}</h5>
            </div>
            <div class="container">
                <hr class="solid text-black">
                <h5>R: ${faq.risposta}</h5>
            </div>
        </div>
    </div>

    <c:if test="${sessionScope.utente.admin}">
        <form>
            <div class="container mt-2">
                <input type="hidden" name="idFaq" value="${faq.idFaq}" class="btn btn-primary">
                <input type="submit" formmethod="get" formaction="${pageContext.request.contextPath}/faq/modificaFAQ"
                       value="Modifica FAQ" class="btn btn-primary pulsante" style="background-color: #00AB98;
    border-color: #00AB98;">
                <input type="submit" formmethod="post" formaction="${pageContext.request.contextPath}/faq/eliminaFAQ"
                       value="Elimina FAQ" class="btn btn-primary pulsante" style="background-color: #00AB98;
    border-color: #00AB98;">
            </div>

        </form>
    </c:if>
</c:forEach>

<c:if test="${sessionScope.utente.admin}">
    <div class = "container my-5">
        <form>
            <input type="submit" formmethod="get" formaction="${pageContext.request.contextPath}/faq/inserisciFAQ"
                   value="Inserisci FAQ" class="btn btn-primary pulsante" style="background-color: #00AB98;
    border-color: #00AB98;">
        </form>
    </div>

</c:if>

</body>
</html>