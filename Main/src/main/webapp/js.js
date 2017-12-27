$(function() {
    $.get('/api/header_items', function(data){
        var headerItems = JSON.parse(data);
        for(var i = 0; i< headerItems.length; i++) {
            var li = $('<li></li>');
            li.append('<a href="#">' + headerItems[i].name +'</a>');
             var ul = $('<ul item_id=' + headerItems[i].id + ' class="animenu__nav__child"></ul>');
             li.append(ul);
            $('.animenu__nav').append(li);
        }

        $('.animenu__nav__child').on('mouseenter', function () {
            this.append('<li><a href="#">' + fffffff + '</a></li>');
        });
    })
});

        // $('.animenu_nav_child').mouseenter(function(){
        //     $(this).children('<li><a href="#"></a></li>').slideDown('normal');
        // });
        // $('.animenu_nav_child').mouseleave(function(){
        //     $(this).children('<li><a href="#"></a></li>').slideUp('normal');
        // });

