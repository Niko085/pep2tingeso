package com.example.Gestor.de.reparaciones.controller;

import com.example.Gestor.de.reparaciones.entities.DatosBonosEntity;
import com.example.Gestor.de.reparaciones.entities.ReparacionEntity;
import com.example.Gestor.de.reparaciones.services.DatosBonosService;
import com.example.Gestor.de.reparaciones.services.ReparacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/datosbonos")
@CrossOrigin("*")
public class DatosBonosController {

    @Autowired
    DatosBonosService datosBonosService;

    @GetMapping("/")
    public ResponseEntity<List<DatosBonosEntity>> listBonos() {
        List<DatosBonosEntity> bonos = datosBonosService.getDatosBonos();
        return ResponseEntity.ok(bonos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosBonosEntity> getBonoById(@PathVariable Long id) {
        DatosBonosEntity bono = datosBonosService.getDatosBonosById(id);
        return ResponseEntity.ok(bono);
    }

    @PostMapping("/")
    public ResponseEntity<DatosBonosEntity> saveBono(@RequestBody DatosBonosEntity bono) {
        DatosBonosEntity bonoNew = datosBonosService.saveDatosBonos(bono);
        return ResponseEntity.ok(bonoNew);
    }

    @PutMapping("/")
    public ResponseEntity<DatosBonosEntity> updateBono(@RequestBody DatosBonosEntity bono){
        DatosBonosEntity bonoUpdated = datosBonosService.updateDatosBonos(bono);
        return ResponseEntity.ok(bonoUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBonoById(@PathVariable Long id) throws Exception {
        var isDeleted = datosBonosService.deleteDatosBonos(id);
        return ResponseEntity.noContent().build();
    }
}
