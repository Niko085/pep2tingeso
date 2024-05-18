package com.example.vehiculosservice.repository;


import com.example.vehiculosservice.entity.AutomovilEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AutomovilRepository extends JpaRepository<AutomovilEntity, Long> {
    public AutomovilEntity findById(long id);
    public AutomovilEntity findByPatente(String patente);

    public AutomovilEntity findByAnioFabricacion(int anioFabricacion);

}
