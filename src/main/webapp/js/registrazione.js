function loadForm(){
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function() {
        document.innerHTML = this.responseText;
    }
    xhttp.open("GET", window.location.origin + window.location.pathname.substring(0, window.location.pathname.indexOf('/',1)) +"/AutenticazioneController/registrazione", true);
    xhttp.send();

}

