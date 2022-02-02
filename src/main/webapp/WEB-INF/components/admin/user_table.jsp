<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th scope="col">#ID</th>
        <th scope="col">Nome</th>
        <th scope="col">Cognome</th>
        <th scope="col">E-mail</th>
        <th scope="col">Codice Fiscale</th>
        <th scope="col">Status</th>
        <th scope="col">Promuovi/Declassa</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${utentiList}" var = "utente">
        <c:if test="${utente.idUtente != sessionScope.utente.idUtente}">
            <tr>
                <th scope="row">${utente.idUtente}</th>
                <td>${utente.nome}</td>
                <td>${utente.cognome}</td>
                <td>${utente.email}</td>
                <td>${utente.cf}</td>

                    <c:choose>
                        <c:when test="${utente.admin}">
                            <td>Amministratore</td>
                            <td>
                                <form action = ${pageContext.request.contextPath}/GestioneUtenteController/promuoviDeclassaUtente method="post">
                                    <input id = "inputhidden" type="text" value = "${utente.idUtente}" name = "utentemod" hidden>
                                    <button  type = "submit" class = "btn btn-primary btnUtente" style="">
                                        <i class="fas fa-arrow-down"></i>
                                    </button>
                                </form>

                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                Utente semplice
                            </td>
                            <td>
                                <form action = ${pageContext.request.contextPath}/GestioneUtenteController/promuoviDeclassaUtente method="post">
                                    <input type="text" value = "${utente.idUtente}" name = "utentemod" hidden>
                                    <button type="submit" class = "btn btn-primary" style="">
                                        <i class="fas fa-arrow-up"></i>
                                    </button>
                                </form>

                            </td>
                        </c:otherwise>
                    </c:choose>

            </tr>
        </c:if>
    </c:forEach>
    </tbody>
</table>