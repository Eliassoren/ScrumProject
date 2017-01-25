/**
 * Created by Chris on 25.01.2017.
 */


$(document).ready(function() {
    $(".days").click(function(){
        $(".opacity").addClass("blur-activate");
        $(".employee-form-banner").addClass("show-banner");
    });

    $(".fa-times").click(function(){
        $(".opacity").removeClass("blur-activate");
        $(".employee-form-banner").removeClass("show-banner");
    });
});