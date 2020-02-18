let followBtn = document.querySelectorAll(".follow");

function createFollow(event) {
    followBtn.forEach(function (followBtn) {
        if (followBtn.id === event.target.id) {
            if (followBtn.innerHTML === "팔로우") {
                followBtn.innerHTML = "언팔로우"
            } else {
                followBtn.innerHTML = "record_voice_over";
                followBtn.classList.add("blue-text", "text-darken-3");
            }
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

function deleteFollow(event) {
    followBtn.forEach(function (followBtn) {
        if (followBtn.id === event.target.id) {
            if (followBtn.innerHTML === "언팔로우") {
                followBtn.innerHTML = "팔로우"
            } else {
                followBtn.innerHTML = "person_add";
                followBtn.classList.remove("blue-text", "text-darken-3");
            }
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

function handleFollowBtnClick(event) {
    if (event.target.innerHTML === "person_add" || event.target.innerHTML === "팔로우") {
        createFollow(event);
    } else {
        deleteFollow(event);
    }
}

function listeningEvent() {
    if (followBtn) {
        [].forEach.call(followBtn, function (e) {
            e.addEventListener("click", handleFollowBtnClick);
        })
    }
}

function init() {
    listeningEvent();
}

init();