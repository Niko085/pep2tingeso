package com.example.reparacionesvehiculosservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reparaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReparacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private int tipoReparacion;
    private String descripcion;//Ej: Reparación de Neumáticos y Ruedas
    private LocalDate fechaReparacion;
    private LocalTime horaReparacion;
    private double montoReparacion;

    //Foranea
    private Long idHistorialReparaciones;
    private String patente;
}