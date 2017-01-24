/**
 * Created by Chris on 24.01.2017.
 */

var dateNow;
var week;
var availableUser = [];
$(document).ready(function() {
    dateNow = new Date();
    week = weekNumberFromDate(dateNow);
    $("#month-title").text("Uke " + week);

    $("#left-arrow").click(function () {
        week--;
        $("#month-title").text("Uke " + week);
    });

    $("#right-arrow").click(function () {
        week++;
        $("#month-title").text("Uke " + week);
    });
    var theDay = getDateOfWeek(week, dateNow.getFullYear());
    var nextDay = getDateOfWeek(week, dateNow.getFullYear());
    nextDay.setDate(nextDay.getDate()+1)
    for (var i = 0; i < 7; i++){
        console.log(theDay + " " + nextDay);
        getAvailableUsers(theDay, nextDay, i);
        theDay.setDate(theDay.getDate()+1);
        nextDay.setDate(nextDay.getDate()+1);
    }


    $(".day").click(function(){
        $(this).toggleClass("blue");
    });
});

function getFilteredUsers() {

}

function getAvailableUsers(start, end, place) {
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/users/availability/" + start.getTime() + "/" + end.getTime(),
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function(data){
            if(data.length != 0){
                availableUser[place] = data;
                console.log("Dag " + place + ": "  + data.length);
            }

        },
        statusCode: {
            401: function () {
                localStorage.removeItem("token");
                window.location.replace("/MinVakt/");
            }
        }
    })
}

function addRow(userObj) {
    var user = $("<tr/>").addClass("days");
    user.append($("<td>").addClass("day").text(userObj.firstName + " " + userObj.lastName));
    user.append($("<td>").addClass("day").text(userObj.userCategoryString));
    $("tbody").append(user);

}

function weekNumberFromDate(date){
    var weeknumber = 0;
    var first = new Date(date.getFullYear(), 0, 1);
    while (first < date) {
        var nextDate = first.getDate();
        var lastOfMonth = new Date(first.getFullYear(), first.getMonth() +1, 0).getDate();
        if (nextDate + 7 > lastOfMonth){
            first = new Date(first.getFullYear(), first.getMonth() + 1, (nextDate + 7)%lastOfMonth);
            weeknumber++;
        } else {
            first = new Date(first.getFullYear(), first.getMonth(), nextDate + 7);
            weeknumber++;
        }
    }
    return weeknumber;
}

function getDateOfWeek(w, y) {
    var date = new Date(y, 0, 1 + (w - 1) * 7);
    var dateOfWeek = date.getDay();
    var weekStart = date;
    if (dateOfWeek <= 4) {
        weekStart.setDate(date.getDate() - date.getDay() + 1);
    } else {
        weekStart.setDate(date.getDate() + 8 - date.getDay());
    }
    return weekStart;
}