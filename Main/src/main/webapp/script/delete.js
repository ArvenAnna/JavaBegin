$(document).ready(function () {
    $.get('/api/allSubChapters', function (data) {
        var listSubChapters = JSON.parse(data);
        for (var i = 0; i < listSubChapters.length; i++) {
            var subChapter = $('<li>' + listSubChapters[i].name + '</li>');
            $('.child').append(subChapter);
            subChapter.on('click', {name: listSubChapters[i].name}, deleteFunction);
        }
    });
});

function deleteFunction(event) {
    var url = 'api/deleteSubChapter?name=' + event.data.name;
    $.get(url, function (data) {
        if (data) {
            $('.mydiv').empty().append("Successufull delete file");
            location.reload();
        }
        else {
            $('.mydiv').empty().append("File not delete");
        }
    });
}

function exit() {
    self.location = "head.html";
}