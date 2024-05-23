package com.example.reparacionesvehiculosservice.Controller;

import com.example.reparacionesvehiculosservice.Entity.HistorialEntity;
import com.example.reparacionesvehiculosservice.Model.AutomovilEntity;
import com.example.reparacionesvehiculosservice.Service.HistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/historial")
@CrossOrigin("*")
public class HistorialController {

    @Autowired
    HistorialService historialService;


    @GetMapping("/")
    public ResponseEntity<List<HistorialEntity>> listhistorialReparaciones() {
        List<HistorialEntity> historialReparaciones = historialService.getHistorialReparaciones();
        return ResponseEntity.ok(historialReparaciones);
    }
/*
    @GetMapping("/{patente}")
    public ResponseEntity<List<HistorialEntity>> getHistorialReparacionesByPatente(@PathVariable String patente) {
        List<HistorialEntity> reparaciones = historialService.getHistorialReparacionesByPatente(patente);
        return ResponseEntity.ok(reparaciones);
    }

 */

/*
    @GetMapping("/{id}")
    public HistorialEntity getHistorialReparacionesById(@PathVariable Long id) {
        HistorialEntity reparaciones = historialService.getHistorialAutoById(id);
        return ResponseEntity.ok(reparaciones).getBody();
    }

 */
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
/*
    @PutMapping("/pagar/{id}")
    public ResponseEntity<Void> updatePago(@PathVariable Long id) {
        HistorialEntity historial = historialService.getHistorialAutoById(id);
        if (historial == null) {
            return ResponseEntity.notFound().build();
        }

        historial.setPagado(true);
        historialService.updateHistorial(historial);
        return ResponseEntity.ok().build();
    }

 */

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
    //http://localhost:8090/api/historialreparaciones/calculate?patente=CFYF55
    /*@GetMapping("/calculate")
    public ResponseEntity<Void> calculatehistorial(@RequestParam("patente") String patente) {
        historialService.calcularMontoTotalPagar(patente);
        return ResponseEntity.noContent().build();
    }


     */
/*
    //http://localhost:8090/api/historialreparaciones/reporte/reparaciones-vs-tipo-autos
    @GetMapping("/reporte/reparaciones-vs-tipo-autos")
    public List<ReparacionesvsTipoAutos> getReporteReparacionesvsTipoAutos() {
        return historialService.reporteReparacionesvsTipoAutos();
    }

 */
/*
    //http://localhost:8090/api/historialreparaciones/reporte/reparaciones-vs-tipo-motores
    @GetMapping("/reporte/reparaciones-vs-tipo-motores")
    public List<ReparacionesvsTipoMotor> getReporteReparacionesvsTipoMotor() {
        return historialService.reporteReparacionesvsTipoMotor();
    }

    @GetMapping("/reporte/marcas-vs-tiempo-promedio")
    public List<TiemposPromedio> getReporteMarcasvsTiempoReparacion() {
        return historialService.reporteTiempoPromedioReparacion();
    }

 */

}