function loadForm(){
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function() {
        document.getElementById("form-div").innerHTML = this.responseText;
    }
    xhttp.open("GET", "./text/registrazione.jsp", true);
    xhttp.send();
}

