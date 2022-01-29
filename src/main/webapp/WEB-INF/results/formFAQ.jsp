<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Form per nuova FAQ</title>
    </head>
    <body>
        <form method="post">
            <input type="text" name="domanda" maxlength="200" placeholder="Domanda" value="${requestScope.faq.domanda}">
            <input type="text" name="risposta" maxlength="200" placeholder="Risposta" value="${requestScope.faq.risposta}">
            <input type="hidden" name="idFaq" value="${requestScope.faq.idFaq}">
            <input type="submit"
            <c:choose>
                <c:when test="${requestScope.faq != null}">
                   formaction="${pageContext.request.contextPath}/GestioneFAQController/modificaFAQ" value="Salva modifiche">
                </c:when>
                <c:otherwise>
                    formaction="${pageContext.request.contextPath}/GestioneFAQController/inserisciFAQ" value="Inserisci FAQ">
                </c:otherwise>
            </c:choose>

            <c:if test="${sessionScope.utente.admin}">
                <input type="submit" formaction="${pageContext.request.contextPath}/GestioneFAQController/modificaFAQ" value="Modifica FAQ">
            </c:if>
        </form>
    </body>
</html>
