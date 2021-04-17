package com.metricars.users_backend.domains;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "username")
    private String username;

    @Column(name = "auth0_id")
    private String auth0Id;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private Timestamp createdAt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private final List<Watchlist> watchlists;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private final Dashboard dashboard;

    public User() {
        this.watchlists = new ArrayList<>();
        this.dashboard = new Dashboard(this);
    }

    public User(Builder builder) {
        this.auth0Id = builder.auth0Id;
        this.name = builder.name;
        this.familyName = builder.familyName;
        this.username = builder.username;
        this.email = builder.email;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
        this.watchlists = new ArrayList<>();
        this.dashboard = new Dashboard(this);
    }

    public void addWatchlist(Watchlist watchList) {
        this.watchlists.add(watchList);
    }

    public Optional<Watchlist> getWatchlistById(Long id) {
        return this.watchlists
                .stream()
                .filter(lS -> lS.getId().equals(id))
                .findFirst();
    }

    public void deleteWatchlist(Long watchlistId) {
        this.watchlists.removeIf(lS -> lS.getId().equals(watchlistId));
    }

    public static class Builder {
        private final String auth0Id;
        private String name;
        private String familyName;
        private String username;
        private String email;

        public Builder(String auth0Id) {
            this.auth0Id = auth0Id;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setFamilyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
