let card = document.querySelectorAll(".card-wrapper img");

function handleHoverCard(e) {
    e.target.classList.remove("animated", "pulse-out", "faster");
    e.target.classList.add("animated", "pulse", "faster");
}

function handleLeaveCard(e) {
    e.target.classList.remove("animated", "pulse", "faster");
    e.target.classList.add("animated", "pulse-out", "faster");
}

function listeningEvent() {
    if (card) {
        card.forEach(function (el) {
            el.addEventListener("mouseover", handleHoverCard);
            el.addEventListener("mouseout", handleLeaveCard);
        });
    }
}

function init() {
    listeningEvent();
}

init();