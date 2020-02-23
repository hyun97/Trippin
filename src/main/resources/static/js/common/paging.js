let next = document.querySelector(".page-wrapper .next");
let previous = document.querySelector(".page-wrapper .previous");

let queryString = window.location.search;
let urlParams = new URLSearchParams(queryString);
let page = parseInt(urlParams.get("page"));
let pageSize = document.querySelector(".page-wrapper");

function handleNextBtn() {
    if (page < pageSize.id) {
        if (page == 1) {
            location.href = `?page=${2}`;
        } else {
            page++;
            location.href = `?page=${page}`;
        }
    }
}

function handlePreviousBtn() {
    if (page <= pageSize.id && page != 1) {
        if (page == 2) {
            location.href = `?page=${1}`;
        } else {
            page--;
            location.href = `?page=${page}`;
        }
    }
}

function eventListening() {
    if (next && previous) {
        next.addEventListener("click", handleNextBtn);
        previous.addEventListener("click", handlePreviousBtn);

        if (isNaN(page)) {
            page = 1;
        }

        if (page == 1) {
            previous.classList.add("grey");
        }
        if (page >= pageSize.id || pageSize.id == 1) {
            next.classList.add("grey");
        }
    }
}

function init() {
    eventListening();
}

init();