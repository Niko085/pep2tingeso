package com.example.reparacionesvehiculosservice.Service;

import com.example.reparacionesvehiculosservice.Entity.HistorialEntity;
import com.example.reparacionesvehiculosservice.Repository.HistorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HistorialService {
    @Autowired
    HistorialRepository historialRepository;
    @Autowired
    CostManagerService costManagerService;
    @Autowired
    ReparacionService reparacionService;
    //@Autowired
    //AutomovilService automovilService;
    //@Autowired
    //ValorReparacionService valorReparacionService;

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

    public HistorialEntity getHistorialAutoById(Long id){
        return historialRepository.findById(id).get();
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


    //Función modificada para que calcule el monto total a pagar de un auto en particular, en un historial ya creado
    public Boolean calcularMontoTotalPagar(String patente) {
        double montoTotal = 0;

        //Buscar historiales por patente
        List<HistorialReparacionesEntity> historiales = historialReparacionesRepository.findByPatente(patente);

        //Buscar automovil por patente
        AutomovilEntity automovil = automovilService.getAutomovilByPatente(patente);
        String tipoMotor = automovil.getMotor();

        // Buscar el historial existente por patente que esté sin pagar
        HistorialReparacionesEntity historial = historialReparacionesRepository.findByPatenteAndAndPagadoIsFalse(patente);
        long idhistorial = historial.getId();
        List<ReparacionEntity> reparaciones = reparacionService.getReparacionByIdHistorialReparaciones(idhistorial);

        //Calculo del monto de reparaciones, sin descuentos, recargos ni iva
        for (ReparacionEntity reparacion : reparaciones) {
            montoTotal += valorReparacionesService.getMonto(reparacion.getTipoReparacion(), tipoMotor);
        }


        //Descuentos
        double descuentoDia = (officeHRMService.getPorcentajeDescuentoDia(historial.getFechaIngresoTaller(), historial.getHoraIngresoTaller()) * montoTotal);
        double descuentoCantidadReparaciones = (officeHRMService.getDescuentoCantidadReparaciones(automovil,encontrarReparacionesPorFecha(historiales)))* montoTotal;
        int descuentoPorBonos = officeHRMService.getMontoDescuentoBonos(automovil);
        double descuentos = descuentoDia + descuentoCantidadReparaciones + descuentoPorBonos;


        //Recargos
        //historial.setRecargos(recargoAntiguedad);
        double recargoKilometraje = officeHRMService.getPorcentajeRecargoKilometraje(automovil) * montoTotal;
        double recargoAntiguedad = officeHRMService.getPorcentajeRecargoAntiguedad(automovil) * montoTotal;
        double recargoRetraso = officeHRMService.getPorcentajeRecargoRetraso(historial) * montoTotal;
        double recargos = recargoAntiguedad + recargoKilometraje + recargoRetraso;

        double iva = (montoTotal + recargos - descuentos) * 0.19;

        //Setters de nuevos valores
        historial.setRecargos(recargos);
        historial.setDescuentos(descuentos);
        historial.setIva(iva);
        historial.setMontoTotalPagar((montoTotal + recargos - descuentos) + iva);
        historial.setPagado(true); //está extra, pero por si las moscas

        // Guardar o actualizar el historial en la base de datos
        historialReparacionesRepository.save(historial);
        return true;
    }


    public int encontrarReparacionesPorFecha(List<HistorialReparacionesEntity> historialReparaciones){
        int cantidad = 0;
        //Fecha actual
        LocalDate fechaActual = LocalDate.now();
        //La fecha actual, pero hace 1 año
        LocalDate fechaHace12Meses = fechaActual.minus(12, ChronoUnit.MONTHS);

        for (HistorialReparacionesEntity historialReparacion : historialReparaciones){
            if((historialReparacion.getFechaIngresoTaller()).isAfter(fechaHace12Meses) || (historialReparacion.getFechaIngresoTaller()).isEqual(fechaHace12Meses)){
                cantidad += reparacionService.contarReparacionesPorHistorial(historialReparacion.getId());
            }
        }
        return cantidad;
    }

    public int getCantidadTipoReparaciones(int tipoReparacion) {
        List<String> tiposAutomovil = new ArrayList<>(); // Utilizamos una lista en lugar de un Set

        List<ReparacionEntity> reparaciones = reparacionService.getReparaciones();
        for (ReparacionEntity reparacion : reparaciones) {
            if (reparacion.getTipoReparacion() == tipoReparacion) {
                String patente = reparacion.getPatente();
                AutomovilEntity automovil = automovilService.getAutomovilByPatente(patente);
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
                AutomovilEntity automovil = automovilService.getAutomovilByPatente(patente);
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
            sumaMontos += valorReparacionesService.getMonto(tipoReparacion, tipoMotor);
        }
        return sumaMontos;
    }


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


    private int calcularTiempoReparacion(HistorialReparacionesEntity historial) {
        LocalDate fechaIngreso = historial.getFechaIngresoTaller();
        LocalTime horaIngreso = historial.getHoraIngresoTaller();
        LocalDate fechaSalida = historial.getFechaSalidaTaller();
        LocalTime horaSalida = historial.getHoraSalidaTaller();

        long diasReparacion = ChronoUnit.DAYS.between(fechaIngreso.atTime(horaIngreso), fechaSalida.atTime(horaSalida));
        return (int) diasReparacion; // Convertimos de long a int ya que el tiempo promedio probablemente será un entero
    }


    public List<TiemposPromedio> reporteTiempoPromedioReparacion() {
        List<HistorialReparacionesEntity> historiales = getHistorialReparaciones();
        List<TiemposPromedio> tiemposPromedio = new ArrayList<>();

        // Obtener la lista de patentes que hay en el historial
        Set<String> patentes = new HashSet<>();
        for (HistorialReparacionesEntity historial : historiales) {
            patentes.add(historial.getPatente());
        }

        // Obtener los autos por su patente
        for (String patente : patentes) {
            List<HistorialReparacionesEntity> historialesPorPatente = new ArrayList<>();
            for (HistorialReparacionesEntity historial : historiales) {
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

    private double calcularTiempoPromedioReparacion(List<HistorialReparacionesEntity> historiales) {
        long totalHorasReparacion = 0;
        int cantidadHistoriales = historiales.size();

        for (HistorialReparacionesEntity historial : historiales) {
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

}
