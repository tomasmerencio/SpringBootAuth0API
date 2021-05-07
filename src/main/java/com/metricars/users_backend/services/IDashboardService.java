package com.metricars.users_backend.services;

import com.metricars.users_backend.domains.Dashboard;
import com.metricars.users_backend.dtos.AssetDTO;
import com.metricars.users_backend.dtos.DashboardDTO;

public interface IDashboardService {
    AssetDTO addAssetToDashboard(Long assetId, Dashboard dashboard);
    void deleteAsset(Long assetId, Dashboard dashboardId);
    DashboardDTO getDashboardDTO(Dashboard dashboard);
}
