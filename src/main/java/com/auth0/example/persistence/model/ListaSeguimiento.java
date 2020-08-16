package com.auth0.example.persistence.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="lista_seguimiento")
public class ListaSeguimiento{
    @Id
    @GeneratedValue
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    @Column(name="nombre")
    private String nombre;

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL,
        mappedBy = "listaSeguimiento",
        orphanRemoval=true
    )
    private List<ListaSeguimientoActivo> listaSeguimientoActivos;

    public ListaSeguimiento(){

    }

    public ListaSeguimiento(String nombre){
        this.nombre = nombre;
        this.listaSeguimientoActivos = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Activo> getActivos(){
        return this.listaSeguimientoActivos.stream().map(ListaSeguimientoActivo::getActivo).collect(Collectors.toList());
    }

    public void agregarListaSeguimientoActivo(ListaSeguimientoActivo listaSeguimientoActivo){
        this.listaSeguimientoActivos.add(listaSeguimientoActivo);
    }

    public void eliminarActivo(Long activoId){
        this.listaSeguimientoActivos.removeIf(dA -> dA.getActivo().getId().equals(activoId));
    }
}
