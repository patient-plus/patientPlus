package com.capstone.patientplus.models;

import javax.persistence.*;

@Entity
@Table(name = "surgery")
public class Surgery {

    @ManyToOne
    @JoinColumn(name="patient_id", nullable=false)
    private User patient;

    @Column(nullable = false)
    private String date;
    @Column(nullable = false)
    private String operation;

    @Id
    @GeneratedValue
    private long id;

    Surgery(){}

    public Surgery(User patient, String date, String operation) {
        this.patient = patient;
        this.date = date;
        this.operation = operation;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}