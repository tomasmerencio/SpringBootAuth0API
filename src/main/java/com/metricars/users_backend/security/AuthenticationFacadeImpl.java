package com.metricars.users_backend.security;

import com.metricars.users_backend.domains.Auth0User;
import com.metricars.users_backend.utils.Auth0Client;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacadeImpl implements IAuthenticationFacade{
    private final Auth0Client auth0Client;

    @Override
    public String getAuthenticationName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public String getAccessToken() {
        JwtAuthenticationToken accessToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return "Bearer " + accessToken.getToken().getTokenValue();
    }

    @Override
    public Auth0User getAuth0User() {
        return auth0Client.getAuth0User(this.getAccessToken());
    }
}
