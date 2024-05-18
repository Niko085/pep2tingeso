package com.example.reparacionesvehiculosservice.Repository;

import com.example.reparacionesvehiculosservice.Entity.DatosBonosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatosBonosRepository extends JpaRepository<DatosBonosEntity, Long> {
    public DatosBonosEntity findByMarcaAutomovil(String marca);
    //public DatosBonosEntity findById(long id);

}