$(document).ready(function () {

    let counter = 1;
    let tabs = ['emergency-contact','insurance', 'pharmacy', 'surgery', 'medications'];

    $('.dropdown').click(function () {
        (counter++) % 2 === 1 ? $(this).next().removeClass('d-none') : $(this).next().addClass('d-none');
    });

    function footerPosition() {
        $('footer').css('position', $(document).height() > $(window).height() ? "inherit" : "fixed");
    }
    footerPosition();



    //sections that interact with changing footer
    for(let i = 0; i < tabs.length; i++){
        $(`#drop-down-menu-${i}`).on('click', (e) => {
            e.preventDefault(); //prevents submitting button
            $(`#collapsible-${tabs[i]}`).toggleClass('d-none');
            footerPosition();
        });

        $(`#to-${tabs[i]}`).on('click', (e) => {
            e.preventDefault();
            $(`#collapsible-${tabs[i]}`).toggleClass('d-none');
            $(`#collapsible-${tabs[i + 1]}`).toggleClass('d-none');
            footerPosition();
        });

        $(`#${tabs[i]}-tab`).on('click', () => {
            footerPosition();
        });
    }

});