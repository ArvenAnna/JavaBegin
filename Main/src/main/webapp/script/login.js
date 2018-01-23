$(document).ready(function () {
    $("#button").click(function () {
        if (document.getElementById("password").value === null || document.getElementById("password").value === ""
            || document.getElementById("login").value === null || document.getElementById("login").value === "") {
            $('.mydiv').empty().append('Please input login or password');
        } else {
            loginServletCall();
        }
    });
});

function loginServletCall() {
    var userDate = {
        "login": document.getElementById("login").value,
        "password": document.getElementById("password").value
    };

    $.get('api/login', userDate, function (data) {
        if (data === "Your login or password have some error please write again") {
            $('.mydiv').empty().append(data.trim(data));
        } else {
            $('.mydiv').empty().append(data.trim(data));
            self.location = "head.html";
        }
    });
}

function registration() {
    self.location = "registration.html";
}