package com.capstone.patientplus.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "insurance")
public class Insurance {

//    @ManyToMany
//    @JoinTable(
//            name = "doctor_insurance",
//            joinColumns = @JoinColumn(name = "doctor_id"),
//            inverseJoinColumns = @JoinColumn(name = "insurance_id"))
//    List<Insurance> doctors;

    @ManyToMany(mappedBy = "doctors")
    List<User> doctors;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String plan;

    @Id
    @GeneratedValue
    private long id;


    Insurance(){}

    public Insurance(String name, String plan, List<User> doctorInsurances) {
        this.name = name;
        this.plan = plan;
        this.doctorInsurances = doctorInsurances;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<User> getDoctorInsurances() {
        return doctorInsurances;
    }

    public void setDoctorInsurances(List<User> doctorInsurances) {
        this.doctorInsurances = doctorInsurances;
    }
}
