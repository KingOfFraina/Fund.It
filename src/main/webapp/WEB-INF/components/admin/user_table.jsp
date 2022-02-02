<table class="table table-striped table-bordered table-hover">
    <thead>
    <tr>
        <th scope="col">#ID</th>
        <th scope="col">Nome</th>
        <th scope="col">Cognome</th>
        <th scope="col">E-mail</th>
        <th scope="col">Codice Fiscale</th>
        <th scope="col">Promuovi/Declassa</th>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${utentiList}" var = "utente">
        <tr>
            <th scope="row">${utente.id}</th>
            <td>${utente.nome}</td>
            <td>${utente.cognome}</td>
            <td>${utente.email}</td>
            <td>${utente.cf}</td>
            <td><i class="fas fa-arrow-up"></i> / <i class="fas fa-arrow-down"></i></td>
        </tr>
    </c:forEach>
    </tbody>
</table>