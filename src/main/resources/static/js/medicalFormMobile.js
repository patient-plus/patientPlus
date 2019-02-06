$(document).ready(() => {

    let sections = ['ec', 'insurance', 'pharmacy', 'surgeries', 'medications'];

    $(`#drop-down-menu-1`).on('click', (e) => {
        e.preventDefault(); //prevents submitting button
        $(`#collapsible-ec`).toggleClass('d-none');
    });


    for(let i = 0 ; i < sections.length ; i++){
        $(`#${sections[i]}-next`).on('click', (e) => {
            e.preventDefault();
            $(`#collapsible-${sections[i]}`).toggleClass('d-none');
            $(`#collapsible-${sections[i + 1]}`).toggleClass('d-none');
        })
    }


    //
    // //these event listeners close the current section and open the next when the
    // //next button is pressed
    // $(`#ec-next`).on('click', (e) => {
    //     e.preventDefault();
    //     $(`#collapsible-ec`).toggleClass('d-none');
    //     $(`#collapsible-insurance`).toggleClass('d-none');
    // });
    //
    //
    //
    // $(`#insurance-next`).on('click', (e) => {
    //         e.preventDefault();
    //         $(`#collapsible-insurance`).toggleClass('d-none');
    //         $(`#collapsible-pharmacy`).toggleClass('d-none');
    //     });
    //
    // $(`#pharmacy-next`).on('click', (e) => {
    //     e.preventDefault();
    //     $(`#collapsible-pharmacy`).toggleClass('d-none');
    //     $(`#collapsible-surgeries`).toggleClass('d-none');
    // });
    //
    // $(`#surgeries-next`).on('click', (e) => {
    //     e.preventDefault();
    //     $(`#collapsible-surgeries`).toggleClass('d-none');
    //     $(`#collapsible-medications`).toggleClass('d-none');
    // });
    //
    // // $(`#medications-submit`).on('click', (e) => {
    // //     e.preventDefault();
    // //     $(`#collapsible-insurance`).toggleClass('d-none');
    // //     $(`#collapsible-pharmacy`).toggleClass('d-none');
    // // });




});