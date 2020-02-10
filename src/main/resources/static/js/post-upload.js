let uplodeFile = document.querySelector(".file-upload-input");
let country = document.querySelector("#country");
let countryDescription = document.querySelector("#country-description");
let submitButton = document.querySelector(".country-button");
let fileName;

function createCountry(event) {
    event.preventDefault();

    let data = {
        image: fileName,
        name: country.value,
        content: countryDescription.value,
        userId: 12
    };

    $.ajax({
        type: "POST",
        url: "/api/country/",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
    }).done(function () {
        alert("나라가 등록되었습니다.");
        window.location.href = "/";
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });
}

function listeningEvent() {
    if (submitButton) {
        submitButton.addEventListener("click", createCountry);
    }
}

function init() {
    listeningEvent();
}

init();


function readURL(input) {
    if (input.files && input.files[0]) {

        var reader = new FileReader();

        reader.onload = function (e) {
            $('.image-upload-wrap').hide();

            $('.file-upload-image').attr('src', e.target.result);
            $('.file-upload-content').show();

            $('.image-title').html(input.files[0].name);
        };

        reader.readAsDataURL(input.files[0]);

        fileName = input.files[0].name;
    } else {
        removeUpload();
    }
}

function removeUpload() {
    $('.file-upload-input').replaceWith($('.file-upload-input').clone());
    $('.file-upload-content').hide();
    $('.image-upload-wrap').show();
}

$('.image-upload-wrap').bind('dragover', function () {
    $('.image-upload-wrap').addClass('image-dropping');
});
$('.image-upload-wrap').bind('dragleave', function () {
    $('.image-upload-wrap').removeClass('image-dropping');
});
