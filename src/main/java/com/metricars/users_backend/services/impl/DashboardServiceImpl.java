package com.metricars.users_backend.services.impl;

import com.metricars.users_backend.dtos.AssetDTO;
import com.metricars.users_backend.dtos.DashboardDTO;
import com.metricars.users_backend.exceptions.AlreadyExistsException;
import com.metricars.users_backend.exceptions.NotFoundException;
import com.metricars.users_backend.repositories.IAssetRepository;
import com.metricars.users_backend.domains.Asset;
import com.metricars.users_backend.domains.Dashboard;
import com.metricars.users_backend.domains.DashboardAsset;
import com.metricars.users_backend.repositories.IDashboardAssetRepository;
import com.metricars.users_backend.services.IDashboardService;
import com.metricars.users_backend.utils.DTOConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements IDashboardService {
    private final IAssetRepository iAssetRepository;
    private final IDashboardAssetRepository iDashboardAssetRepository;
    private final DTOConverterUtil dtoConverterUtil;

    @Override
    @Transactional
    public AssetDTO addAssetToDashboard(Long assetId, Dashboard dashboard) {
        if (assetExistsOnDashboard(assetId, dashboard)) {
            throw new AlreadyExistsException("Asset");
        }
        Asset asset = iAssetRepository.findById(assetId).orElseThrow(() -> new NotFoundException("Asset"));
        DashboardAsset dashboardAsset = buildDashboardAsset(asset, dashboard);
        iDashboardAssetRepository.save(dashboardAsset);
        return dtoConverterUtil.convertAssetToDTO(asset);
    }

    private DashboardAsset buildDashboardAsset(Asset asset, Dashboard dashboard) {
        return new DashboardAsset.Builder()
                .setAsset(asset)
                .setDashboard(dashboard)
                .build();
    }

    private Boolean assetExistsOnDashboard(Long assetId, Dashboard dashboard) {
        return dashboard.getDashboardAssets().stream().anyMatch(a -> a.getAsset().getId().equals(assetId));
    }

    @Override
    @Transactional
    public void deleteAsset(Long assetId, Dashboard dashboard) {
        if (!assetExistsOnDashboard(assetId, dashboard)) {
            throw new EntityNotFoundException("Asset");
        }
        iDashboardAssetRepository.deleteByDashboardIdAndAssetId(dashboard.getId(), assetId);
    }

    @Override
    public DashboardDTO getDashboardDTO(Dashboard dashboard) {
        return dtoConverterUtil.convertDashboardToDTO(dashboard);
    }
}
