package com.auth0.example.persistence.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class Persistente {
    @Id
    @GeneratedValue
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    public Long getId() {
        return id;
    }
}
