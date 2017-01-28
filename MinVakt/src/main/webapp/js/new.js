$(document).ready(function() {
    $("#hamburger-menu").click(function () {
        $("#hamburger-menu-items").toggleClass("hamburger-menu-open");
        $("#hamburger-text").toggleClass("hamburger-text-fixed-activate");
        $(".hamburger-items-container").toggleClass("hamburger-items-container-activate");
        $("#hamburger-menu").toggleClass("hamburger-menu-open");
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
        localStorage.removeItem("token");
        localStorage.removeItem("userid");
        $.cookie("token", null, { path: '/' });
        window.location.replace("/MinVakt/site");
    });
});