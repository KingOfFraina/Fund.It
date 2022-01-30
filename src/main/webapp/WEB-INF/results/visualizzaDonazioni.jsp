<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Visualizza Donazioni</title>
    </head>
    <body>

    <c:choose>
        <c:when test="${requestScope.donazioniList.size() == 0}">
            <h1>Nessuna donazione trovata</h1>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th>ID donazione</th>
                    <th>Commento</th>
                    <th>Somma donata</th>
                    <th>Anonimo</th>
                    <th>Data</th>

                    <c:if test="${sessionScope.utente.admin}">
                        <th>ID utente</th>
                    </c:if>

                    <th>ID campagna</th>
                </tr>

                <c:forEach items="${requestScope.donazioniList}" var="donazione">
                    <c:choose>
                        <c:when test="${donazione.utente.idUtente == sessionScope.utente.idUtente && sessionScope.utente.admin}">
                            <tr style="background: yellow">
                        </c:when>
                        <c:otherwise>
                            <tr>
                        </c:otherwise>
                    </c:choose>

                            <td>${donazione.idDonazione}</td>
                            <td>${donazione.commento}</td>
                            <td>${donazione.sommaDonata}</td>
                            <td>${donazione.anonimo}</td>
                            <td>${donazione.dataOra}</td>

                            <c:if test="${sessionScope.utente.admin}">
                                <td>${donazione.utente.idUtente}</td>
                            </c:if>

                            <td>${donazione.campagna.idCampagna}</td>
                        </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>

    </body>
</html>