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
function editPassword(){
    var userId = window.localStorage.getItem("userid");

    $("#edit-password-form").validate({
        rules: {
            old_password: {
                required: true,
                minlength: 8
            },
            new_password: {
                required: true,
                minlength: 8
            },
            confirm_new_password: {
                required: true,
                minlength: 8
            }

        },
        messages: {

            old_password: {
                required: "Passord er obligatorisk.",
                minlength: "Passordet må bestå av minimum 8 tegn."
            },
            new_password: {
                required: "Nytt passord må fylles ut.",
                minlength: "Passordet må bestå av minimum 8 tegn."
            },
            confirm_new_password: {
                required: "Nytt passord må bekreftes.",
                minlength: "Passordet må bestå av minimum 8 tegn."
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo($("#" + element.attr("id") + "-error"));
        },
        submitHandler: function(form) {

            $.ajax({
                type: "PUT",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    "Authorization": "Bearer " + localStorage.getItem("token")
                },
                url: "MinVakt/rest/users/",
                data: $(form).serialize(),
                response: "json",
                statusCode: {
                    409: function() {
                        $("#error-message").text("Noe gikk galt. Bruker ble ikke endret. Prøv igjen senere. Hvis feilen fortsetter, " +
                            "kontakt service");

                    },
                    401: function () {
                        localStorage.removeItem("token");
                        window.location.replace("/MinVakt/");
                    }
                },
                success: function(data) {
                    console.log(data);
                    return data;
                }
            })
        }
    });
}
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
