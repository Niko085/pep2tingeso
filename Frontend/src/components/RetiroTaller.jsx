import React, { useState } from 'react';
import Button from '@mui/material/Button';
import DriveEtaIcon from '@mui/icons-material/DriveEta';
import historialReparacionesService from "../services/historialReparaciones.service";
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableHead from '@mui/material/TableHead';
import Paper from '@mui/material/Paper';

const RetiroTaller = () => {
    const [patente, setPatente] = useState('');
    const [historialReparaciones, setHistorialReparaciones] = useState([]);

    const handleSearch = async () => {
        try {
            console.log("Buscando historial de reparaciones para la patente:", patente);
            const response = await historialReparacionesService.getNoPagadoByPatente(patente);
            console.log("Respuesta de la solicitud:", response);
            if (response.ok) {
                const data = await response.json();
                console.log("Datos obtenidos:", data);
                setHistorialReparaciones(data);
            } else {
                console.error('Error al buscar historial de reparaciones:', response.statusText);
            }
        } catch (error) {
            console.error('Error al buscar historial de reparaciones:', error);
        }
    };

    return (
        <div className="container">
            <h1>Retiro del taller</h1>
            <div>
                <label htmlFor="patenteInput">Ingrese la patente:</label>
                <input 
                    type="text" 
                    id="patenteInput" 
                    value={patente} 
                    onChange={(e) => setPatente(e.target.value)} 
                />
                <Button 
                    variant="contained" 
                    color="primary" 
                    onClick={handleSearch}
                    startIcon={<DriveEtaIcon />}
                >
                    Buscar historial
                </Button>
            </div>
            {historialReparaciones.length > 0 && (
                <div>
                    <h2>Historial de reparaciones no pagadas para la patente {patente}:</h2>
                    <Paper>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell align="left">Fecha Ingreso Taller</TableCell>
                                    <TableCell align="left">Hora Ingreso Taller</TableCell>
                                    <TableCell align="right">Monto Total a Pagar</TableCell>
                                    <TableCell align="right">Recargos</TableCell>
                                    <TableCell align="right">Descuentos</TableCell>
                                    <TableCell align="right">IVA</TableCell>
                                    <TableCell align="left">Fecha Salida Taller</TableCell>
                                    <TableCell align="left">Hora Salida Taller</TableCell>
                                    <TableCell align="left">Fecha Cliente se Lleva Vehículo</TableCell>
                                    <TableCell align="left">Hora Cliente se Lleva Vehículo</TableCell>
                                    <TableCell align="left">Pagado</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {historialReparaciones.map(historial => (
                                    <TableRow key={historial.id}>
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

export default RetiroTaller;
