package com.metricars.users_backend.security;

import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class AuthenticationFacadeImpl implements IAuthenticationFacade{
    @Override
    public String getAuthenticationName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @Override
    public String getAccessToken() {
        JwtAuthenticationToken accessToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return "Bearer " + accessToken.getToken().getTokenValue();
    }
}
