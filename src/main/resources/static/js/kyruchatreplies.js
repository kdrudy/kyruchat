var originalPost = null;

function showPost() {
    $.ajax({
        url: '/post?id=' + get('id'),
        datatype: 'json',
        success: function(post, status) {
            originalPost = post;
            var postcontent = $("#post");
            postcontent.append(
//                '<div class="row border border-dark my-2 p-2 post-panel">' +
                '<div class="row my-2 p-2 post-panel">' +
                    '<div class="col-md-12 font-weight-bold">' + post.time + '</div>' +
                    '<div class="col-md-2 font-italic">' + post.username + '</div><div class="col-md-10">' + post.content + '</div>' +
                '</div>');

            var returnLink = $('#returnLink');
            if(post.parent == null) {
                returnLink.append('<a href="/"><< return to parent</a>');
            } else {
                returnLink.append('<a href="/post.html?id=' + post.parent.id + '"><< return to parent</a>');
            }
        },
        error: function(xhr, status) {
            console.log(status + ':' + xhr.responseJSON.message);
        }

    });
}

function showReplies() {
    $.ajax({
        url: '/replies?id=' + get('id'),
        datatype: 'json',
        success: function(data, status) {
            var chatcontent = $("#chatcontent");
            chatcontent.empty();
            $.each(data, function(index, post) {
                chatcontent.append(
//                '<div class="row border border-dark my-2 p-2 post-panel">' +
                '<div class="row my-2 p-2 post-panel">' +
                    '<div class="col-md-10 font-weight-bold"><a href="post.html?id=' + post.id + '">' + post.time + '</a></div><div class="col-md-2 text-right">Replies: ' + post.replyCount + '</div>' +
                    '<div class="col-md-2 font-italic">' + post.username + '</div><div class="col-md-10">' + post.content + '</div>' +
                '</div>');
            });
        },
        error: function(xhr, status) {
            console.log(status + ':' + xhr.responseJSON.message);
        }

    });
}

function submitReply() {
    var username = $("#username").val();
    var content = $("#content").val();

    console.log(username + ":" + content);
    $.ajax({
        url: '/post',
        method: 'POST',
        data: JSON.stringify({
            'username' : username,
            'content' : content,
            'parent' : originalPost
        }),
        contentType: 'application/json',
        success: function() {
            showReplies();
            $("#content").val('');
        },
        error: function(xhr, status) {
            console.log(status + ':' + xhr.responseJSON.message);
        }
    });
}

function get(name){
   if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
      return decodeURIComponent(name[1]);
}

$("#content").keyup(function(event) {
    if(event.which == 13) {
        submitReply();
       $(this).focus();
    }
});

$("#sendReply").click(function() {
    submitReply();
});

showPost();

showReplies();
setInterval(showReplies, 3000);