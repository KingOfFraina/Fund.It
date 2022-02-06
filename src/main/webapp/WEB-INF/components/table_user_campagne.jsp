
<div class="row mb-3" style="width: fit-content; margin-left: 10px">
    <button id="back" class="btn btn-primary mt-4 mb-3"
            onclick="hideCampagneTable()"
            style="background-color: #00AB98; border-color: #00AB98">
        <i class="fas fa-arrow-left"></i></button>
</div>

<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th scope="col">#ID</th>
        <th scope="col">Titolo</th>
        <th scope="col">Stato</th>
        <th scope="col">Somma Raccolta</th>
        <th scope="col">Somma Target</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${utente.campagne}" var="campagna">

        <tr class="clickable clickable-row"
            onclick="window.location.href ='${pageContext.request.contextPath}/campagna/campagna?idCampagna=${campagna.idCampagna}'">
            <th scope="row">${campagna.idCampagna}</th>
            <td>${campagna.titolo}</td>
            <td>${campagna.stato}</td>
            <td>${campagna.sommaRaccolta}&euro;</td>
            <td>${campagna.sommaTarget}&euro;</td>
        </tr>

    </c:forEach>
    </tbody>
</table>

<div class = "container text-center">
    <button class = "btn btn-primary pulsante" style="background-color: #00AB98; border-color: #00AB98" onclick="window.location.href = '${pageContext.request.contextPath}/campagna/creaCampagna'">
        Crea campagna
    </button>
</div>