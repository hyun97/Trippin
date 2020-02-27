let post = document.querySelector("#autoScroll");
let nextPage = document.querySelector(".nextPage");
let currentPage = 4;

function createFollowAjax(event, followBtn) {
    followBtn.forEach(function (followBtn) {
        if (followBtn.id === event.target.id) {
            followBtn.innerHTML = "record_voice_over";
            followBtn.classList.add("blue-text", "text-darken-3");
        }
    });

    let loginUser = document.querySelector(".user-id").innerHTML;

    let model = {
        userId: loginUser
    };


    $.ajax({
        type: "POST",
        url: `/api/follow/${event.target.id}`,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(model)
    }).done(function () {

    }).fail(function (error) {
        swal("팔로우 실패 😥", "", "error");
        console.log(error);
    });
}

function deleteFollowAjax(event, followBtn) {
    followBtn.forEach(function (followBtn) {
        if (followBtn.id === event.target.id) {
            followBtn.innerHTML = "person_add";
            followBtn.classList.remove("blue-text", "text-darken-3");
        }
    });

    let loginUser = document.querySelector(".user-id").innerHTML;

    let model = {
        userId: loginUser
    };

    $.ajax({
        type: "DELETE",
        url: `/api/follow/${event.target.id}`,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(model)
    }).done(function () {

    }).fail(function (error) {
        swal("팔로우 취소 실패 😥", "", "error");
        console.log(error);
    });
}

function handleFollowBtnClickAjax(event, followBtn) {
    if (event.target.innerHTML === "person_add") {
        createFollowAjax(event, followBtn);
    } else {
        deleteFollowAjax(event, followBtn);
    }
}

function handlePageBtn() {
    currentPage++;

    $.ajax({
        type: "GET",
        url: `/api/post/page/${currentPage}`,
        dataType: "json",
        contentType: "application/json; charset=utf-8"
    }).done(function (result) {
        result.forEach(function (e) {
            renderList(e);
        })
    }).fail(function (error) {
        console.log(JSON.stringify(error));
    });
}

function renderList(e) {
    $("#autoScroll-wrapper").append($("<div id=\"autoScroll\" class=\"col s12 m6 l6 xl4\"><div class=\"card z-depth-4\"><a href=\"/post/" + e.postId + "\" target=\"_blank\"><div class=\"card-image\"><img src=\"" + e.image + "\"><span class=\"card-title\">" + e.countryName + " · " + e.region + "</span></div></a>" + (e.login ? (e.bookmark ? "<a id=\"" + e.postId + "\" class=\"hoverable bookmark btn-floating halfway-fab waves-effect waves-light red\"><i class=\"material-icons\">bookmark</i></a>" : "<a id=\"" + e.postId + "\" class=\"hoverable bookmark btn-floating halfway-fab waves-effect waves-light red\"><i class=\"material-icons\">bookmark_border</i></a>") : "<a class=\"hoverable btn-floating halfway-fab waves-effect waves-light red\"><i class=\"material-icons\">bookmark_border</i></a>") + "<div class=\"card-content\"><a href=\"/user/" + e.userId + "\" class=\"author black-text\">" + e.authorName + "</a><p>" + e.content + "</p><div class=\"card-social\">" + (e.login ? (e.favorite ? "<i id=\"" + e.postId + "\" class=\"small material-icons favorite red-text\">favorite</i>" : "<i id=\"" + e.postId + "\" class=\"small material-icons favorite\">favorite_border</i>") + (e.author ? "" : (e.follow ? "<i id=\"" + e.userId + "\" class=\"small material-icons follow blue-text text-darken-3\">record_voice_over</i>" : "<i id=\"" + e.userId + "\" class=\"small material-icons follow\">person_add</i>")) : "<i class=\"small material-icons unfavorite\">favorite_border</i><i class=\"small material-icons\">person_add</i>") + "<div class=\"favorite-count\">좋아요 <span id=\"" + e.postId + "\">" + e.countFavorite + "</span>개</div><a href=\"/post/" + e.postId + "\" class=\"comment-count\">댓글 " + e.countComment + "개</a></div></div></div></div>").hide().fadeIn(1500));

    $(".bookmark").unbind("click").on("click", function (event) {
        handleBookmarkClick(event);
    });

    $(".favorite").unbind("click").on("click", function (event) {
        handleFavoriteClick(event);
    });

    $(".follow").unbind("click").on("click", function (event) {
        let followBtn = document.querySelectorAll(".follow");
        handleFollowBtnClickAjax(event, followBtn);
    });
}

function eventListening() {
    if (nextPage) {
        window.onscroll = function () {
            if ((window.innerHeight + window.pageYOffset) >= document.body.offsetHeight - 300) {
                handlePageBtn();
            }
        };
    }
}

function init() {
    eventListening();
}

init();
