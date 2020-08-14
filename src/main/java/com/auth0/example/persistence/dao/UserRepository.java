package com.auth0.example.persistence.dao;

import com.auth0.example.persistence.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long>{
    Usuario findByAuth0Id(String auth0Id);
}
