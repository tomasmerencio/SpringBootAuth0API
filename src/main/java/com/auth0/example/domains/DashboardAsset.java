package com.auth0.example.domains;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "dashboard_asset")
public class DashboardAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;

    public DashboardAsset() {

    }

    public DashboardAsset(Builder builder) {
        this.asset = builder.asset;
        this.dashboard = builder.dashboard;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public Asset getAsset() {
        return asset;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public static class Builder {
        private Asset asset;
        private Dashboard dashboard;

        public Builder() {
        }

        public Builder setAsset(Asset asset) {
            this.asset = asset;
            return this;
        }

        public Builder setDashboard(Dashboard dashboard) {
            this.dashboard = dashboard;
            return this;
        }

        public DashboardAsset build() {
            return new DashboardAsset(this);
        }
    }
}
