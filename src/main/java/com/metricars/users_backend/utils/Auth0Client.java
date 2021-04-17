package com.metricars.users_backend.utils;

import com.metricars.users_backend.domains.Auth0User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public enum Auth0Client {
    INSTANCE;

    public Auth0User getAuth0User(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        HttpEntity<String> entity = new HttpEntity<>("headers", headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Auth0User> user = restTemplate.exchange(Constants.USER_INFO_URL, HttpMethod.POST, entity, Auth0User.class);
        return user.getBody();
    }
}
