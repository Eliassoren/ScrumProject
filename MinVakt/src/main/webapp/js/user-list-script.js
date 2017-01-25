/**
 * Created by Chris on 25.01.2017.
 */


$(document).ready(function() {
    $(".days").click(function(){
        $(".opacity").toggleClass("blur-activate");
        $(".employee-form-banner").toggleClass("show-banner");

    })
});

function getAllUsers() {

    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/users/all",
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function (data) {
            console.log(data);
        }
    })
}