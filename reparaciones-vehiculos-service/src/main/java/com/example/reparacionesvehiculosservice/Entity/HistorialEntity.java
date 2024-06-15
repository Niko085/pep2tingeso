package com.example.reparacionesvehiculosservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "historialReparaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private LocalDate fechaIngresoTaller;
    private LocalTime horaIngresoTaller;
    private double montoTotalReparaciones;
    private double montoSinIva;//SUB Total
    private double montoTotalPagar;
    private double recargos;
    private double descuentos;
    private double iva;
    private LocalDate fechaSalidaTaller;
    private LocalTime horaSalidaTaller;
    private LocalDate fechaClienteSeLlevaVehiculo;
    private LocalTime horaClienteSeLlevaVehiculo;
    private boolean pagado;

    //Foranea
    private String patente;
}
