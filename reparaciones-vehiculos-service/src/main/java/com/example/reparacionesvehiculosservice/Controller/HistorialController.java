package com.example.reparacionesvehiculosservice.Controller;

import com.example.reparacionesvehiculosservice.Entity.HistorialEntity;
import com.example.reparacionesvehiculosservice.Service.HistorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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


    @GetMapping("/{id}")
    public HistorialEntity getHistorialReparacionesById(@PathVariable Long id) {
        HistorialEntity reparaciones = historialService.getHistorialAutoById(id);
        return ResponseEntity.ok(reparaciones).getBody();
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteHistorialById(@PathVariable Long id) throws Exception {
        var isDeleted = historialService.deleteHistorial(id);
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