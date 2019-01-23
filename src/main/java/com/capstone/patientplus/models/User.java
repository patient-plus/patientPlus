package com.capstone.patientplus.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue
    private long id;

    @NotBlank(message = "First name is required")
    @Column(nullable = false, name="first_name")
    private String firstName;

    @NotBlank()
    @Column(name = "dob")
    private String dateOfBirth;

    @NotBlank(message = "Last name is required")
    @Column(nullable = false, name="last_name")
    private String lastName;

    @NotBlank(message = "No email submitted")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email must be in the correct format")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "No phone number")
    @Size(min = 10, message = "Phone number is required")
    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @NotBlank(message = "No password submitted")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "is_patient")
    private boolean patient;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Prescription> prescriptions;

    ////insurance connection
    @ManyToOne
    @JoinColumn(name = "patient_insurance_id")
    private Insurance insurance;

    @ManyToMany
    @JoinTable(
            name = "doctor_insurance",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "insurance_id"))
    private List<Insurance> insurances;



    /////Pharmacy connection
    @ManyToOne
    @JoinColumn(name = "patient_pharmacy_id")
    private Pharmacy pharmacy;

    ////medications
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private List<Medication> medications;

    //surgeries
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patient")
    private List<Surgery> surgeries;


    public User(){}

    public User(User copy) {
        this.firstName = copy.firstName;
        this.lastName = copy.lastName;
        this.username = copy.username;
        this.phoneNumber = copy.phoneNumber;
        this.password = copy.password;
        this.dateOfBirth = copy.dateOfBirth;
        this.patient = copy.patient;
        this.id = copy.id;
        this.prescriptions = copy.prescriptions;
        this.insurance = copy.insurance;
        this.pharmacy = copy.pharmacy;
        this.medications = copy.medications;
        this.surgeries = copy.surgeries;
        this.insurances = copy.insurances;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPatient() {
        return patient;
    }

    public void setPatient(boolean patient) {
        this.patient = patient;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public List<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(List<Insurance> insurances) {
        this.insurances = insurances;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

    public List<Surgery> getSurgeries() {
        return surgeries;
    }

    public void setSurgeries(List<Surgery> surgeries) {
        this.surgeries = surgeries;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
}
