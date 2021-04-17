package com.auth0.example.service;

import com.auth0.example.domains.Asset;
import com.auth0.example.domains.Dashboard;

public interface IDashboardService {
    Asset addAssetToDashboard(Long assetId, Dashboard dashboard);

    void deleteAsset(Long assetId, Dashboard dashboardId);
}
