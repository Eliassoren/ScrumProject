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



function convertToMonday( date ) {
    var day = date.getDay() || 7;
    if( day !== 1 )
        date.setHours(-24 * (day - 1));
    return date;
}
function getMonday( date ) {
    var day = date.getDay() || 7;
    if( day !== 1 )
        date.setHours(-24 * (day - 1));
    return date.getDate();
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


function generateCalendar(shiftArray,year, month){
    var firstDate = getFirstDateOfEachMonth(year)[month];
    var lastDateOfMonth = new Date(year, month + 1, 0).getDate();
    var day = getMonday(firstDate);
    var noPrevMonth = day == 1; // Special case when month starts on a monday..
    var monday = convertToMonday(firstDate); // first monday of calendar view (of previous month)
    var monthStatus = 0; // 0 when previous, 1 when current, 2 when next
    var lastDateOfPrevMonth = new Date(monday.getFullYear(), monday.getMonth() + 1, 0).getDate();
    var shiftDesc = "";
    var shiftTime = "";
    moment().year(year);

    $(".dateNow").each(function () {
        var box = $(this).find(".date").text(day);

        if (monthStatus == MONTH_CURR){
            if(shiftArray[day] != null){
                shiftDesc = "Avdeling " +  shiftArray[day].departmentId;
                shiftTime = formatTime(new Date(shiftArray[day].startTime)) + " - " + formatTime(new Date(shiftArray[day].endTime));
                var eventdiv = $("<div/>").addClass("event").attr("shiftId", shiftArray[day].shiftId);
                $(this).append(eventdiv);
                eventdiv.append($("<span/>").addClass("event-desc").text(shiftDesc));
                eventdiv.append($("<span/>").addClass("event-time").text(shiftTime));
                }
            $(this).removeClass("other-month");
        }else if((monthStatus == MONTH_PREV || monthStatus == MONTH_NEXT) && (Number(box.find("date").text()) < 14 || Number(box.find("date").text())>21)){
            $(this).addClass("other-month");
        }
        if (day < lastDateOfPrevMonth) {
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
        getShiftsForUser(year,month,16);
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
        getShiftsForUser(year,month,16);
        //clearCalendar();
    });

    $(".dateNow").click(function(){
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

    $.when(getShiftsForUser(2017, month, 16)).then(generateCalendar(2017, 0));
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
            generateCalendar(shiftArray, year, month);
        },
        statusCode: {
            401: function () {
                localStorage.removeItem("token");
                window.location.replace("/MinVakt/");
            }
        }
    });
}
function getAvailableShifts(year,month,userId){
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/" + id,
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function(data){
          // TODO : Make function for extracting available shifts
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
}

// Returns the four-digit year corresponding to the ISO week of the date.
Date.prototype.getWeekYear = function() {
    var date = new Date(this.getTime());
    date.setDate(date.getDate() + 3 - (date.getDay() + 6) % 7);
    return date.getFullYear();
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
