package com.auth0.example.web.controller;

import com.auth0.example.persistence.model.Auth0User;
import com.auth0.example.persistence.model.User;
import com.auth0.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Handles requests to "/api" endpoints.
 *
 * @see com.auth0.example.security.SecurityConfig to see how these endpoints are protected.
 */

@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping(path = "api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping(value = "/signup")
    public ResponseEntity<User> registerUser(@RequestHeader(name = "Authorization") String accessToken) {

        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        System.out.println(accessToken);
        try {
            User registered = userService.registerNewUserAccount(auth0User);
            if (registered != null) {
                return new ResponseEntity<>(registered, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/details")
    public ResponseEntity<User> getDetails(@RequestHeader(name = "Authorization") String accessToken) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);
        try {
            User user = userService.getUserFromAuth0Id(auth0User.getSub());
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
