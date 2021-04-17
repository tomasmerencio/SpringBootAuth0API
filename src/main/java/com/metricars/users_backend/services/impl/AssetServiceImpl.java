package com.metricars.users_backend.services.impl;

import com.metricars.users_backend.dtos.AssetDTO;
import com.metricars.users_backend.repositories.IAssetRepository;
import com.metricars.users_backend.services.IAssetService;
import com.metricars.users_backend.utils.DTOConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements IAssetService {
    private final IAssetRepository IAssetRepository;
    private final DTOConverterUtil dtoConverterUtil;

    @Override
    public List<AssetDTO> getAllAssets() {
        return IAssetRepository.findAll().stream().map(dtoConverterUtil::convertAssetToDTO).collect(Collectors.toList());
    }
}
