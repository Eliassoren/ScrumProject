/**
 * Created by Chris on 12.01.2017.
 */

$(document).ready(function() {
    $("#hamburger-toggle").click(function(){
        $("#hamburger-menu").toggleClass("hamburger-menu-open");
        $("#hamburger-toggle").toggleClass("hamburger-toggle-open");
    });
});


$(document).ready(function() {
    $("#dropdown-toggle-day").click(function(){
        $("#day").toggleClass("dropdown-active");
    });
    $("#dropdown-toggle-evening").click(function(){
        $("#evening").toggleClass("dropdown-active");
    });
    $("#dropdown-toggle-night").click(function(){
        $("#night").toggleClass("dropdown-active");
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

window.onload = function addRow() {

    //DUMMY DATA
    var text= '[{"shiftId":1,"startTime":1483254000000,"endTime":1483282800000,"userId":16,"userName":"Siri Sirisen","departmentId":1,"role":1,"tradeable":false,"responsibleUser":false},{"shiftId":6,"startTime":1483542000000,"endTime":1483570740000,"userId":16,"userName":"Siri Sirisen","departmentId":1,"role":1,"tradeable":false,"responsibleUser":false}]'
    var obj = JSON.parse(text);
    console.log(obj);

    //TODO this needs to be removed after the table slector below works
    table = "dateNow-table";


    if (!document.getElementsByTagName) return;

    for (var i = 0; i < obj.length; i++) {

        //TODO: This part needs to add to the correct table given a working time.

        var startTime = new Date(obj[i].startTime).getHours();

         if (startTime >= 8 && startTime < 16) {
         table = "day-table";
         } else if (startTime >= 16 && startTime < 25) {
         table = "evening-table";
         } else if (startTime >= 0 && startTime < 8) {
         table = "night-table";
         } else {
         alert("ERROR CHECK THE ADD ROW FUNCTION IN SHIFT-SCRIPT.JS!")
         }



            tabBody = document.getElementById(table);
            row = document.createElement("tr");
            cell1 = document.createElement("td");
            cell2 = document.createElement("td");
            cell3 = document.createElement("td");
            cell4 = document.createElement("td");
            textnode1 = document.createTextNode(obj[i].userName);
            textnode2 = document.createTextNode(formatTime(startTime + ":" + new Date(obj[i].startTime).getMinutes()));
            textnode3 = document.createTextNode(formatTime(new Date(obj[i].endTime).getHours() + ":" + new Date(obj[i].endTime).getMinutes()));
            textnode4 = document.createTextNode(obj[i].departmentId);
            cell1.appendChild(textnode1);
            cell2.appendChild(textnode2);
            cell3.appendChild(textnode3);
            cell4.appendChild(textnode4);
            row.appendChild(cell1);
            row.appendChild(cell2);
            row.appendChild(cell3);
            row.appendChild(cell4);
            tabBody.appendChild(row);
        //table = "evening-table";
    }
};

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
                window.location.replace("/MinVakt/");
            }
        }
    })
}

function setShiftTradeablePut(shift, bool) {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            "Authorization": "Bearer " + localStorage.getItem("token")
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
        },
        statusCode: {
            401: function () {
                localStorage.removeItem("token");
                window.location.replace("/MinVakt/");
            }
        }
    })
}

function getAvailableShifts(startTime, endTime) {
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/tradeable/" + startTime + "/" + endTime,
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function (data) {
            return data;
        },
        statusCode: {
            401: function () {
                localStorage.removeItem("token");
                window.location.replace("/MinVakt/");
            }
        }
    })
}

