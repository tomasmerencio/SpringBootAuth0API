package com.metricars.users_backend.domains;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dashboard")
@Data
@NoArgsConstructor
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

    public Dashboard(User user) {
        this.user = user;
        this.dashboardAssets = new ArrayList<>();
    }

    public void addDashboardAsset(DashboardAsset dashboardAsset) {
        this.dashboardAssets.add(dashboardAsset);
    }

    public void deleteAsset(Long assetId) {
        this.dashboardAssets.removeIf(dA -> dA.getAsset().getId().equals(assetId));
    }
}
