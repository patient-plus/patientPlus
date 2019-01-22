package com.capstone.patientplus.controllers;

import com.capstone.patientplus.models.Appointment;
import com.capstone.patientplus.models.DoctorPatient;
import com.capstone.patientplus.models.User;
import com.capstone.patientplus.repositories.AppointmentRepository;
import com.capstone.patientplus.repositories.DoctorPatientRepository;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final DoctorPatientRepository dpDao;

    public DoctorController(AppointmentRepository apptDao, DoctorPatientRepository dpDao){
        this.apptDao = apptDao;
        this.dpDao = dpDao;
    }

    //allows doctor to go to registration page
    @GetMapping("/doctor/registration")
    public String seeDoctorRegistration(){
        return "doctors/registration";
    }

    //doctor registers
    @PostMapping("/doctor/registration")
    public String registerDoctor(User doctor){
        //check that doctor is in api and then say verified
        //register doctor in db
        //send doctor to their dashboard
        return "doctor/dashboard";
    }

    //doctor is able to view upcoming appointments
    @GetMapping("/doctor/dashboard")
    public String viewAppointments(Model model){
        //grab appointments for doctor and set as model
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!user.isPatient()){
            //finds combinations using logged in user and then uses those combinations to grab scheduled appointments
            List<DoctorPatient> combinations = dpDao.findCombinationsByUser(user);
            List<Appointment> appointmentList = new ArrayList<>();
            for(DoctorPatient combination : combinations){
                appointmentList = apptDao.findByCombination(combination);
            }
            model.addAttribute("appointments", appointmentList);
        }
        return "doctor/dashboard";
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
