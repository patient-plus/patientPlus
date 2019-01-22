package com.capstone.patientplus.repositories;

import com.capstone.patientplus.models.Prescription;
import org.springframework.data.repository.CrudRepository;

public interface PrescriptionRepository extends CrudRepository<Prescription, Long> {
}
