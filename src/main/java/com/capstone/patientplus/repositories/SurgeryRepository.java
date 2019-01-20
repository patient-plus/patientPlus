package com.capstone.patientplus.repositories;

import com.capstone.patientplus.models.Surgery;
import com.capstone.patientplus.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SurgeryRepository extends CrudRepository<Surgery, Long> {
    List<Surgery> findAllByPatient(User patient);
}
