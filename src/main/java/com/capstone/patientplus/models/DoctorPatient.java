package com.capstone.patientplus.models;

import javax.persistence.*;

@Entity
@Table(name = "doctor_patient", uniqueConstraints={@UniqueConstraint(columnNames={"doctor_id", "patient_id"})})
public class DoctorPatient {

    @OneToOne
    private User doctor;
    @OneToOne
    private User patient;

    @Id
    @GeneratedValue
    private long id;

    DoctorPatient(){}

    public DoctorPatient(User doctor, User patient) {
        this.doctor = doctor;
        this.patient = patient;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
