package com.auth0.example.persistence.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "dashboard")
public class Dashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "dashboard",
            orphanRemoval = true
    )
    private List<DashboardAsset> dashboardAssets;

    @OneToOne
    private User user;

    public Dashboard() {

    }

    public Dashboard(User user) {
        this.user = user;
        this.dashboardAssets = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public List<Asset> getAssets() {
        return this.dashboardAssets.stream().map(DashboardAsset::getAsset).collect(Collectors.toList());
    }

    public void addDashboardAsset(DashboardAsset dashboardAsset) {
        this.dashboardAssets.add(dashboardAsset);
    }

    public void deleteAsset(Long assetId) {
        this.dashboardAssets.removeIf(dA -> dA.getAsset().getId().equals(assetId));
    }
}
