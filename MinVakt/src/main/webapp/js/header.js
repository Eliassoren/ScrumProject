var $popupDialog;

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
        localStorage.removeItem("token");
        localStorage.removeItem("userid");
        var date = new Date();
        date.setTime(date.getTime()+(-1*24*60*60*1000));
        var expires = " expires="+date.toUTCString();
        document.cookie = "token=;" + expires + "; path=/MinVakt;"; // Deletes cookie by setting expiration date to yesterday
        window.location.replace("/MinVakt/site");
    });
    createDialog("#header-user-profile", "Forandre passord", "/MinVakt/site/dialog-update-password", 600);
});

function createDialog(selector, title, url, width) {
    $(selector).each(function() {
        $popupDialog = $("<div/>");
        var $link = $(this).one("click", function() {
            $popupDialog.dialog({
                title: title,
                modal: true,
                closeOnEscape: true,
                width: width,
                resizable: false,
                autoOpen: false,
                show: "fade",
                hide: "fade",
                position: {my: "center", at: "center", of: window}
            }).load(url, function() {
                $(this).dialog("open");
            });
            $link.click(function() {
                $popupDialog.dialog("open");
                return false;
            });
            return false;
        });
    });
}