package com.example.reparacionesvehiculosservice.Service;

import com.example.reparacionesvehiculosservice.Entity.HistorialEntity;
import com.example.reparacionesvehiculosservice.Entity.ReparacionEntity;
import com.example.reparacionesvehiculosservice.Model.AutomovilEntity;
import com.example.reparacionesvehiculosservice.Model.ValorReparacionEntity;
import com.example.reparacionesvehiculosservice.Repository.HistorialRepository;
import jakarta.ws.rs.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Service
public class HistorialService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    HistorialRepository historialRepository;
    @Autowired
    CostManagerService costManagerService;
    @Autowired
    ReparacionService reparacionService;

    public ArrayList<HistorialEntity> getHistorialReparaciones() {
        return (ArrayList<HistorialEntity>) historialRepository.findAll();
    }

    public HistorialEntity saveHistorialReparaciones(HistorialEntity historialReparaciones) {
        return historialRepository.save(historialReparaciones);
    }

    public List<HistorialEntity> getHistorialReparacionesByPatente(String patente) {
        return historialRepository.findByPatente(patente);
    }

    public HistorialEntity getHistorialReparacionesNoPagadasByPatente(String patente) {
        return historialRepository.findByPatenteAndAndPagadoIsFalse(patente);
    }

    public HistorialEntity getHistorialAutoByPatente(String patente){
        return historialRepository.findHistorialByPatente(patente);
    }
/*
    public HistorialEntity getHistorialAutoById(Long id){
        return historialRepository.findById(id).get();
    }

 */


    public Optional<HistorialEntity> getHistorialAutoById(Long id) {
        // Utiliza el método findById del repositorio
        return historialRepository.findById(id);
    }




    public HistorialEntity updateHistorial(HistorialEntity historial) {
        return historialRepository.save(historial);
    }

    public boolean deleteHistorial(Long id) throws Exception {
        try{
            historialRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }


    /////////////////////////////////////COMUNICACIÓN CON VALOR REPARACIONES/////////////////////////////////////

    //http://localhost:8081/valorReparacion/montos/Hibrido
    public List<ValorReparacionEntity> getMontosByTipoMotor(String tipoMotor) {
        // Utiliza el nombre lógico del servicio registrado en Eureka
        String url = "http://valor-reparaciones-service/valorReparacion/montos/" + tipoMotor;

        // Realiza la solicitud utilizando RestTemplate
        List<ValorReparacionEntity> valores = restTemplate.getForObject(url, List.class);
        return valores;
    }


    public List<ValorReparacionEntity> getMojjjntosByTipoMotor(String tipoMotor) {
        // Utiliza el nombre lógico del servicio registrado en Eureka
        String url = "http://valor-reparaciones-service/valorReparacion/montos/" + tipoMotor;

        // Realiza la solicitud utilizando RestTemplate
        ValorReparacionEntity[] valoresArray = restTemplate.getForObject(url, ValorReparacionEntity[].class);

        // Convierte el array en una lista
        return Arrays.asList(valoresArray);
    }


    public List<ValorReparacionEntity> getMontosByPatente(String patente){
        AutomovilEntity automovil = reparacionService.getAutomovilByPatente(patente);
        List<ValorReparacionEntity> montos = getMontosByTipoMotor(automovil.getMotor());
        return montos;
    }



    //Función modificada para que calcule el monto total a pagar de un auto en particular, en un historial ya creado
    public Boolean calcularMontoTotalPagar(String patente) {
        double montoTotalReparaciones = 0;

        //Buscar historiales por patente
        List<HistorialEntity> historiales = historialRepository.findByPatente(patente);

        //Buscar automovil por patente
        AutomovilEntity automovil = reparacionService.getAutomovilByPatente(patente);
        String tipoMotor = automovil.getMotor();

        // Buscar el historial existente por patente que esté sin pagar
        HistorialEntity historial = historialRepository.findByPatenteAndAndPagadoIsFalse(patente);
        long idhistorial = historial.getId();
        List<ReparacionEntity> reparaciones = reparacionService.getReparacionByIdHistorialReparaciones(idhistorial);

        //Calculo del monto de reparaciones, sin descuentos, recargos ni iva
        for (ReparacionEntity reparacion : reparaciones) {
            montoTotalReparaciones += reparacionService.getMonto(reparacion.getTipoReparacion(), tipoMotor);//////////////////////////////////////////////////////////////////
        }

        //Descuentos
        double descuentoDia = (costManagerService.getPorcentajeDescuentoDia(historial.getFechaIngresoTaller(), historial.getHoraIngresoTaller()) * montoTotalReparaciones);
        double descuentoCantidadReparaciones = (costManagerService.getDescuentoCantidadReparaciones(automovil,encontrarReparacionesPorFecha(historiales)))* montoTotalReparaciones;
        int descuentoPorBonos = costManagerService.getMontoDescuentoBonos(automovil);
        double descuentos = descuentoDia + descuentoCantidadReparaciones + descuentoPorBonos;

        //Recargos
        //historial.setRecargos(recargoAntiguedad);
        double recargoKilometraje = costManagerService.getPorcentajeRecargoKilometraje(automovil) * montoTotalReparaciones;
        double recargoAntiguedad = costManagerService.getPorcentajeRecargoAntiguedad(automovil) * montoTotalReparaciones;
        double recargoRetraso = costManagerService.getPorcentajeRecargoRetraso(historial) * montoTotalReparaciones;
        double recargos = recargoAntiguedad + recargoKilometraje + recargoRetraso;

        double montoTotalPagar = montoTotalReparaciones + recargos - descuentos;
        double iva = montoTotalPagar * 0.19; //iva de 19%

        //Setters de nuevos valores
        historial.setRecargos(recargos);
        historial.setDescuentos(descuentos);
        historial.setIva(iva);
        historial.setMontoTotalReparaciones(montoTotalReparaciones);
        //historial.setMontoTotalPagar((montoTotalReparaciones + recargos - descuentos) + iva);
        historial.setMontoSinIva(montoTotalPagar - iva);
        historial.setMontoTotalPagar(montoTotalPagar);
        //historial.setPagado(true); //está extra, pero por si las moscas

        // Guardar o actualizar el historial en la base de datos
        historialRepository.save(historial);
        return true;
    }

    public Boolean pagarHistorial(String patente) {
        HistorialEntity historial = getHistorialReparacionesNoPagadasByPatente(patente);
        if (historial != null && historial.getMontoTotalPagar() > 0 && !historial.isPagado()) { //No se porque, pero no funciona !historial.isPagado()
            historial.setPagado(true);
            historialRepository.save(historial);
            return true;
        }
        return false;
    }






    public int getCantidadTipoReparaciones(int tipoReparacion) {
        List<String> tiposAutomovil = new ArrayList<>(); // Utilizamos una lista en lugar de un Set

        List<ReparacionEntity> reparaciones = reparacionService.getReparaciones();
        for (ReparacionEntity reparacion : reparaciones) {
            if (reparacion.getTipoReparacion() == tipoReparacion) {
                String patente = reparacion.getPatente();
                AutomovilEntity automovil = reparacionService.getAutomovilByPatente(patente);
                String tipoAutomovil = automovil.getTipo();

                // Si el tipo de automóvil no está en la lista, lo agregamos
                if (!tiposAutomovil.contains(tipoAutomovil)) {
                    tiposAutomovil.add(tipoAutomovil);
                }
            }
        }

        return tiposAutomovil.size();
    }



    public int getMontoTipoReparaciones(int tipoReparacion) {
        List<String> tiposAutomovil = new ArrayList<>(); // Utilizamos una lista en lugar de un Set
        List<String> tiposMotor = new ArrayList<>();

        List<ReparacionEntity> reparaciones = reparacionService.getReparaciones();
        for (ReparacionEntity reparacion : reparaciones) {
            if (reparacion.getTipoReparacion() == tipoReparacion) {
                String patente = reparacion.getPatente();
                AutomovilEntity automovil = reparacionService.getAutomovilByPatente(patente);
                String tipoAutomovil = automovil.getTipo();

                // Si el tipo de automóvil no está en la lista, lo agregamos
                if (!tiposAutomovil.contains(tipoAutomovil)) {
                    tiposAutomovil.add(tipoAutomovil);
                    tiposMotor.add(automovil.getMotor());
                }
            }
        }
        int sumaMontos = 0;
        for(String tipoMotor : tiposMotor){
            sumaMontos += reparacionService.getMonto(tipoReparacion, tipoMotor);
        }
        return sumaMontos;
    }

/*
    public List<ReparacionesvsTipoAutos> reporteReparacionesvsTipoAutos(){
        List<ReparacionesvsTipoAutos> reparacionesvsTipoAutos = new ArrayList<>();

        int cantidadReparaciones = 0;
        int montoTotalReparaciones = 0;
        String nombreReparacion = null;

        // Obtener los datos y agregarlos a la lista
        for(int tipoReparacion = 1; tipoReparacion <= 11; tipoReparacion++){
            cantidadReparaciones = getCantidadTipoReparaciones(tipoReparacion);
            montoTotalReparaciones = getMontoTipoReparaciones(tipoReparacion);
            if(tipoReparacion == 1) {
                nombreReparacion = "Reparaciones del Sistema de Frenos";
            }else if(tipoReparacion == 2){
                nombreReparacion = "Servicio del Sistema de Refrigeración";
            }else if(tipoReparacion == 3){
                nombreReparacion = "Reparaciones del Motor";
            }else if(tipoReparacion == 4){
                nombreReparacion = "Reparaciones de la Transmisión";
            }else if(tipoReparacion == 5){
                nombreReparacion = "Reparación del Sistema Eléctrico";
            }else if(tipoReparacion == 6){
                nombreReparacion = "Reparaciones del Sistema de Escape";
            }else if(tipoReparacion == 7){
                nombreReparacion = "Reparación de Neumáticos y Ruedas";
            }else if(tipoReparacion == 8){
                nombreReparacion = "Reparaciones de la Suspensión y la Dirección";
            }else if(tipoReparacion == 9){
                nombreReparacion = "Reparación del Sistema de Aire Acondicionado y Calefacción";
            }else if(tipoReparacion == 10){
                nombreReparacion = "Reparaciones del Sistema de Combustible";
            }else if(tipoReparacion == 11){
                nombreReparacion = "Reparación y Reemplazo del Parabrisas y Cristales";
            }

            // Crear objeto ReparacionesvsTipoAutos y agregarlo a la lista
            ReparacionesvsTipoAutos reparacionPorTipoAuto = new ReparacionesvsTipoAutos(nombreReparacion, cantidadReparaciones, montoTotalReparaciones);
            reparacionesvsTipoAutos.add(reparacionPorTipoAuto);
        }

        // Ordenar la lista por montoTotalReparaciones de mayor a menor
        Collections.sort(reparacionesvsTipoAutos, Comparator.comparingInt(ReparacionesvsTipoAutos::getMontoTotalReparaciones).reversed());

        return reparacionesvsTipoAutos;
    }


    public int getCantidadTipoMotor(int tipoReparacion, String tipoMotor) {
        int cantidad = 0;

        List<ReparacionEntity> reparaciones = reparacionService.getReparaciones();
        for (ReparacionEntity reparacion : reparaciones) {
            if (reparacion.getTipoReparacion() == tipoReparacion) {
                AutomovilEntity automovil = automovilService.getAutomovilByPatente(reparacion.getPatente());
                if (automovil != null && automovil.getMotor() != null && automovil.getMotor().equals(tipoMotor)) {
                    cantidad++;
                }
            }
        }

        return cantidad;
    }


    public List<ReparacionesvsTipoMotor> reporteReparacionesvsTipoMotor(){
        List<ReparacionesvsTipoMotor> reparacionesvsTipoMotores = new ArrayList<>();
        String nombreReparacion = null;

        // Obtener los datos y agregarlos a la lista
        for(int tipoReparacion = 1; tipoReparacion <= 11; tipoReparacion++){
            int cantGasolina = getCantidadTipoMotor(tipoReparacion, "Gasolina");
            int montoGasolina = valorReparacionesService.getMonto(tipoReparacion, "Gasolina") * cantGasolina;
            int cantDiesel = getCantidadTipoMotor(tipoReparacion, "Diesel");
            int montoDiesel = valorReparacionesService.getMonto(tipoReparacion, "Diesel") * cantDiesel;
            int cantHibrido = getCantidadTipoMotor(tipoReparacion, "Hibrido");
            int montoHibrido = valorReparacionesService.getMonto(tipoReparacion, "Hibrido") * cantHibrido;
            int cantElectrico = getCantidadTipoMotor(tipoReparacion, "Electrico");
            int montoElectrico = valorReparacionesService.getMonto(tipoReparacion, "Electrico") * cantElectrico;
            int  montoTotal = montoGasolina + montoDiesel + montoHibrido + montoElectrico;
            if(tipoReparacion == 1) {
                nombreReparacion = "Reparaciones del Sistema de Frenos";
            }else if(tipoReparacion == 2){
                nombreReparacion = "Servicio del Sistema de Refrigeración";
            }else if(tipoReparacion == 3){
                nombreReparacion = "Reparaciones del Motor";
            }else if(tipoReparacion == 4){
                nombreReparacion = "Reparaciones de la Transmisión:";
            }else if(tipoReparacion == 5){
                nombreReparacion = "Reparación del Sistema Eléctrico";
            }else if(tipoReparacion == 6){
                nombreReparacion = "Reparaciones del Sistema de Escape";
            }else if(tipoReparacion == 7){
                nombreReparacion = "Reparación de Neumáticos y Ruedas";
            }else if(tipoReparacion == 8){
                nombreReparacion = "Reparaciones de la Suspensión y la Dirección";
            }else if(tipoReparacion == 9){
                nombreReparacion = "Reparación del Sistema de Aire Acondicionado y Calefacción";
            }else if(tipoReparacion == 10){
                nombreReparacion = "Reparaciones del Sistema de Combustible";
            }else if(tipoReparacion == 11){
                nombreReparacion = "Reparación y Reemplazo del Parabrisas y Cristales";
            }

            // Crear objeto ReparacionesvsTipoMotor y agregarlo a la lista
            ReparacionesvsTipoMotor reparacionPorTipoMotor = new ReparacionesvsTipoMotor(nombreReparacion, cantGasolina, cantDiesel, cantHibrido, cantElectrico, montoTotal);
            reparacionesvsTipoMotores.add(reparacionPorTipoMotor);
        }

        // Ordenar la lista por montoTotalReparaciones de mayor a menor
        Collections.sort(reparacionesvsTipoMotores, Comparator.comparingInt(ReparacionesvsTipoMotor::getMontoTotalReparaciones).reversed());

        return reparacionesvsTipoMotores;
    }



    public List<TiemposPromedio> reporteTiempoPromedioReparacion() {
        List<HistorialEntity> historiales = getHistorialReparaciones();
        List<TiemposPromedio> tiemposPromedio = new ArrayList<>();

        // Obtener la lista de patentes que hay en el historial
        Set<String> patentes = new HashSet<>();
        for (HistorialEntity historial : historiales) {
            patentes.add(historial.getPatente());
        }

        // Obtener los autos por su patente
        for (String patente : patentes) {
            List<HistorialEntity> historialesPorPatente = new ArrayList<>();
            for (HistorialEntity historial : historiales) {
                if (historial.getPatente().equals(patente)) {
                    historialesPorPatente.add(historial);
                }
            }
            double tiempoPromedio = calcularTiempoPromedioReparacion(historialesPorPatente);
            AutomovilEntity automovil = automovilService.getAutomovilByPatente(patente);
            if (automovil != null) {
                TiemposPromedio tiempoPromedioObj = new TiemposPromedio(automovil.getMarca(), tiempoPromedio);
                tiemposPromedio.add(tiempoPromedioObj);
            }
        }
        // Ordenar la lista por el tiempo promedio de reparación de menor a mayor
        Collections.sort(tiemposPromedio, Comparator.comparingDouble(TiemposPromedio::getTiempoEnDias));


        return tiemposPromedio;
    }

     */


    public int encontrarReparacionesPorFecha(List<HistorialEntity> historialReparaciones){
        int cantidad = 0;
        //Fecha actual
        LocalDate fechaActual = LocalDate.now();
        //La fecha actual, pero hace 1 año
        LocalDate fechaHace12Meses = fechaActual.minus(12, ChronoUnit.MONTHS);

        for (HistorialEntity historialReparacion : historialReparaciones){
            if((historialReparacion.getFechaIngresoTaller()).isAfter(fechaHace12Meses) || (historialReparacion.getFechaIngresoTaller()).isEqual(fechaHace12Meses)){
                cantidad += reparacionService.contarReparacionesPorHistorial(historialReparacion.getId());
            }
        }
        return cantidad;
    }

    private int calcularTiempoReparacion(HistorialEntity historial) {
        LocalDate fechaIngreso = historial.getFechaIngresoTaller();
        LocalTime horaIngreso = historial.getHoraIngresoTaller();
        LocalDate fechaSalida = historial.getFechaSalidaTaller();
        LocalTime horaSalida = historial.getHoraSalidaTaller();

        long diasReparacion = ChronoUnit.DAYS.between(fechaIngreso.atTime(horaIngreso), fechaSalida.atTime(horaSalida));
        return (int) diasReparacion; // Convertimos de long a int ya que el tiempo promedio probablemente será un entero
    }


    private double calcularTiempoPromedioReparacion(List<HistorialEntity> historiales) {
        long totalHorasReparacion = 0;
        int cantidadHistoriales = historiales.size();

        for (HistorialEntity historial : historiales) {
            LocalDateTime fechaHoraIngreso = LocalDateTime.of(historial.getFechaIngresoTaller(), historial.getHoraIngresoTaller());
            LocalDateTime fechaHoraSalida = LocalDateTime.of(historial.getFechaSalidaTaller(), historial.getHoraSalidaTaller());

            Duration duracionReparacion = Duration.between(fechaHoraIngreso, fechaHoraSalida);
            totalHorasReparacion += duracionReparacion.toHours();
        }

        if (cantidadHistoriales != 0) {
            return (double) totalHorasReparacion / cantidadHistoriales / 24; // Convertir horas a días
        } else {
            return 0;
        }
    }




///////////////////////////////////////////PARA REPORTES PEP 2///////////////////////////////////////////

    public int getCantidadTipoReparacioneByTipoAutomovil(int tipoReparacion, String tipoAuto, int numMes, int ano) {
        int cantidad = 0;
        Month[] meses = Month.values(); // Obtener todos los meses como un array
        Month mes = meses[numMes - 1]; // Obtener el mes correspondiente al número (restamos 1 porque los arrays comienzan en 0)

        //Se obtienen todas las reparaciones, este se puede modificar después
        List<ReparacionEntity> reparaciones = reparacionService.getReparaciones();
        for (ReparacionEntity reparacion : reparaciones) {
            if (reparacion.getTipoReparacion() == tipoReparacion) {
                String patente = reparacion.getPatente();
                AutomovilEntity automovil = reparacionService.getAutomovilByPatente(patente);
                Long idHistorial = reparacion.getIdHistorialReparaciones();

                // Usamos Optional para manejar la posible ausencia de historialAuto
                Optional<HistorialEntity> optionalHistorialAuto = getHistorialAutoById(idHistorial);

                // Verificamos si existe un historial de reparaciones para este ID
                if (optionalHistorialAuto.isPresent()) {
                    HistorialEntity historialAuto = optionalHistorialAuto.get();

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





    public int getMontoTipoReparacionByTipoAutomovil(int tipoReparacion, String tipoAuto, int numMes, int ano) {
        Month[] meses = Month.values(); // Obtener todos los meses como un array
        Month mes = meses[numMes - 1]; // Obtener el mes correspondiente al número (restamos 1 porque los arrays comienzan en 0)

        List<String> tiposMotor = new ArrayList<>();

        List<ReparacionEntity> reparaciones = reparacionService.getReparaciones();
        for (ReparacionEntity reparacion : reparaciones) {
            if (reparacion.getTipoReparacion() == tipoReparacion) {
                String patente = reparacion.getPatente();
                AutomovilEntity automovil = reparacionService.getAutomovilByPatente(patente);
                Long idHistorial = reparacion.getIdHistorialReparaciones();

                // Usamos Optional para manejar la posible ausencia de historialAuto
                Optional<HistorialEntity> optionalHistorialAuto = getHistorialAutoById(idHistorial);

                // Verificamos si existe un historial de reparaciones para este ID
                if (optionalHistorialAuto.isPresent()) {
                    HistorialEntity historialAuto = optionalHistorialAuto.get();

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
            sumaMontos += reparacionService.getMonto(tipoReparacion, tipoMotor);
        }
        return sumaMontos;
    }







}
