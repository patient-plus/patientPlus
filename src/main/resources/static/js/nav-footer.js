$(document).ready(function () {

    let counter = 1;
    let sections = ['ec', 'insurance', 'pharmacy', 'surgeries', 'medications'];
    let sectionBtns = ['insurance', 'pharmacy', 'surgeries', 'medications'];

    $('.dropdown').click(function () {
        (counter++) % 2 === 1 ? $(this).next().removeClass('d-none') : $(this).next().addClass('d-none');
    });

    function footerPosition() {
        $('footer').css('position', $(document).height() > $(window).height() ? "inherit" : "fixed");
    }
    footerPosition();




    for(let i = 0; i < sections.length; i++){
        $(`#drop-down-menu-${i}`).on('click', (e) => {
            e.preventDefault(); //prevents submitting button
            $(`#collapsible-${sections[i]}`).toggleClass('d-none');
            footerPosition();
        });
    }


    for(let i = 0 ; i < sectionBtns.length ; i++){
        $(`#to-${sectionBtns[i]}`).on('click', (e) => {
            e.preventDefault();
            if($(`#`))
            $(`#collapsible-${sections[i]}`).toggleClass('d-none');
            $(`#collapsible-${sections[i + 1]}`).toggleClass('d-none');
            footerPosition();
        });
    }
});