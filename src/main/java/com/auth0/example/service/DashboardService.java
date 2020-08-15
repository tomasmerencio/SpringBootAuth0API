package com.auth0.example.service;

import com.auth0.example.persistence.dao.ActivoRepository;
import com.auth0.example.persistence.dao.DashboardRepository;
import com.auth0.example.persistence.model.Activo;
import com.auth0.example.persistence.model.Dashboard;
import com.auth0.example.persistence.model.DashboardActivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class DashboardService implements IDashboardService{
    @Autowired
    private ActivoRepository activoRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Override
    @Transactional
    public Activo addActivoToDashboard(Long activoId, Dashboard dashboard){
        System.out.println("activoId: " + activoId);
        Activo activo = activoRepository
                .findById(activoId)
                .orElseThrow(() -> new EntityNotFoundException("activoId no encontrado: " + activoId));
        System.out.println("activo: " + activo);
        DashboardActivo dashboardActivo = new DashboardActivo.Builder()
                .setActivo(activo)
                .setDashboard(dashboard)
                .build();

        dashboard.agregarDashboardActivo(dashboardActivo);

        dashboardRepository.save(dashboard);
        return activo;
    }
}
