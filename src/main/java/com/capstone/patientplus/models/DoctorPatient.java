package com.capstone.patientplus.models;

import javax.persistence.*;

@Entity
@Table(name = "doctor_patient", uniqueConstraints={@UniqueConstraint(columnNames={"doctor_id", "patient_id"})})
public class DoctorPatient {
    @Id
    @GeneratedValue
    private long id;

    //These work because the relation between a doctor and the user entity is one to one
    //just as the relation between patient and user is one to one
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;


    public DoctorPatient(){}

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
