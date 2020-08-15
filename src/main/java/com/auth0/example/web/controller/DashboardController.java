package com.auth0.example.web.controller;

import com.auth0.example.persistence.model.*;
import com.auth0.example.service.IDashboardService;
import com.auth0.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles requests to "/api" endpoints.
 * @see com.auth0.example.security.SecurityConfig to see how these endpoints are protected.
 */

@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping(path = "api/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
public class DashboardController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IDashboardService dashboardService;

    @GetMapping("/assets")
    public ResponseEntity<List<Activo>> getAllAssets(@RequestHeader(name="Authorization") String accessToken){
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);

        try{
            Usuario usuario = userService.getUserFromAuth0Id(auth0User.getSub());
            List<DashboardActivo> dashboardActivos = usuario.getDashboard().getDashboardActivos();
            List<Activo> activos = dashboardActivos.stream().map(DashboardActivo::getActivo).collect(Collectors.toList());

            if(activos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(activos, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/assets")
    public ResponseEntity<Activo> addAsset(@RequestHeader(name="Authorization") String accessToken, @RequestBody String activoId) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);

        try{
            Usuario usuario = userService.getUserFromAuth0Id(auth0User.getSub());
            Dashboard dashboard = usuario.getDashboard();
            Long activoIdL = Long.parseLong(activoId);
            Activo activo = dashboardService.addActivoToDashboard(activoIdL, dashboard);
            return new ResponseEntity<>(activo, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
