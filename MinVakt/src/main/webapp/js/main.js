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
var tradeableShifts;
/**
 * Months of the year
 * @type {string}
 */
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


var title = monthNames[month] + " " + year;
var shifts = new Array(31);


// This script is released to the public domain and may be used, modified and
// distributed without restrictions. Attribution not necessary but appreciated.
// Source: https://weeknumber.net/how-to/javascript

/**
 * Function is used to assess the start of the week of any date.
 * @param firstDate
 * @returns {number} The start of the week of given date
 */
function getMonday(firstDate){
    var day1 = Number(firstDate.getDay()) || 7;
    if(day1 !== 1)
        firstDate.setHours(-24 * (day1-1));
    return firstDate.getDate();
}
// End source




/**
 *
 * @param year the given year of calendar view
 * @returns {Array} the first date of each month in given year
 */
function getFirstDateOfEachMonth(year){
    var dates = new Array(12);
    var date;
    for(var i = 0; i < dates.length; i++){
        date = new Date(year,i,1);
        dates[i] = date;
    }
    return dates;
}
/**
 * Clears contents of calendar, and opens for insertion of new content.
 */
function clearCalendar() {
    //alert("clear");
    $(".event").remove();
    $(".free-event").remove();
    $(".free-event-text").remove();
}
/**
 *
 * @param date the date object to be formatted
 * @returns {string}
 */
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
/**
 * Adds an "event" as in information of a shift in a day element of calendar
 * @param day The current day
 * @param shiftArray The shifts of the current day
 * @param box The current calendar element
 */
function appendEvent(day,shiftArray,box){
    if (shiftArray[day] != null) {
        shiftDesc = "Avdeling " + shiftArray[day].departmentId;
        shiftTime = formatTime(new Date(shiftArray[day].startTime)) + " - " + formatTime(new Date(shiftArray[day].endTime));
        var eventDiv = $("<div/>").addClass("event").attr("shiftId", shiftArray[day].shiftId);
        eventDiv.addClass(shiftArray[day].tradeable?"event-active":"");
        box.append(eventDiv);
        eventDiv.append($("<span/>").addClass("event-desc").text(shiftDesc));
        eventDiv.append($("<span/>").addClass("event-time").text(shiftTime));
    }
}
/**
 *  Adds a text containing amount of events ready for changeover
 * @param day The current day
 * @param shiftsInOneDay The amount of free shifts in current day
 */
function appendFreeEvent(day,shiftsInOneDay){

    if (shiftsInOneDay != 0) {
        shiftDesc = shiftsInOneDay + " ledige vakter";
        var tradeableEvent = $("<div/>").addClass("free-event");
        var dayOfThisMonth = $(".day:contains('"+ day +"')");
        if (!dayOfThisMonth.hasClass('other-month') && dayOfThisMonth.find(".event").length == 0){
            dayOfThisMonth.append(tradeableEvent);
            tradeableEvent.append($("<span/>").addClass("free-event-text").text(shiftDesc));

        }
    }
}
/**
 *
 * @param tradeableShifts
 * @param shiftArray
 * @param year
 * @param month
 */
function generateCalendar(tradeableShifts,shiftArray,year,month){
    var firstDate = getFirstDateOfEachMonth(year)[month];
    var lastDateOfMonth = new Date(year, month + 1, 0).getDate();
    /*var day = getMonday(function(){
        firstDate = getFirstDateOfEachMonth(year)[month];
        return firstDate;
    });*/
    var day = getMonday(firstDate); // The first number to write into calendar..
    var noPrevMonth = day == 1; // Special case when month starts on a monday..
    var monthStatus = 0; // 0 when previous, 1 when current, 2 when next
    var lastDateOfPrevMonth = new Date(year, month, 0).getDate();
    moment().year(year); // Moment library finds amount of weeks in one year

    $(".day").each(function () {
        var box = $(this).find(".date").text(day);
        // Events are added only to days in the current month, as day number could duplicate
        if (monthStatus == MONTH_CURR ){
            if(shiftArray != null) {
                appendEvent(day,shiftArray,$(this));
            }
            $(this).removeClass("other-month"); // Days in current month are white, thus class 'other-month' is removed
        }else if(monthStatus !== MONTH_CURR){
            $(this).addClass("other-month"); // Days in other month have a grayer style, in class 'other-month'
        }else if((monthStatus === MONTH_PREV || monthStatus === MONTH_NEXT)){
            $(this).addClass("other-month");
        }
        if (day < lastDateOfPrevMonth) {
            if(noPrevMonth){
                // Special case, update month status immediately
                monthStatus = MONTH_CURR;
                $(this).removeClass("other-month");
                lastDateOfPrevMonth = lastDateOfMonth; // Count to current month's limit instead of previous one
                noPrevMonth = false;
                if(shiftArray != null) {
                    appendEvent(day,shiftArray,$(this));
                }
            }
            // As long as day is within month's daycount, keep incrementing
            day++;
        } else {
            // Reset daycount, update month status
            day = 1;
            lastDateOfPrevMonth = lastDateOfMonth;
            monthStatus++;
        }
    });

    var week = firstDate.getWeek();

    $(".week-number").each(function(){
        $(this).find(".week").text(week);
        // As long as within amount of weeks in current year, keep incrementing for each element
        if(week < moment().isoWeeksInYear()){
            week++;
        }else{
            week = 1;
        }
    });
    // Adds shifts up for changeover to calendar.
    getTradeableShifts(year, month);
}
$(document).ready(function() {
    var userId = localStorage.getItem("userid");
    $("#hamburger-toggle").click(function () {
        $("#hamburger-menu").toggleClass("hamburger-menu-open");
        $("#hamburger-toggle").toggleClass("hamburger-toggle-open");
    });
    $("#hamburger-calendar").click(function() {
        window.location.href = "/MinVakt/html/calendar.html";
    });
    $("#hamburger-shift").click(function() {
       window.location.href = "/MinVakt/html/not-currently-in-use.html";
    });
    $("#hamburger-shifttrade").click(function() {
        window.location.href = "/MinVakt/html/user-shift-view.html";
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
        getShiftsForUser(year,month,userId);
        //getTradeableShifts(year,month,8);
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
        getShiftsForUser(year,month,userId);
        //getTradeableShifts(year,month,8);
        //clearCalendar();
    });

    function formatDate(date) {
        var dayFormat = (date.getDate() < 10) ? "0" + date.getDate() : date.getDate();
        var monthFormat = (date.getMonth() < 10) ? "0"+ (date.getMonth() +1) : (date.getMonth() +1);
        var yearFormat = date.getFullYear();
        return dayFormat + "." + monthFormat + "." + yearFormat;
    }

    $(".day").click( function(){
        var shiftId = $(this).children('.event').attr('shiftId');
        if($(this).find(".event").length > 0 && $(".blur").length == 0){
                $("body").prepend("<div id='banner-div'></div>");
                $("#banner-div").load("/MinVakt/html/template/banner-shift.html", function () {

                    $(".container").click(function () {
                        $("#banner-div").remove();
                        if ($(".container").hasClass("blur")){ $(".container").removeClass("blur")};
                        $(".container").unbind();
                    });
                    $(".container").addClass("blur");
                });


            $.ajax({
                type: "GET",
                url: "/MinVakt/rest/shifts/" + shiftId,
                headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
                success: function(data){
                    $("#banner-shift-date").text(formatDate(new Date(data.startTime)));
                    $("#banner-shift-time span:nth-child(2)").text(formatTime(new Date(data.startTime)) + " - " + formatTime(new Date(data.endTime)));
                    $("#banner-shift-dep span:nth-child(2)").text(data.departmentId);
                    if(data.tradeable){
                        $("#banner-shift").css("background", "#FC4A1A");
                        $("#banner-shift-date").append(" (Til bytte)");
                        $(".approve").text("Ikke bytt vakt");
                    }

                    $(".closer").click(function () {
                        $("#banner-shift").remove();
                        if ($(".container").hasClass("blur")){ $(".container").removeClass("blur")};
                        $(".container").unbind();
                    });
                    $(".absence").click(function(){
                        $("#banner-shift").remove();
                        if ($(".container").hasClass("blur")){ $(".container").removeClass("blur")};
                        $(".container").unbind();
                        bannerAbsence(new Date(data.startTime),formatTime(new Date(data.startTime)));
                    });
                    $(".approve").click(function(){
                        $("#banner-shift").remove();
                        if ($(".container").hasClass("blur")){ $(".container").removeClass("blur")};
                        $(".container").unbind();
                        if(data.tradeable){
                            getShiftAndTrade(shiftId, false);
                            bannerAlert("Vakt fjernet fra bytte");
                        } else {
                            getShiftAndTrade(shiftId, true);
                            bannerAlert("Vakt satt til bytte");
                        }

                    });

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
                    404: function() {
                        console.log("ERROR: could not load shift");
                    }
                }
            })
        }
    });

    getShiftsForUser(year, month, localStorage.getItem("userid"));
});

function getShiftAndTrade(id, bool){
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/" + id,
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function(data){
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
function getShiftsForUser(year, month, userId) {
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/" + getFirstDateOfEachMonth(year)[month].getTime() + "/" + (new Date(year, month + 1, 0)).getTime() + "/" + userId,
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function(data){
            shiftArray = new Array(Number(new Date(year, month + 1, 0).getDate()));
            for (i = 0; i < data.length; i++){
                var shiftBefore = String(new Date(data[i].startTime)).split(" ")[2];
                shiftArray[Number(shiftBefore)] = data[i];
            }
            generateCalendar(null,shiftArray, year, month);
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
/**
 * Adds shifts up for changeoverin current month to calendar.
 * @param year The current year
 * @param month The current month
 */
function getTradeableShifts(year, month){
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/tradeable/" + getFirstDateOfEachMonth(year)[month].getTime() + "/" + (new Date(year, month + 1, 0)).getTime() + "/" + localStorage.getItem("userid"),
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function(data){

            var monthLen = Number(new Date(year, month + 1, 0).getDate());
            tradeableShifts = new Array(monthLen);
            // Reset count in each day
            for (var q = 0; q < tradeableShifts.length; q++){
                tradeableShifts[q] = 0;
            }
            // Count amount of available shifts in each day. Array corresponds with days in current month
            for(var i = 0; i < data.length; i++){
                var shiftDate = new Date(data[i].startTime).getDate();
                ++tradeableShifts[shiftDate];
            }

            for (var k = 0; k < tradeableShifts.length; k++){
                appendFreeEvent(k, tradeableShifts[k]);
            }

            $(".free-event-text").click(function(){
                console.log($(this).parent());
                $(".container").addClass("blur");
                $("#overlay-placer").load("/MinVakt/html/template/free-shift.html", function(){
                    $(".absolute-dropdown").click(function(){
                        $(this).toggleClass("dropdown-active");
                    });
                });
                $("body").prepend($("<div/>").addClass("overlay"));

                $(".overlay").click( function(){
                    $("#absolute-div").remove();
                    if ($(".container").hasClass("blur")){ $(".container").removeClass("blur")};
                    $(".overlay").remove();
                });
                getAvailableShifts(new Date(year,month, Number($(this).parent().parent().find(".date").text())).getTime(), new Date(year,month, Number($(this).parent().parent().find(".date").text())+1 ).getTime());

            });


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
/**
 *
 * @param shift The shift to be exchanged
 * @param bool The status of shift (available or not available)
 */
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


/**
 * Prototype added to date class
 * @returns {number} the ISO week of the date.
 */
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

/**
 * Prototype added to date class
 * @returns {number} the four-digit year corresponding to the ISO week of the date.
 */
Date.prototype.getWeekYear = function() {
    var date = new Date(this.getTime());
    date.setDate(date.getDate() + 3 - (date.getDay() + 6) % 7);
    return date.getFullYear();
};
/**
 * Activates datepicker.
 */
$( function() {
    $("#date-picker").datepicker();
});
/**
 *
 * @param startTime Start of period of available shifts
 * @param endTime End period of available shifts
 */
function getAvailableShifts(startTime, endTime) {
    $.ajax({
        type: "GET",
        url: "/MinVakt/rest/shifts/tradeable/" + startTime + "/" + endTime + "/" + localStorage.getItem("userid"),
        headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
        success: function (data) {
            console.log(data);
            addRow(data);
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
            404: function () {
                console.log("ERROR: No shifts found");
            }
        }
    })
}


/**
 *
 * @param data
 */
function addRow(data) {
    //DUMMY DATA
    //var text= '[{"shiftId":1,"startTime":1483254000000,"endTime":1483282800000,"userId":16,"userName":"Siri Sirisen","departmentId":1,"role":1,"tradeable":true,"responsibleUser":false},{"shiftId":6,"startTime":1483542000000,"endTime":1483570740000,"userId":16,"userName":"Siri Sirisen","departmentId":1,"role":1,"tradeable":false,"responsibleUser":false}]'
    var obj = data;

    //TODO this needs to be removed after the table selector below works
    var table = "day-table";
    //if (!document.getElementsByTagName) return;

    for (var i = 0; i < obj.length; i++) {

        //TODO: This part needs to add to the correct table given a working time.
        console.log(obj[i]);
        var startTime = new Date(obj[i].startTime).getHours() + 1;
        console.log(startTime);
        var isFree = obj[i].tradeable;

        if (startTime >= 8 && startTime < 16) {
            table = $("#day-table");
        } else if (startTime >= 16 && startTime < 24) {
            table = $("#evening-table");
        } else if (startTime >= 0 && startTime < 8) {
            table = $("#night-table");

        } else {
            alert("ERROR CHECK THE ADD ROW FUNCTION IN SHIFT-SCRIPT.JS!")
        }

        console.log("DATO:" + new Date(obj[i].startTime));

        tabBody = table;
        row = document.createElement("tr");
        row.className = "tr" + i;
        cell1 = document.createElement("td");
        cell2 = document.createElement("td");
        cell3 = document.createElement("td");
        cell4 = document.createElement("td");
        cell4.className = "listButton " + "id" + obj[i].shiftId;
        textnode1 = document.createTextNode(obj[i].userName);
        textnode2 = document.createTextNode(formatTime(new Date(obj[i].startTime)) + " - " + formatTime(new Date(obj[i].endTime)));
        textnode3 = document.createTextNode("stilling her");
        textnode4 = document.createTextNode("Ta vakt");
        cell1.appendChild(textnode1);
        cell2.appendChild(textnode2);
        cell3.appendChild(textnode3);
        cell4.appendChild(textnode4);
        row.appendChild(cell1);
        row.appendChild(cell2);
        row.appendChild(cell3);
        row.appendChild(cell4);
        tabBody.append(row);
        //table = "evening-table";
    }
    $(".listButton").click(function(){
        $("#absolute-div").remove();
        if ($(".container").hasClass("blur")){ $(".container").removeClass("blur")};
        $(".overlay").remove();
        var shiftIdFromTradeList = Number($(this).attr("class").split(" ")[1].substring(2,6));
        bannerConfirm("Bekreft at du tar vakt?", function () {
            assignAvailableShift(shiftIdFromTradeList);
        });
    })
}
/**
 *
 * @param shiftId
 */
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
                url: "/MinVakt/rest/shifts/assign/" + shiftId + "/" + localStorage.getItem("userid"),
                dataType: 'text',
                data: JSON.stringify({
                    shiftId: shift.shiftId,
                    userId: userId,
                }),
                success: function () {
                    console.log("Result: Skift tatt");
                    setShiftTradeablePut(shift, false);
                    window.location.replace("/MinVakt/html/calendar.html");
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
/**
 *
 * @param time
 * @param format
 */
function bannerAbsence(time, format) {
    console.log(time);
    $("body").prepend("<div id='banner-div'></div>");

    $("#banner-div").load("/MinVakt/html/template/banner-absence.html", function () {
        $("#alert").text(format);
        $(".container").click(function () {
            $("#banner-div").remove();
            if ($(".container").hasClass("blur")){ $(".container").removeClass("blur")};
            $(".container").unbind();
        });
        $("#absence-confirm").click(function(){
            var aFromTime = Number($("#a-from-time").val());
            var aToTime = Number($("#a-to-time").val());
            time.setHours(aFromTime);
            var fromTimeDate = time.getTime();
            time.setHours(aToTime);
            var toTimeDate = time.getTime();
            $("#banner-div").remove();
            if ($(".container").hasClass("blur")){ $(".container").removeClass("blur")};
            $(".container").unbind();
            if (aFromTime >= aToTime){
                bannerAlert("Ugyldig tidspunkt");
            } else {
                $.ajax({
                    type: "POST",
                    url: "/MinVakt/rest/users/absence/set/" + localStorage.getItem('userid') + "/" + fromTimeDate + "/" + toTimeDate,
                    headers: {"Authorization": "Bearer " + localStorage.getItem("token")},
                    success: function(data){
                        bannerAlert("Fravær registrert");
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
        $(".container").addClass("blur");
        $(".closer").click(function () {
            $("#banner-div").remove();
            if ($(".container").hasClass("blur")){ $(".container").removeClass("blur")};
            $(".container").unbind();
        });
    });
}
/**
 *
 * @param message
 */
function bannerAlert(message) {
    $("body").prepend("<div id='banner-div'></div>");
    $("#banner-div").load("/MinVakt/html/template/banner-alert.html", function () {
        $("#alert").text(message);
        $(".container").click(function () {
            $("#banner-div").remove();
            if ($(".container").hasClass("blur")){ $(".container").removeClass("blur")};
            $(".container").unbind();
        });
        $(".container").addClass("blur");
        $(".closer").click(function () {
            $("#banner-div").remove();
            if ($(".container").hasClass("blur")){ $(".container").removeClass("blur")};
            $(".container").unbind();
        });
    });
}
/**
 *
 * @param message
 * @param callBack
 */
function bannerConfirm(message, callBack) {
    $("body").prepend("<div id='banner-div'></div>");
    $("#banner-div").load("/MinVakt/html/template/banner-alertConfirm.html", function () {
        $("#alert").text(message);
        $(".container").click(function () {
            $("#banner-div").remove();
            if ($(".container").hasClass("blur")){ $(".container").removeClass("blur")};
            $(".container").unbind();
        });
        $(".container").addClass("blur");
        $(".closer").click(function () {
            $("#banner-div").remove();
            if ($(".container").hasClass("blur")){ $(".container").removeClass("blur")};
            $(".container").unbind();
        });

        $(".confirmer").click(function () {
            $("#banner-div").remove();
            if ($(".container").hasClass("blur")){ $(".container").removeClass("blur")};
            $(".container").unbind();
            callBack();
        });
    });
}



