package com.metricars.users_backend.controllers;

import com.metricars.users_backend.domains.Dashboard;
import com.metricars.users_backend.domains.User;
import com.metricars.users_backend.dtos.AssetDTO;
import com.metricars.users_backend.dtos.DashboardDTO;
import com.metricars.users_backend.security.IAuthenticationFacade;
import com.metricars.users_backend.services.IDashboardService;
import com.metricars.users_backend.services.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles requests to "/api" endpoints.
 *
 * @see com.metricars.users_backend.security.SecurityConfig to see how these endpoints are protected.
 */

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DashboardController {
    private final IUserService userService;
    private final IDashboardService dashboardService;
    private final IAuthenticationFacade authenticationFacade;

    @GetMapping("")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<DashboardDTO> getAllAssets() {
        String auth0Sub = authenticationFacade.getAuthenticationName();
        User user = userService.getUserFromAuth0Id(auth0Sub);
        DashboardDTO dashboardDTO = dashboardService.getDashboardDTO(user.getDashboard());
        return new ResponseEntity<>(dashboardDTO, HttpStatus.OK);
    }

    @PostMapping("/assets")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<AssetDTO> addAsset(@RequestBody Long assetId) {
        String auth0Sub = authenticationFacade.getAuthenticationName();
        User user = userService.getUserFromAuth0Id(auth0Sub);
        Dashboard dashboard = user.getDashboard();
        AssetDTO assetDTO = dashboardService.addAssetToDashboard(assetId, dashboard);
        return new ResponseEntity<>(assetDTO, HttpStatus.OK);
    }

    @DeleteMapping("/assets/{assetId}")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<?> deleteAsset(@PathVariable("assetId") Long assetId) {
        String auth0Sub = authenticationFacade.getAuthenticationName();
        User user = userService.getUserFromAuth0Id(auth0Sub);
        Dashboard dashboard = user.getDashboard();
        dashboardService.deleteAsset(assetId, dashboard);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
