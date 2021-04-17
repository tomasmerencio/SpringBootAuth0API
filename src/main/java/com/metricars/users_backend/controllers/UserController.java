package com.metricars.users_backend.controllers;

import com.metricars.users_backend.domains.Auth0User;
import com.metricars.users_backend.dtos.UserDTO;
import com.metricars.users_backend.services.IUserService;
import com.metricars.users_backend.utils.Auth0Client;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping(value = "/signup")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<UserDTO> registerUser(@RequestHeader(name = "Authorization") String accessToken) {
        Auth0User auth0User = Auth0Client.INSTANCE.getAuth0User(accessToken);
        UserDTO userDTO = userService.registerNewUserAccount(auth0User);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/details")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<UserDTO> getDetails(@RequestHeader(name = "Authorization") String accessToken) {
        Auth0User auth0User = Auth0Client.INSTANCE.getAuth0User(accessToken);
        UserDTO userDTO = userService.getUserDTO(auth0User.getSub());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
