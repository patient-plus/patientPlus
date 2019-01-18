package com.capstone.patientplus.repositories;

import com.capstone.patientplus.models.Insurance;
import com.capstone.patientplus.models.User;
import org.springframework.data.repository.CrudRepository;

public interface InsuranceRepository extends CrudRepository<Insurance, Long> {
}
