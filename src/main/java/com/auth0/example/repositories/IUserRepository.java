package com.auth0.example.repositories;

import com.auth0.example.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByAuth0Id(String auth0Id);
}
