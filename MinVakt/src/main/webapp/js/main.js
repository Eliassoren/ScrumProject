/**
 * Created by EliasBrattli on 12/01/2017.
 */
var day = new Date();
var month = day.getMonth();
var monthNames = new Array(12)

var year = day.getFullYear();
var date = new Date(new Date().getFullYear(), 0, 1);
var week = 0;

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

        var firstDate = getFirstDateOfEachMonth(year)[month];
        var lastDate = new Date(firstDate.getFullYear(), firstDate.getMonth() + 1, 0).getDate();
        var count = getMonday(firstDate);
        var monday = convertToMonday(firstDate)
        var lastDateOfPrevMonth = new Date(monday.getFullYear(), monday.getMonth() + 1, 0).getDate();
        moment().year(year);

        //alert(lastDateOfPrevMonth + " " + lastDate);
        $(".day").each(function () {
            $(this).find(".date").text(count);
            if (count < lastDateOfPrevMonth) {
                count++;
            } else {
                count = 1;
                lastDateOfPrevMonth = lastDate;
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
    });
    /**
     * "Increase" month
     */
    $("#right-arrow").click(function () {
        if (month < 11) {
            month++;
        } else {
            year++;
            month = 0;
        }
        title = monthNames[month] + " " + year;
        $("#month-title").text(title);

        var firstDate = getFirstDateOfEachMonth(year)[month];
        var lastDate = new Date(firstDate.getFullYear(), firstDate.getMonth() + 1, 0).getDate();
        var count = getMonday(firstDate);
        var monday = convertToMonday(firstDate)
        var lastDateOfPrevMonth = new Date(monday.getFullYear(), monday.getMonth() + 1, 0).getDate();
        moment().year(year);
       // alert(lastDateOfPrevMonth + " " + lastDate);

        $(".day").each(function () {
            $(this).find(".date").text(count);
            if (count < lastDateOfPrevMonth) {
                count++;
            } else {
                count = 1;
                lastDateOfPrevMonth = lastDate;
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
    });
});