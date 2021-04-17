package com.metricars.users_backend.utils;

import com.metricars.users_backend.domains.Asset;
import com.metricars.users_backend.domains.Dashboard;
import com.metricars.users_backend.domains.User;
import com.metricars.users_backend.domains.Watchlist;
import com.metricars.users_backend.dtos.AssetDTO;
import com.metricars.users_backend.dtos.DashboardDTO;
import com.metricars.users_backend.dtos.UserDTO;
import com.metricars.users_backend.dtos.WatchlistDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DTOConverterUtil {
    private final ModelMapper modelMapper;

    public UserDTO convertUserToDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setDashboard(convertDashboardToDTO(user.getDashboard()));
        List<WatchlistDTO> watchlists = user.getWatchlists().stream().map(this::convertWatchlistToDTO).collect(Collectors.toList());
        userDTO.setWatchlists(watchlists);
        return userDTO;
    }

    public DashboardDTO convertDashboardToDTO(Dashboard dashboard) {
        DashboardDTO dashboardDTO = new DashboardDTO();
        List<AssetDTO> assets = dashboard.getDashboardAssets()
                .stream()
                .map(dA -> convertAssetToDTO(dA.getAsset()))
                .collect(Collectors.toList());
        dashboardDTO.setAssets(assets);
        return dashboardDTO;
    }

    public WatchlistDTO convertWatchlistToDTO(Watchlist watchlist) {
        WatchlistDTO watchlistDTO = new WatchlistDTO();
        watchlistDTO.setName(watchlist.getName());
        List<AssetDTO> assets = watchlist.getWatchlistAssets()
                .stream()
                .map(dA -> convertAssetToDTO(dA.getAsset()))
                .collect(Collectors.toList());
        watchlistDTO.setAssets(assets);
        return watchlistDTO;
    }

    public AssetDTO convertAssetToDTO(Asset asset) {
        AssetDTO assetDTO = modelMapper.map(asset, AssetDTO.class);
        assetDTO.setAssetType(asset.getAssetType().name());
        return assetDTO;
    }
}
