package com.metricars.users_backend.repositories;

import com.metricars.users_backend.domains.DashboardAsset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDashboardAssetRepository extends JpaRepository<DashboardAsset, Long> {
    void deleteByDashboardIdAndAssetId(Long dashboardId, Long assetId);
}
