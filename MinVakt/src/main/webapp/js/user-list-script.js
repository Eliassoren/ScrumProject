/**
 * Created by Chris on 25.01.2017.
 */


$(document).ready(function() {
    $(".days").click(function(){
        $(".opacity").addClass("blur-activate");
        $(".employee-form-banner").addClass("show-banner");
    });

    $(".fa-times").click(function(){
        $(".opacity").removeClass("blur-activate");
        $(".employee-form-banner").removeClass("show-banner");
    });
});

function getAllUsers() {

    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/users/all",
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function (data) {
            console.log(data);
        }
    })
}

function addRowEmployee(data) {

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
