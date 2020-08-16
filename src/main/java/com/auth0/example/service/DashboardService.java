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
        if(!activoExisteEnDashboard(activoId, dashboard)){
            Activo activo = activoRepository
                    .findById(activoId)
                    .orElseThrow(EntityNotFoundException::new);

            DashboardActivo dashboardActivo = new DashboardActivo.Builder()
                    .setActivo(activo)
                    .setDashboard(dashboard)
                    .build();

            dashboard.agregarDashboardActivo(dashboardActivo);
            dashboardRepository.save(dashboard);
            return activo;
        }
        return null;
    }

    private Boolean activoExisteEnDashboard(Long activoId, Dashboard dashboard){
        return dashboard.getActivos().stream().anyMatch(a -> a.getId().equals(activoId));
    }

    @Override
    @Transactional
    public void deleteAsset(Long activoId, Dashboard dashboard){
        dashboard.eliminarActivo(activoId);
        dashboardRepository.save(dashboard);
    }
}
