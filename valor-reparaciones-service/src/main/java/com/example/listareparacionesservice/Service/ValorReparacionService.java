package com.example.listareparacionesservice.Service;

import com.example.listareparacionesservice.Entity.ValorReparacionEntity;
import com.example.listareparacionesservice.Repository.ValorReparacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ValorReparacionService {
    @Autowired
    ValorReparacionRepository valorReparacionRepository;

    public int getMonto(int numeroReparacion, String tipoMotor){
        return valorReparacionRepository.findMontoByNumeroReparacionAndTipoMotor(numeroReparacion, tipoMotor);
    }

    public List<ValorReparacionEntity> getMontosByTipoMotor(String tipoMotor){
        return valorReparacionRepository.findByTipoMotor(tipoMotor);
    }

}
