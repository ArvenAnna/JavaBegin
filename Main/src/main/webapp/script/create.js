function clickOnButton() {
    var chapterDate = {
        "chapterName": document.getElementById("menu").value,
        "nameFile": document.getElementById("name_file").value,
        "chapterText": document.getElementById("text").value,
        "nameSubChapters": document.getElementById("name_subChapters").value
    };
    $.get('/api/createFile', chapterDate, function (data) {
        if (data) {
            $('.result').empty().append("The file successfully save ");
        } else {
            $('.result').empty().append("The file have error ");
        }
    });
}

function exit() {
    self.location = "head.html";
}