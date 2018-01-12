$(function () {
    $.get('/api/chapter', function (data) {
        var headerItems = JSON.parse(data);
        for (var i = 0; i < headerItems.length; i++) {
            var li = $('<li></li>');
            li.append('<a href="#">' + headerItems[i].name + '</a>');
            var ul = $('<ul item_id=' + headerItems[i].id +
                ' class="animenu__nav__child"></ul>');
            li.append(ul);
            $('.animenu__nav').append(li);
            li.mouseover({id: headerItems[i].id}, buildChapterChild)
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
        var li2 = $('<li>' + '<a href="#">' + subchapters[i].name + '</a>' + '</li>');
        $(e.target).parent().find('.animenu__nav__child').append(li2);
        li2.on('click', {name: subchapters[i].name}, onClickHandlerForLastChapterChild);
    }
}

function buildChapterContent(data) {
    var h = $('<h1>' + data + '</h1>');
    $('#l').parent().find('#l').empty().append(h);
}

function onClickHandlerForLastChapterChild(event) {
    var url = 'api/subChapterByName?name=' + event.data.name;
    $.get(url, buildChapterContent);
}

