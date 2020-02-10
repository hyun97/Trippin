let submitButton = document.querySelectorAll(".file-upload .country-button");
let createCountryContent = document.querySelector(".snip1477 .country-post");
let editCountryBtn = document.querySelector(".snip1477 .edit-wrapper .edit");
let deleteCountryBtn = document.querySelector(".snip1477 .edit-wrapper .delete");

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

    let countryId = document.querySelector(".snip1477 .countryId");

    $.ajax({
        type: "DELETE",
        url: `/api/country/${countryId.innerHTML}`,
    }).done(function () {
        alert("삭제 되었습니다.");
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
}

console.log(deleteCountryBtn);

function init() {
    listeningEvent();
}

init();