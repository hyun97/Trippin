let createCountryBtn = document.querySelector(".file-upload .country-button");
let submitUpdateBtn = document.querySelector(".file-upload .update-country-button");
let deleteCountryBtn = document.querySelectorAll(".country-wrapper .edit-wrapper .delete");

let userId = document.querySelector(".user-id");

// Create
function createCountry(event) {
    event.preventDefault();

    let imageName = document.querySelector(".file-upload .file-upload-input");
    let countryName = document.querySelector(".file-upload #country");
    let countryDescription = document.querySelector(".file-upload #country-description");

    if (imageName.files[0] === undefined) {
        swal({
            title: "사진을 업로드 해주세요 😥",
            icon: "error",
        })
    } else if (countryName.value === "") {
        swal({
            title: "나라를 입력 해주세요 😥",
            icon: "error",
        })
    } else {
        let data = {
            image: imageName.files[0].name,
            name: countryName.value,
            content: countryDescription.value,
            userId: userId.innerHTML
        };

        $.ajax({
            type: "POST",
            url: "/api/country",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data)
        }).done(function () {
            swal({
                title: "나라 등록 성공 😀 ",
                icon: "success",
            }).then(() => {
                window.location.href = `/user/${userId.innerHTML}`;
            });
        }).fail(function (error) {
            swal({
                title: "나라 등록 실패 😥",
                icon: "error",
            }).then(() => {
                window.location.href = `/user/${userId.innerHTML}`;
            });
        });
    }
}

// Delete
function deleteCountry(event) {
    event.preventDefault();

    $.ajax({
        type: "DELETE",
        url: `/api/country/${event.currentTarget.id}`,
    }).done(function () {
        swal({
            title: "삭제 완료 😀",
            icon: "success",
        }).then(() => {
            location.href = `/user/${userId.innerHTML}`;
        });
    }).fail(function (error) {
        swal({
            title: "삭제 실패 😥",
            icon: "error",
        }).then(() => {
            location.href = `/user/${userId.innerHTML}`;
        });
    });
}

// Update
function updateCountry(event) {
    event.preventDefault();

    let countryContent = document.querySelector("#update-country-description");
    let countryName = document.querySelector("#update-country-name");
    let ImageName = document.querySelector(".file-upload .file-upload-input");

    let updatedImage = document.querySelector(".file-upload-image").alt;

    // 파일이 업로드 되었을 시 수정
    if (ImageName.files[0] != null) {
        updatedImage = ImageName.files[0].name;
    }

    let data = {
        image: updatedImage,
        name: countryName.value,
        content: countryContent.value
    };

    $.ajax({
        type: "PUT",
        url: `/api/country/${countryName.className}`,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
    }).done(function () {
        swal({
            title: "수정 완료 😀",
            icon: "success",
        }).then(() => {
            location.href = `/user/${userId.innerHTML}`;
        });
    }).fail(function (error) {
        // swal({
        //     title: "수정 실패 😥",
        //     icon: "error",
        // });
        console.log(JSON.stringify(error))
    });
}

// Event Listening
function listeningEvent() {
    if (createCountryBtn) {
        createCountryBtn.addEventListener("click", createCountry);
    }
    if (deleteCountryBtn) {
        [].forEach.call(deleteCountryBtn, function (e) {
            e.addEventListener("click", deleteCountry);
        })
    }
    if (submitUpdateBtn) {
        submitUpdateBtn.addEventListener("click", updateCountry)
    }
}

function init() {
    listeningEvent();
}

init();