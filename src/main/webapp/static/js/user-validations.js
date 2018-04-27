// AJAX Functionality

function checkUsernameAvailability() {
    var xmlhttp = new XMLHttpRequest();
    var username = document.forms["regForm"]["username"].value;
    var url = "/user/availability?username=" + username;
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            if (xmlhttp.responseText === "Available") {
                document.getElementById("username").style.borderColor = 'green';
                document.getElementById("username").style.color = 'green';
                document.getElementById("usernameerror").innerHTML = "";
            } else {
                document.getElementById("username").style.borderColor = "red";
                document.getElementById("usernameerror").innerHTML = document.getElementById("username").value + " is already in use";
                document.getElementById("username").value = null;
            }
        }

    };
    try {
        xmlhttp.open("GET", url, true);
        xmlhttp.send();
    } catch (e) {
        //alert("Unable to connect to server");
    }
}

function checkEmailAvailability() {
    var xmlhttp = new XMLHttpRequest();
    var email = document.forms["regForm"]["email"].value;
    var url = "/user/availability?email=" + email;
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
            if (xmlhttp.responseText === "Available") {
                document.getElementById("email").style.borderColor = 'green';
                document.getElementById("email").style.color = 'green';
                document.getElementById("emailerror").innerHTML = "";
            } else {
                document.getElementById("email").style.borderColor = "red";
                document.getElementById("emailerror").innerHTML = document.getElementById("email").value + " is already associated with another account";
                document.getElementById("email").value = null;
            }
        }

    };
    try {
        xmlhttp.open("GET", url, true);
        xmlhttp.send();
    } catch (e) {
        //alert("Unable to connect to server");
    }
}


// Normal Functions

function firstNameChecker() {
    var check = document.getElementById('firstName').value.match(/[0-9-!$ %^&*()+|~=`{}\[\]:";'<>?,.\/]/);
    if (check == null) {
        document.getElementById("firstName").style.borderColor = 'green';
        document.getElementById("firstName").style.color = 'green';
    }
    else {
        document.getElementById("firstName").style.borderColor = 'red';
        document.getElementById("firstName").style.borderColor = 'red';
        document.getElementById("firstName").value = null;
    }
}

function lastNameChecker() {
    var check = document.getElementById('lastName').value.match(/[0-9-!$ %^&*()+|~=`{}\[\]:";'<>?,.\/]/);
    if (check == null) {
        document.getElementById("lastName").style.borderColor = 'green';
        document.getElementById("lastName").style.color = 'green';
    }
    else {
        document.getElementById("lastName").style.borderColor = 'red';
        document.getElementById("lastName").style.red = 'red';
        document.getElementById("lastName").value = null;
    }
}

function passwordChecker() {
    var check = document.getElementById('password').value;
    if (check.length > 2) {
        document.getElementById("password").style.borderColor = 'green';
    }
    else {
        document.getElementById("password").style.borderColor = 'red';
        document.getElementById("confirmPassword").style.borderColor = 'red';
        document.getElementById("password").value = null;
        document.getElementById("confirmPassword").value = null;
    }
}

function newPasswordChecker() {
    var check = document.getElementById('newpassword').value;
    if (check.length > 2) {
        document.getElementById("newpassword").style.borderColor = 'green';
    }
    else {
        document.getElementById("newpassword").style.borderColor = 'red';
        document.getElementById("newpassword").value = null;
    }
}

function emailChecker() {
    if (document.getElementById('email').value != null)
        checkEmailAvailability();

    /* var check = document.getElementById('email').value.match(/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
     if (check == null) {
     document.getElementById("email").style.borderColor = 'green';
     }
     else {
     document.getElementById("email").style.borderColor = 'red';
     document.getElementById("email").value = null;
     } */
}

function checkFields() {
    if (document.getElementById('password').value === document.getElementById('confirmPassword').value) {
        document.getElementById("password").style.borderColor = 'green';
        document.getElementById("password").style.color = 'green';
        document.getElementById("confirmPassword").style.borderColor = 'green';
        document.getElementById("confirmPassword").style.color = 'green';
    }
    else {
        document.getElementById("password").style.borderColor = 'red';
        document.getElementById("password").value = null;
        document.getElementById("confirmPassword").style.borderColor = 'red';
        document.getElementById("confirmPassword").value = null;
    }
}

function usernameChecker() {
    if (validateEmail(document.getElementById('username').value)) {
        checkEmailAvailability();
    }
    else {
        document.getElementById("username").style.borderColor = 'red';
        document.getElementById("username").style.color = 'red';
        document.getElementById("username").value = null;
    }
}

function validateEmail(email) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}