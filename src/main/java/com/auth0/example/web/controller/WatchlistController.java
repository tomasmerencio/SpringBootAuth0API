package com.auth0.example.web.controller;

import com.auth0.example.persistence.dao.ListaSeguimientoRepository;
import com.auth0.example.persistence.model.*;
import com.auth0.example.service.IUserService;
import com.auth0.example.service.IWatchlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Handles requests to "/api" endpoints.
 * @see com.auth0.example.security.SecurityConfig to see how these endpoints are protected.
 */

@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping(path = "api/watchlists", produces = MediaType.APPLICATION_JSON_VALUE)
public class WatchlistController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IWatchlistService watchlistService;

    @GetMapping("")
    public ResponseEntity<List<ListaSeguimiento>> getAllLists(@RequestHeader(name="Authorization") String accessToken){
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);

        try{
            Usuario usuario = userService.getUserFromAuth0Id(auth0User.getSub());

            return new ResponseEntity<>(usuario.getListasDeSeguimiento(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<ListaSeguimiento> createWatchlist(@RequestHeader(name="Authorization") String accessToken,
                                                         @RequestBody String watchlistName){
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        try{
            Usuario usuario = userService.getUserFromAuth0Id(auth0User.getSub());
            watchlistService.agregarListaSeguimiento(usuario, watchlistName);
            return new ResponseEntity<>(null, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaSeguimiento> updateWatchlistName(@RequestHeader(name="Authorization") String accessToken,
                                                            @PathVariable("id") Long wachlistId,
                                                            @RequestBody String watchlistName){
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        Usuario usuario = userService.getUserFromAuth0Id(auth0User.getSub());

        ListaSeguimiento listaSeguimiento = watchlistService.editarNombre(usuario, wachlistId, watchlistName);

        if(listaSeguimiento != null){
            return new ResponseEntity<>(listaSeguimiento, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<HttpStatus> deleteWatchlist(@RequestHeader(name="Authorization") String accessToken,
                                                      @RequestBody Long wachlistId){
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        Usuario usuario = userService.getUserFromAuth0Id(auth0User.getSub());
        try {
            watchlistService.eliminarListaSeguimiento(usuario, wachlistId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Activo> addAssetToWatchlist(@RequestHeader(name="Authorization") String accessToken,
                                                                @PathVariable("id") Long wachlistId,
                                                                @RequestBody Long assetId){
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        Usuario usuario = userService.getUserFromAuth0Id(auth0User.getSub());

        try{
            Activo activo = watchlistService.addActivoToLista(usuario, assetId, wachlistId);

            if(activo != null){
                return new ResponseEntity<>(activo, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Activo> deleteAssetFromWatchlist(@RequestHeader(name="Authorization") String accessToken,
                                                      @PathVariable("id") Long wachlistId,
                                                      @RequestBody Long assetId){
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        Usuario usuario = userService.getUserFromAuth0Id(auth0User.getSub());

        try{
            watchlistService.deleteAsset(usuario, assetId, wachlistId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
