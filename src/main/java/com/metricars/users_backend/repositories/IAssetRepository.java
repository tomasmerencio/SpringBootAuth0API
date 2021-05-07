package com.metricars.users_backend.repositories;

import com.metricars.users_backend.domains.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAssetRepository extends JpaRepository<Asset, Long> {
}
