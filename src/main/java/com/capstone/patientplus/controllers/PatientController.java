package com.capstone.patientplus.controllers;

import com.capstone.patientplus.models.*;
import com.capstone.patientplus.repositories.UsersRepository;
import com.capstone.patientplus.services.AppointmentService;
import com.capstone.patientplus.services.PatientService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;


@Controller
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final UsersRepository users;

    public PatientController(PatientService patientService, AppointmentService appointmentService, UsersRepository users){
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.users = users;
    }


    //See All Appointments
    @GetMapping("/{id}/dashboard")
    public String index(@PathVariable long id, Model model){
        if (!users.findById(id).isPatient()){
            return "redirect:/doctor/dashboard";
        }
        User patient = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!patient.isPatient()){
            return "redirect:/doctor/dashboard";
        }

        model.addAttribute("appointments", appointmentService.allForPatient(patient));
        model.addAttribute("emergencyContact", patientService.findEmergencyContactForPatient(patient));
        return "patient/profile";
    }

    //See and edit information
    @GetMapping("/patient/info")
    public String infoPage(Model model){
        User patient = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!patient.isPatient()){
            return "redirect:/doctor/dashboard";
        }

        model.addAttribute("emergencyContact", new EmergencyContact());
        model.addAttribute("insurance", new Insurance());
        model.addAttribute("pharmacy", new Pharmacy());
        model.addAttribute("surgeries", new ArrayList<Surgery>());
        model.addAttribute("medications", new ArrayList<Medication>());
        return "patient/information";
    }

    @PostMapping("/patient/info")
    public String infoSubmit(@ModelAttribute EmergencyContact emergencyContact, @ModelAttribute Insurance insurance, @ModelAttribute Pharmacy pharmacy, @ModelAttribute ArrayList<Surgery> surgeries, @ModelAttribute ArrayList<Medication> medications){
        User patient = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        emergencyContact.setPatient(patient);
        patientService.saveEmergencyContact(emergencyContact);
        patient.setInsurance(insurance);
        patientService.savePatientInsurance(insurance);
        patient.setPharmacy(pharmacy);
        patientService.savePatientPharmacy(pharmacy);

        for (Surgery surgery : surgeries){
            if (surgeries.indexOf(surgery) >= 20){
                break;
            }
            if(surgery != null) {
                surgery.setPatient(patient);
                patientService.saveSurgery(surgery);
            }
        }

        for (Medication medication : medications){
            if (medications.indexOf(medication) >= 20){
                break;
            }
            if(medication != null) {
                medication.setPatient(patient);
                patientService.saveMedication(medication);
            }
        }
        return "patient/dashboard";
    }
}
