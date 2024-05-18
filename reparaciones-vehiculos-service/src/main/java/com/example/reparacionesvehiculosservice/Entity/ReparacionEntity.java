package com.example.reparacionesvehiculosservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String descripcion;

    //Foranea
    private int idHistorialReparaciones;
    private String patente;
}