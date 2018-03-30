var page = 0;
var size = 10;


function showMessages() {
    $.ajax({
        url: '/posts?page=' + page + '&size=' + size,
        datatype: 'json',
        success: function(data, status) {
            var chatcontent = $("#chatcontent");
            chatcontent.empty();
            $.each(data, function(index, post) {
                chatcontent.append(
                '<div class="row border border-dark my-2 p-2">' +
                    '<div class="col-md-10 font-weight-bold"><a href="post.html?id=' + post.id + '">' + post.time + '</a></div><div class="col-md-2 text-right">Replies: ' + post.replyCount + '</div>' +
                    '<div class="col-md-2 font-italic">' + post.username + '</div><div class="col-md-10">' + post.content + '</div>' +
                '</div>');
            });
        },
        error: function(xhr, status) {
            console.log(status + ':' + xhr.responseJSON.message);
        }

    });

    $.ajax({
        url: '/pagecount?size=' + size,
        datatype: 'text',
        success: function(data, status) {
            var pagelinks = $("#pagelinks");
            pagelinks.empty();
            pagelinks.append('<button type="button" class="pagelink btn btn-link btn-sm" value="0">1</button>');
            for(i = 1;i<data; i++) {
                pagelinks.append('<button type="button" class="pagelink btn btn-link btn-sm" value="' + i + '">' + (i+1) + '</a>');
            };

            $(".pagelink").click(function() {
                page = $(this).val();
                showMessages();
            });
        },
        error: function(xhr, status) {
            console.log(status + ':' + xhr.responseJSON.message);
        }
    });
}

showMessages();
setInterval(showMessages, 3000);

function submitMessage() {
    var username = $("#username").val();
    var content = $("#content").val();

    console.log(username + ":" + content);
    $.ajax({
        url: '/post',
        method: 'POST',
        data: JSON.stringify({
            'username' : username,
            'content' : content
        }),
        contentType: 'application/json',
        success: function() {
            showMessages();
            $("#content").val('');
        },
        error: function(xhr, status) {
            console.log(status + ':' + xhr.responseJSON.message);
        }
    });
}

$("#content").keyup(function(event) {
    if(event.which == 13) {
        submitMessage();
       $(this).focus();
    }
});

$("#send").click(function() {
    submitMessage();
});