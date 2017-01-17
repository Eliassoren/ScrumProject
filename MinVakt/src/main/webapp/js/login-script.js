$(document).ready(function() {
    $("#login-form").validate({
        rules: {
            email: "required",
            password: {
                required: true,
                minlength: 8
            }
        },
        messages: {
            email: "E-postadresse er obligatorisk.",
            password: {
                required: "Passord er obligatorisk.",
                minlength: "Passordet må bestå av minimum 8 tegn."
            }
        },

        submitHandler: function(form) {
            $.ajax({
                type: "POST",
                url: "/MinVakt/rest/session/login",
                data: $(form).serialize(),
                response: "text",
                statusCode: {
                    401: function() {
                        $("#error-message").text("Feil e-postadresse eller passord.");
                    }
                },
                success: function(token) {
                    localStorage.setItem("token", token);
                    window.location.replace("/MinVakt/");
                }
            });
        }
    });
});
