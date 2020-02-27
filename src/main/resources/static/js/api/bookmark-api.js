let bookmarkBtn = document.querySelectorAll(".bookmark");
let favoriteBtn = document.querySelectorAll(".favorite");

// 북마크 추가
function createBookmark(event) {
    let userId = document.querySelector(".user-id").innerHTML;

    event.target.innerHTML = "bookmark";

    let data = {
        save: 1,
        userId: userId,
    };

    $.ajax({
        type: "POST",
        url: `/api/bookmark/${event.currentTarget.id}`,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
    }).done(function () {
        //
    }).fail(function (error) {
        swal("게시글 저장 실패 😥", "", "error");
    });
}

// 북마크 삭제
function deleteBookmark(event) {
    let userId = document.querySelector(".user-id").innerHTML;

    event.target.innerHTML = "bookmark_border";

    let data = {
        save: 1,
        userId: userId,
    };

    $.ajax({
        type: "DELETE",
        url: `/api/bookmark/${event.currentTarget.id}`,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
    }).done(function () {
        //
    }).fail(function (error) {
        swal("북마크 취소 실패 😥", "", "error");
    });
}

// 좋아요 추가
function createFavorite(event) {
    increaseFavorite(event);

    let userId = document.querySelector(".user-id").innerHTML;

    let data = {
        userId: userId
    };

    $.ajax({
        type: "POST",
        url: `/api/favorite/${event.currentTarget.id}`,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
    }).done(function () {
        //
    }).fail(function (error) {
        swal("좋아요 실패 😥", "", "error");
    });
}

// 좋아요 삭제
function deleteFavorite(event) {
    decreaseFavorite(event);

    let userId = document.querySelector(".user-id").innerHTML;

    let data = {
        userId: userId
    };

    $.ajax({
        type: "DELETE",
        url: `/api/favorite/${event.currentTarget.id}`,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
    }).done(function () {
        //
    }).fail(function (error) {
        swal("좋아요 취소 실패 😥", "", "error");
    });
}

function increaseFavorite(event) {
    let countFavorite = document.querySelectorAll(".favorite-count span");

    countFavorite.forEach(function (e) {
        if (event.target.id == e.id) {
            e.innerHTML++;
        }
    });

    event.target.innerHTML = "favorite";
    event.target.classList.add("red-text");
}

function decreaseFavorite(event) {
    let countFavorite = document.querySelectorAll(".favorite-count span");

    countFavorite.forEach(function (e) {
        if (event.target.id == e.id) {
            e.innerHTML--;
        }
    });

    event.target.innerHTML = "favorite_border";
    event.target.classList.remove("red-text");
}

function handleBookmarkClick(event) {
    if (event.target.innerHTML === "bookmark_border") {
        console.log(1);
        // createBookmark(event);
    } else if (event.target.innerHTML === "bookmark") {
        console.log(0);
        // deleteBookmark(event);
    }
}

function handleFavoriteClick(event) {
    if (event.target.innerHTML === "favorite_border") {
        createFavorite(event);
    } else {
        deleteFavorite(event);
    }
}

function listeningEvent() {
    if (bookmarkBtn) {
        [].forEach.call(bookmarkBtn, function (e) {
            e.addEventListener("click", handleBookmarkClick);
        })
    }
    if (favoriteBtn) {
        [].forEach.call(favoriteBtn, function (e) {
            e.addEventListener("click", handleFavoriteClick);
        })
    }
}

function init() {
    listeningEvent();
}

init();
