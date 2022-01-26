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

    let y = document.getElementById("div-donation-table");
    y.style.display = "block";
}