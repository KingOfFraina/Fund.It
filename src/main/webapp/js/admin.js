function showUserTable(){
    let x = document.getElementById("user_table");
    x.style.display = "block";
    document.getElementById("campaign_table").style.display = "none";
    document.getElementById("donations_table").style.display = "none";
    document.getElementById("reports_table").style.display = "none";
}

function showCampaignTable(){
    let x = document.getElementById("campaign_table");
    x.style.display = "block";
    document.getElementById("user_table").style.display = "none";
    document.getElementById("donations_table").style.display = "none";
    document.getElementById("reports_table").style.display = "none";
}

function showDonationsTable(){
    let x = document.getElementById("donations_table");
    x.style.display = "block";
    document.getElementById("user_table").style.display = "none";
    document.getElementById("campaign_table").style.display = "none";
    document.getElementById("reports_table").style.display = "none";
}

function showReportsTable(){
    let x = document.getElementById("reports_table");
    x.style.display = "block";
    document.getElementById("user_table").style.display = "none";
    document.getElementById("campaign_table").style.display = "none";
    document.getElementById("donations_table").style.display = "none";
}

