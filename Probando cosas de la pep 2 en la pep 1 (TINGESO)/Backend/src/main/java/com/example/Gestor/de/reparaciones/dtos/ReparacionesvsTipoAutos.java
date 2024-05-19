package com.example.Gestor.de.reparaciones.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReparacionesvsTipoAutos {
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
