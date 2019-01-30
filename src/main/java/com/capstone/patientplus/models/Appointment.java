package com.capstone.patientplus.models;

import javax.persistence.*;

@Entity
@Table(name="appointments")
public class Appointment {

    @Id @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String time;

    @Column(nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    private String notes;

    public Appointment(){}

    public Appointment(Appointment copy){
        this.id = copy.id;
        this.time = copy.time;
        this.location = copy.location;
        this.patient = copy.patient;
        this.doctor = copy.doctor;
        this.notes = copy.notes;
    }

    public Appointment(long id, String time, String location, User doctor, User patient){
        this.time = time;
        this.location = location;
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }
}
