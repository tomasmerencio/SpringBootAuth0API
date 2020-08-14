package com.auth0.example.persistence.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="lista_seguimiento")
public class ListaSeguimiento{
    @Id
    @GeneratedValue
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    @Column(name="nombre")
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "listaSeguimiento")
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

    public void agregarListaSeguimientoActivo(ListaSeguimientoActivo listaSeguimientoActivo){
        this.listaSeguimientoActivos.add(listaSeguimientoActivo);
    }

    public String getNombre() {
        return nombre;
    }

    public List<ListaSeguimientoActivo> getListaSeguimientoActivos() {
        return listaSeguimientoActivos;
    }
}
