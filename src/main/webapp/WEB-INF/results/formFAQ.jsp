<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <%@include file="../components/head.jsp" %>
        <title>Fund.it</title>
        <link rel="stylesheet" type="text/css" href=${pageContext.request.contextPath}/css/faq.css>
    </head>
    <body>
        <form method="post">

            <div class = "container mt-4 text-center">
                <h5>Modifica FAQ</h5>
            </div>

            <div class = "container container-faq mx-4">
                <input  class = "form-control mb-4" type="text" name="domanda" maxlength="200" placeholder="Domanda" value="${requestScope.faq.domanda}">
                <input class = "form-control mb-4" type="text" name="risposta" maxlength="200" placeholder="Risposta" value="${requestScope.faq.risposta}">
                <input type="submit" class="btn btn-primary" style="background-color: #00AB98; border-color: #00AB98"

                <c:choose>
                <c:when test="${requestScope.faq != null}">
                       formaction="${pageContext.request.contextPath}/faq/modificaFAQ" value="Salva modifiche">
                </c:when>
                <c:otherwise>
                    formaction="${pageContext.request.contextPath}/faq/inserisciFAQ" value="Inserisci FAQ">
                </c:otherwise>
                </c:choose>
            </div>
            <input type="hidden" name="idFaq" value="${requestScope.faq.idFaq}">

        </form>
    </body>
</html>
