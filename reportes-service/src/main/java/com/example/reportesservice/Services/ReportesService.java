package com.example.reportesservice.Services;

import com.example.reportesservice.Dtos.ReporteCompararMeses;
import com.example.reportesservice.Dtos.ReporteTipoRepVsTipoAuto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
public class ReportesService {
    @Autowired
    RestTemplate restTemplate;

    /////////////////////////////////////COMUNICACIÓN CON HISTORIAL/////////////////////////////////////

    public int getMontoTipoReparacionByTipoAutomovil(int tipoReparacion, String tipoAuto, int numMes, int ano) {
        String url = "http://reparaciones-vehiculos-service/historialreparaciones/montoTipoReparacionByTipoAutomovil/"+tipoReparacion+"/"+tipoAuto+"/"+numMes+"/"+ano;
        int monto = restTemplate.getForObject(url, Integer.class);
        return monto;
    }

    public int getCantidadTipoReparacioneByTipoAutomovil(int tipoReparacion, String tipoAuto, int numMes, int ano) {
        String url = "http://reparaciones-vehiculos-service/historialreparaciones/cantidadTipoReparacioneByTipoAutomovil/"+tipoReparacion+"/"+tipoAuto+"/"+numMes+"/"+ano;
        int cantidad = restTemplate.getForObject(url, Integer.class);
        return cantidad;
    }



    public List<ReporteTipoRepVsTipoAuto> reporteReparacionesvsTipoAutos(int numMes, int ano){
        List<ReporteTipoRepVsTipoAuto> reparacionesvsTipoAutos = new ArrayList<>();

        //Se inicializan las cantidades
        int cantSed,cantHatch,cantSuv,cantPick,cantFurg;
        //Se inicializan los montos
        int montSed,montHatch,montSuv,montPick,montFurg;

        int totReparac; //monto total de reparaciones

        String nombreRep = null;

        // Obtener los datos y agregarlos a la lista
        for(int tipoReparacion = 1; tipoReparacion <= 11; tipoReparacion++){
            cantSed = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Sedan",numMes, ano);
            cantHatch = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Hatchback",numMes, ano);
            cantSuv = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Suv",numMes, ano);
            cantPick = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Pickup",numMes, ano);
            cantFurg = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Furgoneta",numMes, ano);

            montSed = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Sedan",numMes, ano);
            montHatch = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Hatchback",numMes, ano);
            montSuv = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Suv",numMes, ano);
            montPick = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Pickup",numMes, ano);
            montFurg = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Furgoneta",numMes, ano);

            nombreRep = getNombreReparacion(tipoReparacion);


            totReparac = ((montSed * cantSed) + (montHatch * cantHatch) +
                    (montSuv  * cantSuv) + (montPick * cantPick) + (montFurg * cantFurg));

            // Crear objeto ReparacionesvsTipoAutos y agregarlo a la lista
            ReporteTipoRepVsTipoAuto reparacionPorTipoAuto = new ReporteTipoRepVsTipoAuto(nombreRep,cantSed,montSed,
                    cantHatch,montHatch,cantSuv,montSuv,cantPick,montPick,cantFurg,montFurg,totReparac);

            reparacionesvsTipoAutos.add(reparacionPorTipoAuto);
        }
        return reparacionesvsTipoAutos;
    }



    public int getCantidadTotalAutos(int tipoReparacion, int numMes, int ano){

        int cantSed = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Sedan",numMes, ano);
        int cantHatch = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Hatchback",numMes, ano);
        int cantSuv = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Suv",numMes, ano);
        int cantPick = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Pickup",numMes, ano);
        int cantFurg = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Furgoneta",numMes, ano);

        return cantSed + cantHatch + cantSuv + cantPick + cantFurg;
    }



    public int getMontoTotalAutos(int tipoReparacion, int numMes, int ano){
        int montoTotal,montSed,montHatch,montSuv,montPick,montFurg;
        int cantSed,cantHatch,cantSuv,cantPick,cantFurg;

        cantSed = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Sedan",numMes, ano);
        cantHatch = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Hatchback",numMes, ano);
        cantSuv = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Suv",numMes, ano);
        cantPick = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Pickup",numMes, ano);
        cantFurg = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Furgoneta",numMes, ano);

        montSed = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Sedan",numMes, ano);
        montHatch = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Hatchback",numMes, ano);
        montSuv = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Suv",numMes, ano);
        montPick = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Pickup",numMes, ano);
        montFurg = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Furgoneta",numMes, ano);
        montoTotal = ((montSed * cantSed) + (montHatch * cantHatch) +
                (montSuv  * cantSuv) + (montPick * cantPick) + (montFurg * cantFurg));
        return montoTotal;
    }



    public String getNombreReparacion(int tipoReparacion) {
        return switch (tipoReparacion) {
            case 1 -> "Reparaciones del Sistema de Frenos";
            case 2 -> "Servicio del Sistema de Refrigeración";
            case 3 -> "Reparaciones del Motor";
            case 4 -> "Reparaciones de la Transmisión";
            case 5 -> "Reparación del Sistema Eléctrico";
            case 6 -> "Reparaciones del Sistema de Escape";
            case 7 -> "Reparación de Neumáticos y Ruedas";
            case 8 -> "Reparaciones de la Suspensión y la Dirección";
            case 9 -> "Reparación del Sistema de Aire Acondicionado y Calefacción";
            case 10 -> "Reparaciones del Sistema de Combustible";
            case 11 -> "Reparación y Reemplazo del Parabrisas y Cristales";
            default -> "Desconocido";
        };
    }



    public List<ReporteCompararMeses> reporteCompararMeses(int numMes, int ano) {
        List<ReporteCompararMeses> comparaciones = new ArrayList<>();

        int montoTotal,montSed,montHatch,montSuv,montPick,montFurg;
        int cantSed,cantHatch,cantSuv,cantPick,cantFurg;

        int mes1, cantidadAutos1, monto1;
        int mes2, cantidadAutos2, monto2;
        int mes3, cantidadAutos3, monto3;
        String nombRep;

        double variacionCantidad3;
        double variacionMonto3;
        double variacionCantidad2;
        double variacionMonto2;


        // Obtener los datos y agregarlos a la lista
        for (int tipoReparacion = 1; tipoReparacion <= 11; tipoReparacion++) {
            nombRep = getNombreReparacion(tipoReparacion);

            // Mes 1
            //(condición) ? si es true hace esto : si es false hace esto
            mes1 = (numMes - 2 > 0) ? numMes - 2 : 12 + (numMes - 2);
            int ano1 = (numMes - 2 > 0) ? ano : ano - 1;
            cantSed = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Sedan",mes1, ano1);
            cantHatch = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Hatchback",mes1, ano1);
            cantSuv = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Suv",mes1, ano1);
            cantPick = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Pickup",mes1, ano1);
            cantFurg = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Furgoneta",mes1, ano1);
            cantidadAutos1 = cantSed + cantHatch + cantSuv + cantPick + cantFurg;

            montSed = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Sedan",mes1, ano1);
            montHatch = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Hatchback",mes1, ano1);
            montSuv = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Suv",mes1, ano1);
            montPick = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Pickup",mes1, ano1);
            montFurg = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Furgoneta",mes1, ano1);
            montoTotal = ((montSed * cantSed) + (montHatch * cantHatch) +
                    (montSuv  * cantSuv) + (montPick * cantPick) + (montFurg * cantFurg));
            monto1 = montoTotal;

            // Mes 2
            mes2 = (numMes - 1 > 0) ? numMes - 1 : 12 + (numMes - 1);
            int ano2 = (numMes - 1 > 0) ? ano : ano - 1;
            cantSed = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Sedan",mes2, ano2);
            cantHatch = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Hatchback",mes2, ano2);
            cantSuv = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Suv",mes2, ano2);
            cantPick = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Pickup",mes2, ano2);
            cantFurg = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Furgoneta",mes2, ano2);
            cantidadAutos2 = cantSed + cantHatch + cantSuv + cantPick + cantFurg;

            montSed = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Sedan",mes2, ano2);
            montHatch = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Hatchback",mes2, ano2);
            montSuv = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Suv",mes2, ano2);
            montPick = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Pickup",mes2, ano2);
            montFurg = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Furgoneta",mes2, ano2);
            montoTotal = ((montSed * cantSed) + (montHatch * cantHatch) +
                    (montSuv  * cantSuv) + (montPick * cantPick) + (montFurg * cantFurg));
            monto2 = montoTotal;

            variacionCantidad2 = calcularVariacion(cantidadAutos1, cantidadAutos2);
            variacionMonto2 = calcularVariacion(monto1, monto2);

            // Mes 3
            mes3 = numMes;
            cantSed = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Sedan",mes3, ano);
            cantHatch = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Hatchback",mes3, ano);
            cantSuv = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Suv",mes3, ano);
            cantPick = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Pickup",mes3, ano);
            cantFurg = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Furgoneta",mes3, ano);
            cantidadAutos3 = cantSed + cantHatch + cantSuv + cantPick + cantFurg;

            montSed = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Sedan",mes3, ano);
            montHatch = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Hatchback",mes3, ano);
            montSuv = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Suv",mes3, ano);
            montPick = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Pickup",mes3, ano);
            montFurg = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Furgoneta",mes3, ano);
            montoTotal = ((montSed * cantSed) + (montHatch * cantHatch) +
                    (montSuv  * cantSuv) + (montPick * cantPick) + (montFurg * cantFurg));
            monto3 = montoTotal;

            variacionCantidad3 = calcularVariacion(cantidadAutos2, cantidadAutos3);
            variacionMonto3 = calcularVariacion(monto2, monto3);

            // Crear objeto CompararMeses y agregarlo a la lista
            ReporteCompararMeses mesAComparar = new ReporteCompararMeses(nombRep, mes1, cantidadAutos1, cantidadAutos1, mes2,
                    cantidadAutos2, cantidadAutos2, variacionCantidad2, variacionMonto2, mes3, cantidadAutos3,
                    cantidadAutos3, variacionCantidad3, variacionMonto3);

            comparaciones.add(mesAComparar);
        }
        return comparaciones;
    }



    public double calcularVariacion(int valorAnterior, int valorActual) {
        if (valorAnterior == 0) {
            return valorActual == 0 ? 0.0 : valorActual * 100.0;
        } else if (valorActual == 0) {
            return -valorAnterior * 100.0;
        }
        return ((double) (valorActual - valorAnterior) / valorAnterior) * 100.0;
    }


/*
    public List<ReporteCompararMeses> reporteCompararMeses(int numMes, int ano) {
        List<ReporteCompararMeses> comparaciones = new ArrayList<>();

        int mes1, cantidadAutos1, monto1;
        int mes2, cantidadAutos2, monto2;
        int mes3, cantidadAutos3, monto3;
        String nombRep;

        double variacionCantidad3;
        double variacionMonto3;
        double variacionCantidad2;
        double variacionMonto2;


        // Obtener los datos y agregarlos a la lista
        for (int tipoReparacion = 1; tipoReparacion <= 11; tipoReparacion++) {
            nombRep = getNombreReparacion(tipoReparacion);

            // Mes 1
            //(condición) ? si es true hace esto : si es false hace esto
            mes1 = (numMes - 2 > 0) ? numMes - 2 : 12 + (numMes - 2);
            int ano1 = (numMes - 2 > 0) ? ano : ano - 1;
            cantidadAutos1 = getCantidadTotalAutos(tipoReparacion, mes1, ano1);
            monto1 = getMontoTotalAutos(tipoReparacion, mes1, ano1);

            // Mes 2
            mes2 = (numMes - 1 > 0) ? numMes - 1 : 12 + (numMes - 1);
            int ano2 = (numMes - 1 > 0) ? ano : ano - 1;
            cantidadAutos2 = getCantidadTotalAutos(tipoReparacion, mes2, ano2);
            monto2 = getMontoTotalAutos(tipoReparacion, mes2, ano2);
            variacionCantidad2 = calcularVariacion(cantidadAutos1, cantidadAutos2);
            variacionMonto2 = calcularVariacion(monto1, monto2);

            // Mes 3
            mes3 = numMes;
            cantidadAutos3 = getCantidadTotalAutos(tipoReparacion, mes3, ano);
            monto3 = getMontoTotalAutos(tipoReparacion, mes3, ano);
            variacionCantidad3 = calcularVariacion(cantidadAutos2, cantidadAutos3);
            variacionMonto3 = calcularVariacion(monto2, monto3);

            // Crear objeto CompararMeses y agregarlo a la lista
            ReporteCompararMeses mesAComparar = new ReporteCompararMeses(nombRep, mes1, cantidadAutos1, cantidadAutos1, mes2,
                    cantidadAutos2, cantidadAutos2, variacionCantidad2, variacionMonto2, mes3, cantidadAutos3,
                    cantidadAutos3, variacionCantidad3, variacionMonto3);

            comparaciones.add(mesAComparar);
        }
        return comparaciones;
    }

 */


}
