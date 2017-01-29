$(document).ready(function() {
    $("#button_cancel").click(function() {
        $popupDialog.dialog("close");
    })
    $("#update-user-form").validate({
        rules: {
            currentPassword: {
                required: true,
                minlength: 8
            },
            password: {
                required: true,
                minlength: 8
            },
            repeatPassword: {
                required: true,
                minlength: 8,
                equalTo: "#password"
            }
        },
        messages: {
            currentPassword: {
                required: "Gammelt Passord er obligatorisk.",
                minlength: "Passord må være minimum 8 tegn langt."
            },
            password: {
                required: "Passord er obligatorisk.",
                minlength: "Passord må være minimum 8 tegn langt."
            },
            repeatPassword: {
                required: "Passord må gjentas.",
                minlength: "Passord må være minimum 8 tegn langt.",
                equalTo: "Begge feltene må være like."
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo($("#" + element.attr("id") + "-error"));
        },
        submitHandler: function(form) {
            $.ajax({
                type: "PUT",
                headers: {
                    "Authorization": "Bearer " + localStorage.getItem("token")
                },
                url: "/MinVakt/rest/users/updatePassword",
                data: $(form).serialize(),
                datatype: "text",
                success: function() {
                    bannerMessage("Passord er endret!", function() {
                       $popupDialog.dialog("close");
                    });
                },
                statusCode: {
                    400: function() {
                        bannerMessage("Det har skjedd en feil. Passord er ikke endret", function () {
                            $popupDialog.dialog("close");
                        });
                    }
                },
            });
        }
    });
});
