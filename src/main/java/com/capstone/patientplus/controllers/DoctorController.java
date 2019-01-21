package com.capstone.patientplus.controllers;

import com.capstone.patientplus.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DoctorController {

    //allows doctor to go to registration page
    @GetMapping("/doctor/registration")
    public String seeDoctorRegistration(){
        return "doctors/registration";
    }

    //doctor registers
    @PostMapping("/doctor/registration")
    public String registerDoctor(User doctor){
        //register doctor in db
        //send doctor to their dashboard
        return "doctor/dashboard";
    }

    //doctor goes to the add patient form
    @GetMapping("/doctor/add-patient")
    public String addPatientForm(){
        return "doctor/add-patient";
    }

    //doctor submits adding patient form
    @PostMapping("/doctor/add-patient")
    public String addPatientById(){
        //code to add patient to patient list for doctor
        return "redirect: doctor/patients";
    }

    //doctor is able to view upcoming appointments
    @GetMapping("/doctor/dashboard")
    public String viewAppointments(){
        //grab appointments for doctor and set as model
        return "doctor/appointments";
    }

    //When populating the list of appointments we can set an object
    //for the array list and then assign the patient object for each button
    //by iterating through the array list and create a button attached to a form
    //with a get method this way we simply grab the patient object from the button pressed (idea)
    //otherwise we can simply assign the id of the patient to the button and
    //grab it as a path variable
    @GetMapping("/doctor/patient-profile")
    public String patientProfile(User patient){
        //grab patient and
        return "/doctor/patient-profile";
    }

}
