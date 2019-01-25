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

    let state = $(`#state`);
    let city = $(`#city`);
    let options = $(`#insurances`);
    let plans = $(`#plans`);
    let searchBtn = $(`#search-btn`);
    let firstName = $(`#doctor-first-name`);
    let lastName = $(`#doctor-last-name`);
    let location = $(`#location`);
    let resultsSection = $(`#results-list`);

    let doctorArry = [];

    options.empty();
    options.append('<option selected="true" disable value="choose-provider">Choose Insurance Provider</option>');

    plans.append('<option selected="true" value="choose-plan">Choose Plan</option>');

    state.empty();
    state.append('<option selected="true" value="choose-state">Choose A State</option>');

    city.empty();
    city.append('<option selected="true" value="choose-city">Choose A City</option>');

    const request = () =>{
        // return fetch(`https://api.betterdoctor.com/2016-03-01/insurances?user_key=`)
        return fetch(`/json/db.json`)
            .then(response => response.json())
            .then(response => {
                return response.data;
            })
    };

    //grabs json containing array of objects with keys name and abbreviation
    const requestStates = () => {
        return fetch(`/json/states_titlecase.json`)
            .then(response => response.json())
            .then(data => {
                return data;
            });
    };

    const requestCities = () => {
        return fetch(`/json/data_db.json`)
            .then(response => response.json())
            .then(data => data);
    };

    const setStatesList = (data) => {
        console.log(data[0]);
        for(let i =0 ; i < data.length; i++){
            let stateVal = `${data[i].name}-${data[i].abbreviation}`;
            state.append($(`<option></option>`).attr('value', stateVal).text(data[i].abbreviation));
        }
    };

    const setCitiesList = (data, stateChosen) => {
        for(let state in data){
            console.log(data);
            if(stateChosen === state){
                console.log('state matched');
                for(let i = 0; i < data[`${state}`].length ; i++){
                    let citySlug = (data[`${state}`])[i].toLowerCase().split(' ').join('-');
                    city.append($(`<option></option>`).attr('value', citySlug).text((data[`${state}`])[i]));
                }
            }
        }
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

        // if(firstName.val() !== ''){
        //     requestUrl += `first_name=${firstName.val()}&`;
        // }
        // if(lastName.val() !== ''){
        //     requestUrl += `last_name=${lastName.val()}&`;
        // }
        // if (options.val() !== "choose-provider" && plans.val() !== "choose-plan") {
        //     requestUrl += `insurance_uid=${plans.val()}&`;
        // }
        if(location.val() !== ''){
            requestUrl += `location=${location.val()}&`;
        }
        requestUrl += `skip=0&limit=10&user_key=${api_key}`;
        return requestUrl;

    };

    const getDoctorUrl = (locationSlug) => {
        let requestDoctorsUrl = `https://api.betterdoctor.com/2016-03-01/doctors?`;
        if(city.val() !== "choose-city"){
            requestDoctorsUrl += `location=${locationSlug}&skip=0&limit=100&user_key=${api_key}`;
            return requestDoctorsUrl;
        }
        return null;
    };

    request().then((data) => {
        setInsuranceList(data);
    });

    requestStates().then((data) => {
        setStatesList(data);
    });


    state.change(() => {
       city.empty();
       city.append('<option selected="true">State</option>');

       requestCities().then((data) => {
           let stateChosen = state.val().split('-')[0];
           console.log(state.val());
           setCitiesList(data, stateChosen);
       })

    });

    options.change(() => {
        plans.empty();
        plans.append('<option selected="true">Choose Plan</option>');

        request().then((data) => {
            let insuranceProvider = options.val();
            setInsurancePlans(data, insuranceProvider);
        })

    });

    city.change(() => {
        let citySlug = city.val();
        let stateSlug = state.val().split('-')[1].toLowerCase();
        let locationSlug = `${stateSlug}-${citySlug}`;
        let requestUrl = getDoctorUrl(locationSlug);

        resultsSection.empty();

        if(requestUrl !== null){
            fetch(requestUrl).then(response => response.json())
                .then(response => response.data)
                .then(data => {
                    console.log(data);
                    for(let obj in data){
                        //function adds results to Array of doctors so we can keep searching info set
                        //without making multiple api calls
                        doctorArry.push({
                            "first_name": obj.profile.first_name,
                            "last_name" : obj.profile.last_name,
                            "uid": obj.uid,
                            "clinic": obj.practices[0].name,
                            "address": `${obj.practices[0].visit_address.street} ${obj.practices[0].visit_address.city}, 
                            ${obj.practices[0].visit_address.state}, ${obj.practices[0].visit_address.zip}`,
                            "lat": obj.practices[0].lat,
                            "lon": obj.practices[0].lon,
                            "phone_number":
                        });
                    }
                    console.log(doctorArry);
                }).then(() => {
                    //once array is set we can add the listener on the forms
                searchBtn.click( (e) => {
                    e.preventDefault();


                    // fetch(setRequestUrl());

                    // let doctorsRequest = () => {
                    //     let requestUrl = getRequestUrl();
                    //     return fetch(requestUrl)
                    //         .then(response => response.json)
                    //         .then(response => {
                    //             return response.data;
                    //         })
                    //         .then(data => {
                    //             resultsSection.empty();
                    //             // for (let dataObject of data) {
                    //             //     let practiceUid = dataObject.uid;
                    //             //     let firstName = dataObject.profile.first_name;
                    //             //     let lastName = dataObject.profile.last_name;
                    //             //
                    //             //     console.log(firstName);
                    //             //     resultsSection.append($(`<li class="list-group-item" id='${practiceUid}'></li>`))
                    //             //         .text(`${profile.first_name} ${profile.last_name}`);
                    //             //
                    //             //     $(`#${practiceUid}`).append($(`<button></button>`)).text(`View Profile`);
                    //             // }
                    //         });
                    // };

                    // doctorsRequest();

                });

            });
        }
    });



    // let requestWithLocation = fetch()




});

