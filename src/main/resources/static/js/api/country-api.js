let createCountryBtn = document.querySelector(".file-upload .country-button");
let submitUpdateBtn = document.querySelector(".file-upload .update-country-button");
let deleteCountryBtn = document.querySelectorAll(".country-wrapper .edit-wrapper .delete");

let userId = document.querySelector(".user-id");

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

// Event Listening
function listeningEvent() {
    if (deleteCountryBtn) {
        [].forEach.call(deleteCountryBtn, function (e) {
            e.addEventListener("click", deleteCountry);
        })
    }
}

function init() {
    listeningEvent();
}

init();