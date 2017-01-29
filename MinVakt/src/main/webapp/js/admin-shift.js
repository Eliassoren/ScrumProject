/**
 * Author Vegard
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
        getShiftsForWeek();
        var theDay = getDateOfWeek(week, dateNow.getFullYear());
        var nextDay = getDateOfWeek(week, dateNow.getFullYear());
        nextDay.setHours(23);
        nextDay.setMinutes(59);
        for (var i = 1; i <= 7; i++){
            //console.log(theDay + " " + nextDay);
            getAvailableUsers(theDay, nextDay, i);
            getShiftsMissingNurse(theDay, nextDay, i);
            getShiftsMissingHealthWorker(theDay, nextDay, i);
            theDay.setDate(theDay.getDate()+1);
            nextDay.setDate(nextDay.getDate()+1);

        }
    });

    $("#right-arrow").click(function () {
        week++;
        $("#month-title").text("Uke " + week);
        getShiftsForWeek();
        var theDay = getDateOfWeek(week, dateNow.getFullYear());
        var nextDay = getDateOfWeek(week, dateNow.getFullYear());
        nextDay.setHours(23);
        nextDay.setMinutes(59);
        for (var i = 1; i <= 7; i++){
            //console.log(theDay + " " + nextDay);
            getAvailableUsers(theDay, nextDay, i);
            getShiftsMissingNurse(theDay, nextDay, i);
            getShiftsMissingHealthWorker(theDay, nextDay, i);
            theDay.setDate(theDay.getDate()+1);
            nextDay.setDate(nextDay.getDate()+1);

        }
    });
    var theDay = getDateOfWeek(week, dateNow.getFullYear());
    var nextDay = getDateOfWeek(week, dateNow.getFullYear());
    nextDay.setHours(23);
    nextDay.setMinutes(59);
    for (var i = 1; i <= 7; i++){
        //console.log(theDay + " " + nextDay);
        getAvailableUsers(theDay, nextDay, i);
        getShiftsMissingNurse(theDay, nextDay, i);
        getShiftsMissingHealthWorker(theDay, nextDay, i);
        theDay.setDate(theDay.getDate()+1);
        nextDay.setDate(nextDay.getDate()+1);

    }

    getShiftsForWeek();

    $(".no-user").click(function(){
        $(".user-row").remove();
    })

    $(".all-users[name='all-users']").click(function(){
        if ($(".all-users[name='all-users']:checked").length == 1) {
            $.ajax({
                type: "GET",
                url: "/MinVakt/rest/users/all",
                headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
                success: function(data){
                   for (var i = 0; i < data.length; i++){
                       addRow(data[i]);
                   }
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
            });
        } else {
            $(".user-row").remove();
        }

    });


    $(".day").click(function () {
        $(this).toggleClass("blue");
        var daysSelected = selectedDays();
            var dayNumber = Number($(this).attr("id").substring(3,4));
            $("i[day='"+ dayNumber +"']").toggleClass("fa-square-o");
            $("i[day='"+ dayNumber +"']").toggleClass("fa-check-square-o");

        if ($(".no-user[name='no-user']:checked").length == 0 && $(".all-users[name='all-users']:checked").length == 0) {
            $(".user-row").remove();
            var usersAdded = [];
           for (var i = 0; i < daysSelected.length; i++){
               var usersDay = availableUser[Number(daysSelected[i]) - 1];
               if (usersDay !== undefined){
                   for (var j = 0; j < usersDay.length; j++){
                           //console.log(usersDay[j]);
                       if (usersAdded[usersDay[j].id] != 1){
                           usersAdded[usersDay[j].id] = 1;
                           addRow(usersDay[j]);
                       }
                   }
               }
           }
        }

    });


    $(".accept-button").click(function(){
        var shiftProfile = $(".input-time[name=time-of-day]:checked").attr("id");
        var start = "";
        var end = "";

        switch(shiftProfile) {
            case "day-id":
                start = "8:00";
                end = "16:00";
                break;
            case "evening-id":
                start = "16:00";
                end = "23:59";
                break;
            case "night-id":
                start = "00:00";
                end = "8:00";
                break;
            default:
                start = "8:00";
                end = "16:00";
        }

        bannerConfirm("Bekreft " + selectedDays().length * selectedUser().length + " vakter?", function(){
            createShifts(start, end, Number($("#department option:selected").val()),1);
        });
    })
});


function getShiftsForWeek() {
    $(".admin-shift-shift-item").remove();
    var monday = getDateOfWeek(week, dateNow.getFullYear());
    var sunday = getDateOfWeek(week, dateNow.getFullYear());
    sunday.setDate(Number(sunday.getDate()) + 6);
    sunday.setHours(23);
    sunday.setMinutes(59);
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/" + monday.getTime() + "/" + sunday.getTime(),
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function(data){
           for (var i = 0; i < data.length; i++){
               addShifts(data[i]);
           }
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

function getAvailableUsers(starter, ender, place) {
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/users/availability/" + starter.getTime() + "/" + ender.getTime(),
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function(data){
            if(data.length != 0){
                availableUser[place] = data;
            }

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

function addShifts(shift) {
    var date = new Date(shift.startTime).getDay();
    if(date == 0) {date = 7}
    var shiftItem = $("<div/>").addClass("admin-shift-shift-item");
    var assignedUser = (shift.userName != "" ? shift.userName : "Ingen ansatt");
    shiftItem.append("<div>" + assignedUser + "</div>");
    shiftItem.attr("id", shift.shiftId);
    shiftItem.append("<div>" + formatTime(new Date(shift.startTime)) + " - " + formatTime(new Date(shift.endTime)) + "</div>");
    $("#day" + date).append(shiftItem);
}

function selectedUser(){
    var usersSelected = [];
    if ($(".no-user[name='no-user']:checked").length == 1) {
        usersSelected.push(0);
    } else {
        $(".user-selected").each(function(){
            usersSelected.push(Number($(this).attr("id")));
        });
    }
    return usersSelected;
}

function selectedDays(){
    var daysSelected = [];
    $(".blue").each(function(){
        daysSelected.push($(this).attr("id").substring(3,5));
    });
    return daysSelected;
}

function createShifts(start, end, department, role) {
    if (!$(".input-time").is(":checked")) {
        bannerAlert("Ingen tid valgt");
        return;
    }
    var users = selectedUser();
    var days = selectedDays();
    if (($(".no-user[name='no-user']:checked").length == 0 && users == 0) || days == 0) {
        bannerAlert("Vennligst velg bruker(e) og dag(er)");
        return;
    }

    for (var i = 0; i < days.length; i++) {
        var dateOfShift = getDateOfWeek(week, dateNow.getFullYear());
        var endOfShift = getDateOfWeek(week, dateNow.getFullYear());
        console.log(dateOfShift + " " + endOfShift);
        var startTime = start.split(":");
        var endTime = end.split(":");
        dateOfShift.setDate(Number(getDateOfWeek(week, dateNow.getFullYear()).getDate()) + Number(days[i] - 1));
        endOfShift.setDate(Number(getDateOfWeek(week, dateNow.getFullYear()).getDate()) + Number(days[i] - 1));
        console.log(dateOfShift + " " + endOfShift);
        dateOfShift.setHours(startTime[0]);
        dateOfShift.setMinutes(startTime[1]);
        endOfShift.setHours(endTime[0]);
        endOfShift.setMinutes(endTime[1]);
        console.log(dateOfShift + " " + endOfShift);

        for (var j = 0; j < users.length; j++){
            console.log(Number(users[j]));
            if (typeof users[j] !== 'number'){
                console.log("Something wrong in createShifts(): " + (typeof users[j]));
                return;
            }
            $.ajax({
                type: "POST",
                url: "/MinVakt/rest/shifts/",
                headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    shiftId: 0,
                    startTime: dateOfShift.getTime(),
                    endTime: endOfShift.getTime(),
                    userId: users[j],
                    userName: "",
                    departmentId: department,
                    role: role,
                    tradeable: false,
                    responsibleUser: false,
                    roleDescription: ""
                }),
                success: function(data){
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
            });
        }


    }
}

function addRow(userObj) {
    var user = $("<tr/>").addClass("user-row");
    user.append($("<td>").addClass("user-name").text(userObj.firstName + " " + userObj.lastName));
    user.append($("<td>").addClass("user-cat").text(userObj.userCategoryString));
    user.attr("id", userObj.id);
    $("tbody").append(user);
    $(".user-row").unbind();
    $(".user-row").click(function () {
        $(this).toggleClass("user-selected");
    });
}

function getAllUsers(){
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/users/availability/" + starter.getTime() + "/" + ender.getTime(),
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function(data){
            if(data.length != 0){
                availableUser[place] = data;
            }

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

function getShiftsMissingNurse(start, end, day) {
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/missingnurse/" + start.getTime() + "/" + end.getTime(),
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function(data){
            $("#nurse-missing[nday='"+ day +"']").text("-"+data);
            console.log("Mangler: " + data + " Dag:"+ day )
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

function getShiftsMissingHealthWorker(start, end, day) {
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/missinghealthworker/" + start.getTime() + "/" + end.getTime(),
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function(data){
            $("#hw-missing[nday='"+ day +"']").text("-"+data);
            console.log("Mangler: " + data + " Dag:"+ day )
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
