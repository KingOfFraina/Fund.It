var forms = document.querySelectorAll(".needs-validation");
Array.prototype.slice.call(forms).forEach(function (form) {
    form.addEventListener("submit", function (event) {

        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        }
        form.classList.add("was-validated");
        event.stopPropagation();
    }, false);

});

var checkPasswords = function () {
    if (document.getElementById('inputPassword').value ===
        document.getElementById('confermaInputPassword').value) {
        document.getElementById('message').style.color = 'green';
        document.getElementById('message').innerHTML = 'Le password corrispondono';
    } else {
        document.getElementById('message').style.color = 'red';
        document.getElementById('message').innerHTML = 'Le password non corrispondono';
    }

}

var checkEmails = function () {
    if (document.getElementById('inputEmail').value ===
        document.getElementById('inputConfermaEmail').value) {
        document.getElementById('messageEmail').style.color = 'green';
        document.getElementById('messageEmail').innerHTML = 'Le email corrispondono';
    } else {
        document.getElementById('messageEmail').style.color = 'red';
        document.getElementById('messageEmail').innerHTML = 'Le email non corrispondono';
    }
}






