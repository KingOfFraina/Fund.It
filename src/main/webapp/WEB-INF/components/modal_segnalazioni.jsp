<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"
        integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"
        integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13"
        crossorigin="anonymous"></script>


<div class="modal scrollable fade" id="modalSegnalazioni" tabindex="-1" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Segnala campagna</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action = "${pageContext.request.contextPath}/segnalazioni/segnala" method="post">
            <div class="modal-body">

                <div class="form-group">
                    <label for="textAraeSegnalazioni">Spiegaci i motivi della segnalazione(max. 300 caratteri)</label>
                    <textarea name = "descrizione" class="form-control" id="textAraeSegnalazioni" rows="5" required maxlength="300">

                    </textarea>
                    <input type="text" name = "idUtente" value="${sessionScope.utente.idUtente}" hidden>
                    <input type="text" name = "idCampagna" value="${campagna.idCampagna}" hidden>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary pulsante" data-bs-dismiss="modal"
                        style="background-color: #00AB98; border-color: #00AB98">Chiudi
                </button>

                <button type="submit" class="btn btn-secondary pulsante"
                        style="background-color: #00AB98; border-color: #00AB98">Invia segnalazione
                </button>

            </div>
            </form>
        </div>
    </div>
</div>