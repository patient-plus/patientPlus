"use strict";

$(document).ready(function(){
    //example request:
    //https://api.betterdoctor.com/2016-03-01/doctors?
    // location=tx&
    // skip=0&
    // limit=10&
    // user_key=

    //test for connection between files
    // $('#background').css('background-color', 'yellow');
    let api_key = `5c47b7f6015f91ffe3b0692782e7962c`;

    let options = $(`#insurances`);
    let plans = $(`#plans`);
    let searchBtn = $(`#search-btn`);
    let firstName = $(`#doctor-first-name`);
    let lastName = $(`#doctor-last-name`);
    let location = $(`#location`);
    let resultsSection = $(`#results-list`);

    options.empty();
    options.append('<option selected="true" disable value="choose-provider">Choose Insurance Provider</option>');

    plans.empty();
    plans.append('<option selected="true" value="choose-plan">Choose Plan</option>');

    const request = () =>{
        // return fetch(`https://api.betterdoctor.com/2016-03-01/insurances?user_key=`)
        return fetch(`../json/db.json`)
            .then(response => response.json())
            .then(response => {
                return response.data;
            })
    };

    const setInsuranceList = (data) => {
        for(let insurance of data){
            options.append($(`<option></option>`).attr('value', insurance.uid).text(insurance.name));
        }
    };

    const setInsurancePlans = (data, insuranceProvider) => {
        for(let insurance of data){
            let insuranceValue = insurance.uid;
            if(insuranceValue === insuranceProvider){
                for(let plan of insurance.plans){
                    plans.append($(`<option></option>`).attr('value', plan.uid).text(plan.name));
                }
            }
        }
    };

    const getRequestUrl = () => {
        var requestUrl = 'https://api.betterdoctor.com/2016-03-01/doctors?';

        if (options.val() !== "choose-provider" && plans.val() !== "choose-plan") {
            requestUrl += `insurance_uid=${plans.val()}&`;
        }
        if(firstName.val() !== ''){
            requestUrl += `first_name=${firstName.val()}&`;
        }
        if(lastName.val() !== ''){
            requestUrl += `last_name=${lastName.val()}&`;
        }
        if(location.val() !== ''){
            console.log(location.val().trim());
            requestUrl += `location=${location.val()}&`;
        }
        requestUrl += `user_key=${api_key}`;
        return requestUrl;

    };

    request().then((data) => {
        setInsuranceList(data);
    });

    options.change(() => {
        plans.empty();
        plans.append('<option selected="true">Choose Plan</option>');

        request().then((data) => {
            let insuranceProvider = options.val();
            setInsurancePlans(data, insuranceProvider);
        })

    });


    searchBtn.click( (e) => {
        e.preventDefault();

        // fetch(setRequestUrl());

        let doctorsRequest = () => {
            return fetch(getRequestUrl())
                .then(response => response.json)
                .then(response => {
                    return response.data
                })
                .then(data => {
                    resultsSection.empty();
                    for (let dataObject of data) {
                        let practiceUid = dataObject.uid;
                        let firstName = dataObject.profile.first_name;
                        let lastName = dataObject.profile.last_name;

                        console.log(firstName);
                        resultsSection.append($(`<li class="list-group-item" id='${practiceUid}'></li>`))
                            .text(`${profile.first_name} ${profile.last_name}`);

                        $(`#${practiceUid}`).append($(`<button></button>`)).text(`View Profile`);
                    }
                });
        };

        doctorsRequest();

    });


});

