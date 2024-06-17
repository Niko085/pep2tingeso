package com.example.listareparacionesservice.Controller;

import com.example.listareparacionesservice.Entity.ValorReparacionEntity;
import com.example.listareparacionesservice.Service.ValorReparacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/valorReparacion")
//@CrossOrigin("*")
public class ValorReparacionController {
    @Autowired
    ValorReparacionService valorReparacionService;

    //http://localhost:8092/valorReparacion/monto/1/Gasolina
    @GetMapping("/monto/{numeroReparacion}/{tipoMotor}")
    public int getMonto(@PathVariable int numeroReparacion, @PathVariable String tipoMotor) {
        return valorReparacionService.getMonto(numeroReparacion, tipoMotor);
    }

    @GetMapping("/montos/{tipoMotor}")
    public ResponseEntity<List<ValorReparacionEntity>> listaValores(@PathVariable String tipoMotor) {
        List<ValorReparacionEntity> montos = valorReparacionService.getMontosByTipoMotor(tipoMotor);
        return ResponseEntity.ok(montos);

    }
}