package com.capstone.patientplus.controllers;

import com.capstone.patientplus.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GuestController {

    @GetMapping("/find-doctor")
    public String getSearchPage(){
//        model.addAttribute("doctor", new User());
        return "search";
    }


    @PostMapping("/find-doctor")
    @ResponseBody
    public String addToDoctors(){
//        System.out.println();
        //code to add doctor
//        System.out.println(doctor.getFirstName());
        System.out.println("made it to the controller");
        return "okay";
    }
}
