package com.metricars.users_backend.repositories;

import com.metricars.users_backend.domains.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDashboardRepository extends JpaRepository<Dashboard, Long> {
}
