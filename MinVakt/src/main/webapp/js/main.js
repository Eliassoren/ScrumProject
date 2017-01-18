/**
 * Created by EliasBrattli on 12/01/2017.
 */
var day = new Date();
var month = day.getMonth();
var monthNames = new Array(12)

var year = day.getFullYear();
var date = new Date(new Date().getFullYear(), 0, 1);
var week = 0;
var shiftArray = new Array(Number(new Date(year, month + 1, 0).getDate()));

function getShiftById(id){
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/" + id,
        success: function(data){
            console.log("Shift: " + data);
        }
    })
}

function getShiftsForUser(year, month, userId) {
        $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/" + getFirstDateOfEachMonth(year)[month].getTime() + "/" + (new Date(year, month + 1, 0)).getTime() + "/" + userId,
            //async: false,
        success: function(data){
            console.log(data.length);
            shiftArray = new Array(Number(new Date(year, month + 1, 0).getDate()));
            for (i = 0; i < data.length; i++){
                console.log(" Lagt til " + data[i].shiftId);
                var shiftBefore = String(new Date(data[i].startTime)).split(" ")[2];
                console.log(" Split :" + String(new Date(data[i].startTime)).split(" ")[2]);
                shiftArray[Number(shiftBefore)] = data[i];
            }
            generateCalendar(shiftArray,year, month);

        }
    });
}

// This script is released to the public domain and may be used, modified and
// distributed without restrictions. Attribution not necessary but appreciated.
// Source: https://weeknumber.net/how-to/javascript

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


function generateCalendar(shiftArray,year, month){
    console.log(getFirstDateOfEachMonth(year)[month]);
    var firstDate = getFirstDateOfEachMonth(year)[month];
    var lastDateOfMonth = new Date(year, month + 1, 0).getDate();
    var count = getMonday(firstDate);
    var monday = convertToMonday(firstDate); // first monday of calendar view (of previous month)
    var monthPreviewed = 0; // 0 when previous, 1 when current, 2 when next
    var lastDateOfPrevMonth = new Date(monday.getFullYear(), monday.getMonth() + 1, 0).getDate();
    var shiftDesc = "";
    var shiftTime = "h";
    moment().year(year);
    //$(".event").remove();
    $(".day").each(function () {
        $(this).find(".date").text(count);
        if (monthPreviewed == 1 || (count < 14 && monthPreviewed === 0)){
            if(shiftArray[count] != null){
                shiftDesc = "Avdeling " +  shiftArray[count].departmentId;
                shiftTime = moment(new Date(shiftArray[count].startTime)).format('hh:mm') + " - " + moment(new Date(shiftArray[count].endTime)).format('hh:mm');
                var eventdiv = $("<div/>").addClass("event").attr("id","day-"+count-1);
                $(this).append(eventdiv);
                eventdiv.append($("<span/>").addClass("event-desc").text(shiftDesc));
                eventdiv.append($("<span/>").addClass("event-time").text(shiftTime));
            }
        }
        if (count < lastDateOfPrevMonth) {
            count++;
        } else {
            count = 1;
            lastDateOfPrevMonth = lastDateOfMonth;
            monthPreviewed++;
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

    $.when(getShiftsForUser(2017, month, 16)).then(generateCalendar(2017, 0));
});


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