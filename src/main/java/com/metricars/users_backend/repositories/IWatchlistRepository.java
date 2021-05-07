package com.metricars.users_backend.repositories;

import com.metricars.users_backend.domains.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IWatchlistRepository extends JpaRepository<Watchlist, Long> {
    Optional<Watchlist> findWatchlistById(Long id);
}
