package com.example.Gestor.de.reparaciones.repositories;

import com.example.Gestor.de.reparaciones.entities.AutomovilEntity;
import com.example.Gestor.de.reparaciones.entities.DatosBonosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatosBonosRepository extends JpaRepository<DatosBonosEntity, Long> {
    public DatosBonosEntity findByMarcaAutomovil(String marca);
    //public DatosBonosEntity findById(long id);

}
