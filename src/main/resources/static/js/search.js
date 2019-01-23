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
                    let insuranceValue = insurance.name.split(' ').join('-');
                    options.append($(`<option></option>`).attr('value', insuranceValue).text(insurance.name));
                }
                return data;
            });
    };

    const setInsurancePlans = (data, insuranceProvider) => {
        for(let insurance of data){
            let insuranceValue = insurance.name.split(' ').join('-');
            if(insuranceValue === insuranceProvider){
                for(let plan of insurance.plans){
                    let planValue = plan.split(" ").join('-');
                    options.append($(`<option></option>`).attr('value', planValue).text(plan.name));
                }
            }
        }
    };

    setInsuranceList();

    options.click(
        setInsuranceList().then( (data) => {
            let insuranceProvider = options.attr('value');
            setInsurancePlans(data, insuranceProvider);

        })
    )

});

