package com.capstone.patientplus.services;


import com.capstone.patientplus.models.User;
import com.capstone.patientplus.models.UserWithRoles;
import com.capstone.patientplus.repositories.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsLoader implements UserDetailsService {

    private final UsersRepository users;

    public UserDetailsLoader(UsersRepository users){
        this.users = users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = users.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("No user found for " + username);
        }
        return new UserWithRoles(user);
    }
}
