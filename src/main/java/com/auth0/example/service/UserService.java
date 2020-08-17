package com.auth0.example.service;

import com.auth0.example.persistence.dao.UserRepository;
import com.auth0.example.persistence.model.Auth0User;
import com.auth0.example.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.transaction.Transactional;

@Service
public class UserService implements IUserService{
    @Autowired
    private UserRepository userRepository;

    @Value("${spring.security.oauth2.resourceserver.jwt.user-info-uri}")
    private String userInfoUri;

    @Override
    public Auth0User getAuth0UserFromAccessToken(String accessToken){
        // Create and set the "Authorization" header before sending HTTP request
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        HttpEntity<String> entity = new HttpEntity<>("headers", headers);

        // Use the "RestTemplate" API provided by Spring to make the HTTP request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Auth0User> user = restTemplate.exchange(userInfoUri, HttpMethod.POST, entity, Auth0User.class);
        return user.getBody();
    }

    @Override
    @Transactional
    public User registerNewUserAccount(final Auth0User auth0User) {
        if (auth0IdExists(auth0User.getSub())) {
            return null;
        }
        final User user = new User.Builder(auth0User.getSub())
                .setName(auth0User.getGiven_name())
                .setFamilyName(auth0User.getFamily_name())
                .setUsername(auth0User.getNickname())
                .setEmail(auth0User.getEmail())
                .build();

        return userRepository.save(user);
    }

    private Boolean auth0IdExists(final String auth0Id) {
        return userRepository.findByAuth0Id(auth0Id) != null;
    }

    public User getUserFromAuth0Id(final String auth0Id){
        return userRepository.findByAuth0Id(auth0Id);
    }
}
