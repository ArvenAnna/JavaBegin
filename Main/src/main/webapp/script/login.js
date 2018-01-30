$(document).ready(function () {
    $("#button").click(function () {
        if (document.getElementById("password").value === null || document.getElementById("password").value === ""
            || document.getElementById("login").value === null || document.getElementById("login").value === "") {
            $('.mydiv').empty().append('Please input login or password');
        } else {
            loginServletCall();
            setCookie("login",document.getElementById("login").value,6000);
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
                    $('#bodyId').load("head.html");
                }
                else if (data.responseText.trim(data.responseText) === "This is logged user") {
                    $('#bodyId').load("user.html");
                }
            }
        }],
        error: function (data) {
            if (data.responseText === "Your login or password have some error please write again") {
                $('.mydiv').empty().append(data.responseText.trim(data.responseText));
            } else {
                $('.mydiv').empty().append(data.responseText.trim(data.responseText));
                if (data.responseText.trim(data.responseText) === "This is logged admin") {
                    $('#bodyId').empty().load("head.html");
                }
                else if (data.responseText.trim(data.responseText) === "This is logged user") {
                    $('#bodyId').empty().load("user.html");
                }
            }
        }
    });
}

function registration() {
    $('#bodyId').load("registration.html");
}

function setCookie(name, value, options) {

    var date = new Date(0);
    document.cookie = "name=; path=/; expires=" + date.toUTCString();//delete cookie

    options = options || {};                                         //create new cookie with name user
    var expires = options.expires;
    if (typeof expires == "number" && expires) {
        var d = new Date();
        d.setTime(d.getTime() + expires * 1000);
        expires = options.expires = d;
    }
    if (expires && expires.toUTCString) {
        options.expires = expires.toUTCString();
    }
    value = encodeURIComponent(value);
    var updatedCookie = name + "=" + value;
    for (var propName in options) {
        updatedCookie += "; " + propName;
        var propValue = options[propName];
        if (propValue !== true) {
            updatedCookie += "=" + propValue;
        }
    }
    document.cookie = updatedCookie;
}
