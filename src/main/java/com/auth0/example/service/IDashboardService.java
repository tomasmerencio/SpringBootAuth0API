package com.auth0.example.service;

import com.auth0.example.persistence.model.Activo;
import com.auth0.example.persistence.model.Dashboard;

public interface IDashboardService {
    Activo addActivoToDashboard(Long activoId, Dashboard dashboard);
}
