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

    @PostMapping("/patient/appointment/create/test")
    public String appointmentCreateSubmit(@ModelAttribute Appointment appointment,
                                          @RequestParam("date") String date,
                                          @RequestParam("time") String time,
                                          @RequestParam("selectedDoctor") String chosenDoctorID,
                                          @RequestParam(name = "firstName") String firstName,
                                          @RequestParam(name = "lastName") String lastName,
                                          @RequestParam(name = "patient") String isPatient
    ){
        boolean patientIndicator = Boolean.parseBoolean(isPatient);

        User patient = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User doctor = new User( firstName, lastName, patientIndicator);



        String dateTime = date + " " + time;
        appointment.setTime(dateTime);
        appointment.setPatient(patient);
        appointment.setDoctor(users.findById(Long.parseLong(chosenDoctorID)));
        appointmentDao.save(appointment);
        return "/";
    }
}
