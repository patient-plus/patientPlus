package com.capstone.patientplus.controllers;

import com.capstone.patientplus.models.*;
import com.capstone.patientplus.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
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
        User thisUser = users.findById(patient.getId());
        if (!patient.isPatient()){
            return "redirect:/0/dashboard";
        }

        model.addAttribute("emergencyContact", new EmergencyContact());
        model.addAttribute("pharmacy", new Pharmacy());
        return "patient/information";
    }

    @PostMapping("/patient/info")
    public String infoSubmit(@ModelAttribute EmergencyContact emergencyContact,
                             @RequestParam("insuranceUrl") String insuranceImgURL,
                             @ModelAttribute Pharmacy pharmacy,
                             @RequestParam("surgeryOperation1") String surgeryOperation1,
                             @RequestParam("surgeryDate1") String surgeryDate1,
                             @RequestParam("surgeryOperation2") String surgeryOperation2,
                             @RequestParam("surgeryDate2") String surgeryDate2,
                             @RequestParam("surgeryOperation3") String surgeryOperation3,
                             @RequestParam("surgeryDate3") String surgeryDate3,
                             @RequestParam("medicationName1") String medicationName1,
                             @RequestParam("medicationDose1") String medicationDose1,
                             @RequestParam("medicationName2") String medicationName2,
                             @RequestParam("medicationDose2") String medicationDose2,
                             @RequestParam("medicationName3") String medicationName3,
                             @RequestParam("medicationDose3") String medicationDose3
                             ){
        User patient = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User thisUser = users.findById(patient.getId());
        emergencyContact.setPatient(patient);
        emergencyDao.save(emergencyContact);

        Insurance insurance = new Insurance(insuranceImgURL);
        thisUser.setInsurance(insurance);
        insuranceDao.save(insurance);

        thisUser.setPharmacy(pharmacy);
        pharmacyDao.save(pharmacy);

        Surgery surgery1 = new Surgery(patient, surgeryDate1, surgeryOperation1);
        surgeryDao.save(surgery1);

        if (!surgeryOperation2.equalsIgnoreCase("")){
            Surgery surgery2 = new Surgery(patient, surgeryDate2, surgeryOperation2);
            surgeryDao.save(surgery2);
        }

        if (!surgeryOperation3.equalsIgnoreCase("")){
            Surgery surgery3 = new Surgery(patient, surgeryDate3, surgeryOperation3);
            surgeryDao.save(surgery3);
        }

        Medication medication1 = new Medication(patient, medicationName1, medicationDose1);
        medicationDao.save(medication1);

        if (!medicationName2.equalsIgnoreCase("")){
            Medication medication2 = new Medication(patient, medicationName2, medicationDose2);
            medicationDao.save(medication2);
        }

        if (!medicationName3.equalsIgnoreCase("")){
            Medication medication3 = new Medication(patient, medicationName3, medicationDose3);
            medicationDao.save(medication3);
        }

        users.save(thisUser);
        return "redirect:/0/dashboard";
    }

    @GetMapping("/patient/appointment/create")
    public String appointmentCreate(Model model, HttpSession session){
        User patient = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!patient.isPatient()){
            return "redirect:/0/dashboard";
        }

        if(session.getAttribute("recentlyAddedDocId") != null){

            User doctor = users.findById((Long) session.getAttribute("recentlyAddedDocId"));
            System.out.println("PatientController:122 Doctor sent to form is : " + doctor.getFirstName());
            model.addAttribute("newDoctor", doctor);

        }


//        User doctor = (User) users.findByUsername((Long) session.getAttribute("recentlyAddedDocId"));
        //Get list of doctors for that user
        List<DoctorPatient> doctorsPatient = doctorPatientDao.findAllDoctorsByPatient(patient);
        List<User> doctors = new ArrayList<>();
        outerLoop:
        for(DoctorPatient combo : doctorsPatient){
            for (Appointment appointment : appointmentDao.findByPatient(patient)){
                if (appointment.getDoctor() == combo.getDoctor()){
                    continue outerLoop;
                }
            }
            doctors.add(combo.getDoctor());
        }

//        System.out.println("PatientController: 134 name of doctor just created: " + doctor.getFirstName());

        if (doctors.size() == 0){
            return "redirect:/find-doctor";
        }
        model.addAttribute("doctors", doctors);

        model.addAttribute("Title", "Create Appointment");
        model.addAttribute("create", true);
        //Make new appointment object
        model.addAttribute("appointment", new Appointment());

        return "appointments/create-edit";
    }

    @PostMapping("/patient/appointment/create")
    public String appointmentCreateSubmit(@ModelAttribute Appointment appointment,
                                          @RequestParam("date") String date,
                                          @RequestParam("time") String time,
                                          @RequestParam("selectedDoctor") String selectedDoctorId
    ){
//        refactored form and controller to use form model binding for the appointment to set the doctor
        User patient = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User doctor = users.findById(Long.parseLong(selectedDoctorId));

        String dateTime = date + " " + time;
        appointment.setTime(dateTime);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setLocation(doctor.getAddress());

        appointmentDao.save(appointment);
        return "redirect:/";
    }

    @PostMapping("/patient/appointment/delete/{id}")
    public String appointmentDelete(@PathVariable long id){
        appointmentDao.delete(id);
        return "redirect:/0/dashboard";
    }

    @PostMapping("/patient/appointment/update-time/{id}")
    public String appointmentUpdate(@PathVariable long id, @RequestParam("date") String date, @RequestParam("time") String time){
        Appointment appointment = appointmentDao.findOne(id);

        String dateTime = date + " " + time;
        appointment.setTime(dateTime);
        appointmentDao.save(appointment);
        return "redirect:/0/dashboard";
    }

    @GetMapping("/patient/directions/{id}")
    public String getDirections(@PathVariable long id, Model model){
        String location = appointmentDao.findOne(id).getLocation();
        model.addAttribute("location", location);

        return "appointments/directions";
    }
}
