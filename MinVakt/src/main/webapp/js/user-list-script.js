/**
 * Created by Chris on 25.01.2017.
 */

var users = [];

$(document).ready(function() {

    getAllUsers();

    $(".new-button").click(function(){
        $("body").prepend("<div/>").addClass("new-user-div");
        $(".new-user-div").load("/MinVakt/html/template/new-user.html", function(){
            newUser();
        })
    })

});

function getAllUsers() {

    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/users/all",
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function (data) {
            addRowEmployee(data);
        },
        statusCode: {
            401: function () {
                localStorage.removeItem("token");
                localStorage.removeItem("userid");
                var date = new Date();
                date.setTime(date.getTime()+(-1*24*60*60*1000));
                var expires = " expires="+date.toUTCString();
                document.cookie = "token=;" + expires + "; path=/MinVakt;"; // Deletes cookie by setting expiration date to yesterday
                window.location.replace("/MinVakt/site");
            }
        }
    })
}

function addRowEmployee(data) {

    console.log(data);

    var obj = data;

    if (!document.getElementsByTagName) return;

    for (var i = 0; i < obj.length; i++) {
        users[obj[i].id] = obj[i];
        tabBody = document.getElementById("employee-table");
        row = document.createElement("tr");
        row.className = "days";
        $(row).attr("user-id",obj[i].id);
        cell1 = document.createElement("td");
        cell2 = document.createElement("td");
        textnode1 = document.createTextNode(obj[i].firstName + " " + obj[i].lastName);
        textnode2 = document.createTextNode(obj[i].userCategoryString);
        cell1.appendChild(textnode1);
        cell2.appendChild(textnode2);
        row.appendChild(cell1);
        row.appendChild(cell2);
        tabBody.appendChild(row);

        $(".days").unbind();
        $(".fa-times").unbind();

        $(".days").click(function(){
            $(".opacity").addClass("blur-activate");
            $(".employee-form-banner").addClass("show-banner");
            var userId = $(this).attr("user-id");
            console.log(userId);
            $("#overlay-placer").load("/MinVakt/site/user-list-user", function(){
                $(".stilling").text("Stilling: " + users[userId].userCategoryString);
                $(".name").text(users[userId].firstName + " " + users[userId].lastName);
                $(".telefon").text("Telefon: " + users[userId].mobile);
                $(".epost").text("Epost: " + users[userId].email);
                $(".adresse").text("Adresse: " + users[userId].address);


                $(".fa-times").click(function(){
                    $(".opacity").removeClass("blur-activate");
                    $(".employee-form-banner").remove();

                });

                $(".employee-form-delete-button").click(function(){
                    bannerConfirm("Er du helt sikker på at du vil slette bruker?", function(){
                            $.ajax({
                                type: "PUT",
                                url: "/MinVakt/rest/users/delete/" + userId,
                                headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
                                success: function (data) {
                                    location.reload(true);
                                },
                                statusCode: {
                                    401: function () {
                                        localStorage.removeItem("token");
                                        localStorage.removeItem("userid");
                                        var date = new Date();
                                        date.setTime(date.getTime()+(-1*24*60*60*1000));
                                        var expires = " expires="+date.toUTCString();
                                        document.cookie = "token=;" + expires + "; path=/MinVakt;"; // Deletes cookie by setting expiration date to yesterday
                                        window.location.replace("/MinVakt/site");
                                    }
                                }
                            })
                    })
                })
            })
        });
    }
}
