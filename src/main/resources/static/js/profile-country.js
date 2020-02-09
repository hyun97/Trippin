let countryPost = document.querySelector(".country-post");

function doPost() {
    location.href = "#";
}

if (countryPost) {
    addEventListener("click", doPost);
}

$(".hover").mouseleave(
    function () {
        $(this).removeClass("hover");
    }
);
