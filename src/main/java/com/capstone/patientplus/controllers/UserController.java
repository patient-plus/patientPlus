package com.capstone.patientplus.controllers;

import com.capstone.patientplus.models.User;
import com.capstone.patientplus.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {
    private UsersRepository users;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserController(UsersRepository users, PasswordEncoder passwordEncoder){
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String showSignUpForm(Model model){
        model.addAttribute("user", new User());
        return "home";
    }

    @PostMapping("/sign-up")
    public String saveUser(@Valid User user, Errors validation, Model model, HttpServletRequest request){
        if (validation.hasErrors()) {
            model.addAttribute("errors", validation);
            model.addAttribute("user", user);
            return "home";
        }
        String username = user.getUsername();
        String password = user.getPassword();
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        users.save(user);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
        return "redirect:/appointments";
    }
}
