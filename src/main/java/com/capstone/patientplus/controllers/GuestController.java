package com.capstone.patientplus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GuestController {

    @GetMapping("/find-doctor")
    public String getSearchPage(){
        return "search";
    }


    @PostMapping("/find-doctor")
    public String addToDoctors(){
        //code to add doctor
        return "redirect: /home";
    }
}
