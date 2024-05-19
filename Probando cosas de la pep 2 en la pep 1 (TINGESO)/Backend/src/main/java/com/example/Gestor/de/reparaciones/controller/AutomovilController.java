package com.example.Gestor.de.reparaciones.controller;

import com.example.Gestor.de.reparaciones.entities.AutomovilEntity;
import com.example.Gestor.de.reparaciones.services.AutomovilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/automoviles")
@CrossOrigin("*")
public class AutomovilController {
    @Autowired
    AutomovilService automovilService;

    @GetMapping("/")
    public ResponseEntity<List<AutomovilEntity>> listAutomoviles() {
        List<AutomovilEntity> automoviles = automovilService.getAutomoviles();
        return ResponseEntity.ok(automoviles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutomovilEntity> getAutomovilById(@PathVariable Long id) {
        AutomovilEntity automovil = automovilService.getAutomovilById(id);
        return ResponseEntity.ok(automovil);
    }


    @GetMapping("/patente/{patente}")
    public ResponseEntity<AutomovilEntity> getAutomovilByPatente(@PathVariable String patente) {
        AutomovilEntity automovil = automovilService.getAutomovilByPatente(patente);
        return ResponseEntity.ok(automovil);
    }


    @PostMapping("/")
    public ResponseEntity<AutomovilEntity> saveAutomovil(@RequestBody AutomovilEntity automovil) {
        AutomovilEntity automovilNew = automovilService.saveAutomovil(automovil);
        return ResponseEntity.ok(automovilNew);
    }

    @PutMapping("/")
    public ResponseEntity<AutomovilEntity> updateAutomovil(@RequestBody AutomovilEntity automovil){
        AutomovilEntity automovilUpdated = automovilService.updateAutomovil(automovil);
        return ResponseEntity.ok(automovilUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAutomovilById(@PathVariable Long id) throws Exception {
        var isDeleted = automovilService.deleteAutomovil(id);
        return ResponseEntity.noContent().build();
    }
}
