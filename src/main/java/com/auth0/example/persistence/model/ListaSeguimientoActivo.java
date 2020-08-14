package com.auth0.example.persistence.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "lista_seguimiento_activo")
public class ListaSeguimientoActivo{
    @Id
    @GeneratedValue
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "activo_id")
    private Activo activo;

    @ManyToOne
    @JoinColumn(name = "lista_seguimiento_id")
    private ListaSeguimiento listaSeguimiento;

    @Column(name = "fecha_hora", columnDefinition = "TIMESTAMP")
    private Timestamp fechaHora;

    public ListaSeguimientoActivo(){

    }

    public ListaSeguimientoActivo(Activo activo, ListaSeguimiento listaSeguimiento){
        this.activo = activo;
        this.listaSeguimiento = listaSeguimiento;
        this.fechaHora = Timestamp.valueOf(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public Activo getActivo() {
        return activo;
    }

    public ListaSeguimiento getListaSeguimiento() {
        return listaSeguimiento;
    }
}
