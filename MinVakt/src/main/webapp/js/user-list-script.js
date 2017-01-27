/**
 * Created by Chris on 25.01.2017.
 */

var users = [];

$(document).ready(function() {

    getAllUsers();

    $(".new-button").click(function(){
        $("body").prepend("<div/>").addClass("new-user-div");
        $(".new-user-div").load("template/new-user.html", function(){

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
            $("#overlay-placer").load("template/user-list-user.html", function(){
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
                                }
                            });
                    });
                });

                $(".employee-form-edit-button").click(function(){
                    $(".text").each(function(){
                        console.log($(this).text());
                        var newElement = $("<input>");
                        if ($(this).attr("jsonName") == 'userCategoryInt') {
                            newElement = $("<select>");
                            newElement.attr("type", "dropdown");
                            newElement.append("<option value = '1'>Assistent</option><option value = '2'>Helsefagarbeider</option><option value = '3'>Sykepleier</option>");
                        } else {
                            newElement.attr("placeholder", $(this).text());
                        }
                        newElement.attr("id", "input-user-info-new")
                        newElement.attr("jsonName", $(this).attr("jsonName"));
                        newElement.addClass($(this).attr("class"));

                        if($(this).text() != "") {$(this).parent().append(newElement); }
                        $(this).remove();
                    });
                    $(".employee-form-edit-button").text("Lagre");
                    $(".employee-form-edit-button").unbind();
                    $(".employee-form-edit-button").click(function(){
                        if ($(".text[jsonName='userName']").val() != "") {
                            console.log("New name");
                            var fullname = $(".text[jsonName='userName']").val()
                            var firstName = fullname.substring(0, fullname.lastIndexOf(" "));
                            var lastName = fullname.substring(fullname.lastIndexOf(" ")+1, fullname.length);
                            users[userId].firstName = firstName;
                            users[userId].lastName = lastName;
                        }
                        if ($(".text[jsonName='address']").val() != ""){
                            users[userId].address = $(".text[jsonName='address']").val();
                        }
                        if ($(".text[jsonName='mobile']").val() != ""){
                            users[userId].address = $(".text[jsonName='mobile']").val();
                        }
                        if ($(".text[jsonName='email']").val() != ""){
                            users[userId].email = $(".text[jsonName='email']").val();
                        }

                        console.log(users[userId]);

                        $.ajax({
                            type: "PUT",
                            url: "/MinVakt/rest/users",
                            headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
                            contentType: "application/json",
                            data: users[userId],
                            success: function (data) {
                                location.reload(true);
                            }
                        })
                    });
                });
            });



        });


    }
}
