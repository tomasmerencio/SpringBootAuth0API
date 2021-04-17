package com.metricars.users_backend.controllers;

import com.metricars.users_backend.domains.Auth0User;
import com.metricars.users_backend.domains.User;
import com.metricars.users_backend.dtos.AssetDTO;
import com.metricars.users_backend.dtos.WatchlistDTO;
import com.metricars.users_backend.services.IUserService;
import com.metricars.users_backend.services.IWatchlistService;
import com.metricars.users_backend.utils.Auth0Client;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles requests to "/api" endpoints.
 *
 * @see com.metricars.users_backend.security.SecurityConfig to see how these endpoints are protected.
 */

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/watchlist", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class WatchlistController {
    private final IUserService userService;
    private final IWatchlistService watchlistService;

    @GetMapping("")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<WatchlistDTO>> getAllLists(@RequestHeader(name = "Authorization") String accessToken) {
        Auth0User auth0User = Auth0Client.INSTANCE.getAuth0User(accessToken);
        User user = userService.getUserFromAuth0Id(auth0User.getSub());
        List<WatchlistDTO> watchlists = watchlistService.getWatchlists(user);
        return new ResponseEntity<>(watchlists, HttpStatus.OK);
    }

    @PostMapping("")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<WatchlistDTO> createWatchlist(@RequestHeader(name = "Authorization") String accessToken,
                                                     @RequestBody String watchlistName) {
        Auth0User auth0User = Auth0Client.INSTANCE.getAuth0User(accessToken);
        User user = userService.getUserFromAuth0Id(auth0User.getSub());
        WatchlistDTO watchlistDTO = watchlistService.addWatchlist(user, watchlistName);
        return new ResponseEntity<>(watchlistDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{watchlistId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<WatchlistDTO> updateWatchlistName(@RequestHeader(name = "Authorization") String accessToken,
                                                         @PathVariable("watchlistId") Long watchlistId,
                                                         @RequestBody String watchlistName) {
        Auth0User auth0User = Auth0Client.INSTANCE.getAuth0User(accessToken);
        User user = userService.getUserFromAuth0Id(auth0User.getSub());
        WatchlistDTO watchlistDTO = watchlistService.editName(user, watchlistId, watchlistName);
        return new ResponseEntity<>(watchlistDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{watchlistId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<?> deleteWatchlist(@RequestHeader(name = "Authorization") String accessToken,
                                                      @PathVariable("watchlistId") Long watchlistId) {
        Auth0User auth0User = Auth0Client.INSTANCE.getAuth0User(accessToken);
        User user = userService.getUserFromAuth0Id(auth0User.getSub());
        watchlistService.deleteWatchlist(user, watchlistId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{watchlistId}/assets")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<AssetDTO> addAssetToWatchlist(@RequestHeader(name = "Authorization") String accessToken,
                                                        @PathVariable("watchlistId") Long watchlistId,
                                                        @RequestBody Long assetId) {
        Auth0User auth0User = Auth0Client.INSTANCE.getAuth0User(accessToken);
        User user = userService.getUserFromAuth0Id(auth0User.getSub());
        AssetDTO assetDTO = watchlistService.addAssetToWatchlist(user, assetId, watchlistId);
        return new ResponseEntity<>(assetDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{watchlistId}/assets/{assetId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<?> deleteAssetFromWatchlist(@RequestHeader(name = "Authorization") String accessToken,
                                                          @PathVariable("watchlistId") Long watchlistId,
                                                          @PathVariable("assetId") Long assetId) {
        Auth0User auth0User = Auth0Client.INSTANCE.getAuth0User(accessToken);
        User user = userService.getUserFromAuth0Id(auth0User.getSub());
        watchlistService.deleteAsset(user, assetId, watchlistId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
