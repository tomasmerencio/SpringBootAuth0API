package com.metricars.users_backend.repositories;

import com.metricars.users_backend.domains.WatchlistAsset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWatchlistAssetRepository extends JpaRepository<WatchlistAsset, Long> {
    void deleteByWatchlistIdAndAssetId(Long watchlistId, Long assetId);
}
