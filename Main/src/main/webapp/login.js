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
    var userObj = {
        "login": document.getElementById("login").value,
        "password": document.getElementById("password").value
    };

    $.get("/login", userObj, function (data) {
        $('.mydiv').empty().append(data.trim(data));
        self.location="head.html";
    });
};

function registration() {
    self.location="registration.html";
}