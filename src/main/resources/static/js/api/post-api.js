let createPostBtn = document.querySelector(".create-post .post-button");
let submitPostBtn = document.querySelector(".update-post-button");
let deletePostBtn = document.querySelectorAll(".card-wrapper .edit-wrapper .delete");

// Create
function createPost(event) {
    event.preventDefault();

    let countryId = document.querySelector(".create-post #country").className;
    let userId = document.querySelector(".create-post #region").className;
    let region = document.querySelector(".create-post #region");
    let content = document.querySelector(".create-post #content");
    let imageName = document.querySelector(".file-upload .file-upload-input");

    if (imageName.files[0] === undefined) {
        swal({
            title: "사진을 업로드 해주세요 😥",
            icon: "error",
        })
    } else if (region.value === "") {
        swal({
            title: "지역을 입력하세요 😥",
            icon: "error",
        })
    } else if (content.value === "") {
        swal({
            title: "내용을 입력하세요 😥",
            icon: "error",
        })
    } else {
        let data = {
            image: imageName.files[0].name,
            region: region.value.toUpperCase(),
            content: content.value,
            countryId: countryId,
            userId: userId
        };

        $.ajax({
            type: "POST",
            url: "/api/post",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data)
        }).done(function () {
            swal({
                title: "등록 완료 😀",
                icon: "success",
            }).then(() => {
                location.href = `/`;
            });
        }).fail(function (error) {
            swal({
                title: "등록 실패 😥",
                icon: "error",
            }).then(() => {
                location.href = `/`;
            });
        });
    }
}

// Update
function updatePost(event) {
    event.preventDefault();

    let postId = document.querySelector("#update-region").className;
    let region = document.querySelector("#update-region");
    let content = document.querySelector("#update-content");
    let imageName = document.querySelector(".file-upload .file-upload-input");

    let updatedImage = document.querySelector(".file-upload-image").alt;

    if (region.value === "") {
        swal({
            title: "지역을 입력하세요 😥",
            icon: "error",
        })
    } else if (content.value === "") {
        swal({
            title: "내용을 입력하세요 😥",
            icon: "error",
        })
    } else {
        // 파일이 업로드 되었을 시 수정
        if (imageName.files[0] != null) {
            updatedImage = imageName.files[0].name;
        }

        let data = {
            image: updatedImage,
            region: region.value.toUpperCase(),
            content: content.value
        };

        $.ajax({
            type: "PUT",
            url: `/api/post/${postId}`,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data)
        }).done(function () {
            swal({
                title: "수정 완료 😀",
                icon: "success",
            }).then(() => {
                location.href = `/post/${postId}`;
            });
        }).fail(function (error) {
            swal({
                title: "수정 실패 😥",
                icon: "error",
            }).then(() => {
                location.href = `/post/${postId}`;
            });
        });
    }
}

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
    if (createPostBtn) {
        createPostBtn.addEventListener("click", createPost);
    }
    if (submitPostBtn) {
        submitPostBtn.addEventListener("click", updatePost);
    }
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