package com.capstone.patientplus.controllers;

import com.capstone.patientplus.models.User;
import com.capstone.patientplus.services.AppointmentService;
import com.capstone.patientplus.services.PatientService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    public PatientController(PatientService patientService, AppointmentService appointmentService){
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }


    //See All Appointments
    @GetMapping("/patient/profile")
    public String index(Model model){
        User patient = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (patient.getRole() != "patient"){
//            return "redirect:/doctor/profile";
//        }

//        model.addAttribute(" ", appointmentService.getAllForPatient(patient));
        return "patient/profile";
    }

}
