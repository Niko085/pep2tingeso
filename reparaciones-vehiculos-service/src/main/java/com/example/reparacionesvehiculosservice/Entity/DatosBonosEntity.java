package com.example.reparacionesvehiculosservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "datosBonos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatosBonosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marcaAutomovil;
    private int cantidadBonos;
    private int montoBono;
}
