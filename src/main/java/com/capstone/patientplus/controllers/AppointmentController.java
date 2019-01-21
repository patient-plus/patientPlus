package com.capstone.patientplus.controllers;


import com.capstone.patientplus.models.Appointment;
import com.capstone.patientplus.models.User;
import com.capstone.patientplus.services.AppointmentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    //See All Appointments
    @GetMapping("/appointments")
    public String index(Model model){
//        model.addAttribute("appointments", appointmentService.all());
        return "appointments/index";
    }

    //See One Appointment
    @GetMapping("/appointments/{id}")
    public String singlePost(@PathVariable long id, Model model){
        model.addAttribute("appointment", appointmentService.one(id));

        User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (appointmentService.one(id).getCombination().getId() == owner.getId()){
            model.addAttribute("owner", true);
        }
        return "appointments/show";
    }

    //Delete Appointment
    @PostMapping("/appointments/{id}/delete")
    public String deletePost(@PathVariable long id){
        User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (appointmentService.one(id).getCombination().getId() != owner.getId()){
            return "redirect:/appointments/" + String.valueOf(id);
        }
        appointmentService.delete(id);
        return "redirect:/appointments";
    }

    //Edit Appointment Form
    @GetMapping("/appointments/{id}/edit")
    public String editPostForm(@PathVariable long id, Model model){
        User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (appointmentService.one(id).getCombination().getId() != owner.getId()){
            return "redirect:/appointments/" + String.valueOf(id);
        }
        model.addAttribute("edit", true);
        model.addAttribute("appointment", appointmentService.one(id));
        return "appointments/create-edit";
    }

    //Submit Form Edit
    @PostMapping("/appointments/{id}/edit")
    public String editPost(@ModelAttribute Appointment appointment){
        String idString = String.valueOf(appointmentService.save(appointment));
        return "redirect:/appointments/" + idString;
    }

    //Create Appointment Page
    @GetMapping("/appointments/create")
    public String createForm(Model model){
        model.addAttribute("create", true);
        model.addAttribute("appointment", new Appointment());
        return "appointments/create-edit";
    }

    //Submit Appointment Creation
//    @PostMapping("/appointments/create")
//    public String createPost(@ModelAttribute Appointment appointment){
//        User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        appointment.setCombination(owner);
//        String idString = String.valueOf(appointmentService.save(appointment));
//        return "redirect:/appointments/" + idString;
//    }

}
