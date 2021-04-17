package com.metricars.users_backend.repositories;

import com.metricars.users_backend.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAuth0Id(String auth0Id);
}
