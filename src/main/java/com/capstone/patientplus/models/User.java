package com.capstone.patientplus.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue
    private long id;

    @NotBlank(message = "No username submitted")
    @Size(min = 5, message = "A username must be at least 5 characters")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "No first name submitted")
    @Size(min = 5, message = "First name is required")
    @Column(nullable = false, name="first_name")
    private String firstName;

    @NotBlank(message = "No last name submitted")
    @Size(min = 5, message = "Last name is required")
    @Column(nullable = false, name="last_name")
    private String lastName;

    @NotBlank(message = "No email submitted")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email must be in the correct format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "No phone number")
    @Size(min = 10, message = "Phone number is required")
    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @NotBlank(message = "No email submitted")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must have one number, one uppercase letter, and one special character")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Column(nullable = false)
    private String password;

//    @NotBlank(message = "Must choose user type")
//    @Column(nullable = false, )


    public User(){}

    public User(User copy) {
        this.id = copy.id;
        this.username = copy.username;
        this.email = copy.email;
        this.password = copy.password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
