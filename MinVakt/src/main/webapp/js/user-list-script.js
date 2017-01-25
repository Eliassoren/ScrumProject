/**
 * Created by Chris on 25.01.2017.
 */


$(document).ready(function() {
    $(".days").click(function(){
        $(".opacity").toggleClass("blur-activate");
        $(".employee-form-banner").toggleClass("show-banner");

    });
});