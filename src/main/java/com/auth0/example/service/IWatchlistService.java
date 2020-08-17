package com.auth0.example.service;

import com.auth0.example.persistence.model.Asset;
import com.auth0.example.persistence.model.Watchlist;
import com.auth0.example.persistence.model.User;

public interface IWatchlistService {
    Asset addAssetToWatchlist(User user, Long assetId, Long watchlistId);
    void deleteAsset(User user, Long assetId, Long watchlistId);
    Watchlist addWatchlist(User user, String name);
    Watchlist editName(User user, Long id, String name);
    void deleteWatchlist(User user, Long id);
}
