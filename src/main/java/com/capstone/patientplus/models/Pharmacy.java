package com.capstone.patientplus.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pharmacies")
public class Pharmacy {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank(message = "pharmacy name is required")
    @Column(nullable = false)
    private String pharmacyName;

    @Column(nullable = false)
    private String pharmacyAddress;

    @Column(name = "phone_number")
    private String pharmacyPhoneNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pharmacy")
    private List<User> patients;


    public Pharmacy(){}

    public Pharmacy(String pharmacyName, String pharmacyAddress, String pharmacyPhoneNumber) {
        this.pharmacyName = pharmacyName;
        this.pharmacyAddress = pharmacyAddress;
        this.pharmacyPhoneNumber = pharmacyPhoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }

    public String getPharmacyPhoneNumber() {
        return pharmacyPhoneNumber;
    }

    public void setPharmacyPhoneNumber(String pharmacyPhoneNumber) {
        this.pharmacyPhoneNumber = pharmacyPhoneNumber;
    }
}
