<form id="form" class="needs-validation" novalidate method="post" action="${pageContext.request.contextPath}/AutenticazioneController/registrazione">

    <!--Nome e Cognome-->
    <div class="row">
        <!--nome-->
        <div class="col">
            <label for="inputNome" class="form-label">Nome</label>
            <input name="nome" type="text" class="form-control" id="inputNome" placeholder="Mario"
                   pattern = "^[A-Za-zà-ź \s]{2,50}$" required>
            <div class=invalid-feedback>
                Formato nome non corretto
            </div>
        </div>

        <!--cognome-->
        <div class="col">
            <label for="inputCognome" class="form-label">Cognome</label>
            <input name="cognome" type="text" class="form-control" id="inputCognome" placeholder="Rossi"
                   pattern="^[A-Za-zà-ź \s]{2,50}$" required>
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
            <input name="email" type="email" class="form-control" id="inputEmail" aria-describedby="emailHelp"
                   placeholder="mario.rossi@example.it"
                   pattern = "^[a-zA-Z0-9.!#$%&’*+\=?^_`{|}~-]{1,29}+@[a-zA-Z0-9-]{1,29}+(?:\.[a-zA-Z0-9-]+){1,10}$"
                   onkeyup="checkEmails()" required>
            <div class=invalid-feedback>
                Formato email non corretto
            </div>
            <div id="emailHelp" class="form-text">Non condivideremo la tua email con nessuno</div>
        </div>

        <!--Conferma email-->
        <div class="col">
            <label for="inputConfermaEmail" class="form-label">Conferma Email</label>
            <input name="confermaEmail" type="text" class="form-control" id="inputConfermaEmail" onkeyup="checkEmails()" required>
            <span id='messageEmail'></span>
        </div>
    </div>

    <!--Password e conferma-->
    <div class="row mt-4">

        <!--Password-->
        <div class="col">
            <label for="inputPassword" class="form-label">Password</label>
            <input name="password" type="password" class="form-control" onkeyup="checkPasswords();" id="inputPassword" required>
            <div class=invalid-feedback>
                La password deve essere di almeno 8 caratteri, contenere almeno una lettera maiuscola e una minuscola e
                un carattere speciale.
            </div>
        </div>

        <!--Conferma Password-->
        <div class="col">
            <label for="confermaInputPassword" class="form-label"> Conferma Password</label>
            <input name="confermaPassword" type="password" class="form-control" id="confermaInputPassword"  onkeyup="checkPasswords();" required>
            <span id='message'></span>
        </div>
    </div>
    <!--Fine password e conferma-->

    <!--Data di nascita-->
    <div class="row mt-4">
        <div class="col">
            <label for="inputData" class="form-label">Data di nascita</label>
            <input name="dataDiNascita" type="date" class="form-control" id="inputData" placeholder="DD/MM/YY" required>
        </div>
    </div>

    <!--Telefono-->
    <div class="row mt-4">
        <div class="col">
            <label for="inputTelefono" class="form-label">Telefono</label>
            <input name="telefono" type="text" class="form-control" id="inputTelefono" placeholder="0000000000"
                   required>
            <div class=invalid-feedback>
                Il numero di telefono deve essere composto da 10 cifre
            </div>
        </div>
    </div>

    <!--Indirizzo-->
    <div class="row mt-4">
        <div class="col">
            <label for="inputIndirizzo" class="form-label">Indirizzo</label>
            <input name="indirizzo" type="text" class="form-control" id="inputIndirizzo" placeholder="Via Roma, 10"
                   required>
            <div class=invalid-feedback>
                Formato indirizzo non corretto(Strada, civico)
            </div>
        </div>

        <div class="col">
            <label for="inputCittà" class="form-label">Citt&agrave</label>
            <input name="citta" type="text" class="form-control" id="inputCittà" placeholder="Roma" required>
            <div class=invalid-feedback>
                Formato Città non corretto
            </div>
        </div>

        <div class="col">
            <label for="inputCAP" class="form-label">CAP</label>
            <input name="cap" type="text" class="form-control" id="inputCAP" placeholder="00100" required>
            <div class=invalid-feedback>
                Formato CAP non corretto
            </div>
        </div>
    </div>

    <div class="row mt-4">
        <div class="col">
            <label for="inputCF" class="form-label">Codice Fiscale</label>
            <input name="cf" type="text" class="form-control" id="inputCF" placeholder="RSSMRA80A01H501U" required>
            <div class=invalid-feedback>
                Formato Codice Fiscale non corretto
            </div>
        </div>
    </div>

    <div class="row mt-4">
        <div class="col">
            <label for="formFile" class="form-label">Immagine del profilo</label>
            <input name="fotoProfilo" class="form-control" type="file" id="formFile">
        </div>
    </div>

    <button type="submit" class="btn btn-primary pulsante mt-4 mb-3">Registrati</button>
</form>
