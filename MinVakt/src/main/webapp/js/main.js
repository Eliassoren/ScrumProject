/**
 * Created by EliasBrattli on 12/01/2017.
 */
var day = new Date();
var month = day.getMonth();
var monthNames = new Array(12);
var year = day.getFullYear();
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


$("#month-title").text(title);

    $("#left-arrow").click(function () {

        if(month > 0){
            month--;
        }else{
            year--;
            month = 11;
        }
        title = monthNames[month] + " " + year;
        $("#month-title").text(title);
    });

    $("#right-arrow").click(function () {
        if(month < 11){
            month++;
        }else{
            year++;
            month = 0;
        }
        title = monthNames[month] + " " + year;
        $("#month-title").text(title);
    });
});