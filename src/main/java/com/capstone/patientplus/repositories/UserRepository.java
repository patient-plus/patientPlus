package com.capstone.patientplus.repositories;

import com.capstone.patientplus.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
