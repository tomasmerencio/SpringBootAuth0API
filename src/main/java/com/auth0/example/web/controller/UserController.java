package com.auth0.example.web.controller;

import com.auth0.example.persistence.model.Auth0User;
import com.auth0.example.persistence.model.User;
import com.auth0.example.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles requests to "/api" endpoints.
 *
 * @see com.auth0.example.security.SecurityConfig to see how these endpoints are protected.
 */

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping(value = "/signup")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<User> registerUser(@RequestHeader(name = "Authorization") String accessToken) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);

        try {
            Boolean userExists = userService.userExists(auth0User.getSub());
            User user = null;
            if (!userExists) {
                user = userService.registerNewUserAccount(auth0User);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            user = userService.getUserFromAuth0Id(auth0User.getSub());
            return new ResponseEntity<>(user, HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/details")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<User> getDetails(@RequestHeader(name = "Authorization") String accessToken) {
        Auth0User auth0User = userService.getAuth0UserFromAccessToken(accessToken);

        try {
            User user = userService.getUserFromAuth0Id(auth0User.getSub());
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
