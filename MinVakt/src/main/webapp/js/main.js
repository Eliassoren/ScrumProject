/**
 * Created by EliasBrattli on 12/01/2017.
 */
var dateNow = new Date();
var month = dateNow.getMonth();
var year = dateNow.getFullYear();
var monthNames = new Array(12)
const MONTH_PREV = 0;
const MONTH_CURR = 1;
const MONTH_NEXT = 2;
//var date = new Date(new Date().getFullYear(), 0, 1);
var week = 0;
var shiftArray = new Array(Number(new Date(year, month + 1, 0).getDate()));
var tradeableShifts = new Array(Number(new Date(year, month + 1, 0).getDate()));

function getShiftById(id){
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/" + id,
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function(data){
            console.log("Shift: " + data);
        },
        statusCode: {
            401: function () {
                localStorage.removeItem("token");
                window.location.replace("/MinVakt/");
            }
        }
    })
}



// This script is released to the public domain and may be used, modified and
// distributed without restrictions. Attribution not necessary but appreciated.
// Source: https://weeknumber.net/how-to/javascript

/**
 *
 * @param date
 * @returns a date converted to the monday in given date's week
 */
function convertToMonday( date ) {
    var day = date.getDay() || 7;
    if( day !== 1 )
        date.setHours(-24 * (day - 1));
    return date;
}
function setHrs(callBack, day1){
    callBack(day1);
}
function getMonday( dateFunc ) {
    var day1 = function (dateFunc) {
        return Number(dateFunc().getDay()) || 7;
    };
    if (day1(dateFunc) !== 1) {
    setHrs(function (day1) {
        dateFunc().setHours(-24 * (day1(dateFunc) - 1));
    }, day1);
    }
    var date = function(dateFunc){
        return dateFunc().getDate();
    };
    return date(dateFunc);
}
function getMonday2(firstDate){
    var day1 = Number(firstDate.getDay()) || 7;
    if(day1 !== 1)
        firstDate.setHours(-24 * (day1-1));
    return firstDate.getDate();
}
monthNames[0] = "Januar";
monthNames[1] = "Februar";
monthNames[2] = "Mars";
monthNames[3] = "April";
monthNames[4] = "Mai";
monthNames[5] = "Juni";
monthNames[6] = "Juli";
monthNames[7] = "August";
monthNames[8] = "September";
monthNames[9] = "Oktober";
monthNames[10] = "November";
monthNames[10] = "November";
monthNames[11] = "Desember";

function getFirstDaysOfEachMonth(year){
    var firstDays = new Array(12);
    var date;
    for(var i = 0; i < 12; i++){
        date = new Date(year,i,1);
        firstDays[i] = date.getDay();
    }
    return firstDays;
}
function getFirstDateOfEachMonth(year){
    var dates = new Array(12);
    var date;
    for(var i = 0; i < dates.length; i++){
        date = new Date(year,i,1);
        dates[i] = date;
    }
    return dates;
}
var tab = getFirstDaysOfEachMonth(year);
var out = "";
for(var i = 0; i< tab.length;i++){
    out += tab[i]+"\n";
}
var title = monthNames[month] + " " + year;
var shifts = new Array(31);

function clearCalendar() {
    //alert("clear");
    $(".event").remove();
    $(".free-event").remove();
    $(".free-event-text").remove();
}

function formatTime(date) {
    var string = date.getHours() + ":" + date.getMinutes();
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

function generateCalendar(tradeableShifts,shiftArray,year,month){
    var firstDate = getFirstDateOfEachMonth(year)[month];
    var lastDateOfMonth = new Date(year, month + 1, 0).getDate();
    /*var day = getMonday(function(){
        firstDate = getFirstDateOfEachMonth(year)[month];
        return firstDate;
    });*/
    var day = getMonday2(firstDate);

    console.log("first "+ firstDate);
    console.log("monday "+day);
    //console.log("monday2 "+day2);
    //var day = moment().startOf(moment(firstDate).isoWeek()).getDate();
    var noPrevMonth = day == 1; // Special case when month starts on a monday..
    var monday = convertToMonday(firstDate); // first monday of calendar view (of previous month)
    var monthStatus = 0; // 0 when previous, 1 when current, 2 when next
    var lastDateOfPrevMonth = new Date(year, month, 0).getDate();
    var shiftDesc = "";
    var shiftTime = "";

    moment().year(year);

    $(".day").each(function () {
        var box = $(this).find(".date").text(day);
        if (monthStatus == MONTH_CURR){
            if(shiftArray != null) {
                if (shiftArray[day] != null) {
                    shiftDesc = "Avdeling " + shiftArray[day].departmentId;
                    shiftTime = formatTime(new Date(shiftArray[day].startTime)) + " - " + formatTime(new Date(shiftArray[day].endTime));
                    var eventDiv = $("<div/>").addClass("event").attr("shiftId", shiftArray[day].shiftId);
                    $(this).append(eventDiv);
                    eventDiv.append($("<span/>").addClass("event-desc").text(shiftDesc));
                    eventDiv.append($("<span/>").addClass("event-time").text(shiftTime));
                }
            }
            $(this).removeClass("other-month");
            if(tradeableShifts != null) {
                shiftsInOneDay = tradeableShifts[day];
                if (shiftsInOneDay != null) {
                    console.log("tradeable");
                    var amountShifts = shiftsInOneDay.length;
                    console.log(amountShifts);
                    shiftDesc = amountShifts + " ledige vakter";
                    //shiftTime = formatTime(new Date(tradeableShifts[day].startTime)) + " - " + formatTime(new Date(tradeableShifts[day].endTime));
                    var tradeableEvent = $("<div/>").addClass("free-event").attr("shiftId", tradeableShifts[day].shiftId);
                    $(this).append(tradeableEvent);
                    tradeableEvent.append($("<span/>").addClass("free-event-text").text(shiftDesc));
                }
            }
        }else if((monthStatus === MONTH_PREV || monthStatus === MONTH_NEXT)){
            $(this).addClass("other-month");
        }

        if (day < lastDateOfPrevMonth) {
            console.log("lastprev "+lastDateOfPrevMonth+ " m "+month);
            if(noPrevMonth){
                monthStatus = MONTH_CURR;
                $(this).removeClass("other-month");
                lastDateOfPrevMonth = lastDateOfMonth;
                noPrevMonth = false;
            }
            day++;
        } else {
            // Reset daycount
            day = 1;
            lastDateOfPrevMonth = lastDateOfMonth;
            monthStatus++;
        }
    });
    var week = firstDate.getWeek();

    $(".week-number").each(function(){
        $(this).find(".week").text(week);
        if(week < moment().isoWeeksInYear()){
            week++;
        }else{
            week = 1;
        }
    })
}
$(document).ready(function() {
    //var localStorage = localStorage.getItem();
    $("#hamburger-toggle").click(function () {
        $("#hamburger-menu").toggleClass("hamburger-menu-open");
        $("#hamburger-toggle").toggleClass("hamburger-toggle-open");
    });
    $("#hamburger-calendar").click(function() {
        window.location.href = "/MinVakt/html/calendar.html";
    });
    $("#hamburger-shift").click(function() {
       window.location.href = "/MinVakt/html/shift.html";
    });
    $("#hamburger-shifttrade").click(function() {
        window.location.href = "/MinVakt/html/shift-tradable.html";
    });
    $("#hamburger-logout").click(function() {
        localStorage.removeItem("token");
        window.location.replace("/MinVakt/");
    });

//alert(out);
    //alert(getMonday(new Date(2017, 0, 1)));
    $("#month-title").text(title);

    /**
     * "Decrease" month
     */
    $("#left-arrow").click(function () {
        if (month > 0) {
            month--;
        } else {
            year--;
            month = 11;
        }
        title = monthNames[month] + " " + year;
        $("#month-title").text(title);
        //clearCalendar();
        clearCalendar();
        // TODO: add localstorage instead of hardcode
        getShiftsForUser(year,month,8);
        getTradeableShifts(year,month,8);
    });

    /**
     * "Increase" month
     */
    $("#right-arrow").click(function () {
        // Increment month and/or year
        if (month < 11) {
            month++;
        } else {
            year++;
            month = 0;
        }
        title = monthNames[month] + " " + year;
        $("#month-title").text(title);
        clearCalendar();
        // Localstorage
        // todo: add localstorage instead of hardcoded
        getShiftsForUser(year,month,8);
        getTradeableShifts(year,month,8);
        //clearCalendar();
    });

    $(".day").click(function(){
        var shiftId = $(this).children('.event').attr('shiftId');
        if($(".event-open").length == 0){
            $("body").prepend("<div class='event-open'></div>");
            $.ajax({
                type: "GET",
                url: "/MinVakt/rest/shifts/" + shiftId,
                headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
                success: function(data){
                    console.log(data);
                    $('.event-open').append('<h5>Shift ID: ' +data.shiftId + '</h5>');
                    $('.event-open').append("<p><b>Tid:</b> " + formatTime(new Date(data.startTime)) + " - " + formatTime(new Date(data.endTime)) + "</p>");
                    $('.event-open').append('<p> <b>Ansatt: </b> ' +data.userName + '</p>');
                    if(data.tradeable){
                        $('.event-open').append("<p class='text-red'>Vakt satt som tilgjengelig for bytte</p>");
                    }
                    $('.event-open').append($("<div/>").addClass("button").text("Lukk").click(function(){
                        $('.event-open').remove();
                    }));
                    $('.event-open').append($("<div/>").addClass("button-tradeable").text("Sett ledig").click(function(){
                        $('.event-open').remove();
                        if(data.tradeable){
                            $("body").prepend("<div class='event-open'></div>");
                            $('.event-open').append('<h3> Din vakt er allerede tilgjengelig for bytte </h3>');
                            $('.event-open').append($("<div/>").addClass("button").text("Lukk").click(function(){
                                $('.event-open').remove();
                            }));
                        } else {
                            getShiftAndTrade(data.shiftId, true);
                        }
                    }));
                },
                statusCode: {
                    401: function () {
                        localStorage.removeItem("token");
                        window.location.replace("/MinVakt/");
                    }
                }
            })
        }
    });

    $(".free-event-text").click(function(){

    });

    $.when(getShiftsForUser(year, month, 8)).then(generateCalendar(null,shiftArray,year, month));
    $.when(getTradeableShifts(year, month, 8)).then(generateCalendar(tradeableShifts,null,year, month));
});

function getShiftAndTrade(id, bool){
    alert(localStorage.getItem("token"));
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
function getShiftsForUser(year, month, userId) {
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/" + getFirstDateOfEachMonth(year)[month].getTime() + "/" + (new Date(year, month + 1, 0)).getTime() + "/" + userId,
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function(data){
            console.log(data.length);
            shiftArray = new Array(Number(new Date(year, month + 1, 0).getDate()));
            for (i = 0; i < data.length; i++){
                console.log(" Lagt til " + data[i].shiftId);
                var shiftBefore = String(new Date(data[i].startTime)).split(" ")[2];
                console.log(" Split :" + String(new Date(data[i].startTime)).split(" ")[2]);
                shiftArray[Number(shiftBefore)] = data[i];
            }
            generateCalendar(null,shiftArray, year, month);
        },
        statusCode: {
            401: function () {
                localStorage.removeItem("token");
                window.location.replace("/MinVakt/");
            }
        }
    });
}
function getTradeableShifts(year, month, userId){
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/tradeable/" + getFirstDateOfEachMonth(year)[month].getTime() + "/" + (new Date(year, month + 1, 0)).getTime() + "/" +userId,
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function(data){
            var monthLen = Number(new Date(year, month + 1, 0).getDate());
            tradeableShifts = new Array(monthLen);
            for(var i = 0; i < monthLen;i++){
                //var shiftBefore = String(new Date(data[i].startTime)).split(" ")[2];
                var dayArr = new Array();
                for(var j = 0; j < data.length;j++) {
                    console.log("Data["+i+"]= "+data[i]);
                    var shiftDate = String(new Date(data[j].startTime)).split(" ")[2];
                    if (Number(shiftDate === i)){
                        dayArr.push(data[j]);
                    }
                }
                if(dayArr != null){
                    tradeableShifts[i] = dayArr;
                }
            }
            generateCalendar(tradeableShifts,null,year,month);
        },
        statusCode: {
            401: function () {
                localStorage.removeItem("token");
                window.location.replace("/MinVakt/");
            }
        }
    });
}
function setShiftTradeablePut(shift, bool) {
    alert(localStorage.getItem("token"));
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
            if(returnValue) {
                $("body").prepend("<div class='event-open'></div>");
                $('.event-open').append('<h3> Din vakt er nå tilgjengelig for bytte </h3>');
                $('.event-open').append($("<div/>").addClass("button").text("Lukk").click(function(){
                    $('.event-open').remove();
                }));
            }
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

// Returns the ISO week of the date.
Date.prototype.getWeek = function() {
    var date = new Date(this.getTime());
    date.setHours(0, 0, 0, 0);
    // Thursday in current week decides the year.
    date.setDate(date.getDate() + 3 - (date.getDay() + 6) % 7);
    // January 4 is always in week 1.
    var week1 = new Date(date.getFullYear(), 0, 4);
    // Adjust to Thursday in week 1 and count number of weeks from date to week1.
    return 1 + Math.round(((date.getTime() - week1.getTime()) / 86400000
            - 3 + (week1.getDay() + 6) % 7) / 7);
};

// Returns the four-digit year corresponding to the ISO week of the date.
Date.prototype.getWeekYear = function() {
    var date = new Date(this.getTime());
    date.setDate(date.getDate() + 3 - (date.getDay() + 6) % 7);
    return date.getFullYear();
};

$( function() {
    $("#date-picker" ).datepicker();
});

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

$(document).ready(function() {
        getAvailableShifts(0,1589483849399);
});

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

        tabBody = $(".table");
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
        tabBody.append(row);
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

/*function getShiftArray(month, year) {
    var firstDate = getFirstDateOfEachMonth(year)[month];
    var lastDate = new Date(firstDate.getFullYear(), firstDate.getMonth() + 1, 0);
    return $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/1",
        contentType: "application/json",
        response: "json",
        data: JSON.stringify({
            shiftId:"0",
            startTime: firstDate.getMilliseconds(),
            endTime: lastDate.getMilliseconds(),
            userId:0,
            userName:"hallo",
            departmentId:"0",
            role:"0",
            tradeable:"false",
            responsibleUser:"false",
        }),
        success: function (data){
            var jsonshit = JSON.parse(data);
            console.log(jsonshit);
        }
    })
}*/


