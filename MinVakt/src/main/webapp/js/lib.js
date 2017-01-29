/**
 * Created by Vegard on 25/01/2017.
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

function weekNumberFromDate(date){
    var weeknumber = 0;
    var first = new Date(date.getFullYear(), 0, 1);
    while (first < date) {
        var nextDate = first.getDate();
        var lastOfMonth = new Date(first.getFullYear(), first.getMonth() +1, 0).getDate();
        if (nextDate + 7 > lastOfMonth){
            first = new Date(first.getFullYear(), first.getMonth() + 1, (nextDate + 7)%lastOfMonth);
            weeknumber++;
        } else {
            first = new Date(first.getFullYear(), first.getMonth(), nextDate + 7);
            weeknumber++;
        }
    }
    return weeknumber;
}

function getDateOfWeek(w, y) {
    var date = new Date(y, 0, 1 + (w - 1) * 7);
    var dateOfWeek = date.getDay();
    var weekStart = date;
    if (dateOfWeek <= 4) {
        weekStart.setDate(date.getDate() - date.getDay() + 1);
    } else {
        weekStart.setDate(date.getDate() + 8 - date.getDay());
    }
    return weekStart;
}

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

function bannerMessage(message, callBack) {
    $("body").prepend("<div id='banner-div'></div>");
    $("#banner-div").load("/MinVakt/html/template/banner-alert.html", function () {
        $("#alert").text(message);
        $(".container").click(function () {
            $("#banner-div").remove();
            if ($(".container").hasClass("blur")) {
                $(".container").removeClass("blur")
            }
            ;
            $(".container").unbind();
        });
        $(".container").addClass("blur");
        $(".closer").click(function () {
            $("#banner-div").remove();
            if ($(".container").hasClass("blur")) {
                $(".container").removeClass("blur")
            }
            ;
            $(".container").unbind();
            callBack();
        });
    });
}
