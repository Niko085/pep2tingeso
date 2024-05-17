package com.example.listareparacionesservice.Service;

import com.example.listareparacionesservice.Repository.ValorReparacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValorReparacionService {
    @Autowired
    ValorReparacionRepository valorReparacionRepository;

    public int getMonto(int numeroReparacion, String tipoMotor){
        return valorReparacionRepository.findMontoByNumeroReparacionAndTipoMotor(numeroReparacion, tipoMotor);
    }

}
