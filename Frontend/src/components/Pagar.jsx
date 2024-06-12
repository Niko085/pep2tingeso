import React, { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import DriveEtaIcon from '@mui/icons-material/DriveEta';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';
import historialReparacionesService from "../services/historialReparaciones.service";
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableHead from '@mui/material/TableHead';
import Paper from '@mui/material/Paper';
import TableContainer from '@mui/material/TableContainer'; // Asegúrate de importar TableContainer
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const Pagar = () => {
    const [historialReparaciones, setHistorialReparaciones] = useState([]);
    const [reparaciones, setReparaciones] = useState([]);
    const { patente } = useParams();
    const navigate = useNavigate();

    const handleCalculate = (patente) => {
        console.log("Calculando historial de reparaciones para la patente:", patente);
        axios
          .get(`http://localhost:8081/historialreparaciones/calculate?patente=${patente}`)
          .then(() => {
            console.log("Historial de reparaciones calculado con éxito");
            navigate("/historialReparaciones/list");
          })
          .catch((error) => {
            console.log("Error al calcular historial de reparaciones:", error);
          });
    };

    useEffect(() => {
        const getHistorialReparaciones = async () => {
            try {
                console.log("Buscando historial de reparaciones para la patente:", patente);
                const response = await historialReparacionesService.getNoPagadoByPatente(patente);
                console.log("Respuesta de la solicitud:", response);
                if (response.status === 200) {
                    const data = response.data;
                    console.log("Datos obtenidos:", data);
                    setHistorialReparaciones(Array.isArray(data) ? data : [data]);

                    if (Array.isArray(data)) {
                        // Obtener reparaciones para cada historial
                        const reparacionesPromises = data.map(historial => 
                            axios.get(`http://localhost:8081/historialreparaciones/reparacion/historial/${historial.id}`)
                        );
                        const reparacionesResponses = await Promise.all(reparacionesPromises);
                        const reparacionesData = reparacionesResponses.map(res => res.data).flat();
                        console.log("Datos de las reparaciones obtenidos:", reparacionesData);
                        setReparaciones(reparacionesData);
                    } else {
                        const reparacionesResponse = await axios.get(`http://localhost:8081/historialreparaciones/reparacion/historial/${data.id}`);
                        console.log("Datos de las reparaciones obtenidos:", reparacionesResponse.data);
                        setReparaciones(reparacionesResponse.data);
                    }
                } else {
                    console.error('Error al buscar historial de reparaciones:', response.statusText);
                }
            } catch (error) {
                console.error('Error al buscar historial de reparaciones:', error);
            }
        };
    
        if (patente) {
            getHistorialReparaciones();
        }
    }, [patente]);

    return (
        <div className="container">
            <h1>Retiro del taller</h1>
            {historialReparaciones.length > 0 && (
                <div>
                    <h2>Pagar reparaciones de la patente {patente}</h2>
                    <Paper>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell align="left">Patente</TableCell>
                                    <TableCell align="left">Fecha Ingreso Taller</TableCell>
                                    <TableCell align="left">Hora Ingreso Taller</TableCell>
                                    <TableCell align="right">Monto Total a Pagar</TableCell>
                                    <TableCell align="right">Recargos</TableCell>
                                    <TableCell align="right">Descuentos</TableCell>
                                    <TableCell align="right">IVA</TableCell>
                                    <TableCell align="left">Fecha Salida Taller</TableCell>
                                    <TableCell align="left">Hora Salida Taller</TableCell>
                                    <TableCell align="left">Fecha Retira Vehículo</TableCell>
                                    <TableCell align="left">Hora Retira Vehículo</TableCell>
                                    <TableCell align="left">Pagado</TableCell>
                                    <TableCell align="left">Acciones</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {historialReparaciones.map((historial, index) => (
                                    <TableRow key={index}>
                                        <TableCell align="left">{historial.patente}</TableCell>
                                        <TableCell align="left">{historial.fechaIngresoTaller}</TableCell>
                                        <TableCell align="left">{historial.horaIngresoTaller}</TableCell>
                                        <TableCell align="right">{historial.montoTotalPagar}</TableCell>
                                        <TableCell align="right">{historial.recargos}</TableCell>
                                        <TableCell align="right">{historial.descuentos}</TableCell>
                                        <TableCell align="right">{historial.iva}</TableCell>
                                        <TableCell align="left">{historial.fechaSalidaTaller}</TableCell>
                                        <TableCell align="left">{historial.horaSalidaTaller}</TableCell>
                                        <TableCell align="left">{historial.fechaClienteSeLlevaVehiculo}</TableCell>
                                        <TableCell align="left">{historial.horaClienteSeLlevaVehiculo}</TableCell>
                                        <TableCell align="left">{historial.pagado ? 'Sí' : 'No'}</TableCell>
                                        <TableCell>
                                            {!historial.pagado && (
                                                <Button
                                                    variant="contained"
                                                    color="secondary"
                                                    size="small"
                                                    onClick={() => handleCalculate(historial.patente)}
                                                    style={{ marginLeft: "0.5rem" }}
                                                    startIcon={<AttachMoneyIcon />}
                                                >
                                                    Calcular y pagar
                                                </Button>
                                            )}
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </Paper>
                </div>
            )}
            <TableContainer component={Paper}>
                <br />
                <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
                    <TableHead>
                        <TableRow>
                            <TableCell align="left">Patente</TableCell>
                            <TableCell align="left">N° de reparación</TableCell>
                            <TableCell align="left">Descripción</TableCell>
                            {/*<TableCell align="left">ID Historial Reparaciones</TableCell>*/}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {reparaciones.length > 0 && reparaciones.map((reparacion) => (
                            <TableRow key={reparacion.id}>
                                {/*<TableCell align="left">{reparacion.id}</TableCell>*/}
                                <TableCell align="left">{reparacion.patente}</TableCell>
                                <TableCell align="left">{reparacion.tipoReparacion}</TableCell>
                                <TableCell align="left">{reparacion.descripcion}</TableCell>
                                {/*<TableCell align="left">{reparacion.idHistorialReparaciones}</TableCell>*/}
                                <TableCell align="left"> {/* Aquí puedes agregar las acciones para esta tabla si es necesario */}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
};

export default Pagar;
