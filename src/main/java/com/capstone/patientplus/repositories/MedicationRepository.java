package com.capstone.patientplus.repositories;

import com.capstone.patientplus.models.Medication;
import com.capstone.patientplus.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MedicationRepository extends CrudRepository<Medication, Long> {
    List<Medication> findAllByPatient(User patient);
}
