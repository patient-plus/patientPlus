package com.capstone.patientplus.services;



import com.capstone.patientplus.models.Appointment;
import com.capstone.patientplus.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository postDao;

    public AppointmentService(AppointmentRepository postDao){
        this.postDao = postDao;
    }

    public List<Appointment> all() {
        return (List<Appointment>) postDao.findAll();
    }

    public Appointment one(long id) {
        return postDao.findOne(id);
    }

    public long save(Appointment appointment){
        postDao.save(appointment);
        return appointment.getId();
    }

    public void delete(long id){
        postDao.delete(id);
    }

}
