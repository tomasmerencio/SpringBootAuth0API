package com.auth0.example.service;

import com.auth0.example.persistence.dao.AssetRepository;
import com.auth0.example.persistence.dao.DashboardRepository;
import com.auth0.example.persistence.model.Asset;
import com.auth0.example.persistence.model.Dashboard;
import com.auth0.example.persistence.model.DashboardAsset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class DashboardService implements IDashboardService {
    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Override
    @Transactional
    public Asset addAssetToDashboard(Long assetId, Dashboard dashboard) {
        if (assetExistsOnDashboard(assetId, dashboard)) {
            return null;
        }
        Asset asset = assetRepository.findById(assetId).orElseThrow(EntityNotFoundException::new);

        DashboardAsset dashboardAsset = new DashboardAsset
                .Builder().setAsset(asset).setDashboard(dashboard)
                .build();

        dashboard.addDashboardAsset(dashboardAsset);
        dashboardRepository.save(dashboard);

        return asset;
    }

    private Boolean assetExistsOnDashboard(Long assetId, Dashboard dashboard) {
        return dashboard.getAssets().stream().anyMatch(a -> a.getId().equals(assetId));
    }

    @Override
    @Transactional
    public void deleteAsset(Long assetId, Dashboard dashboard) {
        if (!assetExistsOnDashboard(assetId, dashboard)) {
            throw new EntityNotFoundException();
        }
        dashboard.deleteAsset(assetId);
        dashboardRepository.save(dashboard);
    }
}
