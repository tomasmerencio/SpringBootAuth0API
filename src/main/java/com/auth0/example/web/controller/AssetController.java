package com.auth0.example.web.controller;

import com.auth0.example.persistence.dao.AssetRepository;
import com.auth0.example.persistence.model.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping(path = "api/asset", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssetController {
    @Autowired
    AssetRepository assetRepository;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Asset>> getAllAssets() {
        try {
            return new ResponseEntity<>(assetRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
