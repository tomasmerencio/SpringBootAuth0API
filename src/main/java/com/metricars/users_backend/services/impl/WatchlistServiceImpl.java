package com.metricars.users_backend.services.impl;

import com.metricars.users_backend.domains.*;
import com.metricars.users_backend.dtos.AssetDTO;
import com.metricars.users_backend.dtos.WatchlistDTO;
import com.metricars.users_backend.exceptions.AlreadyExistsException;
import com.metricars.users_backend.exceptions.NoAccessException;
import com.metricars.users_backend.exceptions.NotFoundException;
import com.metricars.users_backend.repositories.IAssetRepository;
import com.metricars.users_backend.repositories.IWatchlistRepository;
import com.metricars.users_backend.repositories.IWatchlistAssetRepository;
import com.metricars.users_backend.services.IWatchlistService;
import com.metricars.users_backend.utils.DTOConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchlistServiceImpl implements IWatchlistService {
    private final IAssetRepository iAssetRepository;
    private final IWatchlistRepository iWatchlistRepository;
    private final IWatchlistAssetRepository iWatchlistAssetRepository;
    private final DTOConverterUtil dtoConverterUtil;

    @Override
    public AssetDTO addAssetToWatchlist(User user, Long assetId, Long watchlistId) {
        Watchlist watchlist = getWatchlistById(watchlistId);
        if (assetExistsOnWatchlist(assetId, watchlist)) {
            throw new AlreadyExistsException("Asset");
        }
        Asset asset = iAssetRepository.findById(assetId).orElseThrow(() -> new NotFoundException("Asset"));
        WatchlistAsset watchlistAsset = buildWatchlistAsset(asset, watchlist);
        iWatchlistAssetRepository.save(watchlistAsset);
        return dtoConverterUtil.convertAssetToDTO(asset);
    }

    @Override
    public void deleteAsset(User user, Long assetId, Long watchlistId) {
        Watchlist watchlist = getWatchlistById(watchlistId);
        verifyUserWatchlist(user, watchlist);
        iWatchlistAssetRepository.deleteByWatchlistIdAndAssetId(watchlistId, assetId);
    }

    @Override
    @Transactional
    public WatchlistDTO addWatchlist(User user, String name) {
        Watchlist watchList = new Watchlist(name);
        iWatchlistRepository.save(watchList);
        return dtoConverterUtil.convertWatchlistToDTO(watchList);
    }

    @Override
    @Transactional
    public WatchlistDTO editName(User user, Long watchlistId, String name) {
        Watchlist watchlist = getWatchlistById(watchlistId);
        verifyUserWatchlist(user, watchlist);
        watchlist.setName(name);
        iWatchlistRepository.save(watchlist);
        return dtoConverterUtil.convertWatchlistToDTO(watchlist);
    }

    @Override
    @Transactional
    public void deleteWatchlist(User user, Long watchlistId) {
        Watchlist watchlist = getWatchlistById(watchlistId);
        verifyUserWatchlist(user, watchlist);
        iWatchlistRepository.delete(watchlist);
    }

    @Override
    public List<WatchlistDTO> getWatchlists(User user) {
        return user.getWatchlists().stream().map(dtoConverterUtil::convertWatchlistToDTO).collect(Collectors.toList());
    }

    private WatchlistAsset buildWatchlistAsset(Asset asset, Watchlist watchlist) {
        return new WatchlistAsset.Builder()
                .setAsset(asset)
                .setWatchlist(watchlist)
                .build();
    }

    private Boolean assetExistsOnWatchlist(Long assetId, Watchlist watchList) {
        return watchList.getWatchlistAssets().stream().anyMatch(a -> a.getAsset().getId().equals(assetId));
    }

    private Watchlist getWatchlistById(Long watchlistId) {
        return iWatchlistRepository.findWatchlistById(watchlistId).orElseThrow(() -> new NotFoundException("Watchlist"));
    }

    private void verifyUserWatchlist(User user, Watchlist watchList) {
        if (user.getWatchlists().stream().noneMatch(w -> w.getId().equals(watchList.getId()))) {
            throw new NoAccessException();
        }
    }
}
