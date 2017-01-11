/**
 * Created by Chris on 11.01.2017.
 */


function login() {

    var loginName = document.getElementById('uname').value;
    var password = document.getElementById('password').value;

    if (loginName == "Chris" && password == "123") {
        location.href = 'http://google.com';
    } else {
        document.getElementById('errormessage').style.visibility = "initial";
        setTimeout(errorMessageRemoval, 5000);
    }
}

function errorMessageRemoval() {

    document.getElementById('errormessage').style.visibility = "hidden";

}
/**
 * Created by Chris on 11.01.2017.
 */
