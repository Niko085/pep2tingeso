package com.example.reparacionesvehiculosservice.Service;

import com.example.reparacionesvehiculosservice.Entity.ReparacionEntity;
import com.example.reparacionesvehiculosservice.Model.AutomovilEntity;
import com.example.reparacionesvehiculosservice.Repository.ReparacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReparacionService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ReparacionRepository reparacionRepository;

    public ArrayList<ReparacionEntity> getReparaciones(){
        return (ArrayList<ReparacionEntity>) reparacionRepository.findAll();
    }

    public ReparacionEntity saveReparacion(ReparacionEntity reparacion){
        String patente = reparacion.getPatente();
        AutomovilEntity automovil = getAutomovilByPatente(patente);
        double monto = getMonto(reparacion.getTipoReparacion(), automovil.getMotor());
        reparacion.setMontoReparacion(monto);
        return reparacionRepository.save(reparacion);
    }

    public List<ReparacionEntity> createVarious(List<ReparacionEntity> reparacionesList) {
        for (ReparacionEntity reparacion : reparacionesList) {
            saveReparacion(reparacion);
        }
        return reparacionesList;
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


    /////////////////////////////////////COMUNICACIÓN CON AUTOMOVIL/////////////////////////////////////

    //http://localhost:8081/historial/patente/CFYF55
    public AutomovilEntity getAutomovilByPatente(String patente) {
        // Utiliza el nombre lógico del servicio registrado en Eureka
        String url = "http://vehiculos-service/automoviles/patente/" + patente;

        // Realiza la solicitud utilizando RestTemplate
        AutomovilEntity automovil = restTemplate.getForObject(url, AutomovilEntity.class);
        return automovil;
    }


    //http://localhost:8081/historial/monto/1/Gasolina
    public int getMonto(int numeroReparacion, String tipoMotor) {
        String url = "http://valor-reparaciones-service/valorReparacion/monto/" + numeroReparacion + "/" + tipoMotor;
        int monto = restTemplate.getForObject(url, Integer.class);
        return monto;
    }

}
