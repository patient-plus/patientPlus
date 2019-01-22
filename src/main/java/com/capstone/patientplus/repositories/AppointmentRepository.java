package com.capstone.patientplus.repositories;


import com.capstone.patientplus.models.Appointment;
import com.capstone.patientplus.models.DoctorPatient;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

}