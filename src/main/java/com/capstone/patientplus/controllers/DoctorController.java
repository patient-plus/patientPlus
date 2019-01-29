package com.capstone.patientplus.controllers;

import com.capstone.patientplus.models.Appointment;
//import com.capstone.patientplus.models.DoctorPatient;
import com.capstone.patientplus.models.DoctorPatient;
import com.capstone.patientplus.models.Prescription;
import com.capstone.patientplus.models.User;
import com.capstone.patientplus.repositories.*;
//import com.capstone.patientplus.repositories.DoctorPatientRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DoctorController {
//doctors need to be able to:
// -grab all appointments they have
// -view patient profiles and paperwork
//-verify account

    //use appointment repo to find all appointments using the doctor_id:
    // First find all combos where the doctor_id is the same as the id of the logged in user
    // Second using the list of the ids for each combo, return a list of appointments

    private final AppointmentRepository apptDao;
    private final DoctorPatientRepository doctorPatientDao;
    private final InsuranceRepository insuranceDao;
    private final PrescriptionRepository prescriptionDao;
    private final UsersRepository usersDao;


    public DoctorController(
            AppointmentRepository apptDao,
            DoctorPatientRepository doctorPatientDao,
            InsuranceRepository insuranceDao,
            PrescriptionRepository prescriptionDao,
            UsersRepository usersDao

    ){
        this.apptDao = apptDao;
        this.doctorPatientDao = doctorPatientDao;
        this.insuranceDao = insuranceDao;
        this.prescriptionDao = prescriptionDao;
        this.usersDao = usersDao;

    }

    //doctor is able to view upcoming appointments
    @GetMapping("/doctor/dashboard")
    public String viewAppointments(Model model){
        //grab appointments for doctor and set as model
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.isPatient()){
            //finds combinations using logged in user and then uses those combinations to grab scheduled appointments
            List<Appointment> appointmentList = apptDao.findByDoctor(user);
            model.addAttribute("appointments", appointmentList);
        }
        return "redirect: dashboard";
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

    @GetMapping("/doctor/write-prescription/{id}")
    public String writePrescription(@PathVariable long id, Model model){
        User doctor = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (doctor.isPatient()){
            return "redirect:/0/dashboard";
        }
        List<DoctorPatient> doctorsPatient = doctorPatientDao.findAllPatientsByDoctor(doctor);
        for (DoctorPatient combo : doctorsPatient){
            if (combo.getPatient() == usersDao.findById(id)){
                model.addAttribute("patientId", id);
                model.addAttribute("prescription", new Prescription());
                return "prescription";
            }
        }
        return "redirect:/0/dashboard";
    }


    @PostMapping("/doctor/write-prescription/{id}")
    public String prescriptionSubmit(@PathVariable long id, @ModelAttribute Prescription prescription){
        User patient = usersDao.findById(id);
        prescription.setUser(patient);
        prescriptionDao.save(prescription);
        return "redirect:/0/dashboard";
    }
}
