let searchForm = document.querySelector(".search-form");
let searchBtn = document.querySelector(".search-btn");

function handleSubmitSearch(event) {
    event.preventDefault();

    if (searchForm.search.value != "") {
        location.href = `/search/${searchForm.search.value.toUpperCase()}`;
    }
}

function eventListening() {
    if (searchForm && searchBtn) {
        searchBtn.addEventListener("click", handleSubmitSearch);
        searchForm.addEventListener("submit", handleSubmitSearch);
    }
}

function init() {
    eventListening();
}

init();