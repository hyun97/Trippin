let updateUserBtn = document.querySelector(".update-user-button");
let deleteUserBtn = document.querySelector(".delete-user-button");

// Update
function updateUser(event) {
    event.preventDefault();

    let userId = document.querySelector("#update-name").className;
    let name = document.querySelector("#update-name").value;
    let comment = document.querySelector("#update-comment").value;
    let imageName = document.querySelector(".file-upload .file-upload-input");

    let updatedImage = document.querySelector(".user-image img").alt;

    if (name === "") {
        swal({
            title: "이름을 입력하세요 😥",
            icon: "error",
        })
    } else {
        // 파일이 업로드 되었을 시 수정
        if (imageName.files[0] != null) {
            updatedImage = imageName.files[0].name;
        }

        let data = {
            name: name,
            feel: comment,
            picture: updatedImage
        };

        console.log(data);

        $.ajax({
            type: "PUT",
            url: `/api/user/${userId}`,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data)
        }).done(function () {
            swal({
                title: "수정 완료 😀",
                icon: "success",
            }).then(() => {
                location.href = `/user/${userId}`;
            });
        }).fail(function (error) {
            console.log(error);
            swal({
                title: "수정 실패 😥",
                icon: "error",
            })
        });
    }
}

// Delete
function deleteUser(event) {
    event.preventDefault();

    let userId = document.querySelector("#update-name").className;

    $.ajax({
        type: "DELETE",
        url: `/api/user/${userId}`,
    }).done(function () {
        swal({
            title: "탈퇴 완료 😥",
            icon: "success",
        }).then(() => {
            location.href = `/logout`;
        });
    }).fail(function (error) {
        swal({
            title: "탈퇴 실패 😥",
            icon: "error",
        }).then(() => {
            location.href = `/user/${userId}`;
        });
    });
}

function listeningEvent() {
    if (updateUserBtn) {
        updateUserBtn.addEventListener("click", updateUser)
    }
    if (deleteUserBtn) {
        deleteUserBtn.addEventListener("click", deleteUser);
    }
}

function init() {
    listeningEvent();
}

init();