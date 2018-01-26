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

    var userDataJson = JSON.stringify(userDate);

    $.ajax({
        type: "POST",
        url: 'api/login',
        data: userDataJson,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: [function () {
            if (data.responseText === "Your login or password have some error please write again") {
                $('.mydiv').empty().append(data.responseText.trim(data.responseText));
            } else {
                $('.mydiv').empty().append(data.responseText.trim(data.responseText));
                if (data.responseText.trim(data.responseText) === "This is logged admin") {
                    self.location = "head.html";
                }
                else if (data.responseText.trim(data.responseText) === "This is logged user") {
                    self.location = "user.html";
                }
            }
        }],
        error: function (data) {
            if (data.responseText === "Your login or password have some error please write again") {
                $('.mydiv').empty().append(data.responseText.trim(data.responseText));
            } else {
                $('.mydiv').empty().append(data.responseText.trim(data.responseText));
                if (data.responseText.trim(data.responseText) === "This is logged admin") {
                    self.location = "head.html";
                }
                else if (data.responseText.trim(data.responseText) === "This is logged user") {
                    self.location = "user.html";
                }
            }
        }
    });
}

function registration() {
    self.location = "registration.html";
}