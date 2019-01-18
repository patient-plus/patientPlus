package com.capstone.patientplus.controllers;

import com.capstone.patientplus.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DoctorController {

    @GetMapping("/doctor/registration")
    public String seeDoctorRegistration(){
        return "doctors/registration";
    }

    @PostMapping("/doctor/registration")
    public String registerDoctor(User doctor){
        //register doctor in db

        //send doctor to their dashboard
        return "doctor/dashboard";
    }

    @GetMapping("/doctor/add-patient")
    public String addPatientForm(){
        return "doctor/add-patient";
    }


    @PostMapping("/doctor/add-patient")
    public String addPatientById(){
        return "redirect: /doctor/patients";
    }

}
