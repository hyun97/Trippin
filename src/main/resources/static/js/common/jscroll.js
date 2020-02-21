let post = document.querySelector("#autoScroll");
let nextPage = document.querySelector(".nextPage");
let currentPage = 1;

function handlePageBtn() {
    currentPage++;
    $.ajax({
        type: "GET",
        url: `/api/post/page/${currentPage}`,
        dataType: "json",
        contentType: "application/json; charset=utf-8"
    }).done(function (result) {
        result.forEach(function (e) {
            renderList(e);
        })
    }).fail(function (error) {
        console.log(JSON.stringify(error));
    });
}

function renderList(e) {
    $("#autoScroll-wrapper").append("<div id=\"autoScroll\" class=\"col s12 m6 l6 xl4\">\n" +
        "                    <div class=\"card z-depth-4\">\n" +
        "                        <a href=\"/post/" + e.id + "\">\n" +
        "                            <div class=\"card-image\">\n" +
        "                                <img src=\"/img/" + e.image + "\">\n" +
        "                                <span class=\"card-title\">" + e.country + " · " + e.region + "</span>\n" +
        "                            </div>\n" +
        "                        </a>\n" +
        "                        {{#isLogin}}\n" +
        "                            {{^validBookmark}}\n" +
        "                                <a id=\"" + e.id + "\"\n" +
        "                                   class=\"hoverable bookmark btn-floating halfway-fab waves-effect waves-light red\">\n" +
        "                                    <i class=\"material-icons\">bookmark_border</i>\n" +
        "                                </a>\n" +
        "                            {{/validBookmark}}\n" +
        "                            {{#validBookmark}}\n" +
        "                                <a id=\"" + e.id + "\"\n" +
        "                                   class=\"hoverable bookmark btn-floating halfway-fab waves-effect waves-light red\">\n" +
        "                                    <i class=\"material-icons\">bookmark</i>\n" +
        "                                </a>\n" +
        "                            {{/validBookmark}}\n" +
        "                        {{/isLogin}}\n" +
        "                        {{^isLogin}}\n" +
        "                            <a class=\"hoverable btn-floating halfway-fab waves-effect waves-light red\">\n" +
        "                                <i class=\"material-icons\">bookmark_border</i>\n" +
        "                            </a>\n" +
        "                        {{/isLogin}}\n" +
        "                        <div class=\"card-content\">\n" +
        "                            <a href=\"/user/{{user.id}}\" class=\"author black-text\">{{country.user.name}}</a>\n" +
        "                            <p>" + e.content + "</p>\n" +
        "                            <div class=\"card-social\">\n" +
        "                                {{#isLogin}}\n" +
        "                                    {{^validFavorite}}\n" +
        "                                        <i id=\"" + e.id + "\" class=\"small material-icons favorite\">favorite_border</i>\n" +
        "                                    {{/validFavorite}}\n" +
        "                                    {{#validFavorite}}\n" +
        "                                        <i id=\"" + e.id + "\" class=\"small material-icons favorite red-text\">favorite</i>\n" +
        "                                    {{/validFavorite}}\n" +
        "                                    {{^validUser}}\n" +
        "                                        {{^validFollow}}\n" +
        "                                            <i id=\"{{user.id}}\" class=\"small material-icons follow\">person_add</i>\n" +
        "                                        {{/validFollow}}\n" +
        "                                        {{#validFollow}}\n" +
        "                                            <i id=\"{{user.id}}\"\n" +
        "                                               class=\"small material-icons follow blue-text text-darken-3\">record_voice_over</i>\n" +
        "                                        {{/validFollow}}\n" +
        "                                    {{/validUser}}\n" +
        "                                {{/isLogin}}\n" +
        "                                {{^isLogin}}\n" +
        "                                    <i class=\"small material-icons unfavorite\">favorite_border</i>\n" +
        "                                    <i class=\"small material-icons\">person_add</i>\n" +
        "                                {{/isLogin}}\n" +
        "                                <div class=\"favorite-count\">좋아요 <span id=\"" + e.id + "\">{{favorite}}</span>개</div>\n" +
        "                                <a href=\"/post/" + e.id + "\" class=\"comment-count\">댓글 {{countComment}}개</a>\n" +
        "                            </div>\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                </div>")
}

function eventListening() {
    if (nextPage) {
        window.onscroll = function () {
            if ((window.innerHeight + window.pageYOffset) >= document.body.offsetHeight) {
                handlePageBtn();
            }
        };
    }
}

function init() {
    eventListening();
}

init();
