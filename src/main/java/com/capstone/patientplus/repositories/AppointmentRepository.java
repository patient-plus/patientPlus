package com.capstone.patientplus.repositories;


import com.capstone.patientplus.models.Appointment;
import com.capstone.patientplus.models.DoctorPatient;
import com.capstone.patientplus.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    List<Appointment> findByCombination(DoctorPatient doctorPatient);
}