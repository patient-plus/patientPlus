package com.capstone.patientplus.models;

import javax.persistence.*;

@Entity
@Table(name = "emergency_contacts")
public class EmergencyContact {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, name = "phone_number")
    private String emergencyContactPhoneNumber;

    @Column(nullable = false)
    private String emergencyContactName;

    @Column(name = "relationship_to_patient")
    private String relationshipToPatient;

    @OneToOne
    private User patient;

    public EmergencyContact(){}

    public EmergencyContact(String emergencyContactPhoneNumber, String emergencyContactName, String relationshipToPatient) {
        this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
        this.emergencyContactName = emergencyContactName;
        this.relationshipToPatient = relationshipToPatient;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmergencyContactPhoneNumber() {
        return emergencyContactPhoneNumber;
    }

    public void setEmergencyContactPhoneNumber(String emergencyContactPhoneNumber) {
        this.emergencyContactPhoneNumber = emergencyContactPhoneNumber;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getRelationshipToPatient() {
        return relationshipToPatient;
    }

    public void setRelationshipToPatient(String relationshipToPatient) {
        this.relationshipToPatient = relationshipToPatient;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }
}
