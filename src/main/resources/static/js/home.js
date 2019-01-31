"use strict";
$(document).ready(function() {

    $(window).on('load', function () {
        $('#logged').modal('show');
        setTimeout(function() {
            $('#logged').modal({show: false});
        }, 2000);
    });

    $("#phone").mask("(999) 999-9999");
});