package com.metricars.users_backend.services;

import com.metricars.users_backend.domains.User;
import com.metricars.users_backend.dtos.AssetDTO;
import com.metricars.users_backend.dtos.WatchlistDTO;

import java.util.List;

public interface IWatchlistService {
    AssetDTO addAssetToWatchlist(User user, Long assetId, Long watchlistId);
    void deleteAsset(User user, Long assetId, Long watchlistId);
    WatchlistDTO addWatchlist(User user, String name);
    WatchlistDTO editName(User user, Long id, String name);
    void deleteWatchlist(User user, Long id);
    List<WatchlistDTO> getWatchlists(User user);
}
