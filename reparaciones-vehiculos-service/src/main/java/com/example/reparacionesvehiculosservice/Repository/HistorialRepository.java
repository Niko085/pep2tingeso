package com.example.reparacionesvehiculosservice.Repository;

import com.example.reparacionesvehiculosservice.Entity.HistorialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistorialRepository extends JpaRepository<HistorialEntity, Long> {

    public List<HistorialEntity> findByPatente(String patente);

    //public Optional<HistorialEntity> findById(Long id);
    public Optional<HistorialEntity> findById(Long id);

    public HistorialEntity findByPatenteAndAndPagadoIsFalse(String patente);

    public HistorialEntity findHistorialByPatente(String patente);


}