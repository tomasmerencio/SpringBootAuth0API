package com.auth0.example.web.controller;

import com.auth0.example.persistence.dao.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

/**
 * Handles requests to "/api" endpoints.
 * @see com.auth0.example.security.SecurityConfig to see how these endpoints are protected.
 */

@RestController
@CrossOrigin("http://localhost:8080")
@RequestMapping(path = "api", produces = MediaType.APPLICATION_JSON_VALUE)
public class DashboardController {
    @Autowired
    private DashboardRepository dashboardRepository;
}
