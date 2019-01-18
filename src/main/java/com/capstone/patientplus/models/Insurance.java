package com.capstone.patientplus.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "insurance")
public class Insurance {

    @ManyToMany(mappedBy = "doctors")
    private List<User> doctors;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String plan;

    @Id
    @GeneratedValue
    private long id;


    Insurance(){}

    public Insurance(String name, String plan, List<User> doctors) {
        this.name = name;
        this.plan = plan;
        this.doctors = doctors;
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

    public List<User> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<User> doctorInsurances) {
        this.doctors = doctors;
    }
}
