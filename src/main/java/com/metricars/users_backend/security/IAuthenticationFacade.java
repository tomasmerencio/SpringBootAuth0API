package com.metricars.users_backend.security;

public interface IAuthenticationFacade {
    String getAuthenticationName();
    String getAccessToken();
}
