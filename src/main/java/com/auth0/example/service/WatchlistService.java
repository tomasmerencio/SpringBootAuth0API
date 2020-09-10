package com.auth0.example.service;

import com.auth0.example.persistence.dao.AssetRepository;
import com.auth0.example.persistence.dao.WatchlistRepository;
import com.auth0.example.persistence.dao.UserRepository;
import com.auth0.example.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class WatchlistService implements IWatchlistService {
    @Autowired
    AssetRepository assetRepository;

    @Autowired
    WatchlistRepository watchlistRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Asset addAssetToWatchlist(User user, Long assetId, Long watchlistId) {
        Watchlist watchList = getWatchlistFromUser(user, watchlistId).orElseThrow(EntityNotFoundException::new);
        if (!assetExistsOnWatchlist(assetId, watchList)) {
            Asset asset = assetRepository
                    .findById(assetId)
                    .orElseThrow(EntityNotFoundException::new);

            WatchlistAsset watchlistAsset = new WatchlistAsset.Builder()
                    .setAsset(asset)
                    .setWatchlist(watchList)
                    .build();

            watchList.addWatchlistAsset(watchlistAsset);
            watchlistRepository.save(watchList);
            return asset;
        }
        return null;
    }

    private Boolean assetExistsOnWatchlist(Long activoId, Watchlist watchList) {
        return watchList.getAssets().stream().anyMatch(a -> a.getId().equals(activoId));
    }

    @Override
    public void deleteAsset(User user, Long assetId, Long watchlistId) {
        Watchlist watchList = getWatchlistFromUser(user, watchlistId).orElseThrow(EntityNotFoundException::new);
        watchList.deleteAsset(assetId);
        watchlistRepository.save(watchList);
    }

    @Override
    @Transactional
    public Watchlist addWatchlist(User user, String name) {
        Watchlist watchList = new Watchlist(name);
        user.addWatchlist(watchList);
        watchlistRepository.save(watchList);
        return watchList;
    }

    @Override
    @Transactional
    public Watchlist editName(User user, Long watchlistId, String name) {
        Optional<Watchlist> watchlistData = getWatchlistFromUser(user, watchlistId);
        if (watchlistData.isPresent()) {
            Watchlist watchList = watchlistData.get();
            watchList.setName(name);
            watchlistRepository.save(watchList);
            return watchList;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteWatchlist(User user, Long watchlistId) {
        if (getWatchlistFromUser(user, watchlistId).isPresent()) {
            user.deleteWatchlist(watchlistId);
            watchlistRepository.deleteById(watchlistId);
            userRepository.save(user);
        }
    }

    private Optional<Watchlist> getWatchlistFromUser(User user, Long watchlistId) {
        return user.getWatchlistById(watchlistId);
    }
}
