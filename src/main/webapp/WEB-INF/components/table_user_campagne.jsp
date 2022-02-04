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
            onclick="window.location.href ='${pageContext.request.contextPath}/GestioneCampagnaController/campagna?idCampagna=${campagna.idCampagna}'">
            <th scope="row">${campagna.idCampagna}</th>
            <td>${campagna.titolo}</td>
            <td>${campagna.stato}</td>
            <td>${campagna.sommaRaccolta}&euro;</td>
            <td>${campagna.sommaTarget}&euro;</td>
        </tr>

    </c:forEach>
    </tbody>
</table>