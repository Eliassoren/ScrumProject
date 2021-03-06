var $popupDialog;

$(document).ready(function() {
    createDialog("#login-lost-password", "Glemt passord", "/MinVakt/html/dialogs/login-password.html", 400);
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
                datatype: "json",
                statusCode: {
                    401: function() {
                        $("#error-message").text("Feil e-postadresse eller passord.");
                    }
                },
                success: function(loginObj) {
                    localStorage.setItem("token", loginObj.token);
                    localStorage.setItem("userid", loginObj.id);
                    document.cookie = "token=" + loginObj.token;
                    window.location.replace("/MinVakt/site/");
                }
            });
        }
    });
});

function createDialog(selector, title, url, width) {
    $(selector).each(function() {
        $popupDialog = $("<div/>");
        var $link = $(this).one("click", function() {
            $popupDialog.dialog({
                title: title,
                modal: true,
                closeOnEscape: true,
                width: width,
                resizable: false,
                autoOpen: false,
                show: "fade",
                hide: "fade",
                position: {my: "center", at: "center", of: window}
            }).load(url, function() {
                $(this).dialog("open");
            });
            $link.click(function() {
                $popupDialog.dialog("open");
                return false;
            });
            return false;
        });
    });
}
