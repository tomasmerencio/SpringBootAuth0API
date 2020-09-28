package com.auth0.example.web.controller;

import com.auth0.example.persistence.model.Asset;
import com.auth0.example.persistence.model.Auth0User;
import com.auth0.example.persistence.model.Dashboard;
import com.auth0.example.persistence.model.User;
import com.auth0.example.service.IDashboardService;
import com.auth0.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Handles requests to "/api" endpoints.
 *
 * @see com.auth0.example.security.SecurityConfig to see how these endpoints are protected.
 */

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
public class DashboardController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IDashboardService dashboardService;

    @GetMapping("")
    public ResponseEntity<Dashboard> getAllAssets(@RequestHeader(name = "Authorization") String accessToken) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);

        try {
            User user = userService.getUserFromAuth0Id(auth0User.getSub());

            return new ResponseEntity<>(user.getDashboard(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/assets")
    public ResponseEntity<Asset> addAsset(@RequestHeader(name = "Authorization") String accessToken,
                                          @RequestBody Long assetId) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);

        try {
            User user = userService.getUserFromAuth0Id(auth0User.getSub());
            Dashboard dashboard = user.getDashboard();
            Asset asset = dashboardService.addAssetToDashboard(assetId, dashboard);

            if (asset != null) {
                return new ResponseEntity<>(asset, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/assets/{assetId}")
    public ResponseEntity<HttpStatus> deleteAsset(@RequestHeader(name = "Authorization") String accessToken,
                                                  @PathVariable("assetId") Long assetId) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);

        try {
            User user = userService.getUserFromAuth0Id(auth0User.getSub());
            Dashboard dashboard = user.getDashboard();
            dashboardService.deleteAsset(assetId, dashboard);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
