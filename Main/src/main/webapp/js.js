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

        }
        $('.animenu__nav').on('click',
            function () {
                    $.get('/api/child', function (data) {
                        alert(data.toString());
                    });


            }
            );

    });
});



