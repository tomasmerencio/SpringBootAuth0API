package com.metricars.users_backend.services;

import com.metricars.users_backend.dtos.AssetDTO;

import java.util.List;

public interface IAssetService {
    List<AssetDTO> getAllAssets();
}
