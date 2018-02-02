$(function () {
    if (document.cookie==""){
        exitLogin();
    }
    $.get('/api/chapter', function (data) {
        document.body.style.cursor = 'pointer';
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
        varLi.on('click', {name: subchapters[i].name}, onClickHandlerForLastChapterChild);
    }
}

function buildChapterSearch(data) {
    document.body.style.cursor = 'pointer';
    var nameChapter = JSON.parse(data);
    $('#l').parent().find('#l').empty();
    for(var i = 0; i < nameChapter.length; i ++){
        var variableH = $('<a>' + nameChapter[i].name + '</a>' + '<br/>');
        $('#l').parent().find('#l').append(variableH);
        variableH.on('click', {name: nameChapter[i].name},onClickHandlerForLastChapterChild);
    }
}

function onClickHandlerForLastChapterChild(event) {
    var url = 'api/subChapterByName?name=' + event.data.name;
    $('#mySearch').val('');
    $.get(url, buildChapterContent);
}

function buiilChpatersContentFromSearch(value) {
    var url = 'api/similarChapter?name=' + value;
    $.get(url, buildChapterSearch);
}

function buildChapterContent(data) {
    var variableH = $('<h1>' + data + '</h1>');
    $('#l').parent().find('#l').empty().append(variableH);
}

function exitLogin() {
    $('#bodyId').load("index.html");
}

function addNewFile() {
    $('#bodyId').load("create.html");
}

function deleteFile() {
    $('#bodyId').load("delete.html");
}

function updateFile(){
    $('#bodyId').load("update.html");
}
