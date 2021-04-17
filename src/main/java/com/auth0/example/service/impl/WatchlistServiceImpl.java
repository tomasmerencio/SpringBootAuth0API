package com.auth0.example.service.impl;

import com.auth0.example.repositories.IAssetRepository;
import com.auth0.example.repositories.IUserRepository;
import com.auth0.example.repositories.IWatchlistRepository;
import com.auth0.example.domains.Asset;
import com.auth0.example.domains.User;
import com.auth0.example.domains.Watchlist;
import com.auth0.example.domains.WatchlistAsset;
import com.auth0.example.service.IWatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class WatchlistServiceImpl implements IWatchlistService {
    @Autowired
    IAssetRepository IAssetRepository;

    @Autowired
    IWatchlistRepository IWatchlistRepository;

    @Autowired
    IUserRepository IUserRepository;

    @Override
    public Asset addAssetToWatchlist(User user, Long assetId, Long watchlistId) {
        Watchlist watchList = getWatchlistFromUser(user, watchlistId).orElseThrow(EntityNotFoundException::new);
        if (assetExistsOnWatchlist(assetId, watchList)) {
            return null;
        }
        Asset asset = IAssetRepository
                .findById(assetId)
                .orElseThrow(EntityNotFoundException::new);

        WatchlistAsset watchlistAsset = new WatchlistAsset.Builder()
                .setAsset(asset)
                .setWatchlist(watchList)
                .build();

        watchList.addWatchlistAsset(watchlistAsset);
        IWatchlistRepository.save(watchList);
        return asset;
    }

    private Boolean assetExistsOnWatchlist(Long assetId, Watchlist watchList) {
        return watchList.getAssets().stream().anyMatch(a -> a.getId().equals(assetId));
    }

    @Override
    public void deleteAsset(User user, Long assetId, Long watchlistId) {
        Watchlist watchList = getWatchlistFromUser(user, watchlistId).orElseThrow(EntityNotFoundException::new);
        watchList.deleteAsset(assetId);
        IWatchlistRepository.save(watchList);
    }

    @Override
    @Transactional
    public Watchlist addWatchlist(User user, String name) {
        Watchlist watchList = new Watchlist(name);
        user.addWatchlist(watchList);
        IWatchlistRepository.save(watchList);
        return watchList;
    }

    @Override
    @Transactional
    public Watchlist editName(User user, Long watchlistId, String name) {
        Optional<Watchlist> watchlistData = getWatchlistFromUser(user, watchlistId);
        if (!watchlistData.isPresent()) {
            return null;
        }
        Watchlist watchList = watchlistData.get();
        watchList.setName(name);
        IWatchlistRepository.save(watchList);
        return watchList;
    }

    @Override
    @Transactional
    public void deleteWatchlist(User user, Long watchlistId) {
        Optional<Watchlist> userWatchlist = getWatchlistFromUser(user, watchlistId);
        if (!userWatchlist.isPresent()) {
            throw new EntityNotFoundException();
        }
        user.deleteWatchlist(watchlistId);
        IWatchlistRepository.deleteById(watchlistId);
        IUserRepository.save(user);
    }

    private Optional<Watchlist> getWatchlistFromUser(User user, Long watchlistId) {
        return user.getWatchlistById(watchlistId);
    }
}
