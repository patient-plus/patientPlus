package com.capstone.patientplus.repositories;

import com.capstone.patientplus.models.DoctorPatient;
import com.capstone.patientplus.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DoctorPatientRepository extends CrudRepository<DoctorPatient, Long> {
    List<DoctorPatient> findAllDoctorsByPatient(User patient);
    List<DoctorPatient> findAllPatientsByDoctor(User doctor);
    Integer countByDoctorAndPatient(User doctor, User patient);
}
