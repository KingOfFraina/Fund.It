<div class="row mb-3" style="width: fit-content; margin-left: 10px">
    <button id="back" class="btn btn-primary mt-4 mb-3"
            onclick="hideDonationTable()"
            style="background-color: #00AB98; border-color: #00AB98">
        <i class="fas fa-arrow-left"></i></button>
</div>


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
                <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${donazione.sommaDonata}"/>&euro;</td>
                <td>${donazione.dataOra}</td>
                <td>${donazione.campagna.idCampagna}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>