percentage = function (target, collected){
    let x = document.getElementById("progressbar");
    let val =  (collected*100)/target;

    x.style.width = val+"%";
}

donationsCount = function (array) {
    let x = document.getElementById("donation-count");
    x.innerHTML = array.length + "";
}