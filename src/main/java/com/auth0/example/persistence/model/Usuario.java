package com.auth0.example.persistence.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @Column(name = "auth0_id")
    private String auth0Id;

    @Column(name = "email")
    private String email;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private List<ListaSeguimiento> listasDeSeguimiento;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Dashboard dashboard;

    public Usuario() {
        this.listasDeSeguimiento = new ArrayList<>();
        this.dashboard = new Dashboard(this);
    }

    public Usuario(String nombre, String apellido, String nombreUsuario, String auth0Id, String email){
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.auth0Id = auth0Id;
        this.email = email;
        this.listasDeSeguimiento = new ArrayList<>();
        this.dashboard = new Dashboard(this);
    }

    public Long getId() {
        return id;
    }

    public String getAuth0Id() {
        return auth0Id;
    }

    public void setAuth0Id(String auth0Id) {
        this.auth0Id = auth0Id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void agregarListaSeguimiento(ListaSeguimiento listaSeguimiento){
        this.listasDeSeguimiento.add(listaSeguimiento);
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
