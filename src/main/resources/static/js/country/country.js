$(".hover").mouseleave(
    function () {
        $(this).removeClass("hover");
    }
);

let goToCreatePost = document.querySelectorAll(".country-wrapper .country-post");
let updateCountryBtn = document.querySelectorAll(".country-wrapper .edit-wrapper .edit");
let country = document.querySelectorAll(".country-wrapper img");
let updatePostBtn = document.querySelectorAll(".card-wrapper .edit-wrapper .edit");

function goToUpdateCountryForm(event) {
    location.href = `/country/update/${event.currentTarget.id}`;
}

function goToPostForm(event) {
    location.href = `/post/create/${event.currentTarget.id}`;
}

function goToPostList(event) {
    location.href = `/country/${event.currentTarget.id}`;
}

function goToUpdatePostForm(event) {
    location.href = `/post/update/${event.currentTarget.id}`;
}

function eventListening() {
    if (updateCountryBtn) {
        [].forEach.call(updateCountryBtn, function (e) {
            e.addEventListener("click", goToUpdateCountryForm);
        })
    }
    if (goToCreatePost) {
        [].forEach.call(goToCreatePost, function (e) {
            e.addEventListener("click", goToPostForm);
        })
    }
    if (country) {
        [].forEach.call(country, function (e) {
            e.addEventListener("click", goToPostList);
        })
    }
    if (updatePostBtn) {
        [].forEach.call(updatePostBtn, function (e) {
            e.addEventListener("click", goToUpdatePostForm);
        })
    }
}

function init() {
    eventListening();
}

init();