package com.auth0.example.persistence.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @Column(name = "fecha_hora_creacion", columnDefinition = "TIMESTAMP")
    private Timestamp fechaHoraCreacion;

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
        this.fechaHoraCreacion = Timestamp.valueOf(LocalDateTime.now());
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

    public String getEmail() {
        return email;
    }

    public Timestamp getFechaHoraCreacion() {
        return fechaHoraCreacion;
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

    public Optional<ListaSeguimiento> getListaSeguimientoPorId(Long id){
        return this.listasDeSeguimiento
                .stream()
                .filter(lS -> lS.getId().equals(id))
                .findFirst();
    }

    public void eliminarListaSeguimiento(Long watchlistId){
        this.listasDeSeguimiento.removeIf(lS -> lS.getId().equals(watchlistId));
    }
}
