package com.example.reportesservice.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteTipoRepVsTipoAuto {
    //----------------------------------------REPORTE  1(HU5)----------------------------------------
    private String reparacion;
    //Sedan
    private int cantidadSedan;
    private int montoSedan;
    //Hatchback
    private int cantidadHatchback;
    private int montoHatchback;
    //SUV
    private int cantidadSuv;
    private int montoSuv;
    //Pickup
    private int cantidadPickup;
    private int montoPickup;
    //Furgoneta
    private int cantidadFurgoneta;
    private int montoFurgoneta;
    //Monto total
    private int montoTotalReparaciones;
}
