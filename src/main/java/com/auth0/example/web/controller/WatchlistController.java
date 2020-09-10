package com.auth0.example.web.controller;

import com.auth0.example.persistence.model.Asset;
import com.auth0.example.persistence.model.Auth0User;
import com.auth0.example.persistence.model.User;
import com.auth0.example.persistence.model.Watchlist;
import com.auth0.example.service.IUserService;
import com.auth0.example.service.IWatchlistService;
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
@CrossOrigin("http://localhost:8080")
@RequestMapping(path = "api/watchlist", produces = MediaType.APPLICATION_JSON_VALUE)
public class WatchlistController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IWatchlistService watchlistService;

    @GetMapping("")
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
    public ResponseEntity<Watchlist> createWatchlist(@RequestHeader(name = "Authorization") String accessToken,
                                                     @RequestBody String watchlistName) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        try {
            User user = userService.getUserFromAuth0Id(auth0User.getSub());
            watchlistService.addWatchlist(user, watchlistName);
            return new ResponseEntity<>(null, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Watchlist> updateWatchlistName(@RequestHeader(name = "Authorization") String accessToken,
                                                         @PathVariable("id") Long wachlistId,
                                                         @RequestBody String watchlistName) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        User user = userService.getUserFromAuth0Id(auth0User.getSub());

        Watchlist watchList = watchlistService.editName(user, wachlistId, watchlistName);

        if (watchList != null) {
            return new ResponseEntity<>(watchList, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("")
    public ResponseEntity<HttpStatus> deleteWatchlist(@RequestHeader(name = "Authorization") String accessToken,
                                                      @RequestBody Long wachlistId) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        User user = userService.getUserFromAuth0Id(auth0User.getSub());

        try {
            watchlistService.deleteWatchlist(user, wachlistId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Asset> addAssetToWatchlist(@RequestHeader(name = "Authorization") String accessToken,
                                                     @PathVariable("id") Long wachlistId,
                                                     @RequestBody Long assetId) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        User user = userService.getUserFromAuth0Id(auth0User.getSub());

        try {
            Asset asset = watchlistService.addAssetToWatchlist(user, assetId, wachlistId);

            if (asset != null) {
                return new ResponseEntity<>(asset, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Asset> deleteAssetFromWatchlist(@RequestHeader(name = "Authorization") String accessToken,
                                                          @PathVariable("id") Long wachlistId,
                                                          @RequestBody Long assetId) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        User user = userService.getUserFromAuth0Id(auth0User.getSub());

        try {
            watchlistService.deleteAsset(user, assetId, wachlistId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
