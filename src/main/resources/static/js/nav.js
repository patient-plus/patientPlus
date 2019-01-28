$(document).ready(function () {

    let counter = 1;

    $('.dropdown').click(function () {
        (counter++) % 2 === 1 ? $(this).next().removeClass('d-none') : $(this).next().addClass('d-none');
    })
});