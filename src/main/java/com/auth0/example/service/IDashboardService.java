package com.auth0.example.service;

import com.auth0.example.persistence.model.Asset;
import com.auth0.example.persistence.model.Dashboard;

public interface IDashboardService {
    Asset addAssetToDashbooard(Long assetId, Dashboard dashboard);

    void deleteAsset(Long assetId, Dashboard dashboardId);
}
