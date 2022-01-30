<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Visualizza FAQ</title>
    </head>
    <body>
        <c:forEach items="${requestScope.faqList}" var="faq">
            <details>
                <summary>Q: ${faq.domanda}</summary>
                A: ${faq.risposta}
            </details>
            <c:if test="${sessionScope.utente.admin}">
                <form>
                    <input type="hidden" name="idFaq" value="${faq.idFaq}">
                    <input type="submit" formmethod="get" formaction="${pageContext.request.contextPath}/faq/modificaFAQ" value="Modifica FAQ">
                    <input type="submit" formmethod="post" formaction="${pageContext.request.contextPath}/faq/eliminaFAQ" value="Elimina FAQ">
                </form>
            </c:if>
        </c:forEach>

        <c:if test="${sessionScope.utente.admin}">
            <form>
                <input type="submit" formmethod="get" formaction="${pageContext.request.contextPath}/faq/inserisciFAQ" value="Inserisci FAQ">
            </form>
        </c:if>

    </body>
</html>