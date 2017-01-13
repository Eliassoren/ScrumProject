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
                url: "/MinVakt/rest/session",
                data: $(form).serialize(),
                statusCode: {
                    200: function() {
                        $("#error-message").text("Du er logget inn!");
                        //window.location.replace("/MinVakt/html/shift.html"):
                    },
                    401: function() {
                        $("#error-message").text("Feil e-postadresse eller passord.");
                    },
                    404: function() {
                        $("#error-message").text("Feil: Serveren.");
                    }
                }
            });
        }
    });
});