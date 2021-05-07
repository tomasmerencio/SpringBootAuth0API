package com.metricars.users_backend.services;

import com.metricars.users_backend.domains.Auth0User;
import com.metricars.users_backend.domains.User;
import com.metricars.users_backend.dtos.UserDTO;

public interface IUserService {
    UserDTO registerNewUserAccount(Auth0User auth0User);
    User getUserFromAuth0Id(String auth0Id);
    UserDTO getUserDTO(String auth0Id);
}
