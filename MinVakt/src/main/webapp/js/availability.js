var start;
var end;
var dayStart = 28800000;
var eveningStart = 54000000;
var nightStart = 79200000;
var nightEnd = 104400000;

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

$(document).ready(function() {
    var weekNumber = weekNumberFromDate(new Date());
    var year = new Date().getFullYear();

    $('#left-arrow').click(function () {
        weekNumber -= 1;
        if (weekNumber < 1) {
            year -= 1;
            weekNumber = 52;
            $('#month-title').text("Uke " + weekNumber);
            $('#year-title').text(year);
        }
        $('#month-title').text("Uke " + weekNumber);
    });
    $('#right-arrow').click(function () {
        weekNumber += 1;
        if (weekNumber > 52) {
            year += 1;
            weekNumber = 1;
            $('#month-title').text("Uke " + weekNumber);
            $('#year-title').text(year);
        }
        $('#month-title').text("Uke " + weekNumber);
    });

    $('#month-title').text("Uke " + weekNumber);
    $('#year-title').text(year);

    $('.day').click(function () {
        $(this).toggleClass("day-activated");
    })

    $('#accept-btn').click(function () {
        bannerConfirm("Lagre?", function () {
            var daysArray = [];
            var dateArray = [];
            $(".day-activated").each(function () {
                daysArray.push($(this).attr("id").substring(3,10));
            });
            var dateOf = getDateOfWeek(weekNumber, year);
            for (var i = 0; i < daysArray.length; i++){
                dateArray.push(new Date(dateOf.getFullYear(), dateOf.getMonth(), dateOf.getDate() + Number(daysArray[i]) -1));
            }
            console.log(daysArray);
            console.log(dateArray);
            dateArray.forEach(function (i) {
                if (document.getElementById("day-id").checked) {
                    start = i.getTime() + dayStart;
                    end = i.getTime() + eveningStart;
                }
                if (document.getElementById("evening-id").checked) {
                    start = i.getTime() + eveningStart;
                    end = i.getTime() + nightStart;
                }
                if (document.getElementById("night-id").checked) {
                    start = i.getTime() + nightStart;
                    end = i.getTime() + nightEnd;
                }
                setUserAvailable(start, end);
            });
        });
    });
});

function setUserAvailable(start, end) {
    userId = parseInt(window.localStorage.getItem("userid"));
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        type: "POST",
        url: "/MinVakt/rest/users/available/" + userId + "/" + start + "/" + end,
        dataType: 'text',
        data: JSON.stringify({
            userId: userId,
            start: start,
            end: end
        }),
        success: function () {
            console.log("Result: Satt ledig");
            location.reload();
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