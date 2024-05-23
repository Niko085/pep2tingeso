package com.example.reparacionesvehiculosservice.Controller;

import com.example.reparacionesvehiculosservice.Entity.HistorialEntity;
import com.example.reparacionesvehiculosservice.Entity.ReparacionEntity;
import com.example.reparacionesvehiculosservice.Model.AutomovilEntity;
import com.example.reparacionesvehiculosservice.Service.HistorialService;
import com.example.reparacionesvehiculosservice.Service.ReparacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/historial-reparaciones")
@CrossOrigin("*")
public class HistorialController {
    @Autowired
    ReparacionService reparacionService;
    @Autowired
    HistorialService historialService;

//------------------------------------HISTORIAL CONTROLLER------------------------------------
    @GetMapping("/")
    public ResponseEntity<List<HistorialEntity>> listhistorialReparaciones() {
        List<HistorialEntity> historialReparaciones = historialService.getHistorialReparaciones();
        return ResponseEntity.ok(historialReparaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialEntity> buscarPorId(@PathVariable Long id) {
        // Llama al método del servicio para buscar por ID
        Optional<HistorialEntity> optionalHistorial = historialService.getHistorialAutoById(id);

        // Verifica si se encontró el historial
        if (optionalHistorial.isPresent()) {
            // Si se encontró, devuelve la entidad con estado 200 OK
            return ResponseEntity.ok(optionalHistorial.get());
        } else {
            // Si no se encontró, devuelve un estado 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/")
    public ResponseEntity<HistorialEntity> saveHistorial(@RequestBody HistorialEntity historial) {
        HistorialEntity historialNew = historialService.saveHistorialReparaciones(historial);
        return ResponseEntity.ok(historialNew);
    }

    @PutMapping("/")
    public ResponseEntity<HistorialEntity> updateHistorial(@RequestBody HistorialEntity historial){
        HistorialEntity historialUpdated = historialService.updateHistorial(historial);
        return ResponseEntity.ok(historialUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteHistorialById(@PathVariable Long id) throws Exception {
        var isDeleted = historialService.deleteHistorial(id);
        return ResponseEntity.noContent().build();
    }


///////////////////////////////////////////////////PRUEBAS DE CONEXIÓN///////////////////////////////////////////////////
   //http://localhost:8081/historial/patente/CFYF55
    @GetMapping("/patente/{patente}")
    public ResponseEntity<AutomovilEntity> getAutomovilByPatente(@PathVariable String patente) {
        AutomovilEntity automovil = historialService.getAutomovilByPatente(patente);
        return ResponseEntity.ok(automovil);
    }

    //http://localhost:8081/historial/monto/1/Gasolina
    @GetMapping("/monto/{numeroReparacion}/{tipoMotor}")
    public int getMonto(@PathVariable int numeroReparacion, @PathVariable String tipoMotor) {
        return historialService.getMonto(numeroReparacion, tipoMotor);
    }

    @GetMapping("/montoTipoReparacionByTipoAutomovil/{tipoReparacion}/{tipoAuto}/{numMes}/{ano}")
    public int getMontoTipoReparacionByTipoAutomovil(@PathVariable int tipoReparacion, @PathVariable String tipoAuto, @PathVariable int numMes, @PathVariable int ano) {
        return historialService.getMontoTipoReparacionByTipoAutomovil(tipoReparacion, tipoAuto, numMes, ano);
    }



    @GetMapping("/cantidadTipoReparacioneByTipoAutomovil/{tipoReparacion}/{tipoAuto}/{numMes}/{ano}")
    public int getCantidadTipoReparacioneByTipoAutomovil(@PathVariable int tipoReparacion, @PathVariable String tipoAuto, @PathVariable int numMes, @PathVariable int ano) {
        return historialService.getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, tipoAuto, numMes, ano);
    }




/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/calculate")
    public ResponseEntity<Void> calculatehistorial(@RequestParam("patente") String patente) {
        historialService.calcularMontoTotalPagar(patente);
        return ResponseEntity.noContent().build();
    }

//------------------------------------REPARACION CONTROLLER------------------------------------

    @GetMapping("/reparacion/")
    public ResponseEntity<List<ReparacionEntity>> listReparaciones() {
        List<ReparacionEntity> reparaciones = reparacionService.getReparaciones();
        return ResponseEntity.ok(reparaciones);

    }

    @GetMapping("/reparacion/{id}")
    public ResponseEntity<ReparacionEntity> getReparacionById(@PathVariable Long id) {
        ReparacionEntity reparacion = reparacionService.getReparacionById(id);
        return ResponseEntity.ok(reparacion);
    }


    @PostMapping("/reparacion/")
    public ResponseEntity<ReparacionEntity> saveReparacion(@RequestBody ReparacionEntity reparacion) {
        ReparacionEntity reparacionNew = reparacionService.saveReparacion(reparacion);
        return ResponseEntity.ok(reparacionNew);
    }

    @PutMapping("/reparacion/")
    public ResponseEntity<ReparacionEntity> updateReparacion(@RequestBody ReparacionEntity reparacion){
        ReparacionEntity reparacionUpdated = reparacionService.updateReparacion(reparacion);
        return ResponseEntity.ok(reparacionUpdated);
    }

    @DeleteMapping("/reparacion/{id}")
    public ResponseEntity<Boolean> deleteReparacionById(@PathVariable Long id) throws Exception {
        var isDeleted = reparacionService.deleteReparacion(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/reparacion/historial/{id}")
    public ResponseEntity<List<ReparacionEntity>> listReparacionesByIdHistorial(@PathVariable Long id) {
        List<ReparacionEntity> reparaciones = reparacionService.getReparacionByIdHistorialReparaciones(id);
        return ResponseEntity.ok(reparaciones);

    }

}