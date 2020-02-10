let submitButton = document.querySelector(".file-upload .country-button");
let updateCountryBtn = document.querySelectorAll(".snip1477 .edit-wrapper .edit");
let deleteCountryBtn = document.querySelectorAll(".snip1477 .edit-wrapper .delete");

// Create
function createCountry(event) {
    event.preventDefault();

    let ImageName = document.querySelector(".file-upload .file-upload-input");
    let countryName = document.querySelector(".file-upload #country");
    let countryDescription = document.querySelector(".file-upload #country-description");

    let data = {
        image: ImageName.files[0].name,
        name: countryName.value,
        content: countryDescription.value,
        userId: 12
    };

    $.ajax({
        type: "POST",
        url: "/api/country",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
    }).done(function () {
        alert("나라가 등록되었습니다.");
        window.location.href = "/";
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

// Delete
function deleteCountry(event) {
    event.preventDefault();

    $.ajax({
        type: "DELETE",
        url: `/api/country/${event.currentTarget.id}`,
    }).done(function () {
        alert("삭제 되었습니다.");
        location.href = "/";
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

// Update
function updateCountry(event) {
    event.preventDefault();

    $.ajax({
        type: "PUT",
        url: `/api/country/${event.currentTarget.id}`,
    }).done(function () {
        alert("수정 되었습니다.");
        location.href = "/";
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

// Event Listening
function listeningEvent() {
    if (submitButton) {
        submitButton.addEventListener("click", createCountry);
    }
    if (deleteCountryBtn) {
        [].forEach.call(deleteCountryBtn, function (e) {
            e.addEventListener("click", deleteCountry);
        })
    }
    if (updateCountryBtn) {
        [].forEach.call(updateCountryBtn, function (e) {
            e.addEventListener("click", updateCountry);
        })
    }
}

function init() {
    listeningEvent();
}

init();