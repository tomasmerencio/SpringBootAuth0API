package com.metricars.users_backend.security;

import com.metricars.users_backend.domains.Auth0User;

public interface IAuthenticationFacade {
    String getAuthenticationName();
    String getAccessToken();
    Auth0User getAuth0User();
}
