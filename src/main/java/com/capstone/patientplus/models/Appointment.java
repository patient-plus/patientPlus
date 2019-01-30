package com.capstone.patientplus.models;

import javax.persistence.*;

@Entity
@Table(name="appointments")
public class Appointment {

//    @ManyToOne
//    private DoctorPatient combination;

    @Column(nullable = false)
    private String time;
    @Column(nullable = false)
    private String location;

    @Id @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @OneToOne
    @Column(name = "appt_notes")
    private String apptNotes;

    public Appointment(){}

    public Appointment(long id, String time, String location, User doctor, User patient){
        this.time = time;
        this.location = location;
        this.id = id;
        this.doctor = doctor;
        this.patient = patient;
    }

//    public DoctorPatient getCombination() {
//        return combination;
//    }
//
//    public void setCombination(DoctorPatient combination) {
//        this.combination = combination;
//    }


    public String getApptNotes() {
        return apptNotes;
    }

    public void setApptNotes(String apptNotes) {
        this.apptNotes = apptNotes;
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
