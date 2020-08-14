package com.auth0.example.persistence.model;

public class Auth0User {
    private String sub;
    private String given_name;
    private String family_name;
    private String nickname;
    // private String name;
    // private String picture;
    // private String locale;
    // private String updated_at;
    private String email;
    // private boolean email_verified;

    public String getSub() {
        return sub;
    }

    public String getFamily_name() {
        return family_name;
    }

    public String getEmail() {
        return email;
    }

    public String getGiven_name() {
        return given_name;
    }

    public String getNickname() {
        return nickname;
    }
}