<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th scope="col">#ID</th>
        <th scope="col">Stato</th>
        <th scope="col">Titolo</th>
        <th scope="col">Organizzatore</th>
        <th scope="col">Codice Fiscale Organizzatore</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${applicationScope.campagneList}" var = "campagna">
        <tr>
            <th scope="row">${campagna.idCampagna}</th>
            <c:choose>
                <c:when test="${campagna.stato.toString().equalsIgnoreCase('Attiva')}">
                    <td style="color: green">${campagna.stato.toString()}</td>
                </c:when>

                <c:otherwise>
                    <td style="color: red">${campagna.stato.toString()}</td>
                </c:otherwise>

            </c:choose>

            <td>${campagna.titolo}</td>
            <td>${campagna.utente.nome}  ${campagna.utente.cognome}</td>
            <td>${campagna.utente.cf}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>