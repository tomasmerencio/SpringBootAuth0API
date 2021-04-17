package com.auth0.example.controller;

import com.auth0.example.repositories.IAssetRepository;
import com.auth0.example.domains.Asset;
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
@CrossOrigin("*")
@RequestMapping(path = "api/asset", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssetController {
    @Autowired
    IAssetRepository IAssetRepository;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Asset>> getAllAssets() {
        try {
            return new ResponseEntity<>(IAssetRepository.findAll(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
