var forms = document.querySelectorAll(".needs-validation");
Array.prototype.slice.call(forms).forEach(function (form) {
    form.addEventListener("submit", function (event) {

        if (!form.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        } else if (document.getElementById('inputPassword').value !==
            document.getElementById('confermaInputPassword').value) {
            event.preventDefault();
            event.stopPropagation();
        } else if (document.getElementById('inputEmail').value !==
            document.getElementById('inputConfermaEmail').value) {
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
        return true;
    } else {
        document.getElementById('message').style.color = 'red';
        document.getElementById('message').innerHTML = 'Le password non corrispondono';
        return false;
    }

}

var checkEmails = function () {
    if (document.getElementById('inputEmail').value ===
        document.getElementById('inputConfermaEmail').value) {
        document.getElementById('messageEmail').style.color = 'green';
        document.getElementById('messageEmail').innerHTML = 'Le email corrispondono';
        return false;
    } else {
        document.getElementById('messageEmail').style.color = 'red';
        document.getElementById('messageEmail').innerHTML = 'Le email non corrispondono';
        return true;
    }
}

function togglePassword() {
    let button = document.getElementById("button-addon2");
    const x = document.getElementById("exampleInputPassword1");
    if (x.type === "password") {
        x.type = "text";
        button.innerHTML = "<i class=\"fas fa-eye-slash\"></i>"
    } else {
        x.type = "password";
        button.innerHTML = "<i class=\"fas fa-eye\"></i>"
    }
}

