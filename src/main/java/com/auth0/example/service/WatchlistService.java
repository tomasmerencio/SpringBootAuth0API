package com.auth0.example.service;

import com.auth0.example.persistence.dao.ActivoRepository;
import com.auth0.example.persistence.dao.ListaSeguimientoRepository;
import com.auth0.example.persistence.dao.UserRepository;
import com.auth0.example.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class WatchlistService implements IWatchlistService{
    @Autowired
    ActivoRepository activoRepository;

    @Autowired
    ListaSeguimientoRepository listaSeguimientoRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Activo addActivoToLista(Usuario usuario, Long assetId, Long watchlistId) {
        ListaSeguimiento listaSeguimiento = getWatctlistFromUser(usuario, watchlistId).orElseThrow(EntityNotFoundException::new);
        if(!activoExisteEnListaSeguimiento(assetId, listaSeguimiento)){
            Activo activo = activoRepository
                    .findById(assetId)
                    .orElseThrow(EntityNotFoundException::new);

            ListaSeguimientoActivo listaSeguimientoActivo = new ListaSeguimientoActivo.Builder()
                    .setActivo(activo)
                    .setListaSeguimiento(listaSeguimiento)
                    .build();

            listaSeguimiento.agregarListaSeguimientoActivo(listaSeguimientoActivo);
            listaSeguimientoRepository.save(listaSeguimiento);
            return activo;
        }
        return null;
    }

    private Boolean activoExisteEnListaSeguimiento(Long activoId, ListaSeguimiento listaSeguimiento){
        return listaSeguimiento.getActivos().stream().anyMatch(a -> a.getId().equals(activoId));
    }

    @Override
    public void deleteAsset(Usuario usuario, Long activoId, Long watchlistId) {
        ListaSeguimiento listaSeguimiento = getWatctlistFromUser(usuario, watchlistId).orElseThrow(EntityNotFoundException::new);
        listaSeguimiento.eliminarActivo(activoId);
        listaSeguimientoRepository.save(listaSeguimiento);
    }

    @Override
    @Transactional
    public ListaSeguimiento agregarListaSeguimiento(Usuario usuario, String nombre){
        ListaSeguimiento listaSeguimiento = new ListaSeguimiento(nombre);
        usuario.agregarListaSeguimiento(listaSeguimiento);
        listaSeguimientoRepository.save(listaSeguimiento);
        return listaSeguimiento;
    }

    @Override
    @Transactional
    public ListaSeguimiento editarNombre(Usuario usuario, Long watchlistId, String nombre){
        Optional<ListaSeguimiento> datosListaSeguimiento = getWatctlistFromUser(usuario, watchlistId);
        if(datosListaSeguimiento.isPresent()){
            ListaSeguimiento listaSeguimiento = datosListaSeguimiento.get();
            listaSeguimiento.setNombre(nombre);
            listaSeguimientoRepository.save(listaSeguimiento);
            return listaSeguimiento;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void eliminarListaSeguimiento(Usuario usuario, Long watchlistId){
        if(getWatctlistFromUser(usuario, watchlistId).isPresent()){
            usuario.eliminarListaSeguimiento(watchlistId);
            listaSeguimientoRepository.deleteById(watchlistId);
            userRepository.save(usuario);
        }
    }

    private Optional<ListaSeguimiento> getWatctlistFromUser(Usuario usuario, Long watchlistId){
        return usuario.getListaSeguimientoPorId(watchlistId);
    }
}
