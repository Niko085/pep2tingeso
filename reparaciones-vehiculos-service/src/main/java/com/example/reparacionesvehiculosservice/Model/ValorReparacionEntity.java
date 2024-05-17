package com.example.reparacionesvehiculosservice.Model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValorReparacionEntity {

    private String tipoReparacion;//Título de la reparación Ej: reparacion sistema electrico
    private int numeroReparacion;//Reparacion tipo 1, 2, ... , 11
    private String descripcion;
    private int monto;
    private String tipoMotor;

}
