let commentBtn = document.querySelector(".detail-wrapper .comment-btn");

function createComment(event) {
    let content = document.querySelector(".detail-wrapper .comment-content");

    if (content.value != "") {
        let data = {
            content: content.value,
            userId: document.querySelector(".user-id").innerHTML
        };

        $.ajax({
            type: "POST",
            url: `/api/comment/${event.target.id}`,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data)
        }).done(function () {
            location.href = `/post/${event.target.id}`;
        }).fail(function (error) {
            swal({
                title: "댓글 등록 실패 😥",
                icon: "error",
            })
        });
    }
}

function listeningEvent() {
    if (commentBtn) {
        commentBtn.addEventListener("click", createComment);
    }
}

function init() {
    listeningEvent();
}

init();