package com.capstone.patientplus.controllers;


import com.capstone.patientplus.models.Appointment;
import com.capstone.patientplus.models.User;
import com.capstone.patientplus.repositories.AppointmentRepository;
import com.capstone.patientplus.repositories.UsersRepository;
import org.aspectj.weaver.ast.Test;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {
    private final AppointmentRepository appointmentDao;
    private final UsersRepository users;


    public TestController(AppointmentRepository appointmentDao, UsersRepository users){
        this.appointmentDao = appointmentDao;
        this.users = users;
    }


    @GetMapping("/search")
    public String testSearch(){
        return "search";
    }

    @GetMapping("/meet-the-team")
    public String testMTT(){
        return "meet-the-team";
    }

}
