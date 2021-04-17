package com.auth0.example.controller;

import com.auth0.example.domains.Asset;
import com.auth0.example.domains.Auth0User;
import com.auth0.example.domains.User;
import com.auth0.example.domains.Watchlist;
import com.auth0.example.service.IUserService;
import com.auth0.example.service.IWatchlistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles requests to "/api" endpoints.
 *
 * @see com.auth0.example.security.SecurityConfig to see how these endpoints are protected.
 */

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/watchlist", produces = MediaType.APPLICATION_JSON_VALUE)
public class WatchlistController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IWatchlistService watchlistService;

    @GetMapping("")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<Watchlist>> getAllLists(@RequestHeader(name = "Authorization") String accessToken) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);

        try {
            User user = userService.getUserFromAuth0Id(auth0User.getSub());
            return new ResponseEntity<>(user.getWatchlists(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Watchlist> createWatchlist(@RequestHeader(name = "Authorization") String accessToken,
                                                     @RequestBody String watchlistName) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        try {
            User user = userService.getUserFromAuth0Id(auth0User.getSub());
            Watchlist watchlist = watchlistService.addWatchlist(user, watchlistName);
            return new ResponseEntity<>(watchlist, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{watchlistId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Watchlist> updateWatchlistName(@RequestHeader(name = "Authorization") String accessToken,
                                                         @PathVariable("watchlistId") Long watchlistId,
                                                         @RequestBody String watchlistName) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        User user = userService.getUserFromAuth0Id(auth0User.getSub());

        Watchlist watchList = watchlistService.editName(user, watchlistId, watchlistName);

        if (watchList != null) {
            return new ResponseEntity<>(watchList, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{watchlistId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<HttpStatus> deleteWatchlist(@RequestHeader(name = "Authorization") String accessToken,
                                                      @PathVariable("watchlistId") Long watchlistId) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        User user = userService.getUserFromAuth0Id(auth0User.getSub());

        try {
            watchlistService.deleteWatchlist(user, watchlistId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{watchlistId}/assets")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Asset> addAssetToWatchlist(@RequestHeader(name = "Authorization") String accessToken,
                                                     @PathVariable("watchlistId") Long watchlistId,
                                                     @RequestBody Long assetId) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        User user = userService.getUserFromAuth0Id(auth0User.getSub());

        try {
            Asset asset = watchlistService.addAssetToWatchlist(user, assetId, watchlistId);

            if (asset != null) {
                return new ResponseEntity<>(asset, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{watchlistId}/assets/{assetId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Asset> deleteAssetFromWatchlist(@RequestHeader(name = "Authorization") String accessToken,
                                                          @PathVariable("watchlistId") Long watchlistId,
                                                          @PathVariable("assetId") Long assetId) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        User user = userService.getUserFromAuth0Id(auth0User.getSub());

        try {
            watchlistService.deleteAsset(user, assetId, watchlistId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
