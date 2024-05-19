package com.example.Gestor.de.reparaciones.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "automoviles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutomovilEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String patente;//4 letras y 2 números
    private String marca;//Toyota, Kia, Honda, Ford, Chevrolet, Hyundai, etc.
    private String modelo;
    private String tipo;//Sedan, Hatchback, SUV, Pickup, Furgoneta)
    private int anioFabricacion;
    private String motor;//gasolina, diésel, híbrido, eléctrico
    private int cantAsientos;
    private int kilometraje;

}
