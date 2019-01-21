package com.capstone.patientplus.services;



import com.capstone.patientplus.models.Appointment;
import com.capstone.patientplus.models.DoctorPatient;
import com.capstone.patientplus.models.User;
import com.capstone.patientplus.repositories.AppointmentRepository;
import com.capstone.patientplus.repositories.DoctorPatientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentDao;
    private final DoctorPatientRepository comboDao;

    public AppointmentService(AppointmentRepository appointmentDao, DoctorPatientRepository comboDao){
        this.appointmentDao = appointmentDao;
        this.comboDao = comboDao;
    }

    public List<Appointment> allForPatient(User patient) {
        List<Appointment> appointments = new ArrayList<>();
        List<DoctorPatient> combinations = comboDao.findAllByPatient(patient);

        for (DoctorPatient combination : combinations){
            appointments.add(appointmentDao.findByCombination(combination));
        }
        return appointments;
    }

    public Appointment one(long id) {
        return appointmentDao.findOne(id);
    }

    public long save(Appointment appointment){
        appointmentDao.save(appointment);
        return appointment.getId();
    }

    public void delete(long id){
        appointmentDao.delete(id);
    }

}
