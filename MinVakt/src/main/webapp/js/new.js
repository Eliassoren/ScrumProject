$(document).ready(function() {
    $("#hamburger-menu").click(function () {
        $("#hamburger-text").toggleClass("hamburger-text-activate");
        $(".hamburger-items-container").toggleClass("hamburger-items-container-activate");
    });
    $("#hamburger-calendar").click(function() {
        window.location.href = "/MinVakt/site/calendar";
    });
    $("#hamburger-shift").click(function() {
        window.location.href = "/MinVakt/site/shift";
    });
    $("#hamburger-shifttrade").click(function() {
        window.location.href = "/MinVakt/site/shift-tradeable";
    });
    $("#hamburger-logout").click(function() {
        alert("This function works, but it doesnt log-out. Needs fixing? just remove " +
            "this alert if everything is working as it should");
        localStorage.removeItem("token");
        localStorage.removeItem("userid");
        $.cookie("token", null, { path: '/' });
        window.location.replace("/MinVakt/site");
    });
    $("#header-user-profile").click(function() {
        $(".header-user-items-container").toggleClass("header-user-items-container-activate");
        $("#header-user-profile").toggleClass("header-user-activate");
    });
});