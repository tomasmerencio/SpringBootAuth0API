package com.auth0.example.service.impl;

import com.auth0.example.repositories.IAssetRepository;
import com.auth0.example.repositories.IDashboardRepository;
import com.auth0.example.domains.Asset;
import com.auth0.example.domains.Dashboard;
import com.auth0.example.domains.DashboardAsset;
import com.auth0.example.service.IDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class DashboardServiceImpl implements IDashboardService {
    @Autowired
    private IAssetRepository IAssetRepository;

    @Autowired
    private IDashboardRepository IDashboardRepository;

    @Override
    @Transactional
    public Asset addAssetToDashboard(Long assetId, Dashboard dashboard) {
        if (assetExistsOnDashboard(assetId, dashboard)) {
            return null;
        }
        Asset asset = IAssetRepository.findById(assetId).orElseThrow(EntityNotFoundException::new);

        DashboardAsset dashboardAsset = new DashboardAsset
                .Builder().setAsset(asset).setDashboard(dashboard)
                .build();

        dashboard.addDashboardAsset(dashboardAsset);
        IDashboardRepository.save(dashboard);

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
        IDashboardRepository.save(dashboard);
    }
}
