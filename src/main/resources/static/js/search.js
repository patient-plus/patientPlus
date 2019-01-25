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
    let resultsSection2 = $(`#results-results`);


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
            let insuranceSlug = insurance.name + '-' + insurance.uid;
            options.append($(`<option></option>`).attr('value', insuranceSlug).text(insurance.name));
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
           console.log(stateChosen);
           console.log(state.val());
           setCitiesList(data, stateChosen);
       })

    });

    options.change(() => {
        plans.empty();
        plans.append('<option selected="true">Choose Plan</option>');

        request().then((data) => {
            let insuranceProviderId = options.val().split('-')[1];
            setInsurancePlans(data, insuranceProviderId);
        })

    });

    city.change(() => {
        $(`body`).off("click", searchBtn);

        let citySlug = city.val();
        let stateSlug = state.val().split('-')[1].toLowerCase();
        let locationSlug = `${stateSlug}-${citySlug}`;
        let requestUrl = getDoctorUrl(locationSlug);



        resultsSection.empty();

        if(requestUrl !== null){
            fetch(requestUrl).then(response => response.json())
                .then(response => response.data)
                .then(data => {

                    let doctorArray = [];
                    for(let i = 0; i < data.length; i++){
                        //function adds results to Array of doctors so we can keep searching info set
                        //without making multiple api calls

                        doctorArray.push({
                            "first_name": data[i].profile.first_name,
                            "last_name" : data[i].profile.last_name,
                            "uid": data[i].uid,
                            "clinic": data[i].practices[0].name,
                            "address": `${data[i].practices[0].visit_address.street} ${data[i].practices[0].visit_address.city},
                            ${data[i].practices[0].visit_address.state}, ${data[i].practices[0].visit_address.zip}`,
                            "lat": data[i].practices[0].lat,
                            "lon": data[i].practices[0].lon,
                            "phone_number": data[i].practices[0].phones,
                            "insurance_uids" : data[i].practices[0].insurance_uids,
                            "insurances" : data[i].practices[0].insurances,
                            "link": `<form action="/patient/appointment/create" method="post" >
                                <span>Dr. ${data[i].profile.first_name} ${data[i].profile.last_name}</span>
                                <input type="hidden" name="firstName" value="${data[i].profile.first_name}"/>
                                <input type="hidden" name="lastName" value="${data[i].profile.last_name}"/>
                                <input type="hidden" name="username" value="doctor${data[i].profile.last_name}"/>
                                <input type="hidden" name="password" value="Doctorpassword1"/>
                                <input type="hidden" name="patient" value="${false}"/>
                                <input type="hidden" name="phoneNumber" value="${data[i].practices[0].phones}">
                                
                                <button type="submit" class="btn btn-primary">Schedule Appointment</button>
                            </form>`
                        });

                    }
                    return doctorArray;
                }).then((doctorArray) => {

                for(let i = 0; i < doctorArray.length; i++){
                    console.log(doctorArray[i].insurance_uids);
                }
                    //once array is set we can add the listener on the forms
                searchBtn.click( (e) => {
                    e.preventDefault();
                    let firstNameSearch;
                    let lastNameSearch;
                    // let insuranceName;
                    let planSlug;
                    let parameters = [];
                    let doctorResultsArr = [];


                    if(firstName.val() !== ''){
                        firstNameSearch = firstName.val();
                        parameters.push("first_name");
                        console.log(firstNameSearch);
                    }
                    if(lastName.val() !== ''){
                        lastNameSearch = lastName.val();
                        parameters.push("last_name");
                        console.log(lastNameSearch);
                    }
                    if (options.val().split('-')[0] !== "choose-provider") {
                        // insuranceName = options.val().split('-')[0];
                        // parameters.push("")

                        if(plans.val() !== "choose-plan"){
                            planSlug = plans.val();
                            console.log(planSlug);
                        }
                    }

                    for(let i = 0; i < doctorArray.length; i++){
                        console.log('in loop');
                        let first_name = doctorArray[i].first_name;
                        let last_name = doctorArray[i].last_name;
                        let insurances = doctorArray[i].insurance_uids;

                        if(first_name === firstNameSearch || last_name === lastNameSearch || insurances.indexOf(planSlug) >= 0){
                            console.log('doctor match');
                            doctorResultsArr.push(doctorArray[i]);
                        }
                    }


                    console.log(doctorResultsArr);

                    for(let i = 0; i < doctorResultsArr.length; i++){
                        resultsSection2.append($(`<div class="list-group-item"></div>`).html(doctorResultsArr[i].link));
                    }
                });

            });
        }
    });



    // let requestWithLocation = fetch()




});

