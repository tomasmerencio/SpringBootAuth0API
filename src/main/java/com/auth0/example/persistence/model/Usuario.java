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

    public Usuario(Builder builder){
        this.auth0Id = builder.auth0Id;
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.nombreUsuario = builder.nombreUsuario;
        this.email = builder.email;
        this.listasDeSeguimiento = new ArrayList<>();
        this.dashboard = new Dashboard(this);
    }

    public static class Builder{
        private String auth0Id;
        private String nombre;
        private String apellido;
        private String nombreUsuario;
        private String email;

        public Builder(String auth0Id){
            this.auth0Id = auth0Id;
        }

        public Builder setNombre(String nombre){
            this.nombre = nombre;
            return this;
        }

        public Builder setApellido(String apellido){
            this.apellido = apellido;
            return this;
        }

        public Builder setNombreUsuario(String nombreUsuario){
            this.nombreUsuario = nombreUsuario;
            return this;
        }

        public Builder setEmail(String email){
            this.email = email;
            return this;
        }

        public Usuario build(){
            return new Usuario(this);
        }
    }

    public Long getId() {
        return id;
    }

    public String getAuth0Id() {
        return auth0Id;
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

    public Dashboard getDashboard() {
        return dashboard;
    }

    public List<ListaSeguimiento> getListasDeSeguimiento() {
        return listasDeSeguimiento;
    }

    public void agregarListaSeguimiento(ListaSeguimiento listaSeguimiento){
        this.listasDeSeguimiento.add(listaSeguimiento);
    }
}
