$(document).ready(function() {
    $("#loginform").validate({
        rules: {
            username: "required",
            password: {
                required: true,
                minlength: 8
            }
        },
        messages: {
            username: "Brukernavn er obligatorisk.",
            password: {
                required: "Passord er obligatorisk.",
                minlength: "Passordet må bestå av minimum 8 tegn."
            }
        }
    });
});