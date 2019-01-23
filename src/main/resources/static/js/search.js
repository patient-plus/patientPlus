"use strict";

$(document).ready(function(){
    //test for connection between files
    // $('#background').css('background-color', 'yellow');
    let insuranceNames = [];
    let options = $(`#insurances`);

    options.empty();
    options.append('<option selected="true" disable>Choose Insurance Provider</option>');

    const getInsuranceList = () =>{
        return fetch(`https://api.betterdoctor.com/2016-03-01/insurances?user_key=5c47b7f6015f91ffe3b0692782e7962c`)
            .then(response => response.json())
            .then(response => {
                return response.data;
            }).then(data => {
                for(let d of data){
                    // console.log(d.name);
                    console.log(insuranceNames.push(d.name));
                }
            });
    };

    getInsuranceList();
    console.log(insuranceNames);

    for(let insuranceName of insuranceNames){
        console.log("in loop");
        let valueName = insuranceName.split(' ').join();
        console.log(valueName);
        options.append($(`<option></option>`).attr('value', valueName).text(insuranceName));

    }

});

