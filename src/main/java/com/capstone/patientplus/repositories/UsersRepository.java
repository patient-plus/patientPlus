package com.capstone.patientplus.repositories;


import com.capstone.patientplus.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findById(long id);
}
