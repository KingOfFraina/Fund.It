<table class="table table-striped table-bordered table-hover">
    <thead>
    <h5>${utente.campagne}</h5>
    <tr>
        <th scope="col">#ID</th>
        <th scope="col">Titolo</th>
        <th scope="col">Stato</th>
        <th scope="col">Descrizione</th>
        <th scope="col">Somma Raccolta</th>
        <th scope="col">Somma Target</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items="${utente.campagne}" var="campagna">
            <tr>
                <th scope="row">${campagna.idCampagna}</th>
                <td>${campagna.titolo}</td>
                <td>${campagna.stato}</td>
                <td>${campagna.descrizione}</td>
                <td>${campagna.sommaRaccolta}&euro;</td>
                <td>${campagna.sommaTarget}&euro;</td>
            </tr>
        </c:forEach>
    </tbody>
</table>