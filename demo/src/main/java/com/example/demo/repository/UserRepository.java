package com.example.demo.repository;

import com.example.demo.constants.UserRole;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    User findFirstByEmailAndPassword(String email, String password);


    User findByRole(UserRole userRole);

    User findFirstByName(String name);

    Optional<User> findByName(String name);



}
