<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${sessionScope.utente != null}">
        <div class="row mb-3" style="width: fit-content; margin-left: 10px;">
            <button id="backII" class="btn btn-primary mt-4 mb-3 pulsante"
                    onclick="hideModifica()">
                <i class="fas fa-arrow-left"></i></button>
        </div>
    </c:when>
    <c:otherwise>
        <div class="row mb-3" style="width: fit-content; margin-left: 10px">
            <button id="back" class="btn btn-primary mt-4 mb-3"
                    onclick="location.href = '${pageContext.request.contextPath}/AutenticazioneController/login'"
                    style="background-color: #00AB98; border-color: #00AB98">
                <i class="fas fa-arrow-left"></i></button>
        </div>
    </c:otherwise>
</c:choose>

<form id="form" class="needs-validation" novalidate method="post"
      action="">

    <!--Nome e Cognome-->
    <div class="row">
        <!--nome-->
        <div class="col">
            <label for="inputNome" class="form-label">Nome</label>
            <input name="nome" type="text" class="form-control" id="inputNome" placeholder="Mario"
                   pattern="^[A-Za-zà-ź \s]{2,50}$" value="${utente.nome}" required>
            <div class=invalid-feedback>
                Formato nome non corretto
            </div>
        </div>

        <!--cognome-->
        <div class="col">
            <label for="inputCognome" class="form-label">Cognome</label>
            <input name="cognome" type="text" class="form-control" id="inputCognome" placeholder="Rossi"
                   pattern="^[A-Za-zà-ź \s]{2,50}$" value="${utente.cognome}" required>
            <div class=invalid-feedback>
                Formato cognome non corretto
            </div>
        </div>
    </div>

    <!--Email/Conferma email-->
    <div class="row mt-4">
        <!--email-->
        <div class="col">
            <label for="inputEmail" class="form-label">Indirizzo email</label>
            <input name="email" type="email" class="form-control" id="inputEmail"
                   aria-describedby="emailHelp"
                   placeholder="mario.rossi@example.it"
                   pattern="^[a-zA-Z0-9.!#$%&’*+\=?^_`{|}~-]{1,29}+@[a-zA-Z0-9-]{1,29}+(?:\.[a-zA-Z0-9-]+){1,10}$"
                   onkeyup="checkEmails()" value="${utente.email}" required>
            <div class=invalid-feedback>
                Formato email non corretto
            </div>
            <div id="emailHelp" class="form-text">Non condivideremo la tua email con nessuno</div>
        </div>

        <!--Conferma email-->
        <div class="col">
            <label for="inputConfermaEmail" class="form-label">Conferma Email</label>
            <input name="confermaEmail" type="text" class="form-control" id="inputConfermaEmail"
                   onkeyup="checkEmails()" value="${utente.email}"
                   required>
            <span id='messageEmail'></span>
        </div>
    </div>

    <!--Password e conferma-->
    <div class="row mt-4">

        <!--Password-->
        <div class="col">
            <label for="inputPassword" class="form-label">Password</label>
            <input name="password" type="password" class="form-control" onkeyup="checkPasswords();"
                   id="inputPassword"
                   pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&._])[A-Za-z\d@$!%*?&._]{8,32}$"
                   required>
            <div class=invalid-feedback>
                La password deve essere di almeno 8 caratteri, contenere almeno una lettera maiuscola e
                una minuscola e
                un carattere speciale.
            </div>
        </div>

        <!--Conferma Password-->
        <div class="col">
            <label for="confermaInputPassword" class="form-label"> Conferma Password</label>
            <input name="confermaPassword" type="password" class="form-control"
                   id="confermaInputPassword" onkeyup="checkPasswords();" required>
            <span id='message'></span>
        </div>
    </div>
    <!--Fine password e conferma-->

    <!--Data di nascita-->
    <div class="row mt-4">
        <div class="col">
            <label for="inputData" class="form-label">Data di nascita</label>
            <input name="dataDiNascita" type="date" class="form-control" id="inputData"
                   placeholder="DD/MM/YY" value="${utente.dataDiNascita}" required>
        </div>
    </div>

    <!--Telefono-->
    <div class="row mt-4">
        <div class="col">
            <label for="inputTelefono" class="form-label">Telefono</label>
            <input name="telefono" type="text" class="form-control" id="inputTelefono"
                   placeholder="0000000000"
                   pattern="^\d{10}$" value="${utente.telefono}" required>
            <div class=invalid-feedback>
                Il numero di telefono deve essere composto da 10 cifre
            </div>
        </div>
    </div>

    <!--Indirizzo-->
    <div class="row mt-4">
        <div class="col">
            <label for="inputIndirizzo" class="form-label">Indirizzo</label>
            <input name="indirizzo" type="text" class="form-control" id="inputIndirizzo"
                   placeholder="Via Roma, 10"
                   pattern="[a-zA-Z0-9\s]{1,74}+,\s[0-9]{1,5}$" value="${utente.strada}" required>
            <div class=invalid-feedback>
                Formato indirizzo non corretto(Strada, civico)
            </div>
        </div>

        <div class="col">
            <label for="inputCittà" class="form-label">Citt&agrave</label>
            <input name="citta" type="text" class="form-control" id="inputCittà" placeholder="Roma"
                   pattern="^[A-Za-zà-ź \s]{2,50}$" value="${utente.citta}" required>
            <div class=invalid-feedback>
                Formato Città non corretto
            </div>
        </div>

        <div class="col">
            <label for="inputCAP" class="form-label">CAP</label>
            <input name="cap" type="text" class="form-control" id="inputCAP" placeholder="00100"
                   pattern="^[0-9]{5}$" value="${utente.cap}" required>
            <div class=invalid-feedback>
                Formato CAP non corretto
            </div>
        </div>
    </div>

    <div class="row mt-4">
        <div class="col">
            <label for="inputCF" class="form-label">Codice Fiscale</label>
            <input name="cf" type="text" class="form-control" id="inputCF"
                   placeholder="RSSMRA80A01H501U"
                   pattern="^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$" value="${utente.cf}" required>
            <div class=invalid-feedback>
                Formato Codice Fiscale non corretto
            </div>
        </div>
    </div>


    <div class="row mt-4">
        <div class="col">
            <label for="formFile" class="form-label">Immagine del profilo</label>
            <input name="fotoProfilo" class="form-control" type="file" id="formFile" oninput="">
        </div>
    </div>

    <c:choose>
        <c:when test="${sessionScope.utente != null}">
            <button type="submit" class="btn btn-primary pulsante mt-4 mb-3"
                    style="background-color: #00AB98; border-color: #00AB98;">Modifica
            </button>
        </c:when>
        <c:otherwise>
            <button type="submit" class="btn btn-primary pulsante mt-4 mb-3"
                    style="background-color: #00AB98; border-color: #00AB98;">Registrati
            </button>
        </c:otherwise>
    </c:choose>

</form>

