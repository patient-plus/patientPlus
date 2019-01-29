package com.capstone.patientplus.controllers;

import com.capstone.patientplus.models.DoctorPatient;
import com.capstone.patientplus.models.User;
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

@Controller
public class GuestController {

    private final PasswordEncoder passwordEncoder;
    private final DoctorPatientRepository doctorPatientDao;
    private final UsersRepository usersDao;


    public GuestController(PasswordEncoder passwordEncoder, DoctorPatientRepository doctorPatientDao, UsersRepository usersDao){
        this.passwordEncoder = passwordEncoder;
        this.doctorPatientDao = doctorPatientDao;
        this.usersDao = usersDao;
    }

    @GetMapping("/find-doctor")
    public String getSearchPage(){
//        model.addAttribute("doctor", new User());
        return "search";
    }

//    @ResponseBody

    @PostMapping("/find-doctor")
    public String addToDoctors(
            @RequestParam (name = "firstName") String firstName,
            @RequestParam (name = "lastName") String lastName,
            @RequestParam (name = "phoneNumber") String phoneNumber,
            @RequestParam (name = "patient") boolean patient
    ){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(user.isPatient()){
            System.out.format("%s %s %b", firstName, lastName, patient);
    //        String phoneNumber = "000-000-0000";
            String username = Character.toString(firstName.charAt(0)) + lastName;
            System.out.println(username);
            String password = "Password1";
            String hash = passwordEncoder.encode(password);
            User doctor = new User(firstName, lastName, username, phoneNumber, hash, patient);
            User loggedInPatient = new User(user);

            System.out.println(usersDao.save(doctor));

            DoctorPatient newDoctor = new DoctorPatient(doctor, loggedInPatient);
            doctorPatientDao.save(newDoctor);
            return "redirect:/" + loggedInPatient.getId() + "/dashboard";

        }
        //if user is logged in

        else{
            return "/";
        }

        //set dummy password for doctor


//        System.out.println();
        //code to add doctor
//        System.out.println(doctor.getFirstName());
    }
}
