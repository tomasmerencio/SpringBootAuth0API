package com.auth0.example.service;

import com.auth0.example.persistence.model.Activo;
import com.auth0.example.persistence.model.ListaSeguimiento;
import com.auth0.example.persistence.model.Usuario;

public interface IWatchlistService {
    Activo addActivoToLista(Usuario usuario, Long activoId, Long listaSeguimientoId);
    void deleteAsset(Usuario usuario, Long activoId, Long watchlistId);
    ListaSeguimiento agregarListaSeguimiento(Usuario usuario, String nombre);
    ListaSeguimiento editarNombre(Usuario usuario, Long id, String nombre);
    void eliminarListaSeguimiento(Usuario usuario, Long id);
}
