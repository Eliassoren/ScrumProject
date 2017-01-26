/**
 * Created by kjosavik on 25-Jan-17.
 */

$(document).ready(
    function getUser () {
        var userId = window.localStorage.getItem("userid");
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                "Authorization": "Bearer " + localStorage.getItem("token")
            },
            type: "GET",
            url: "/MinVakt/rest/users/id/" + userId,
            //dataType: user,
            success: function(data){
                var user = data;
                $("#first_name").val(user.firstName);
                console.log(user.firstName);
                $("#last_name").val(user.lastName);
                $("#address").val(user.address);
                $("#postal_code").val("7020");
                $("#phone").val(user.mobile);
                console.log(user.phone);
                $("#email").val(user.email);
                $("#dept_cb").val(user.departmentId);
                console.log(user.departmentId);
                $("#position_cb").val(user.userCategoryInt);
                $("#spinner").val(user.workPercent);
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
