package com.auth0.example.web.controller;

import com.auth0.example.persistence.model.Auth0User;
import com.auth0.example.persistence.model.Usuario;
import com.auth0.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Handles requests to "/api" endpoints.
 * @see com.auth0.example.security.SecurityConfig to see how these endpoints are protected.
 */

@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping(path = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping(value = "/signup")
    public ResponseEntity<Usuario> registrarUsuario(@RequestHeader(name="Authorization") String accessToken) {

        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);

        try {
            Usuario registrado = userService.registerNewUserAccount(auth0User);
            if (registrado != null) {
                return new ResponseEntity<>(registrado, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
