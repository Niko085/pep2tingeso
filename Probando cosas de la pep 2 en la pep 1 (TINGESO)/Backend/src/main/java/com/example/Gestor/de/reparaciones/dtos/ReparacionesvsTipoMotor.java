package com.example.Gestor.de.reparaciones.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReparacionesvsTipoMotor {
    private String tipoReparacion;
    private int cantidadGasolina;
    private int cantidadDiesel;
    private int cantidadHibrido;
    private int cantidadElectrico;
    private int montoTotalReparaciones;
}
