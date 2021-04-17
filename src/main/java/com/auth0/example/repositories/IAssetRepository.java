package com.auth0.example.repositories;

import com.auth0.example.domains.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAssetRepository extends JpaRepository<Asset, Long> {
}
