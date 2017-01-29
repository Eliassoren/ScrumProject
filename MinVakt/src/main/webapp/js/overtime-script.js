/**
 * Created by Chris on 25.01.2017.
 */

/**
 * Created by Chris on 12.01.2017.
 */

$(document).on("input", function() {
    refreshTable()
});

function refreshTable() {
    var currentDate = $( ".date-picker" ).datepicker( "getDate" );
    var dateTime = currentDate.getTime();
    getAvailableShifts(dateTime, dateTime + 86400000);
}

$(document).ready(function() {
    $("#dropdown-toggle-evening").click(function(){
        $("#evening").toggleClass("dropdown-active");
    });

});

function formatTime(string) {
    var time = "";
    var hour = string.split(":")[0];
    var min = string.split(":")[1];
    if (Number(hour) < 10) {
        time += "0" + hour + ":";
    } else time += hour + ":";
    if (Number(min) < 10) {
        time += "0" + min;
    } else time += min;
    return time;
}

function getCount(data) {

    var obj = data;
    var count = obj.length;
    $(".table-count-alert").text(count);

}

function addRow(data) {

    console.log(data);

    var obj = data;
    for (var i = 0; i < obj.length; i++) {

        var hours = new Date(obj[i].endTime).getHours() - new Date(obj[i].startTime).getHours();
        if (hours < 0){
            hours = hours + 24;
        }
        $("#overtime-table")
            .append($("<tr/>")
                .attr("data-shiftid", obj[i].shiftId)
                .append($("<td/>")
                    .text(obj[i].shiftId)
                ).append($("<td/>")
                    .text(obj[i].firstName + " " + obj[i].lastName)
                ).append($("<td/>")
                    .text(new Date(obj[i].startTime).getDate() + "." + new Date(obj[i].startTime).getMonth() + "." + new Date(obj[i].startTime).getFullYear() + " " + formatTime(new Date(obj[i].startTime).getHours() + ":" + new Date(obj[i].startTime).getMinutes()) + " - " + formatTime(new Date(obj[i].endTime).getHours() + ":" + new Date(obj[i].endTime).getMinutes()))
                ).append($("<td/>")
                    .text(hours)
                ).append($("<td/>")
                    .text("Godkjenn")
                    .addClass("overtime-list-button")
                    .addClass("accept")
                    .attr("data-shiftid", obj[i].shiftId)
                    .attr("data-userid", obj[i].userId)
                    .click(function() {
                        approveOvertime($(this).attr("data-shiftid"), $(this).attr("data-userid"));
                     })
                ).append($("<td/>")
                    .text("Ikke godkjenn")
                    .addClass("overtime-list-button")
                    .addClass("cancel")
                    .attr("data-shiftid", obj[i].shiftId)
                    .attr("data-userid", obj[i].userId)
                    .click(function() {
                        rejectOvertime($(this).attr("data-shiftid"), $(this).attr("data-userid"));
                    })
                )
            )
    }
}

function approveOvertime(shiftId, userId) {
    $.ajax({
        headers: {"Authorization": "Bearer " + localStorage.getItem("token"),
            'Content-Type': 'application/json'
        },
        type: "PUT",
        url: "/MinVakt/rest/shifts/approveOvertime",
        dataType: 'text',

        data: JSON.stringify({
            shiftId: shiftId,
            userId: userId
        }),
        success: function (data) {
            getUnapprovedOvertime(0,1589483849399);
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
            },
            400: function () {
                console.log(data);
            }
        }
    })
}

function rejectOvertime(shiftId, userId) {
    $.ajax({
        headers: {"Authorization": "Bearer " + localStorage.getItem("token"),
            'Content-Type': 'application/json'
        },
        type: "DELETE",
        url: "/MinVakt/rest/shifts/rejectOvertime",
        dataType: 'text',
        data: JSON.stringify({
            shiftId: shiftId,
            userId: userId
        }),
        success: function (data) {
            getUnapprovedOvertime(0,1589483849399);
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
            },
            400: function () {
                console.log(data);
            }
        }
    })
}

function getShiftAndTrade(id, bool){
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/" + id,
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function(data){
            console.log(data);
            setShiftTradeablePut(data, bool)
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

function setShiftTradeablePut(shift, bool) {
    $.ajax({
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        type: "PUT",
        url: "/MinVakt/rest/shifts/",
        dataType: 'json',
        data: JSON.stringify({
            shiftId: shift.shiftId,
            startTime: shift.startTime,
            endTime: shift.endTime,
            userId: shift.userId,
            userName: shift.userName,
            departmentId: shift.departmentId,
            role: shift.role,
            tradeable: bool,
            responsibleUser: shift.responsibleUser
        }),
        success: function (data) {
            console.log("Result: " + data);
            returnValue = JSON.parse(data);
            return returnValue;
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

function getUnapprovedOvertime(startTime, endTime) {
    $("#overtime-table").empty();
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/overtime/",
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function (data) {
            addRow(data);
            getCount(data);
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

function getAvailableShiftNumber(startTime, endTime) {

    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/tradeable/" + startTime + "/" + endTime,
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function (data) {
            getCount(data);
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

function assignAvailableShift(shiftId) {
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/" + shiftId,
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function (shift) {
            console.log(shift);
            userId = parseInt(window.localStorage.getItem("userid"));
            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    "Authorization": "Bearer " + localStorage.getItem("token")
                },
                type: "POST",
                url: "/MinVakt/rest/shifts/assign/" + shiftId + "/" + userId,
                dataType: 'text',
                data: JSON.stringify({
                    shiftId: shift.shiftId,
                    userId: userId,
                }),
                success: function () {
                    console.log("Result: Skift tatt");
                    setShiftTradeablePut(shift, false);
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
                    },
                    400: function () {
                        console.log(data);
                    }
                }
            })
        }
    })
}

function setUserAvailable(start, end) {
    userId = parseInt(window.localStorage.getItem("userid"));
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        type: "POST",
        url: "/MinVakt/rest/users/available/" + userId + "/" + start + "/" + end,
        dataType: 'text',
        data: JSON.stringify({
            userId: userId,
            start: start,
            end: end
        }),
        success: function () {
            console.log("Result: Satt ledig");
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
            },
            400: function () {
                console.log(data);
            }
        }
    })
}

function getAvailableUsers(startTime, endTime) {

    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/users/availability/" + startTime + "/" + endTime,
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function (data) {
            console.log(data);
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
            },
            400: function () {
                console.log(data);
            }
        }
    })
}

function getAllOvertimeRequest(){

    $.ajax({
        type: "GET",
        url: "MinVakt/rest/shifts/overtime",
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function (data){
            console.log(data);
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
            },
            400: function () {
                console.log(data);
            }
        }
    })
}

$(document).ready(function() {
    getUnapprovedOvertime(0,1589483849399);
    $("#evening").toggleClass("dropdown-active");
});

$( function() {
    $("#date-picker" ).datepicker();
});





