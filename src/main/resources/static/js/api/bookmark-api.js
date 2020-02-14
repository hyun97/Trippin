let bookmarkBtn = document.querySelectorAll(".card .bookmark");

function createBookmark(event) {
    let userId = document.querySelector(".card-wrapper .user-id").innerHTML;

    let data = {
        userId: userId
    };

    $.ajax({
        type: "POST",
        url: `/api/bookmark/${event.currentTarget.id}`,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
    }).done(function () {
        swal("게시글 저장 성공 😀", "", "success");
    }).fail(function (error) {
        swal("게시글 저장 실패 😥", "", "error");
    });
}

function listeningEvent() {
    if (bookmarkBtn) {
        [].forEach.call(bookmarkBtn, function (e) {
            e.addEventListener("click", createBookmark);
        })
    }
}

function init() {
    listeningEvent();
}

init();
