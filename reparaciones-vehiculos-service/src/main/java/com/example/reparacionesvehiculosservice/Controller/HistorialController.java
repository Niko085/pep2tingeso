package com.example.reparacionesvehiculosservice.Controller;

import com.example.reparacionesvehiculosservice.Entity.DatosBonosEntity;
import com.example.reparacionesvehiculosservice.Entity.HistorialEntity;
import com.example.reparacionesvehiculosservice.Entity.ReparacionEntity;
import com.example.reparacionesvehiculosservice.Model.AutomovilEntity;
import com.example.reparacionesvehiculosservice.Service.DatosBonosService;
import com.example.reparacionesvehiculosservice.Service.HistorialService;
import com.example.reparacionesvehiculosservice.Service.ReparacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/historialreparaciones/")
//@CrossOrigin("*")
public class HistorialController {
    @Autowired
    ReparacionService reparacionService;
    @Autowired
    HistorialService historialService;
    @Autowired
    DatosBonosService datosBonosService;

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

    @GetMapping("/no-pagado/{patente}")
    public ResponseEntity<HistorialEntity> getHistorialReparacionesNoPagadasByPatente(@PathVariable String patente) {
        HistorialEntity historial = historialService.getHistorialReparacionesNoPagadasByPatente(patente);
        if (historial != null) {
            return new ResponseEntity<>(historial, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        AutomovilEntity automovil = reparacionService.getAutomovilByPatente(patente);
        return ResponseEntity.ok(automovil);
    }

    //http://localhost:8081/historial/monto/1/Gasolina
    @GetMapping("/monto/{numeroReparacion}/{tipoMotor}")
    public int getMonto(@PathVariable int numeroReparacion, @PathVariable String tipoMotor) {
        return reparacionService.getMonto(numeroReparacion, tipoMotor);
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

    @GetMapping("/pagar")
    public ResponseEntity<Void> pagarHistorial(@RequestParam("patente") String patente) {
        historialService.pagarHistorial(patente);
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


    @PostMapping("/reparacion/crearVarias")
    public ResponseEntity<List<ReparacionEntity>> createVariousReparaciones(@RequestBody List<ReparacionEntity> reparacionesList) {
        List<ReparacionEntity> savedReparaciones = reparacionService.createVarious(reparacionesList);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReparaciones);
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



    //------------------------------------DATOS BONOS CONTROLLER------------------------------------
    @GetMapping("/datosbonos/")
    public ResponseEntity<List<DatosBonosEntity>> listBonos() {
        List<DatosBonosEntity> bonos = datosBonosService.getDatosBonos();
        return ResponseEntity.ok(bonos);

    }

    @GetMapping("/datosbonos/{id}")
    public ResponseEntity<DatosBonosEntity> getBonoById(@PathVariable Long id) {
        DatosBonosEntity bono = datosBonosService.getDatosBonosById(id);
        return ResponseEntity.ok(bono);
    }

    @PostMapping("/datosbonos/")
    public ResponseEntity<DatosBonosEntity> saveBono(@RequestBody DatosBonosEntity bono) {
        DatosBonosEntity bonoNew = datosBonosService.saveDatosBonos(bono);
        return ResponseEntity.ok(bonoNew);
    }

    @PutMapping("/datosbonos/")
    public ResponseEntity<DatosBonosEntity> updateBono(@RequestBody DatosBonosEntity bono){
        DatosBonosEntity bonoUpdated = datosBonosService.updateDatosBonos(bono);
        return ResponseEntity.ok(bonoUpdated);
    }

    @DeleteMapping("/datosbonos/{id}")
    public ResponseEntity<Boolean> deleteBonoById(@PathVariable Long id) throws Exception {
        var isDeleted = datosBonosService.deleteDatosBonos(id);
        return ResponseEntity.noContent().build();
    }

}