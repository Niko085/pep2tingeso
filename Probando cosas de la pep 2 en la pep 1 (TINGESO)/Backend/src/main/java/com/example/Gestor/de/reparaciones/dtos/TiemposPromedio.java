package com.example.Gestor.de.reparaciones.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TiemposPromedio {
    public String marcaAutomovil;
    public double tiempoEnDias;
}
