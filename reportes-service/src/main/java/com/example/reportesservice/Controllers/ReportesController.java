package com.example.reportesservice.Controllers;

import com.example.reportesservice.Dtos.ReporteCompararMeses;
import com.example.reportesservice.Dtos.ReporteTipoRepVsTipoAuto;
import com.example.reportesservice.Services.ReportesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reportes")
//@CrossOrigin("*")
public class ReportesController {
    @Autowired
    ReportesService reportesService;

    //http://localhost:8081/reportes/reporte/compararMeses/1/2024
    @GetMapping("/reporte/compararMeses/{mes}/{anio}")
    public List<ReporteCompararMeses> getReporteComparMeses(@PathVariable int mes, @PathVariable int anio) {
        return reportesService.reporteCompararMeses(mes, anio);
    }


    //http://localhost:8081/reportes/reporte/reparaciones-vs-tipo-autos/12/2023
    @GetMapping("/reporte/reparaciones-vs-tipo-autos/{mes}/{anio}")
    public List<ReporteTipoRepVsTipoAuto> getReporteReparacionesvsTipoAutos(@PathVariable int mes, @PathVariable int anio) {
        return reportesService.reporteReparacionesvsTipoAutos(mes, anio);
    }

    @GetMapping("/variacion/{valorAnterior}/{valorActual}")
    public double getVariacion(@PathVariable int valorAnterior, @PathVariable int valorActual) {
        return reportesService.calcularVariacion(valorAnterior, valorActual);
    }

}
