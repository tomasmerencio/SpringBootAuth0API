package com.auth0.example.persistence.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

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

    @Column(name = "fecha_hora_creacion", columnDefinition = "TIMESTAMP")
    private Timestamp fechaHoraCreacion;

    public DashboardActivo(){

    }

    public DashboardActivo(Builder builder){
        this.activo = builder.activo;
        this.dashboard = builder.dashboard;
        this.fechaHoraCreacion = Timestamp.valueOf(LocalDateTime.now());
    }

    public static class Builder{
        private Activo activo;
        private Dashboard dashboard;

        public Builder(){
        }

        public Builder setActivo(Activo activo){
            this.activo = activo;
            return this;
        }

        public Builder setDashboard(Dashboard dashboard){
            this.dashboard = dashboard;
            return this;
        }

        public DashboardActivo build(){
            return new DashboardActivo(this);
        }
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

    public Timestamp getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }
}
