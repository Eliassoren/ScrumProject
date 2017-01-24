var $lostPasswordDialog;

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
                    localStorage.removeItem("userid");
                    window.location.replace("/MinVakt/");
                }
            }
        })
    }
    createDialog("#login-lost-password", "Glemt passord", "/MinVakt/html/dialogs/login-password.html");
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
                    window.location.replace("/MinVakt/html/calendar.html");
                }
            });
        }
    });
});

function createDialog(selector, title, url) {
    $(selector).each(function() {
        $lostPasswordDialog = $("<div/>");
        var $link = $(this).one("click", function() {
            $lostPasswordDialog.dialog({
                title: title,
                modal: true,
                closeOnEscape: true,
                width: 400,
                resizable: false,
                autoOpen: false,
                show: "fade",
                hide: "fade",
                position: {my: "center", at: "center", of: window}
            }).load(url, function() {
                $(this).dialog("open");
            });
            $link.click(function() {
                $lostPasswordDialog.dialog("open");
                return false;
            });
            return false;
        });
    });
}
