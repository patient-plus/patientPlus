package com.capstone.patientplus.models;

import javax.persistence.*;

@Entity
@Table(name = "surgery")
public class Surgery {

    @ManyToOne
    @JoinColumn(name="patient_id", nullable=false)
    private User patient;

    @Column()
    private String surgeryDate;
    @Column()
    private String operation;

    @Id
    @GeneratedValue
    private long id;

    public Surgery(){}

    public Surgery(User patient, String surgeryDate, String operation) {
        this.patient = patient;
        this.surgeryDate = surgeryDate;
        this.operation = operation;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public String getSurgeryDate() {
        return surgeryDate;
    }

    public void setSurgeryDate(String surgeryDate) {
        this.surgeryDate = surgeryDate;
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