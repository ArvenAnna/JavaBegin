$(document).ready(function () {
    $("#button").click(function () {
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
        "password": document.getElementById("password").value,
        "role": document.getElementById("role").value
    };

    $.get("/registration", userLogin, function (data) {
        if (data === "this is free login") {
            $.get("/createNewUser", allDate, function (data) {
                self.location = "index.html";
            });
        } else {
            $('.div_result').empty().append(data);
        }
    });
}