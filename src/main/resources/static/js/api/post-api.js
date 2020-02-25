let deletePostBtn = document.querySelectorAll(".card-wrapper .edit-wrapper .delete");

// Delete
function deletePost(event) {
    event.preventDefault();

    $.ajax({
        type: "DELETE",
        url: `/api/post/${event.currentTarget.id}`,
    }).done(function () {
        swal({
            title: "삭제 완료 😀",
            icon: "success",
        }).then(() => {
            location.href = `/`;
        });
    }).fail(function (error) {
        swal({
            title: "삭제 실패 😥",
            icon: "success",
        }).then(() => {
            location.href = `/`;
        });
    });
}

// Event Listening
function listeningEvent() {
    if (deletePostBtn) {
        [].forEach.call(deletePostBtn, function (e) {
            e.addEventListener("click", deletePost);
        })
    }
}

function init() {
    listeningEvent();
}

init();