/**
 * Created by Chris on 26.01.2017.
 */

$(document).ready(function() {
    $("#a-cancel").click(function(){
        $(".fixed-main-container-outer").toggleClass("cancel-activate");
    });
    $("#a-confirm").click(function(){
        $(".fixed-main-container-outer").toggleClass("confirm-activate");
        $(".a-removeable").toggleClass("a-removeable-activate");
        $(".a-fade-in-text").toggleClass("a-fade-in-text-activate");
    });
    $(".fa-times").click(function(){
        $(".fixed-main-container-outer").toggleClass("fa-times-activate");
    });
    $(".a-cancel-button").click(function(){
        $(".fixed-main-container-outer").toggleClass("fa-times-activate");
    });
});