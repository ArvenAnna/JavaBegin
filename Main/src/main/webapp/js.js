$(function() {

    $.get('/api/header_items', function(data){
        var headerItems = JSON.parse(data);
       // var str='';
        //str = str + '<a href="#"></a>';
        for(var i = 0; i< headerItems.length; i++) {
            var li = $('<li></li>');
            li.append('<a href="#">' + headerItems[i].name +'</a>');
             var ul = $('<ul item_id=' + headerItems[i].id + ' class="animenu__nav__child"></ul>');
             li.append(ul);
           // $('.animenu__nav').append($('<li></li>')).append('<a href="#">' + headerItems[i].name +'</a>');
            $('.animenu__nav').append(li);
        }

        $('.animenu__nav__child').on('mouseenter', function () {
            this.append('<li><a href="#">' + fffffff + '</a></li>');
        });
    })

});


