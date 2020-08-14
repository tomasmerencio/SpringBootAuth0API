package com.auth0.example.persistence.dao;

import com.auth0.example.persistence.model.ListaSeguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaSeguimientoRepository extends JpaRepository<ListaSeguimiento, Long> {
}
