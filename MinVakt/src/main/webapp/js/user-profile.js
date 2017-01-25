/**
 * Created by kjosavik on 25-Jan-17.
 */
var user;

$(document).ready(
    function getUser () {
        var userId = window.localStorage.getItem("userid")
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "Authorization": "Bearer " + localStorage.getItem("token")
            },
            type: "GET",
            url: "MinVakt/rest/users/id/" + userId,
            dataType: user,
            success: function(){
                console.log()
            }
        })
    }
);

function updateUser (user) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        type: "PUT",
        url: "/MinVakt/rest/users/",
        dataType: 'text',
        data: user,
        success: function(){
            console.log()
        }
    })
}
