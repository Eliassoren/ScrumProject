/**
 * Created by kjosavik on 20-Jan-17.
 */

$("#new-user-form").validate({
    rules: {
        firstName: {
            required: true
        },
        lastName: {
            required : true
        },
        password: {
            required: true,
            minlength: 8
        },
        admin: {
            required: false
        },
        mobile: {
            required : true,
            minlength: 8
        },
        userCategory: {
            required: true
        },
        email: {
            required: true,
            email: true
        },
        active: {
            required: false

        }
    },
    messages: {
        firstName: {
          required: "Fornavn er obligatorisk"
        },
        lastName: {
          required: "Etternavn er obligatorisk"
        },
        mobile: {
            required: "Telefonnummer må registreres",
            minlength: "Telefonnummer må være minst 8 tegn"
        },
        email: {
            required: "E-postadresse er obligatorisk.",
            email: "E-postadressen er ugyldig."
        },
        password: {
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
            url: "MinVakt/rest/users/",
            data: $(form).serialize(),
            response: "json",
            statusCode: {
                409: function() {
                    $("#error-message").text("Noe gikk galt. Bruker ble ikke opprettet. Prøv igjen senere. Hvis feilen fortsetter, " +
                        "kontakt utviklerene eller utvikle noe selv din late faen.");

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



