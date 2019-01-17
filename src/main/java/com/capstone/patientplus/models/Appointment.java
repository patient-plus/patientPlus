package com.capstone.patientplus.models;

import javax.persistence.*;

@Entity
@Table(name="appointments")
public class Appointment {

    @ManyToOne
    private DoctorPatient combination;

    @Column(nullable = false)
    private String time;
    @Column(nullable = false)
    private String location;

    @Id @GeneratedValue
    private long id;

    public Appointment(){}

    public Appointment(String time, String location, long id){
        this.time = time;
        this.location = location;
        this.id = id;
    }

    public DoctorPatient getCombination() {
        return combination;
    }

    public void setCombination(DoctorPatient combination) {
        this.combination = combination;
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
}
