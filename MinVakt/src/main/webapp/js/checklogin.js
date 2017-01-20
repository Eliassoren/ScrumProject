$(document).ready(function() {
    if (localStorage.getItem("token") != null) {
        $.ajax({
            type: "GET",
            url: "/MinVakt/rest/session/checktoken",
            headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
            statusCode: {
                401: function () {
                    localStorage.removeItem("token");
                    window.location.replace("/MinVakt/");
                }
            }
        })
    } else {
        window.location.replace("/MinVakt");
    }
});