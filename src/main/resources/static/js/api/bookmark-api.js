let bookmarkBtn = document.querySelectorAll(".card-image a");

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
        alert("게시글이 저장되었습니다.");
        window.location = "/";
    }).fail(function (error) {
        alert(JSON.stringify(error));
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
