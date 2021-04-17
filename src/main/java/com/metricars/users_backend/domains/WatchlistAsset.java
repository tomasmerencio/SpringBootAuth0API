package com.metricars.users_backend.domains;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "watchlist_asset")
@Data
@NoArgsConstructor
public class WatchlistAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "watchlist_id")
    private Watchlist watchlist;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;

    public WatchlistAsset(Builder builder) {
        this.asset = builder.asset;
        this.watchlist = builder.watchList;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public static class Builder {
        private Asset asset;
        private Watchlist watchList;

        public Builder() {
        }

        public Builder setAsset(Asset asset) {
            this.asset = asset;
            return this;
        }

        public Builder setWatchlist(Watchlist watchList) {
            this.watchList = watchList;
            return this;
        }

        public WatchlistAsset build() {
            return new WatchlistAsset(this);
        }
    }
}
