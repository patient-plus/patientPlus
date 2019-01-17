package com.capstone.patientplus.repositories;


import com.capstone.patientplus.models.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
}