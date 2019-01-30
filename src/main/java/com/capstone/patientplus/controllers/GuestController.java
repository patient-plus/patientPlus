package com.capstone.patientplus.controllers;

import com.capstone.patientplus.models.Appointment;
import com.capstone.patientplus.models.DoctorPatient;
import com.capstone.patientplus.models.User;
import com.capstone.patientplus.repositories.AppointmentRepository;
import com.capstone.patientplus.repositories.DoctorPatientRepository;
import com.capstone.patientplus.repositories.UsersRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GuestController {

    private final PasswordEncoder passwordEncoder;
    private final DoctorPatientRepository doctorPatientDao;
    private final UsersRepository usersDao;
    private final AppointmentRepository appointmentDao;


    public GuestController(PasswordEncoder passwordEncoder, DoctorPatientRepository doctorPatientDao, UsersRepository usersDao, AppointmentRepository appointmentDao){
        this.passwordEncoder = passwordEncoder;
        this.doctorPatientDao = doctorPatientDao;
        this.usersDao = usersDao;
        this.appointmentDao = appointmentDao;
    }

    @GetMapping("/find-doctor")
    public String getSearchPage(){
        //checks if anyone is logged in
        if(!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String)){
            User doctor = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!doctor.isPatient()){
                return "redirect:/0/dashboard";
            }

        }
        return "search";
    }


    @PostMapping("/find-doctor")
    @ResponseBody
    public Map<String, String> addToDoctors(
            @RequestParam (name = "firstName") String firstName,
            @RequestParam (name = "lastName") String lastName,
            @RequestParam (name = "patient") boolean patient
    ){
//        List<String> results = new ArrayList<>();
//            @RequestParam (name = "phoneNumber") String phoneNumber,

        HashMap<String, String> map = new HashMap<>();
        //if user is logged in as a patient
        if(!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String)){
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User loggedInPatient = usersDao.findById(user.getId());

            if(usersDao.countByUsername(loggedInPatient.getUsername()) != 0 && loggedInPatient.isPatient()){
                String username = Character.toString(firstName.charAt(0)).toLowerCase() + lastName.toLowerCase() + "@gmail.com";
                User doctor;

                if(usersDao.countByUsername(username) == 0 ){
                    //creates new doctor to add
                    String phoneNumber = "000-000-0000";
                    System.out.println("username GuestController:69" + username);
                    String password = "Password1";
                    String hash = passwordEncoder.encode(password);
                    doctor = new User(firstName, lastName, username, phoneNumber, hash, patient);
                    doctor.setDateOfBirth("07/07/1994");
                    //saves new doctor
                    usersDao.save(doctor);

                } else{
                    //finds existing doctor object
                    doctor = usersDao.findByUsername(username);
                }


                //finds doctors for given patient
                List<DoctorPatient> doctorPatientCombos = doctorPatientDao.findAllDoctorsByPatient(loggedInPatient);

                DoctorPatient newDoctor = new DoctorPatient(doctor, loggedInPatient);

//            if combo is not saved then save to db
                if(doctorPatientDao.countByDoctorAndPatient(doctor, loggedInPatient) == 0){
                    System.out.println("is not a current doctor");
                    doctorPatientDao.save(newDoctor);
                }

                //redirects page
//            results.add("/patient/appointment/create");
                map.put("redirect", "true");
                map.put("redirectUrl", "/patient/appointment/create");
                return map;
            }

                map.put("redirect", "false");
                map.put("redirectUrl", "/login");
                return map;
        }
        else{
            map.put("redirect", "false");
            map.put("redirectUrl", "/login");
//            results.add("/login");
            return map;
        }
    }
}
