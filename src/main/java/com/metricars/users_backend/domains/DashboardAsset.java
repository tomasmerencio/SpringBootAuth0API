package com.metricars.users_backend.domains;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "dashboard_asset")
@Data
@NoArgsConstructor
public class DashboardAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;

    public DashboardAsset(Builder builder) {
        this.asset = builder.asset;
        this.dashboard = builder.dashboard;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
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
