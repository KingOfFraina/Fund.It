<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th scope="col">#ID</th>
        <th scope="col">Somma donata</th>
        <th scope="col">Data e ora</th>
        <th scope="col">#ID Campagna</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items="${requestScope.utente.donazioni}" var="donazione">
            <tr>
                <th scope="row">${donazione.idDonazione}</th>
                <td>${donazione.sommaDonata}&euro;</td>
                <td>${donazione.dataOra}</td>
                <td>${donazione.campagna.idCampagna}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>