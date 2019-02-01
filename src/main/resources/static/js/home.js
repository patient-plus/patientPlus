"use strict";
$(document).ready(function() {

    $(window).on('load', function () {
        $('#logged').modal('show');
    });

    $("#phone").mask("(999) 999-9999");
});