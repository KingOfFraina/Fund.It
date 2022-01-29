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
        </c:forEach>
    </body>
</html>