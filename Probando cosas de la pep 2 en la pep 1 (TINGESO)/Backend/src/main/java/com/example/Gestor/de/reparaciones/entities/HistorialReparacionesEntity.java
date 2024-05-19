package com.example.Gestor.de.reparaciones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "historialReparaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialReparacionesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private LocalDate fechaIngresoTaller;
    private LocalTime horaIngresoTaller;
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
