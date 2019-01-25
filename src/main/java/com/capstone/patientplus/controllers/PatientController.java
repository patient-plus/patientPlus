package com.capstone.patientplus.controllers;

import com.capstone.patientplus.models.*;
import com.capstone.patientplus.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("pharmacy", new Pharmacy());
        return "patient/information";
    }

    @PostMapping("/patient/info")
    public String infoSubmit(@ModelAttribute EmergencyContact emergencyContact, @RequestParam("insuranceUrl") String insuranceImgURL, @ModelAttribute Pharmacy pharmacy, @ModelAttribute Surgery surgery1, @ModelAttribute Surgery surgery2, @ModelAttribute Surgery surgery3, @ModelAttribute Medication medication1, @ModelAttribute Medication medication2, @ModelAttribute Medication medication3){
        User patient = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        emergencyContact.setPatient(patient);
        emergencyDao.save(emergencyContact);

        Insurance insurance = new Insurance(insuranceImgURL);
        patient.setInsurance(insurance);
        insuranceDao.save(insurance);

        patient.setPharmacy(pharmacy);
        pharmacyDao.save(pharmacy);

        surgery1.setPatient(patient);
        surgeryDao.save(surgery1);

        if (surgery2 != null){
            Surgery thisSurgery = new Surgery(patient, surgery2.getDate(), surgery2.getOperation());
            surgeryDao.save(thisSurgery);
        }

        if (surgery3 != null){
            Surgery thisSurgery = new Surgery(patient, surgery3.getDate(), surgery3.getOperation());
            surgeryDao.save(thisSurgery);
        }

        medication1.setPatient(patient);
        medicationDao.save(medication1);

        if (medication2 != null){
            Medication thisMedication = new Medication(patient, medication2.getName(), medication2.getDose());
            medicationDao.save(thisMedication);
        }

        if (medication3 != null){
            Medication thisMedication = new Medication(patient, medication3.getName(), medication3.getDose());
            medicationDao.save(thisMedication);
        }
        return "redirect:/0/dashboard";
    }

    @GetMapping("/patient/appointment/create")
    public String appointmentCreate(Model model){
        User patient = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!patient.isPatient()){
            return "redirect:/doctor/dashboard";
        }

        //Get list of doctors for that user
        List<DoctorPatient> doctorsPatient = doctorPatientDao.findAllDoctorsByPatient(patient);
        List<User> doctors = new ArrayList<>();
        for(DoctorPatient combo : doctorsPatient){
            doctors.add(combo.getDoctor());
        }
        if (doctors.size() == 0){
            return "redirect:/find-doctor";
        }
        model.addAttribute("doctors", doctors);

        model.addAttribute("Title", "Create Appointment");
        model.addAttribute("create", true);
        //Make new appointment object
        model.addAttribute("appointment", new Appointment());

        return "/appointments/create-edit";
    }

    @PostMapping("/patient/appointment/create")
    public String appointmentCreateSubmit(@ModelAttribute Appointment appointment, @RequestParam("date") String date, @RequestParam("time") String time, @RequestParam("selectedDoctor") String chosenDoctorID){
        User patient = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String dateTime = date + " " + time;
        appointment.setTime(dateTime);
        appointment.setPatient(patient);
        appointment.setDoctor(users.findById(Long.parseLong(chosenDoctorID)));
        appointmentDao.save(appointment);
        return "redirect:/0/dashboard";
    }

    @PostMapping("/patient/appointment/delete/{id}")
    public String appointmentDelete(@PathVariable long id){
        appointmentDao.delete(id);
        return "redirect:/0/dashboard";
    }
}
