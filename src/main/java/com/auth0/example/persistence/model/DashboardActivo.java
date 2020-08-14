package com.auth0.example.persistence.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "dashboard_activo")
public class DashboardActivo{
    @Id
    @GeneratedValue
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "activo_id")
    private Activo activo;

    @ManyToOne
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;

    @Column(name = "fecha_hora", columnDefinition = "TIMESTAMP")
    private Timestamp fechaHora;

    public DashboardActivo(){

    }

    public DashboardActivo(Activo activo, Dashboard dashboard){
        this.activo = activo;
        this.dashboard = dashboard;
        this.fechaHora = Timestamp.valueOf(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public Activo getActivo() {
        return activo;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }
}
