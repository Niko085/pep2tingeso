package com.example.reportesservice.Services;

import com.example.reportesservice.Entities.ReporteCompararMeses;
import com.example.reportesservice.Entities.ReporteTipoRepVsTipoAuto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
public class ReportesService {
    @Autowired
    RestTemplate restTemplate;

    /////////////////////////////////////COMUNICACIÓN CON HISTORIAL/////////////////////////////////////

    public int getMontoTipoReparacionByTipoAutomovil(int tipoReparacion, String tipoAuto, int numMes, int ano) {
        // Utiliza el nombre lógico del servicio registrado en Eureka
        String url = "http://reparaciones-vehiculos-service/historial-reparaciones/montoTipoReparacionByTipoAutomovil/"+tipoReparacion+"/"+tipoAuto+"/"+numMes+"/"+ano;
        // Realiza la solicitud utilizando RestTemplate
        int monto = restTemplate.getForObject(url, Integer.class);
        return monto;
    }

    public int getCantidadTipoReparacioneByTipoAutomovil(int tipoReparacion, String tipoAuto, int numMes, int ano) {
        // Utiliza el nombre lógico del servicio registrado en Eureka
        String url = "http://reparaciones-vehiculos-service/historial-reparaciones/cantidadTipoReparacioneByTipoAutomovil/"+tipoReparacion+"/"+tipoAuto+"/"+numMes+"/"+ano;
        // Realiza la solicitud utilizando RestTemplate
        int cantidad = restTemplate.getForObject(url, Integer.class);
        return cantidad;
    }
    //////////////////////////////////COMUNICACIÓN CON VALOR REPARACION/////////////////////////////////
    /////////////////////////////////////COMUNICACIÓN CON AUTOMOVIL/////////////////////////////////////

/*
    public int getCantidadTipoReparacioneByTipoAutomovil(int tipoReparacion, String tipoAuto, int numMes, int ano) {
        int cantidad = 0;
        Month[] meses = Month.values(); // Obtener todos los meses como un array
        Month mes = meses[numMes - 1]; // Obtener el mes correspondiente al número (restamos 1 porque los arrays comienzan en 0)

        //Se obtienen todas las reparaciones, este se puede modificar después
        List<ReparacionEntity> reparaciones = reparacionService.getReparaciones();
        for (ReparacionEntity reparacion : reparaciones) {
            if (reparacion.getTipoReparacion() == tipoReparacion) {
                String patente = reparacion.getPatente();
                AutomovilEntity automovil = automovilService.getAutomovilByPatente(patente);
                Long idHistorial = reparacion.getIdHistorialReparaciones();

                // Usamos Optional para manejar la posible ausencia de historialAuto
                Optional<HistorialReparacionesEntity> optionalHistorialAuto = getHistorialAutoById(idHistorial);

                // Verificamos si existe un historial de reparaciones para este ID
                if (optionalHistorialAuto.isPresent()) {
                    HistorialReparacionesEntity historialAuto = optionalHistorialAuto.get();

                    // Comprobamos si la fecha de ingreso al taller está en el mes y año deseados
                    if (automovil.getTipo().equals(tipoAuto)
                            && historialAuto.getFechaIngresoTaller().getMonth() == mes
                            && historialAuto.getFechaIngresoTaller().getYear() == ano) {
                        cantidad++;
                    }
                }
            }
        }
        return cantidad;
    }



 */


/*
    public int getMontoTipoReparacionByTipoAutomovil(int tipoReparacion, String tipoAuto, int numMes, int ano) {
        Month[] meses = Month.values(); // Obtener todos los meses como un array
        Month mes = meses[numMes - 1]; // Obtener el mes correspondiente al número (restamos 1 porque los arrays comienzan en 0)

        List<String> tiposMotor = new ArrayList<>();

        List<ReparacionEntity> reparaciones = reparacionService.getReparaciones();
        for (ReparacionEntity reparacion : reparaciones) {
            if (reparacion.getTipoReparacion() == tipoReparacion) {
                String patente = reparacion.getPatente();
                AutomovilEntity automovil = automovilService.getAutomovilByPatente(patente);
                Long idHistorial = reparacion.getIdHistorialReparaciones();

                // Usamos Optional para manejar la posible ausencia de historialAuto
                Optional<HistorialReparacionesEntity> optionalHistorialAuto = getHistorialAutoById(idHistorial);

                // Verificamos si existe un historial de reparaciones para este ID
                if (optionalHistorialAuto.isPresent()) {
                    HistorialReparacionesEntity historialAuto = optionalHistorialAuto.get();

                    // Comprobamos si la fecha de ingreso al taller está en el mes y año deseados
                    if (automovil.getTipo().equals(tipoAuto)
                            && historialAuto.getFechaIngresoTaller().getMonth() == mes
                            && historialAuto.getFechaIngresoTaller().getYear() == ano) {
                        tiposMotor.add(automovil.getMotor());
                    }
                }
            }
        }

        int sumaMontos = 0;
        for (String tipoMotor : tiposMotor) {
            sumaMontos += valorReparacionesService.getMonto(tipoReparacion, tipoMotor);
        }
        return sumaMontos;
    }


 */





    public List<ReporteTipoRepVsTipoAuto> reporteReparacionesvsTipoAutos(int numMes, int ano){
        List<ReporteTipoRepVsTipoAuto> reparacionesvsTipoAutos = new ArrayList<>();

        //Se inicializan las cantidades
        int cantSed,cantHatch,cantSuv,cantPick,cantFurg;
        //Se inicializan los montos
        int montSed,montHatch,montSuv,montPick,montFurg;

        int totReparac; //monto total de reparaciones

        String nombRep = null;

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

            //cantidadReparaciones = getCantidadTipoReparaciones(tipoReparacion);
            //montoTotalReparaciones = getMontoTipoReparaciones(tipoReparacion);
            if(tipoReparacion == 1) {
                nombRep = "Reparaciones del Sistema de Frenos";
            }else if(tipoReparacion == 2){
                nombRep = "Servicio del Sistema de Refrigeración";
            }else if(tipoReparacion == 3){
                nombRep = "Reparaciones del Motor";
            }else if(tipoReparacion == 4){
                nombRep = "Reparaciones de la Transmisión";
            }else if(tipoReparacion == 5){
                nombRep = "Reparación del Sistema Eléctrico";
            }else if(tipoReparacion == 6){
                nombRep = "Reparaciones del Sistema de Escape";
            }else if(tipoReparacion == 7){
                nombRep = "Reparación de Neumáticos y Ruedas";
            }else if(tipoReparacion == 8){
                nombRep = "Reparaciones de la Suspensión y la Dirección";
            }else if(tipoReparacion == 9){
                nombRep = "Reparación del Sistema de Aire Acondicionado y Calefacción";
            }else if(tipoReparacion == 10){
                nombRep = "Reparaciones del Sistema de Combustible";
            }else if(tipoReparacion == 11){
                nombRep = "Reparación y Reemplazo del Parabrisas y Cristales";
            }

            totReparac = ((montSed * cantSed) + (montHatch * cantHatch) +
                    (montSuv  * cantSuv) + (montPick * cantPick) + (montFurg * cantFurg));

            // Crear objeto ReparacionesvsTipoAutos y agregarlo a la lista
            ReporteTipoRepVsTipoAuto reparacionPorTipoAuto = new ReporteTipoRepVsTipoAuto(nombRep,cantSed,montSed,
                    cantHatch,montHatch,cantSuv,montSuv,cantPick,montPick,cantFurg,montFurg,totReparac);

            reparacionesvsTipoAutos.add(reparacionPorTipoAuto);
        }


        return reparacionesvsTipoAutos;
    }


























    public int getCantidadTotalAutos(int tipoReparacion, int numMes, int ano){
        int cantAutos,cantSed,cantHatch,cantSuv,cantPick,cantFurg;
        cantSed = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Sedan",numMes, ano);
        cantHatch = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Hatchback",numMes, ano);
        cantSuv = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Suv",numMes, ano);
        cantPick = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Pickup",numMes, ano);
        cantFurg = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Furgoneta",numMes, ano);
        cantAutos = cantSed + cantHatch + cantSuv + cantPick + cantFurg;
        return cantAutos;
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


    public double calcularVariacion(int valorAnterior, int valorActual) {
        if (valorAnterior == 0) {
            return valorActual == 0 ? 0.0 : 100.0;
        }
        return ((double) (valorActual - valorAnterior) / valorAnterior) * 100.0;
    }
}
