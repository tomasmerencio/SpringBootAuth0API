package com.metricars.users_backend.controllers;

import com.metricars.users_backend.domains.User;
import com.metricars.users_backend.dtos.AssetDTO;
import com.metricars.users_backend.dtos.WatchlistDTO;
import com.metricars.users_backend.security.IAuthenticationFacade;
import com.metricars.users_backend.services.IUserService;
import com.metricars.users_backend.services.IWatchlistService;
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
    private final IAuthenticationFacade authenticationFacade;

    @GetMapping("")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<WatchlistDTO>> getAllLists() {
        String auth0Sub = authenticationFacade.getAuthenticationName();
        User user = userService.getUserFromAuth0Id(auth0Sub);
        List<WatchlistDTO> watchlists = watchlistService.getWatchlists(user);
        return new ResponseEntity<>(watchlists, HttpStatus.OK);
    }

    @PostMapping("")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<WatchlistDTO> createWatchlist(@RequestBody String watchlistName) {
        String auth0Sub = authenticationFacade.getAuthenticationName();
        User user = userService.getUserFromAuth0Id(auth0Sub);
        WatchlistDTO watchlistDTO = watchlistService.addWatchlist(user, watchlistName);
        return new ResponseEntity<>(watchlistDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{watchlistId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<WatchlistDTO> updateWatchlistName(@PathVariable("watchlistId") Long watchlistId,
                                                            @RequestBody String watchlistName) {
        String auth0Sub = authenticationFacade.getAuthenticationName();
        User user = userService.getUserFromAuth0Id(auth0Sub);
        WatchlistDTO watchlistDTO = watchlistService.editName(user, watchlistId, watchlistName);
        return new ResponseEntity<>(watchlistDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{watchlistId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<?> deleteWatchlist(@PathVariable("watchlistId") Long watchlistId) {
        String auth0Sub = authenticationFacade.getAuthenticationName();
        User user = userService.getUserFromAuth0Id(auth0Sub);
        watchlistService.deleteWatchlist(user, watchlistId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{watchlistId}/assets")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<AssetDTO> addAssetToWatchlist(@PathVariable("watchlistId") Long watchlistId,
                                                        @RequestBody Long assetId) {
        String auth0Sub = authenticationFacade.getAuthenticationName();
        User user = userService.getUserFromAuth0Id(auth0Sub);
        AssetDTO assetDTO = watchlistService.addAssetToWatchlist(user, assetId, watchlistId);
        return new ResponseEntity<>(assetDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{watchlistId}/assets/{assetId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<?> deleteAssetFromWatchlist(@PathVariable("watchlistId") Long watchlistId,
                                                      @PathVariable("assetId") Long assetId) {
        String auth0Sub = authenticationFacade.getAuthenticationName();
        User user = userService.getUserFromAuth0Id(auth0Sub);
        watchlistService.deleteAsset(user, assetId, watchlistId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
