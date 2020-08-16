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

    @Column(name = "fecha_hora_creacion", columnDefinition = "TIMESTAMP")
    private Timestamp fechaHoraCreacion;

    public ListaSeguimientoActivo(){

    }

    public ListaSeguimientoActivo(Builder builder){
        this.activo = builder.activo;
        this.listaSeguimiento = builder.listaSeguimiento;
        this.fechaHoraCreacion = Timestamp.valueOf(LocalDateTime.now());
    }

    public static class Builder{
        private Activo activo;
        private ListaSeguimiento listaSeguimiento;

        public Builder(){
        }

        public Builder setActivo(Activo activo){
            this.activo = activo;
            return this;
        }

        public Builder setListaSeguimiento(ListaSeguimiento listaSeguimiento){
            this.listaSeguimiento = listaSeguimiento;
            return this;
        }

        public ListaSeguimientoActivo build(){
            return new ListaSeguimientoActivo(this);
        }
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

    public Timestamp getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }
}
