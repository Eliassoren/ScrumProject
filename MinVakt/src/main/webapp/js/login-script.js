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
            if(confirm("Er du sikker på at du vil logge inn?")) {
                form.submit();
            }
        }
    });
});