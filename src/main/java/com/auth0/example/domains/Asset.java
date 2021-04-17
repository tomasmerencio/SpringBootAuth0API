package com.auth0.example.domains;

import javax.persistence.*;

@Entity
@Table(name = "asset")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    @Column(name = "ticker")
    private String ticker;

    @Column(name = "description")
    private String description;

    public Asset() {

    }

    public Long getId() {
        return id;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public String getTicker() {
        return ticker;
    }

    public String getDescription() {
        return description;
    }
}
