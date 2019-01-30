$(document).ready(function () {

    let counter = 1;

    $('.dropdown').click(function () {
        (counter++) % 2 === 1 ? $(this).next().removeClass('d-none') : $(this).next().addClass('d-none');
    });

    let docHeight = $(window).height();
    let footerHeight = $('footer').height();
    let footerTop = $('footer').position().top + footerHeight;

    function positionFooter(){
        if (footerTop < docHeight) {
            $('footer').css('margin-top', 10+ (docHeight - footerTop) + 'px');
        }
    }
    positionFooter();
    $(window).resize(positionFooter());
});