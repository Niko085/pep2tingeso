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
public class AutomovilEntity {

    private String patente;//4 letras y 2 números
    private String marca;//Toyota, Kia, Honda, Ford, Chevrolet, Hyundai, etc.
    private String modelo;
    private String tipo;//Sedan, Hatchback, SUV, Pickup, Furgoneta)
    private int anioFabricacion;
    private String motor;//gasolina, diésel, híbrido, eléctrico
    private int cantAsientos;
    private int kilometraje;

}