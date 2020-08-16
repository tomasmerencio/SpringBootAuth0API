package com.auth0.example.persistence.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name="dashboard")
public class Dashboard{
    @Id
    @GeneratedValue
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    @Column(name="nombre")
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "dashboard", orphanRemoval=true)
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Activo> getActivos(){
        return this.dashboardActivos.stream().map(DashboardActivo::getActivo).collect(Collectors.toList());
    }

    public void agregarDashboardActivo(DashboardActivo dashboardActivo){
        this.dashboardActivos.add(dashboardActivo);
    }

    public void eliminarActivo(Long activoId){
        this.dashboardActivos.removeIf(dA -> dA.getActivo().getId().equals(activoId));
    }
}
