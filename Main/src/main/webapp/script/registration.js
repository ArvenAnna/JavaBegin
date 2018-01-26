$(document).ready(function () {
    $("#registration").click(function () {
        if (document.getElementById("password").value === null || document.getElementById("password").value === ""
            || document.getElementById("login").value === null || document.getElementById("login").value === ""
            || document.getElementById("role").value === null || document.getElementById("role").value === "") {
            $('.div_result').empty().append("You did not fill all the fields");
        } else {
            loginServletCall();
        }
    });
});

function loginServletCall() {
    var userLogin = {
        "login": document.getElementById("login").value
    };
    var allDate = {
        "login": document.getElementById("login").value,
        "password": document.getElementById("password").value
    };

    $.get('api/checkDateUser', userLogin, function (data) {
        if (data === "this is free login") {
            $.get('api/createNewUser', allDate, function (data) {
                self.location = "index.html";
            });
        } else {
            $('.div_result').empty().append(data);
        }
    });
};

function exit() {
    self.location = "index.html";
}