package com.auth0.example.persistence.dao;

import com.auth0.example.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByAuth0Id(String auth0Id);
}
