package com.metricars.users_backend.domains;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Auth0User {
    private String sub;
    private String given_name;
    private String family_name;
    private String nickname;
    private String email;
}