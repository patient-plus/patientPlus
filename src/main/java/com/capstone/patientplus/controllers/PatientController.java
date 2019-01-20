package com.capstone.patientplus.controllers;

import com.capstone.patientplus.services.PatientService;
import org.springframework.stereotype.Controller;

@Controller
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }




}
