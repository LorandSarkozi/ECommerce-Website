package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Service class for loading user details by username for authentication.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads user details by username.
     *
     * @param username Username of the user to load.
     * @return UserDetails representing the loaded user.
     * @throws UsernameNotFoundException if the user with the given username is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find the user by email (assuming email is the username)
        User optionalUser = userRepository.findByEmail(username);

        // If user not found, throw exception
        if (optionalUser == null) throw new UsernameNotFoundException("User not found", null);

        // Create and return UserDetails object with user's email and password
        return new org.springframework.security.core.userdetails.User(optionalUser.getEmail(), optionalUser.getPassword(),
                new ArrayList<>());
    }
}
