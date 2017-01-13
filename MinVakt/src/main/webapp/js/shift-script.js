/**
 * Created by Chris on 12.01.2017.
 */

$(document).ready(function() {
    $("#hamburger-toggle").click(function(){
        $("#hamburger-menu").toggleClass("hamburger-menu-open");
        $("#hamburger-toggle").toggleClass("hamburger-toggle-open");
    });
});


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

window.onload = function addRow() {

    //DUMMY DATA
    var text = '{ "User" : [' + '{ "Id":"1" , "firstName":"Christian" , "lastName":"Echtermeyer" , "mobile":"123" , "email":"chechter@mail.com"' +
        ' , "password":"321" , "admin":"1" , "address":"Stibakken" , "userCategoryInt":"1" , "userCategoryString":"Admin"},' +

        '{ "id":"2" , "firstName":"Elias" , "lastName":"Sørensen" , "mobile":"911" , "email":"bsmail@mail.com"' +
        ' , "password":"123" , "admin":"0" , "address":"Valgrind" , "userCategoryInt":"2" , "userCategoryString":"Lege"}]}';


    var obj = JSON.parse(text);


    //TODO this needs to be removed after the table slector below works
    table = "day-table";


    if (!document.getElementsByTagName) return;

    for (var i = 0; i < obj.User.length; i++) {

        //TODO: This part needs to add to the correct table given a working time.
        /*
         if (tableSelect == 1) {
         table = "day-table";
         } else if (tableSelect == 2) {
         table = "evening-table";
         } else if (tableSelect == 3) {
         table = "night-table";
         } else {
         alert("ERROR CHECK THE ADD ROW FUNCTION IN SHIFT-SCRIPT.JS!")
         }*/


        //this loop is just for extra test data, remove for real thing.
        for (var j = 0;j < 10;j++){

            tabBody = document.getElementById(table);
            row = document.createElement("tr");
            cell1 = document.createElement("td");
            cell2 = document.createElement("td");
            cell3 = document.createElement("td");
            cell4 = document.createElement("td");
            textnode1 = document.createTextNode(obj.User[i].firstName);
            textnode2 = document.createTextNode(obj.User[i].from);
            textnode3 = document.createTextNode(obj.User[i].to);
            textnode4 = document.createTextNode(obj.User[i].userCategoryString);
            cell1.appendChild(textnode1);
            cell2.appendChild(textnode2)
            cell3.appendChild(textnode3);
            cell4.appendChild(textnode4);
            row.appendChild(cell1);
            row.appendChild(cell2);
            row.appendChild(cell3);
            row.appendChild(cell4);
            tabBody.appendChild(row);
        }
        table = "evening-table";
    }
}

