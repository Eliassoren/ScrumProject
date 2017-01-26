/**
 * Created by Chris on 25.01.2017.
 */

var users = [];

$(document).ready(function() {

    getAllUsers();

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
            var userId = $(this).attr("userId");
            $("#overlay-placer").load("template/user-list-user.html", function(){
                $(".stilling").text("Stilling: " + users[userId].userCategoryString);
            })


        });

        $(".fa-times").click(function(){
            $(".opacity").removeClass("blur-activate");
            $(".employee-form-banner").removeClass("show-banner");
        });
    }
}
