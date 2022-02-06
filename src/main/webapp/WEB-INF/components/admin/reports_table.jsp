<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th scope="col">#ID Segnalazione</th>
        <th scope="col">#ID Campagna</th>
        <th scope="col">Motivazione</th>
        <th scope="col">Archivia</th>
        <th scope="col">Risolvi</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.segnalazioniList}" var="segnalazione">
        <c:choose>
            <c:when test="${segnalazione.statoSegnalazione.toString().equalsIgnoreCase('Attiva')}">
                <tr class="clickable clickable-row">
                    <th scope="row">${segnalazione.idSegnalazione}</th>
                    <td onclick="window.location.href ='${pageContext.request.contextPath}/campagna/campagna?idCampagna=${segnalazione.campagnaSegnalata.idCampagna}'">${segnalazione.campagnaSegnalata.idCampagna}</td>
                    <td>${segnalazione.descrizione}</td>

                    <td><button class="btn btn-primary pulsante" style="border-color: #00AB98; background-color: #00AB98; color: white" id="archiviaBtn"><i class="fas fa-archive"></i></button></td>
                    <td><button class="btn btn-primary pulsante" style="border-color: #00AB98; background-color: #00AB98; color: white" id="risolviBtn"><i class="fas fa-check-square"></i></button></td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr class="clickable clickable-row"
                    onclick="window.location.href ='${pageContext.request.contextPath}/campagna/campagna?idCampagna=${segnalazione.campagnaSegnalata.idCampagna}'">
                    <th scope="row">${segnalazione.idSegnalazione}</th>
                    <td>${segnalazione.campagnaSegnalata.idCampagna}</td>
                    <td>${segnalazione.descrizione}</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    </tbody>
    <script>
        $(document).ready(
            function () {
                $("#archiviaBtn").click(
                    function () {
                        $.post("./segnalazioni/risolvi",
                            {
                                sceltaSegnalazione: "Archivia"
                            },
                            function (data, status) {
                                location.reload();
                            });
                    }
                )

                $("#risolviBtn").click(
                    function () {
                        $.post("./segnalazioni/risolvi",
                            {
                                sceltaSegnalazione: "Risolvi"
                            },
                            function (data, status) {
                                location.reload();
                            });
                    }
                )
            });
    </script>
</table>