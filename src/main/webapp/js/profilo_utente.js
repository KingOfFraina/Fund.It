function showModifica() {
    let x = document.getElementById("informazioni");
    x.style.display = "none";

    let y = document.getElementById("informazioni-modifica");
    y.style.display = "block";

    let z = document.getElementById("backII");
    z.style.display = "block";
}

function hideModifica() {
    var x = document.getElementById("informazioni-modifica");
    x.style.display = "none";

    var y = document.getElementById("informazioni");
    y.style.display = "block";

    let z = document.getElementById("backII");
    z.style.display = "none";
}

function showDonationTable() {
    let x = document.getElementById("div-select-images");
    x.style.display = "none";
    document.getElementById("adminOption").style.display = "none"

    let y = document.getElementById("div-donation-table");
    y.style.display = "block";
}

function showCampagneTable() {
    let x = document.getElementById("div-select-images");
    x.style.display = "none";
    document.getElementById("adminOption").style.display = "none"

    let y = document.getElementById("div-campagna-table");
    y.style.display = "block";
}

function hideCampagneTable() {
    let x = document.getElementById("div-select-images");
    x.style.display = "block";
    document.getElementById("adminOption").style.display = "block"

    let y = document.getElementById("div-campagna-table");
    y.style.display = "none";
}

function hideDonationTable() {
    let x = document.getElementById("div-select-images");
    x.style.display = "block";
    document.getElementById("adminOption").style.display = "block"

    let y = document.getElementById("div-donation-table");
    y.style.display = "none";
}