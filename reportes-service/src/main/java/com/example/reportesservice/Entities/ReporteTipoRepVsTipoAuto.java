package com.example.reportesservice.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteTipoRepVsTipoAuto {
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
