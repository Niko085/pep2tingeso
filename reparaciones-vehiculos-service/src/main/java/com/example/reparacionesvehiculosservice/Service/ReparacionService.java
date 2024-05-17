package com.example.reparacionesvehiculosservice.Service;

import com.example.reparacionesvehiculosservice.Entity.ReparacionEntity;
import com.example.reparacionesvehiculosservice.Repository.ReparacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReparacionService {
    @Autowired
    ReparacionRepository reparacionRepository;

    public ArrayList<ReparacionEntity> getReparaciones(){
        return (ArrayList<ReparacionEntity>) reparacionRepository.findAll();
    }

    public ReparacionEntity saveReparacion(ReparacionEntity reparacion){
        return reparacionRepository.save(reparacion);
    }

    public List<ReparacionEntity> getReparacionByIdHistorialReparaciones(long id){
        return reparacionRepository.findByIdHistorialReparaciones(id);
    }

    public ReparacionEntity updateReparacion(ReparacionEntity reparacion) {
        return reparacionRepository.save(reparacion);
    }

    public boolean deleteReparacion(Long id) throws Exception {
        try{
            reparacionRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public int contarReparacionesPorHistorial(Long idHistorial) {
        // Llamar al método definido en el repositorio para contar las reparaciones por historial
        return reparacionRepository.countReparacionesByHistorial(idHistorial);
    }

    public ReparacionEntity getReparacionById(Long id){
        return reparacionRepository.findById(id).get();
    }

}
