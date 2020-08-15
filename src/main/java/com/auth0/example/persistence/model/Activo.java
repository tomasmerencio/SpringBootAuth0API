package com.auth0.example.persistence.model;

import javax.persistence.*;

@Entity
@Table(name="activo")
public class Activo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoActivo tipoActivo;

    @Column(name = "ticker")
    private String ticker;

    @Column(name = "descripcion")
    private String descripcion;

    public Activo() {

    }

    public Activo(TipoActivo tipoActivo, String ticker, String descripcion){
        this.tipoActivo = tipoActivo;
        this.ticker = ticker;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public TipoActivo getTipoActivo() {
        return tipoActivo;
    }

    public String getTicker() {
        return ticker;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
