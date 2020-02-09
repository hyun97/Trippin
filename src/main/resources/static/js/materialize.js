// Side bar
$(document).ready(function () {
    $('.sidenav').sidenav();
});

// Modal
$(document).ready(function () {
    $('.modal').modal();
});

// Select
$(document).ready(function () {
    $('select').formSelect();
});

// Input
$(document).ready(function () {
    $('input#input_text, textarea#textarea2').characterCounter();
});
$(document).ready(function () {
    M.updateTextFields();
});