$(document).ready(function() {
    $("#hamburger-menu").click(function () {
        $("#hamburger-menu-items").toggleClass("hamburger-menu-open");
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
        localStorage.removeItem("token");
        localStorage.removeItem("userid");
        var date = new Date();
        date.setTime(date.getTime()+(-1*24*60*60*1000));
        var expires = " expires="+date.toUTCString();
        document.cookie = "token=;" + expires + "; path=/MinVakt;"; // Deletes cookie by setting expiration date to yesterday
        window.location.replace("/MinVakt/site");
    });
    $("#header-user-profile").click(function() {
        $(".header-user-items-container").toggleClass("header-user-items-container-activate");
    });
});