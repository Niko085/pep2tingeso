package com.example.Gestor.de.reparaciones.repositories;

import com.example.Gestor.de.reparaciones.entities.HistorialReparacionesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistorialReparacionesRepository extends JpaRepository<HistorialReparacionesEntity, Long> {

    public List<HistorialReparacionesEntity> findByPatente(String patente);

    public Optional<HistorialReparacionesEntity> findById(Long id);

    public HistorialReparacionesEntity findByPatenteAndAndPagadoIsFalse(String patente);

    public HistorialReparacionesEntity findHistorialByPatente(String patente);


}
