$(document).ready(function() {
    $("#lostpassword-close").click(function() {
        $lostPasswordDialog.dialog("close");
    })
    $("#login-lost-password-form").validate({
        rules: {
            login_lost_email: {
                required: true,
                email: true
            }
        },
        messages: {
            login_lost_email: {
                required: "E-postadresse er obligatorisk.",
                email: "E-postadressen er ugyldig."
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo($("#" + element.attr("id") + "-error"));
        },
        submitHandler: function(form) {
            $.ajax({
                type: "POST",
                url: "/MinVakt/rest/session/lostpassword",
                data: $(form).serialize(),
                datatype: "text",
                statusCode: {
                    200: function() {
                        $("#lostpassword-message").addClass("lostpassword-information-message").removeClass("lostpassword-error-message");
                        $("#lostpassword-message").text("Nytt passord er sendt ut til oppgitt e-postadresse.");
                        $("#login-lost-password-form").css({opacity: 0, transition: "opacity 1.0s", display: "block"}).slideUp(400, function() {
                            $("#lostpassword-close").css("display", "block").hide().fadeIn("0.5s");
                        });
                    },
                    404: function() {
                        $("#lostpassword-message").addClass("lostpassword-error-message").removeClass("lostpassword-information-message");
                        $("#lostpassword-message").text("Konto med oppgitt e-postadresse finnes ikke.");
                    }
                }
            });
        }
    });
});