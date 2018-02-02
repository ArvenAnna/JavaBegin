$(document).ready(function () {
    if (document.cookie==""){
        exit();
    }
});

$(function () {
    $.get('/api/chapter', function (data) {
        var headerItems = JSON.parse(data);
        for (var i = 0; i < headerItems.length; i++) {
            var variableLi = $('<li></li>');
            variableLi.append('<a href="#">' + headerItems[i].name + '</a>');
            var variableUl = $('<ul item_id=' + headerItems[i].id +
                ' class="animenu__nav__child"></ul>');
            variableLi.append(variableUl);
            $('.animenu__nav').append(variableLi);
            variableLi.mouseover({id: headerItems[i].id}, buildChapterChild)
        }
    });
});

function buildChapterChild(e) {
    var id = e.data.id;
    $.get('/api/subChapter?id=' + id, showChildrenChapters.bind(null, e));
}

function showChildrenChapters(e, data) {
    var subchapters = JSON.parse(data);
    $(e.target).parent().find('.animenu__nav__child').empty();
    for (var i = 0; i < subchapters.length; i++) {
        var varLi = $('<li>' + '<a href="#">' + subchapters[i].name + '</a>' + '</li>');
        $(e.target).parent().find('.animenu__nav__child').append(varLi);
        varLi.on('dblclick', {name: subchapters[i].name}, deleteChapter);
    }
}

function deleteChapter(event) {
    var url = 'api/deleteSubChapter?name=' + event.data.name;
    $.get(url, function (data) {
        if (data === "") {
            alert("You don't have access to delete file");
            $('#bodyId').load("index.html");        }
        else if (data) {
            $('.mydiv').empty().append("Successufull delete file");
            $('#bodyId').load("head.html");
        }
        else {
            $('.mydiv').empty().append("File not delete");
        }
    });
}

function exit() {
    $('#bodyId').load("index.html");}
