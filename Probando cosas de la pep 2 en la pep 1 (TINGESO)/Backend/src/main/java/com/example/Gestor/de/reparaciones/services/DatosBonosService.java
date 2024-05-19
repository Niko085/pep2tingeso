package com.example.Gestor.de.reparaciones.services;

import com.example.Gestor.de.reparaciones.entities.AutomovilEntity;
import com.example.Gestor.de.reparaciones.entities.DatosBonosEntity;
import com.example.Gestor.de.reparaciones.repositories.DatosBonosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class DatosBonosService {
    @Autowired
    private DatosBonosRepository datosBonosRepository;

    public ArrayList<DatosBonosEntity> getDatosBonos(){
        return (ArrayList<DatosBonosEntity>) datosBonosRepository.findAll();
    }
    public DatosBonosEntity saveDatosBonos(DatosBonosEntity datosBono){
        return datosBonosRepository.save(datosBono);
    }

    public DatosBonosEntity updateDatosBonos(DatosBonosEntity datosBonosEntity) {
        return datosBonosRepository.save(datosBonosEntity);
    }
    public DatosBonosEntity getDatosBonosById(Long id){
        return datosBonosRepository.findById(id).get();
    }
    public DatosBonosEntity getDatosBonosByMarca(String marca){
        return datosBonosRepository.findByMarcaAutomovil(marca);
    }

    public boolean deleteDatosBonos(Long id) throws Exception {
        try{
            datosBonosRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

}
