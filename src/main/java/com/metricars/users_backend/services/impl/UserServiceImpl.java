package com.metricars.users_backend.services.impl;

import com.metricars.users_backend.domains.*;
import com.metricars.users_backend.dtos.UserDTO;
import com.metricars.users_backend.exceptions.AlreadyExistsException;
import com.metricars.users_backend.exceptions.NotFoundException;
import com.metricars.users_backend.repositories.IUserRepository;
import com.metricars.users_backend.services.IUserService;
import com.metricars.users_backend.utils.DTOConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IUserRepository IUserRepository;
    private final DTOConverterUtil dtoConverterUtil;

    @Override
    @Transactional
    public UserDTO registerNewUserAccount(final Auth0User auth0User) {
        if (userExists(auth0User.getSub())) {
            throw new AlreadyExistsException("User");
        }
        User user = buildUser(auth0User);
        IUserRepository.save(user);
        return dtoConverterUtil.convertUserToDTO(user);
    }

    private User buildUser(Auth0User auth0User) {
        return new User.Builder(auth0User.getSub())
                .setName(auth0User.getGiven_name())
                .setFamilyName(auth0User.getFamily_name())
                .setUsername(auth0User.getNickname())
                .setEmail(auth0User.getEmail())
                .build();
    }

    private Boolean userExists(final String auth0Id) {
        return IUserRepository.findByAuth0Id(auth0Id).isPresent();
    }

    public User getUserFromAuth0Id(final String auth0Id) {
        return IUserRepository.findByAuth0Id(auth0Id).orElseThrow(() -> new NotFoundException("User"));
    }

    @Override
    public UserDTO getUserDTO(final String auth0Id) {
        return dtoConverterUtil.convertUserToDTO(getUserFromAuth0Id(auth0Id));
    }
}
