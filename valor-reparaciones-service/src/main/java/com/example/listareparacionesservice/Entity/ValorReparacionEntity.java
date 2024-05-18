package com.example.listareparacionesservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "valorreparaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValorReparacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String tipoReparacion;//Ej: reparacion sistema electrico
    private int numeroReparacion;//reparacion tipo 1, 2, ... , 11
    private String descripcion;
    private int monto;
    private String tipoMotor;

}
