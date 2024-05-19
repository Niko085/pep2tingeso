package com.example.Gestor.de.reparaciones.services;

import com.example.Gestor.de.reparaciones.dtos.ReparacionesvsTipoAutos;
import com.example.Gestor.de.reparaciones.dtos.ReparacionesvsTipoMotor;
import com.example.Gestor.de.reparaciones.dtos.TiemposPromedio;
import com.example.Gestor.de.reparaciones.entities.AutomovilEntity;
import com.example.Gestor.de.reparaciones.entities.HistorialReparacionesEntity;
import com.example.Gestor.de.reparaciones.entities.ReparacionEntity;
import com.example.Gestor.de.reparaciones.entities.ValorReparacionesEntity;
import com.example.Gestor.de.reparaciones.repositories.HistorialReparacionesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class HistorialReparacionesService {
    @Autowired
    HistorialReparacionesRepository historialReparacionesRepository;
    @Autowired
    AutomovilService automovilService;
    @Autowired
    OfficeHRMService officeHRMService;
    @Autowired
    ReparacionService reparacionService;
    @Autowired
    ValorReparacionesService valorReparacionesService;

    public ArrayList<HistorialReparacionesEntity> getHistorialReparaciones() {
        return (ArrayList<HistorialReparacionesEntity>) historialReparacionesRepository.findAll();
    }

    public HistorialReparacionesEntity saveHistorialReparaciones(HistorialReparacionesEntity historialReparaciones) {
        return historialReparacionesRepository.save(historialReparaciones);
    }



    public List<HistorialReparacionesEntity> getHistorialReparacionesByPatente(String patente) {
        return historialReparacionesRepository.findByPatente(patente);
    }

    public HistorialReparacionesEntity getHistorialReparacionesNoPagadasByPatente(String patente) {
        return historialReparacionesRepository.findByPatenteAndAndPagadoIsFalse(patente);
    }

    public HistorialReparacionesEntity getHistorialAutoByPatente(String patente){
        return historialReparacionesRepository.findHistorialByPatente(patente);
    }

    public HistorialReparacionesEntity getHistorialAutoById(Long id){
        return historialReparacionesRepository.findById(id).get();
    }


    public HistorialReparacionesEntity updateHistorial(HistorialReparacionesEntity historial) {
        return historialReparacionesRepository.save(historial);
    }

    public boolean deleteHistorial(Long id) throws Exception {
        try{
            historialReparacionesRepository.deleteById(id);
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

    /*
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
     */

    //Deficiencia. La función solo muestra una reparacion por tipo por patente, para solucionarlo hay que buscar
    //una lista de autos en ve de una en partirular, recorrerla y buscarlo
    public int getCantidadTipoReparacioneByTipoAutomovil(int tipoReparacion, String tipoAuto) {
        int cantidad = 0;

        //Se obtienen todas las reparaciones, este se puede modificar después
        List<ReparacionEntity> reparaciones = reparacionService.getReparaciones();
        for (ReparacionEntity reparacion : reparaciones) {
            if (reparacion.getTipoReparacion() == tipoReparacion) {
                String patente = reparacion.getPatente();
                AutomovilEntity automovil = automovilService.getAutomovilByPatente(patente);
                //String tipoAutomovil = automovil.getTipo();

                if(automovil.getTipo().equals(tipoAuto)){
                    cantidad++;
                }

            }
        }
        return cantidad;
    }


/*
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
 */

    //Comprobarrrrrrrrrrrrr
    public int getMontoTipoReparacionByTipoAutomovil(int tipoReparacion, String tipoAuto) {
        List<String> tiposAutomovil = new ArrayList<>();
        List<String> tiposMotor = new ArrayList<>();

        List<ReparacionEntity> reparaciones = reparacionService.getReparaciones();
        for (ReparacionEntity reparacion : reparaciones) {
            if (reparacion.getTipoReparacion() == tipoReparacion) {
                String patente = reparacion.getPatente();
                AutomovilEntity automovil = automovilService.getAutomovilByPatente(patente);
                //String tipoAutomovil = automovil.getTipo();
                if(automovil.getTipo().equals(tipoAuto)){
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

        int cantSed = 0; //cantidad de reparaciones Sedan
        int montSed = 0; //monto total Sedan
        int cantHatch = 0;
        int montHatch = 0;
        int cantSuv = 0;
        int montSuv = 0;
        int cantPick = 0;
        int montPick = 0;
        int cantFurg = 0;
        int montFurg = 0;
        int totReparac = 0; //monto total de reparaciones

        String nombRep = null;

        // Obtener los datos y agregarlos a la lista
        for(int tipoReparacion = 1; tipoReparacion <= 11; tipoReparacion++){
            cantSed = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Sedan");
            cantHatch = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Hatchback");
            cantSuv = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Suv");
            cantPick = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Pickup");
            cantFurg = getCantidadTipoReparacioneByTipoAutomovil(tipoReparacion, "Furgoneta");

            montSed = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Sedan");
            montHatch = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Hatchback");
            montSuv = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Suv");
            montPick = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Pickup");
            montFurg = getMontoTipoReparacionByTipoAutomovil(tipoReparacion, "Furgoneta");

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
            ReparacionesvsTipoAutos reparacionPorTipoAuto = new ReparacionesvsTipoAutos(nombRep,cantSed,montSed,
                    cantHatch,montHatch,cantSuv,montSuv,cantPick,montPick,cantFurg,montFurg,totReparac);
            
            reparacionesvsTipoAutos.add(reparacionPorTipoAuto);
        }

        // Ordenar la lista por montoTotalReparaciones de mayor a menor
        //Collections.sort(reparacionesvsTipoAutos, Comparator.comparingInt(ReparacionesvsTipoAutos::getMontoTotalReparaciones).reversed());

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
