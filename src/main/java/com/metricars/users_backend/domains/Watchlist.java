package com.metricars.users_backend.domains;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "watchlist")
@Data
@NoArgsConstructor
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "watchlist",
            orphanRemoval = true
    )
    private List<WatchlistAsset> watchlistAssets;

    public Watchlist(String name) {
        this.name = name;
        this.watchlistAssets = new ArrayList<>();
    }

    public void addWatchlistAsset(WatchlistAsset watchlistAsset) {
        this.watchlistAssets.add(watchlistAsset);
    }

    public void deleteAsset(Long assetId) {
        this.watchlistAssets.removeIf(wA -> wA.getAsset().getId().equals(assetId));
    }
}
