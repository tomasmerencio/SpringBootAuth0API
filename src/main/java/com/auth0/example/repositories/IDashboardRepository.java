package com.auth0.example.repositories;

import com.auth0.example.domains.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDashboardRepository extends JpaRepository<Dashboard, Long> {
}
