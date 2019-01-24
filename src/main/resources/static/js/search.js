"use strict";

$(document).ready(function(){
    //test for connection between files
    // $('#background').css('background-color', 'yellow');
    let options = $(`#insurances`);
    let plans = $(`#plans`);

    options.empty();
    options.append('<option selected="true" disable>Choose Insurance Provider</option>');

    plans.empty();
    plans.append('<option selected="true">Choose Plan</option>');

    const setInsuranceList = () =>{
        // return fetch(`https://api.betterdoctor.com/2016-03-01/insurances?user_key=`)
        return fetch(`../json/db.json`)
            .then(response => response.json())
            .then(response => {
                return response.data;
            }).then(data => {
                for(let insurance of data){
                    options.append($(`<option></option>`).attr('value', insurance.uid).text(insurance.name));
                }
                return data;
            });
    };

    const setInsurancePlans = (data, insuranceProvider) => {
        for(let insurance of data){
            let insuranceValue = insurance.uid;
            if(insuranceValue === insuranceProvider){
                console.log('in the if');
                for(let plan of insurance.plans){
                    plans.append($(`<option></option>`).attr('value', plan.uid).text(plan.name));
                }
            }
        }
    };

    setInsuranceList();

    options.change(() => {
        plans.empty();
        plans.append('<option selected="true">Choose Plan</option>');

        setInsuranceList().then((data) => {
            console.log(options.val() + 'test');
            let insuranceProvider = options.val();
            setInsurancePlans(data, insuranceProvider);
        })

    })

});

