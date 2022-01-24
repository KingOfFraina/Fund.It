function loadForm() {
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
        let div = document.getElementById("div-info");
        div.innerHTML = this.responseText;
    }
    xhttp.open("POST", window.location.origin + window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1)) + "/GestioneUtenteController/visualizzaDashboard", true);
    xhttp.send();

    return false;
}

