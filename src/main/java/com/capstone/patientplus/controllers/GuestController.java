package com.capstone.patientplus.controllers;

import com.capstone.patientplus.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GuestController {

    private PasswordEncoder passwordEncoder;



    @GetMapping("/find-doctor")
    public String getSearchPage(){
//        model.addAttribute("doctor", new User());
        return "search";
    }


    @PostMapping("/find-doctor")
    @ResponseBody
    public String addToDoctors(
            @RequestParam (name = "firstName") String firstName,
            @RequestParam (name = "lastName") String lastName,
            @RequestParam (name = "patient") boolean patient,
            @RequestParam (name = "phoneNumber") String phoneNumber
    ){
        System.out.format("%s %s %b", firstName, lastName, patient);
        String username = Character.toString(firstName.charAt(0)) + lastName;
        String password = "docPassword1";
        String hash = passwordEncoder.encode(password);


        User doctor = new User(firstName, lastName, username, phoneNumber, password, patient);

        //set dummy password for doctor


//        System.out.println();
        //code to add doctor
//        System.out.println(doctor.getFirstName());
        return "okay";
    }
}
