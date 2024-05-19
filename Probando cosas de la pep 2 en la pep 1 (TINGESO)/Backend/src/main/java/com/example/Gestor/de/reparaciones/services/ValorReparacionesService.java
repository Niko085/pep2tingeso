package com.example.Gestor.de.reparaciones.services;

import com.example.Gestor.de.reparaciones.repositories.ValorReparacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ValorReparacionesService {
    @Autowired
    ValorReparacionesRepository valorReparacionesRepository;

    public int getMonto(int numeroReparacion, String tipoMotor){
        return valorReparacionesRepository.findMontoByNumeroReparacionAndTipoMotor(numeroReparacion, tipoMotor);
    }

}
