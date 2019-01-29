$(document).ready(function () {
    let thisTable = '';
    for (let i = 0; i < parseFloat($('.numberOfAppointments').attr('name')); i++){
        thisTable = $('.patient').eq(i).attr('id');
        $('#' + thisTable).next().children().attr('data-target', '#patient' + thisTable);
    }
});
