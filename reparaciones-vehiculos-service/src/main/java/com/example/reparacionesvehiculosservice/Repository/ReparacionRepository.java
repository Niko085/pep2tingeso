package com.example.reparacionesvehiculosservice.Repository;

import com.example.reparacionesvehiculosservice.Entity.ReparacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReparacionRepository extends JpaRepository<ReparacionEntity, Long> {
    public List<ReparacionEntity> findByIdHistorialReparaciones(long id);

    @Query("SELECT COUNT(r) FROM ReparacionEntity r WHERE r.idHistorialReparaciones = :idHistorial")
    Integer countReparacionesByHistorial(@Param("idHistorial") Long idHistorial);

    @Query("SELECT COUNT(r) FROM ReparacionEntity r WHERE r.tipoReparacion = :tipoReparacion")
    Integer countReparacionesByTipoReparacion(@Param("tipoReparacion") int tipoReparacion);

    public Optional<ReparacionEntity> findById(Long id);

}
