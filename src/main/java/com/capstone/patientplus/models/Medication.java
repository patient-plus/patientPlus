package com.capstone.patientplus.models;

import javax.persistence.*;

@Entity
@Table(name = "medication")
public class Medication {

    @ManyToOne
    @JoinColumn(name="patient_id", nullable=false)
    private User patient;

    @Column()
    private String name;
    @Column()
    private String dose;

    @Id
    @GeneratedValue
    private long id;

    public Medication(){}

    public Medication(User patient, String name, String dose) {
        this.patient = patient;
        this.name = name;
        this.dose = dose;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
