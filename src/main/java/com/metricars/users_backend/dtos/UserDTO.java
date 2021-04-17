package com.metricars.users_backend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private String email;
    private String name;
    private String familyName;
    private String username;
    private String auth0Id;
    private DashboardDTO dashboard;
    private List<WatchlistDTO> watchlists;
}
