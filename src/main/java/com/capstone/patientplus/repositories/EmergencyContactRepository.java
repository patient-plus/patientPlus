package com.capstone.patientplus.repositories;

import com.capstone.patientplus.models.EmergencyContact;
import com.capstone.patientplus.models.User;
import org.springframework.data.repository.CrudRepository;

public interface EmergencyContactRepository extends CrudRepository<EmergencyContact, Long> {
    EmergencyContact findByPatient(User patient);
}
