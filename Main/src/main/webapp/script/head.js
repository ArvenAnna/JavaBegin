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
        varLi.on('click', {name: subchapters[i].name}, onClickHandlerForLastChapterChild);
    }
}

function buildChapterContent(data) {
    var variableH = $('<h1>' + data + '</h1>');
    $('#l').parent().find('#l').empty().append(variableH);
}

function onClickHandlerForLastChapterChild(event) {
    var url = 'api/subChapterByName?name=' + event.data.name;
    $.get(url, buildChapterContent);
}

function buiilChpatersContentFromSearch(value) {
    var url = 'api/subChapterByName?name=' + value;
    $.get(url, buildChapterContent);
}

function exitLogin() {
    self.location = "index.html";
}

function addNewFile() {
    self.location = "create.html";
}

function deleteFile() {
    self.location = "delete.html";
}
function updateFile(){
    self.location = "update.html";
}
