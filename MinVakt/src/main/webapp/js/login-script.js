$(document).ready(function() {
    var onLoginPage = true;
    if(localStorage.getItem("token") != null) {
        $.ajax({
            type: "GET",
            url: "/MinVakt/rest/session/checktoken",
            headers: { "Authorization": "Bearer " + localStorage.getItem("token")},
            success: function() {
                if(onLoginPage != undefined && onLoginPage) {
                    window.location.replace("/MinVakt/html/calendar.html");
                }
            },
            statusCode: {
                401: function() {
                    localStorage.removeItem("token");
                    window.location.replace("/MinVakt/");
                }
            }
        })
    }
    $("#login-form").validate({
        rules: {
            login_email: {
                required: true,
                email: true
            },
            login_password: {
                required: true,
                minlength: 8
            }
        },
        messages: {
            login_email: {
                required: "E-postadresse er obligatorisk.",
                email: "E-postadressen er ugyldig."
            },
            login_password: {
                required: "Passord er obligatorisk.",
                minlength: "Passordet må bestå av minimum 8 tegn."
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo($("#" + element.attr("id") + "-error"));
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
                    window.location.replace("/MinVakt/html/calendar.html");
                }
            });
        }
    });
});
