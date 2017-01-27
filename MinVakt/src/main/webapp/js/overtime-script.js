/**
 * Created by Chris on 25.01.2017.
 */

/**
 * Created by Chris on 12.01.2017.
 */

$(document).ready(function() {
    $("#hamburger-toggle").click(function(){
        $(".experimental-menu").toggleClass("experimental-menu-activated");
    });
});

$(document).on("input", function() {
    refreshTable()
});

$(document).ready(function() {
    $("#overtime-dropdown-toggle-evening").click(function(){
        $("#overtime-evening").toggleClass("overtime-dropdown-active");
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

function addRow(data) {

    console.log(data);

    //DUMMY DATA
    //var text= '[{"shiftId":1,"startTime":1483254000000,"endTime":1483282800000,"userId":16,"userName":"Siri Sirisen","departmentId":1,"role":1,"tradeable":true,"responsibleUser":false},{"shiftId":6,"startTime":1483542000000,"endTime":1483570740000,"userId":16,"userName":"Siri Sirisen","departmentId":1,"role":1,"tradeable":false,"responsibleUser":false}]'
    var obj = data;

    //TODO this needs to be removed after the table slector below works
    var table = "dateNow-table";


    if (!document.getElementsByTagName) return;

    for (var i = 0; i < obj.length; i++) {

        //TODO: This part needs to add to the correct table given a working time.

        var startTime = new Date(obj[i].startTime).getHours();
        var isFree = obj[i].tradeable;

        if (startTime >= 8 && startTime < 16) {
            table = "dateNow-table";
        } else if (startTime >= 16 && startTime < 25) {
            table = "evening-table";
        } else if (startTime >= 0 && startTime < 8) {
            table = "night-table";
        } else {
            alert("ERROR CHECK THE ADD ROW FUNCTION IN SHIFT-SCRIPT.JS!")
        }

        tabBody = document.getElementById(table);
        row = document.createElement("tr");
        row.className = "tr" + i;
        cell1 = document.createElement("td");
        cell2 = document.createElement("td");
        cell3 = document.createElement("td");
        cell4 = document.createElement("Button");
        cell4.className = "listButton " + "id" + obj[i].shiftId;
        textnode1 = document.createTextNode(obj[i].userName);
        textnode2 = document.createTextNode(formatTime(startTime + ":" + new Date(obj[i].startTime).getMinutes()));
        textnode3 = document.createTextNode(formatTime(new Date(obj[i].endTime).getHours() + ":" + new Date(obj[i].endTime).getMinutes()));
        textnode4 = document.createTextNode("Ta vakt");
        cell1.appendChild(textnode1);
        cell2.appendChild(textnode2);
        cell3.appendChild(textnode3);
        cell4.appendChild(textnode4)
        row.appendChild(cell1);
        row.appendChild(cell2);
        row.appendChild(cell3);
        row.appendChild(cell4);
        tabBody.appendChild(row);
        //table = "evening-table";
        if (obj[i].userName == ""){
            $('.tr' + i).css('background-color', '#FF5468');
            //$('.id' + obj[i].shiftId).css('background-color', '#BA3E4C');
        } else if (isFree) {
            $('.tr' + i).css('background-color', '#4DFA90');
            //$('.id' + obj[i].shiftId).css('background-color', '#40CD76');
        }
    }
}

function getShiftAndTrade(id, bool){
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/" + id,
        success: function(data){
            console.log(data);
            setShiftTradeablePut(data, bool)
        }
    })
}

function setShiftTradeablePut(shift, bool) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
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
        }
    })
}

function getAvailableShifts(startTime, endTime) {

    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/tradeable/" + startTime + "/" + endTime,
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function (data) {
            addRow(data);
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
                        window.location.replace("/MinVakt/");
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
                window.location.replace("/MinVakt/");
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
                window.location.replace("/MinVakt/");
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
        success: function (data){
            console.log(data);
        },
        statusCode: {
            401: function () {
                localStorage.removeItem("token");
                window.location.replace("/MinVakt/");
            },
            400: function () {
                console.log(data);
            }
        }
    })
}

$(document).ready(function() {
    getAvailableShifts(0,1589483849399);
});

$( function() {
    $("#date-picker" ).datepicker();
});





