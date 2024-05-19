package com.example.Gestor.de.reparaciones.services;

import com.example.Gestor.de.reparaciones.entities.AutomovilEntity;
import com.example.Gestor.de.reparaciones.entities.DatosBonosEntity;
import com.example.Gestor.de.reparaciones.entities.HistorialReparacionesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Service
public class OfficeHRMService {
    @Autowired
    private DatosBonosService datosBonosService;

    public double getPorcentajeRecargoKilometraje(AutomovilEntity automovil){
        int kilometraje = automovil.getKilometraje();
        String tipoAuto = automovil.getTipo();
        double porcentajeRecargo = 0;
        if (kilometraje >= 0 && kilometraje <= 5000){
            porcentajeRecargo = switch (tipoAuto) {
                case "Sedan" -> 0;
                case "Hatchback" -> 0;
                case "SUV" -> 0;
                case "Pickup" -> 0;
                case "Furgoneta" -> 0;
                default -> porcentajeRecargo;
            };
        }else if (kilometraje >= 5001 && kilometraje <= 12000){
            porcentajeRecargo = switch (tipoAuto) {
                case "Sedan" -> 0.03;
                case "Hatchback" -> 0.03;
                case "SUV" -> 0.05;
                case "Pickup" -> 0.05;
                case "Furgoneta" -> 0.05;
                default -> porcentajeRecargo;
            };
        }else if (kilometraje >= 12001 && kilometraje <= 25000){
            porcentajeRecargo = switch (tipoAuto) {
                case "Sedan" -> 0.07;
                case "Hatchback" -> 0.07;
                case "SUV" -> 0.09;
                case "Pickup" -> 0.09;
                case "Furgoneta" -> 0.09;
                default -> porcentajeRecargo;
            };
        }else if (kilometraje >= 25001 && kilometraje <= 40000){
            porcentajeRecargo = switch (tipoAuto) {
                case "Sedan" -> 0.12;
                case "Hatchback" -> 0.12;
                case "SUV" -> 0.12;
                case "Pickup" -> 0.12;
                case "Furgoneta" -> 0.12;
                default -> porcentajeRecargo;
            };
        }else if (kilometraje >= 40001){
            porcentajeRecargo = switch (tipoAuto) {
                case "Sedan" -> 0.2;
                case "Hatchback" -> 0.2;
                case "SUV" -> 0.2;
                case "Pickup" -> 0.2;
                case "Furgoneta" -> 0.2;
                default -> porcentajeRecargo;
            };
        }
        return porcentajeRecargo;
    }


    public double getPorcentajeRecargoAntiguedad(AutomovilEntity automovil){
        int edad = 2024 - automovil.getAnioFabricacion();
        String tipoAuto = automovil.getTipo();
        double porcentajeRecargo = 0.0;
        if (edad >= 0 && edad <= 5){
            porcentajeRecargo = switch (tipoAuto) {
                case "Sedan" -> 0;
                case "Diesel" -> 0;
                case "Hatchback" -> 0;
                case "Pickup" -> 0;
                case "Furgoneta" -> 0;
                default -> porcentajeRecargo;
            };
        }else if (edad >= 6 && edad <= 10){
            porcentajeRecargo = switch (tipoAuto) {
                case "Sedan" -> 0.05;
                case "Diesel" -> 0.05;
                case "Hatchback" -> 0.07;
                case "Pickup" -> 0.07;
                case "Furgoneta" -> 0.07;
                default -> porcentajeRecargo;
            };
        }else if (edad >= 11 && edad <= 15){
            porcentajeRecargo = switch (tipoAuto) {
                case "Sedan" -> 0.09;
                case "Diesel" -> 0.09;
                case "Hatchback" -> 0.11;
                case "Pickup" -> 0.11;
                case "Furgoneta" -> 0.11;
                default -> porcentajeRecargo;
            };
        }else if (edad >= 16){
            porcentajeRecargo = switch (tipoAuto) {
                case "Sedan" -> 0.15;
                case "Diesel" -> 0.15;
                case "Hatchback" -> 0.2;
                case "Pickup" -> 0.2;
                case "Furgoneta" -> 0.2;
                default -> porcentajeRecargo;
            };
        }
        return porcentajeRecargo;
    }


    public double getPorcentajeRecargoRetraso(HistorialReparacionesEntity historialReparaciones){
        LocalDate fechasSalida = historialReparaciones.getFechaSalidaTaller();
        LocalDate fechaRetirada = historialReparaciones.getFechaClienteSeLlevaVehiculo();

        //Calcular diferencia entre fechas
        long diferenciaEnDias = ChronoUnit.DAYS.between(fechasSalida, fechaRetirada);

        //Si hay retraso, calcular el recargo
        double recargo = 0;
        if (diferenciaEnDias > 0) {
            recargo = 0.05 * diferenciaEnDias; // 5% del monto total por cada día de retraso
        }
        return recargo;
    }


    public double getPorcentajeDescuentoDia(LocalDate fechaEntrada, LocalTime horaEntrada){
        DayOfWeek diaDeLaSemana = fechaEntrada.getDayOfWeek();
        int horaDeEntrada = horaEntrada.getHour();

        //Verificar día de la semana
        if((diaDeLaSemana == DayOfWeek.MONDAY || diaDeLaSemana == DayOfWeek.THURSDAY) && (horaDeEntrada >= 9 && horaDeEntrada < 12)){
            return 0.1;
        }
        else{
            return 0;
        }
    }



    public double getDescuentoCantidadReparaciones(AutomovilEntity automovil, int cantidadReparaciones){
        String tipoMotor = automovil.getMotor();
        double porcentajeRecargo = 0;
        if (cantidadReparaciones >= 1 && cantidadReparaciones <= 2){
            porcentajeRecargo = switch (tipoMotor) {
                case "Gasolina" -> 0.05;
                case "Diesel" -> 0.07;
                case "Hibrido" -> 0.1;
                case "Electrico" -> 0.08;
                default -> porcentajeRecargo;
            };
        }else if (cantidadReparaciones >= 3 && cantidadReparaciones <= 5){
            porcentajeRecargo = switch (tipoMotor) {
                case "Gasolina" -> 0.1;
                case "Diesel" -> 0.12;
                case "Hibrido" -> 0.15;
                case "Electrico" -> 0.13;
                default -> porcentajeRecargo;
            };
        }else if (cantidadReparaciones >= 6 && cantidadReparaciones <= 9){
            porcentajeRecargo = switch (tipoMotor) {
                case "Gasolina" -> 0.15;
                case "Diesel" -> 0.17;
                case "Hibrido" -> 0.2;
                case "Electrico" -> 0.18;
                default -> porcentajeRecargo;
            };
        }else if (cantidadReparaciones >= 10){
            porcentajeRecargo = switch (tipoMotor) {
                case "Gasolina" -> 0.2;
                case "Diesel" -> 0.22;
                case "Hibrido" -> 0.25;
                case "Electrico" -> 0.23;
                default -> porcentajeRecargo;
            };
        }
        return porcentajeRecargo;
    }

    public int getMontoDescuentoBonos(AutomovilEntity automovil){
        String marca = automovil.getMarca();
        DatosBonosEntity datosBono = datosBonosService.getDatosBonosByMarca(marca);
        if(datosBono.getCantidadBonos() > 0){
            datosBono.setCantidadBonos(datosBono.getCantidadBonos()-1);
            return datosBono.getMontoBono();
        }else return 0;
    }
}
