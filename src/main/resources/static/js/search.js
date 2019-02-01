"use strict";

$(document).ready(function(){

    $(document).on('click', '.add-doc', function () {
        $('#notLoggedIn').modal('show');
    });

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
    let resultsSection = $(`#results-list`);
    let doctorSection = $(`#doctor-results`);


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
        for(let i =0 ; i < data.length; i++){
            let stateVal = `${data[i].name}-${data[i].abbreviation}`;
            state.append($(`<option></option>`).attr('value', stateVal).text(data[i].abbreviation));
        }
    };

    const setCitiesList = (data, stateChosen) => {
        for(let state in data){
            if(stateChosen === state){
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

    const getDoctorUrl = (locationSlug) => {
        let requestDoctorsUrl = `https://api.betterdoctor.com/2016-03-01/doctors?`;
        if(city.val() !== "choose-city"){
            requestDoctorsUrl += `location=${locationSlug}&skip=0&limit=100&user_key=${api_key}`;
            return requestDoctorsUrl;
        }
        return null;
    };

    let token = $("meta[name='_csrf']").attr("content");
    let header = $("meta[name='_csrf_header']").attr("content");

    //this one makes the call to the back end using the url
    doctorSection.on('submit','.doctor-add',(e) => {
        e.preventDefault();
        console.log(e);
        console.log("in ajax");

        console.log("ajax test");
        $.ajax({
            url: "/find-doctor",
            type: "POST",
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            data: $(e.currentTarget).serialize()
        }).done((response) => {
            if(response.redirect === "true"){
                window.location.href = response.redirectUrl;
            } else{
                console.log('modal');
            }
        }).fail((response)=>console.log(response));
    });

    request().then((data) => {
        setInsuranceList(data);
    });

    requestStates().then((data) => {
        setStatesList(data);
    });


    state.change(() => {
       city.empty();
       city.append('<option selected="true">City</option>');

       requestCities().then((data) => {
           let stateChosen = state.val().split('-')[0];
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




        if(requestUrl !== null){
            fetch(requestUrl)
                .then(response => {
                console.log("in data response");
                return response.json()
            })
                .then(response => response.data)
                .then(data => {

                    let doctorArray = [];
                    for(let i = 0; i < data.length; i++){
                        //function adds results to Array of doctors so we can keep searching info set
                        //without making multiple api calls

                        doctorArray.push({
                            "first_name": data[i].profile.first_name.toLowerCase(),
                            "last_name" : data[i].profile.last_name.toLowerCase(),
                            "uid": data[i].uid,
                            "clinic": data[i].practices[0].name,
                            "address": `${data[i].practices[0].visit_address.street} ${data[i].practices[0].visit_address.city},
                            ${data[i].practices[0].visit_address.state}, ${data[i].practices[0].visit_address.zip}`,
                            "lat": data[i].practices[0].lat,
                            "lon": data[i].practices[0].lon,
                            "phone_number": data[i].practices[0].phones[0].number,
                            "insurance_uids" : data[i].practices[0].insurance_uids,
                            "insurances" : data[i].practices[0].insurances,
                            "link": `<form action="/find-doctor" method="post" class="doctor-add">
                                <div class="row">
                                    <div class="col-6">
                                        <span class="text-capitalize">Dr. ${data[i].profile.first_name} ${data[i].profile.last_name}</span>
                                        <input type="hidden" name="firstName" value="${data[i].profile.first_name}"/>
                                        <input type="hidden" name="lastName" value="${data[i].profile.last_name}"/>
                                        <input type="hidden" name="address" value="${data[i].practices[0].visit_address.street} ${data[i].practices[0].visit_address.city},
                            ${data[i].practices[0].visit_address.state}, ${data[i].practices[0].visit_address.zip}"/>
                                        <input type="hidden" name="phoneNumber" value="${data[i].practices[0].phones[0].number}"/>
                                        <input type="hidden" name="patient" value="${false}"/>
                                    </div>
                                    <div class="col-6">
                                    
                                        <button type="submit" class="add-doc btn btn-primary">Add To Doctors</button>
                                    </div>
                                </div>
                            </form>`
                        });

                    }
                    return doctorArray;
                }).then((doctorArray) => {

                    //once array is set we can add the listener on the forms
                searchBtn.click( (e) => {
                    e.preventDefault();

                    resultsSection.empty();


                    let parameters = {};
                    let paramNames = [];
                    let doctorResultsArr = [];


                    if(firstName.val() !== ''){
                        parameters.first_name = firstName.val().toLowerCase().trim();
                        paramNames.push("first_name");
                    }
                    if(lastName.val() !== ''){
                        parameters.last_name = lastName.val().toLowerCase().trim();
                        paramNames.push("last_name");

                    }
                    if (options.val().split('-')[0] !== "choose-provider") {

                        if(plans.val() !== "choose-plan"){
                            parameters.insurance_uid = plans.val();
                            paramNames.push("insurance_uid");
                        }
                    }

                    //we have a list of parameters that we want to make sure are matches

                    for(let i = 0; i < doctorArray.length; i++){
                        console.log('in loop');
                        if((doctorArray[i].first_name === parameters.first_name || parameters.first_name === undefined)
                        && (doctorArray[i].last_name === parameters.last_name || parameters.last_name === undefined)
                        && (doctorArray[i].insurance_uids.indexOf(parameters.insurance_uid) >= 0 || parameters.insurance_uid === undefined)){
                            doctorResultsArr.push(doctorArray[i]);
                        }

                    }
                    if(doctorResultsArr.length === 0){
                        resultsSection.html(`<li class="list-group-item">No Results Available</li>`);
                    }
                    else{
                        for(let i = 0; i < doctorResultsArr.length; i++){
                            resultsSection.append($(`<div class="list-group-item"></div>`).html(doctorResultsArr[i].link));
                        }
                    }

                    //by this point the elements are created
                });

            });
        }
    });
});

