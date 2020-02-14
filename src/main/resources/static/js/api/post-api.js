let createPostBtn = document.querySelector(".create-post .post-button");
let submitPostBtn = document.querySelector(".update-post-button");
let deletePostBtn = document.querySelectorAll(".card-wrapper .edit-wrapper .delete");

// Create
function createPost(event) {
    event.preventDefault();

    let countryId = document.querySelector(".create-post #country").className;
    let region = document.querySelector(".create-post #region");
    let content = document.querySelector(".create-post #content");
    let imageName = document.querySelector(".file-upload .file-upload-input");

    let data = {
        image: imageName.files[0].name,
        region: region.value,
        content: content.value,
        favorite: 0,
        countryId: countryId
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

// Update
function updatePost(event) {
    event.preventDefault();

    let postId = document.querySelector("#update-region").className;
    let region = document.querySelector("#update-region");
    let content = document.querySelector("#update-content");
    let imageName = document.querySelector(".file-upload .file-upload-input");
    let favorite = document.querySelector(".post-favorite").innerHTML;

    let updatedImage = document.querySelector(".file-upload-image").alt;

    // 파일이 업로드 되었을 시 수정
    if (imageName.files[0] != null) {
        updatedImage = imageName.files[0].name;
    }

    let data = {
        image: updatedImage,
        region: region.value,
        content: content.value,
        favorite: favorite
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
            // TODO: 해당 게시글 디테일로
            location.href = `/`;
        });
    }).fail(function (error) {
        swal({
            title: "수정 실패 😥",
            icon: "error",
        }).then(() => {
            location.href = `/`;
        });
    });
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