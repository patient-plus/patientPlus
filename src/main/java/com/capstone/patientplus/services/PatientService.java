package com.capstone.patientplus.services;

import com.capstone.patientplus.models.*;
import com.capstone.patientplus.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final EmergencyContactRepository emergencyDao;

    private final InsuranceRepository insuranceDao;

    private final MedicationRepository medicationDao;

    private final PharmacyRepository pharmacyDao;

    private final SurgeryRepository surgeryDao;

    public PatientService(EmergencyContactRepository emergencyDao, InsuranceRepository insuranceDao, MedicationRepository medicationDao, PharmacyRepository pharmacyDao, SurgeryRepository surgeryDao) {
        this.emergencyDao = emergencyDao;
        this.insuranceDao = insuranceDao;
        this.medicationDao = medicationDao;
        this.pharmacyDao = pharmacyDao;
        this.surgeryDao = surgeryDao;
    }

    //Emergency Contact Methods
    public EmergencyContact findEmergencyContactForPatient(User patient) {
        return emergencyDao.findByPatient(patient);
    }

    public long saveEmergencyContact(EmergencyContact emergencyContact){
        emergencyDao.save(emergencyContact);
        return emergencyContact.getId();
    }

    //Insurance Methods
//    public Insurance findPatientInsurance(User patient) {
//        return insuranceDao.findOne(patient.getInsurance().getId());
//    }

    public long savePatientInsurance(Insurance insurance){
        insuranceDao.save(insurance);
        return insurance.getId();
    }

    //Medication Methods
    public List<Medication> allMedicationsForPatient(User patient) {
        return medicationDao.findAllByPatient(patient);
    }

    public void saveMedication(Medication medication){
        medicationDao.save(medication);
    }

    //Pharmacy Methods
    public Pharmacy findPatientPharmacy(User patient) {
        return pharmacyDao.findOne(patient.getPharmacy().getId());
    }

    public long savePatientPharmacy(Pharmacy pharmacy){
        pharmacyDao.save(pharmacy);
        return pharmacy.getId();
    }

    //Surgery Methods
    public List<Surgery> allSurgeriesForPatient(User patient) {
        return surgeryDao.findAllByPatient(patient);
    }

    public void saveSurgery(Surgery surgery){
        surgeryDao.save(surgery);
    }

}
