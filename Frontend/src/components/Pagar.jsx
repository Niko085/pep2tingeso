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
import { Link, useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const Pagar = () => {
    const [historialReparaciones, setHistorialReparaciones] = useState([]);
    const { patente } = useParams();
    const navigate = useNavigate();

    const handleCalculate = (patente) => {
        console.log("Calculando historial de reparaciones para la patente:", patente);
        axios
          .get(`http://localhost:8081/historialreparaciones/calculate?patente=${patente}`)
          .then(() => {
            console.log("Historial de reparaciones calculado con éxito");
            // Después de calcular, redirige a "/historialReparaciones/list"
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
                    // Asegúrate de que los datos se manejen como un array
                    setHistorialReparaciones(Array.isArray(data) ? data : [data]);
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
                                    <TableCell align="left">Acciones</TableCell> {/* Nuevo encabezado de columna */}
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
        </div>
    );
};

export default Pagar;
