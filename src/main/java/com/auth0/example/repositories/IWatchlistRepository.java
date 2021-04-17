package com.auth0.example.repositories;

import com.auth0.example.domains.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IWatchlistRepository extends JpaRepository<Watchlist, Long> {
}
