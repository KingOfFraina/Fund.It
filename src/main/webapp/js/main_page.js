$(document).ready(function () {

    let x = $('#sidebarCollapse');

    let y = $('#sidebarCollapse2');

    x.on('click', function () {
        $('#sidebar').toggleClass('active');
    });

    y.on('click', function () {
        $('#sidebar').toggleClass('active');
    });


});

let showCategories = function (){
    let x = document.getElementById("sidebarCollapse");
    x.style.display = "none";
}

let hideCategories = function (){
    let x = document.getElementById("sidebarCollapse");
    x.style.display = "block";
}