package com.capstone.patientplus.controllers;

import com.capstone.patientplus.models.User;
import com.capstone.patientplus.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private UsersRepository users;
    private PasswordEncoder passwordEncoder;
    private final EmergencyContactRepository emergencyDao;
    private final InsuranceRepository insuranceDao;
    private final MedicationRepository medicationDao;
    private final PharmacyRepository pharmacyDao;
    private final SurgeryRepository surgeryDao;
    private final AppointmentRepository appointmentDao;

    @Autowired
    private AuthenticationManager authenticationManager;


    public UserController(UsersRepository users, PasswordEncoder passwordEncoder, EmergencyContactRepository emergencyDao, InsuranceRepository insuranceDao, MedicationRepository medicationDao, PharmacyRepository pharmacyDao, SurgeryRepository surgeryDao, AppointmentRepository appointmentDao){
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.emergencyDao = emergencyDao;
        this.insuranceDao = insuranceDao;
        this.medicationDao = medicationDao;
        this.pharmacyDao = pharmacyDao;
        this.surgeryDao = surgeryDao;
        this.appointmentDao = appointmentDao;
    }

    //See User Dashboard
    @GetMapping("/{id}/dashboard")
    public String index(@PathVariable long id, Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId = user.getId();
        if (id != userId){
            return "redirect:/" + Long.toString(userId) + "/dashboard";
        }

        model.addAttribute("isPatient", user.isPatient());
        if(user.isPatient()){
            //Patient Dashboard info
            model.addAttribute("emergencyContact", emergencyDao.findByPatient(user));
        } else{
            //Doctor dashboard info
        }
//        model.addAttribute("appointments", appointmentDao.allForPatient(user));
        return "patient/profile";
    }

    @GetMapping("/")
    public String showSignUpForm(Model model){
        User user;
        try {
             user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch(ClassCastException e) {
            model.addAttribute("user", new User());
            return "home";
        }
            long userId = user.getId();
            return "redirect:/" + Long.toString(userId) + "/dashboard";
    }

    @PostMapping("/sign-up")
    public String saveUser(@Valid User user, Errors validation, Model model, HttpServletRequest request){
        if (validation.hasErrors()) {
            model.addAttribute("errors", validation);
            model.addAttribute("user", user);
            return "redirect:/";
        }
        String username = user.getUsername();
        String password = user.getPassword();
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);

        for (User eachUser : users.findAll()){
            if (user.getUsername().equalsIgnoreCase(eachUser.getUsername())){
                model.addAttribute("sameEmail", true);
                return "home";
            }
        }
        users.save(user);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        // generate session if one doesn't exist
        request.getSession();
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        return "redirect:/0/dashboard";
    }

    @GetMapping("/home")
    public String failedLogin(){
        return "redirect:/";
    }
}
