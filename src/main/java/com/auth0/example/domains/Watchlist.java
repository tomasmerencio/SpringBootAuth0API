package com.auth0.example.domains;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "watchlist")
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

    public Watchlist() {

    }

    public Watchlist(String name) {
        this.name = name;
        this.watchlistAssets = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Asset> getAssets() {
        return this.watchlistAssets.stream().map(WatchlistAsset::getAsset).collect(Collectors.toList());
    }

    public void addWatchlistAsset(WatchlistAsset watchlistAsset) {
        this.watchlistAssets.add(watchlistAsset);
    }

    public void deleteAsset(Long assetId) {
        this.watchlistAssets.removeIf(wA -> wA.getAsset().getId().equals(assetId));
    }
}
