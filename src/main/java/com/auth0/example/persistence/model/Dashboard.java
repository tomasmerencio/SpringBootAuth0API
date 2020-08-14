package com.auth0.example.persistence.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="dashboard")
public class Dashboard{
    @Id
    @GeneratedValue
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    @Column(name="nombre")
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "dashboard")
    private List<DashboardActivo> dashboardActivos;

    @OneToOne
    private Usuario usuario;

    public Dashboard(){

    }

    public Dashboard(Usuario usuario){
        this.usuario = usuario;
        this.nombre = "Dashboard";
        this.dashboardActivos = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void agregarDashboardActivo(DashboardActivo dashboardActivo){
        this.dashboardActivos.add(dashboardActivo);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public List<DashboardActivo> getDashboardActivos() {
        return dashboardActivos;
    }
}
