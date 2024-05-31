package com.example.reportesservice.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteCompararMeses {
    //----------------------------------------REPORTE  2(HU6)----------------------------------------
    String reparacion;

    //Mes 1
    int mes1;
    int cantidadAutos1;
    int monto1;

    //Mes 2
    int mes2;
    int cantidadAutos2;
    int monto2;
    double variacionCantidad2;
    double variacionMonto2;

    //Mes 3
    int mes3;
    int cantidadAutos3; //cantidad de autos reparados en el mes
    int monto3;
    double variacionCantidad3; //porcentaje
    double variacionMonto3;
}
