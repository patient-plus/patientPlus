package com.capstone.patientplus.controllers;

import com.capstone.patientplus.models.*;
import com.capstone.patientplus.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class PatientController {

    private final EmergencyContactRepository emergencyDao;
    private final InsuranceRepository insuranceDao;
    private final MedicationRepository medicationDao;
    private final PharmacyRepository pharmacyDao;
    private final SurgeryRepository surgeryDao;
    private final AppointmentRepository appointmentDao;
    private final UsersRepository users;
    private final DoctorPatientRepository doctorPatientDao;

    public PatientController(EmergencyContactRepository emergencyDao, InsuranceRepository insuranceDao, MedicationRepository medicationDao, PharmacyRepository pharmacyDao, SurgeryRepository surgeryDao, AppointmentRepository appointmentDao, UsersRepository users, DoctorPatientRepository doctorPatientDao){
        this.emergencyDao = emergencyDao;
        this.insuranceDao = insuranceDao;
        this.medicationDao = medicationDao;
        this.pharmacyDao = pharmacyDao;
        this.surgeryDao = surgeryDao;
        this.appointmentDao = appointmentDao;
        this.users = users;
        this.doctorPatientDao = doctorPatientDao;
    }


    //See and edit information
    @GetMapping("/patient/info")
    public String infoPage(Model model){
        User patient = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!patient.isPatient()){
            return "redirect:/doctor/dashboard";
        }

        for (int i = 1; i <= 3; i++){
            model.addAttribute("surgery" + Integer.toString(i), new Surgery());
            model.addAttribute("medication" + Integer.toString(i), new Medication());

        }

        model.addAttribute("emergencyContact", new EmergencyContact());
        model.addAttribute("insurance", new Insurance());
        model.addAttribute("pharmacy", new Pharmacy());
        return "patient/information";
    }

    @PostMapping("/patient/info")
    public String infoSubmit(@ModelAttribute EmergencyContact emergencyContact, @ModelAttribute Insurance insurance, @ModelAttribute Pharmacy pharmacy, @ModelAttribute Surgery surgery1, @ModelAttribute Surgery surgery2, @ModelAttribute Surgery surgery3, @ModelAttribute Medication medication1, @ModelAttribute Medication medication2, @ModelAttribute Medication medication3){
        User patient = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        emergencyContact.setPatient(patient);
        emergencyDao.save(emergencyContact);
//        patient.setInsurance(insurance);
//        insuranceDao.save(insurance);
        patient.setPharmacy(pharmacy);
        pharmacyDao.save(pharmacy);


        surgery1.setPatient(patient);
        surgeryDao.save(surgery1);
        surgery2.setPatient(patient);
        surgeryDao.save(surgery2);
        surgery3.setPatient(patient);
        surgeryDao.save(surgery3);

        medication1.setPatient(patient);
        medicationDao.save(medication1);
        medication2.setPatient(patient);
        medicationDao.save(medication2);
        medication3.setPatient(patient);
        medicationDao.save(medication3);

        return "redirect:/0/dashboard";
    }

    @GetMapping("patient/appointment/create")
    public String appointmentCreate(Model model){
        User patient = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!patient.isPatient()){
            return "redirect:/doctor/dashboard";
        }

        //Get list of doctors for that user
        List<DoctorPatient> doctorsPatient = doctorPatientDao.findAllDoctorsByPatient(patient);
        List<User> doctors = new ArrayList<>();
        for(DoctorPatient combo : doctorsPatient){
            doctors.add(users.findById(combo.getDoctor().getId()));
        }
        model.addAttribute("doctors", doctors);
        model.addAttribute("chosenDoctorID", 0);
        //Make new appointment object
        model.addAttribute("appointment", new Appointment());

        return "/appointments/create-edit";
    }

    @PostMapping("patient/appointment/create")
    public String appointmentCreateSubmit(@ModelAttribute Appointment appointment, @ModelAttribute long chosenDoctorID){
        User patient = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        appointment.setPatient(patient);
        appointment.setDoctor(users.findById(chosenDoctorID));
        return "redirect:/0/dashboard";
    }
}
