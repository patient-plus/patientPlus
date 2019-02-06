$(document).ready(() => {

    let sections = ['ec', 'insurance', 'pharmacy', 'surgeries', 'medications'];
    let sectionBtns = ['insurance', 'pharmacy', 'surgeries', 'medications'];


    for(let i = 0; i < sections.length; i++){
        $(`#drop-down-menu-${i}`).on('click', (e) => {
            e.preventDefault(); //prevents submitting button
            $(`#collapsible-${sections[i]}`).toggleClass('d-none');
        });
    }


    for(let i = 0 ; i < sectionBtns.length ; i++){
        $(`#to-${sectionBtns[i]}`).on('click', (e) => {
            e.preventDefault();
            $(`#collapsible-${sections[i]}`).toggleClass('d-none');
            $(`#collapsible-${sections[i + 1]}`).toggleClass('d-none');
        });
    }
});