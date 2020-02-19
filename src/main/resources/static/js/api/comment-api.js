let commentBtn = document.querySelector(".detail-wrapper .comment-btn");
let deleteCommentBtn = document.querySelectorAll(".content-wrapper .delete");
let commentContent = document.querySelector(".detail-wrapper .comment-content");
let countComment = document.querySelector(".detail-wrapper .comment-count");

function createComment() {
    if (commentContent.value != "") {
        let data = {
            content: commentContent.value,
            userId: document.querySelector(".user-id").innerHTML
        };

        $.ajax({
            type: "POST",
            url: `/api/comment/${commentContent.id}`,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data)
        }).done(function () {
            location.href = `/post/${commentContent.id}`;
        }).fail(function (error) {
            swal({
                title: "댓글 등록 실패 😥",
                icon: "error",
            })
        });
    }
}

function deleteComment(event) {
    let comment = event.target.parentNode;
    comment.remove(comment);
    countComment.innerHTML--;

    $.ajax({
        type: "DELETE",
        url: `/api/comment/${event.target.id}`,
    }).done(function () {
        //
    }).fail(function (error) {
        swal({
            title: "댓글 삭제 실패 😥",
            icon: "error",
        })
    });
}

function handlePressEnter(event) {
    if (event && event.key === "Enter") {
        createComment();
    }
}

function listeningEvent() {
    if (commentBtn) {
        commentBtn.addEventListener("click", createComment);
        document.onkeydown = handlePressEnter;
    }
    if (deleteCommentBtn) {
        deleteCommentBtn.forEach(function (e) {
            e.addEventListener("click", deleteComment);
        })
    }
    if (commentContent) {
        commentContent.focus();
    }
}

function init() {
    listeningEvent();
}

init();