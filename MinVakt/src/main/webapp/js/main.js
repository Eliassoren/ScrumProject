/**
 * Created by EliasBrattli on 12/01/2017.
 */
var day = new Date();
var month = day.getMonth();
var monthNames = new Array();
var year = day.getYear();
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

var title = monthNames[month] +" "+ year;
$(document).ready(function() {
    $("#hamburger-toggle").click(function(){
        $("#hamburger-menu").toggleClass("hamburger-menu-open");
        $("#hamburger-toggle").toggleClass("hamburger-toggle-open");
    });


$("#month-title").textContent = title;

    $("#left-arrow").click(function () {
        alert("Clicked left");
        if(month > 0){
            month--;
        }else{
            year--;
            month = 11;
        }
        title = monthNames[month] + " " + year;
        $("#month-title").textContent = title;
    });

    $("#right-arrow").click(function () {
        alert("Clicked right");
        if(month < 11){
            month++;
        }else{
            year++;
            month = 0;
        }
        title = monthNames[month] + " " + year;
        $("#month-title").textContent = title;
    });
});