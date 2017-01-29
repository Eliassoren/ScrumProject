$(document).ready(function() {
    $("#button_cancel").click(function() {
        $popupDialog.dialog("close");
    })

    $("#update-user-form").validate({
        rules: {
            firstName: {
                required: true
            },
            lastName: {
                required: true
            },
            email: {
                required: true,
                email: true
            },
            workPercent: {
                required: true,
                number: true,
                range: [0, 100]
            },
            departmentId: {
                required: true,
                number: true
            },
            userCategoryInt: {
                required: true,
                number: true
            }
        },
        messages: {
            firstName: {
                required: "Fornavn er obligatorisk.",
            },
            lastName: {
                required: "Fornavn er obligatorisk.",
            },
            email: {
                required: "Fornavn er obligatorisk.",
                email: "E-postadresse er ugyldig."
            },
            workPercent: {
                required: "Stillingsprosent er obligatorisk.",
            },
            departmentId: {
                required: "Avdeling er obligatorisk.",
            },
            userCategoryInt: {
                required: "Stillingstype er obligatorisk.",
            },
        },
        errorPlacement: function(error, element) {
            error.appendTo($("#" + element.attr("id") + "-error"));
        },
        submitHandler: function(form) {
            $.ajax({
                type: "POST",
                url: "/MinVakt/rest/users/updatepassword",
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

/*

 $("#update-user-form").validate({
 rules: {
 firstName: {
 required: true
 },
 lastName: {
 required: true
 },
 email: {
 required: true,
 email: true
 },
 workPercent: {
 required: true,
 number: true,
 range: [0, 100]
 },
 departmentId: {
 required: true,
 number: true
 },
 userCategoryInt: {
 required: true,
 number: true
 }
 },
 messages: {
 firstName: {
 required: "Fornavn er obligatorisk.",
 },
 lastName: {
 required: "Fornavn er obligatorisk.",
 },
 email: {
 required: "Fornavn er obligatorisk.",
 email: "E-postadresse er ugyldig."
 },
 workPercent: {
 required: "Stillingsprosent er obligatorisk.",
 },
 departmentId: {
 required: "Avdeling er obligatorisk.",
 },
 userCategoryInt: {
 required: "Stillingstype er obligatorisk.",
 },
 },
 errorPlacement: function(error, element) {
 error.appendTo($("#" + element.attr("id") + "-error"));
 },
 submitHandler: function(form) {
 $.ajax({
 type: "POST",
 url: "/MinVakt/rest/users/updatepassword",
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
 */