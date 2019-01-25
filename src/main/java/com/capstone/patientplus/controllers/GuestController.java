package com.capstone.patientplus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestController {

    @GetMapping("/find-doctor")
    public String getSearchPage(){
        return "search";
    }

}
