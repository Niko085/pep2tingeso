import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";

function ReporteReparacionesVsTipoMotor() {
    const [reporte, setReporte] = useState([]);

    useEffect(() => {
        const obtenerReporteReparacionesVsTipoMotor = async () => {
            try {
                const response = await axios.get('http://localhost:8090/api/historialreparaciones/reporte/reparaciones-vs-tipo-motores');
                setReporte(response.data);
            } catch (error) {
                console.error('Error al obtener los datos:', error);
            }
        };
        obtenerReporteReparacionesVsTipoMotor();
    }, []);

    return (
        <div className="container mt-4">
            <h2 className="text-center mb-4">Reporte de Reparaciones VS Tipos de motor</h2>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
                    <TableHead>
                        <TableRow>
                            <TableCell scope="col">Tipo de reparación</TableCell>
                            <TableCell scope="col">Cantidad de vehiculos con motor de tipo gasolina</TableCell>
                            <TableCell scope="col">Cantidad de vehiculos con motor de tipo diesel</TableCell>
                            <TableCell scope="col">Cantidad de vehiculos con motor de tipo híbrido</TableCell>
                            <TableCell scope="col">Cantidad de vehiculos con motor de tipo eléctrico</TableCell>
                            <TableCell scope="col">Monto total reparaciones</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {/* Mapea cada elemento del reporte */}
                        {reporte.map(item => (
                            <TableRow key={item.tipoReparacion}>
                                {/* Añade espacio entre las columnas */}
                                <TableCell>{item.tipoReparacion}</TableCell>
                                <TableCell>{item.cantidadGasolina}</TableCell>
                                <TableCell>{item.cantidadDiesel}</TableCell>
                                <TableCell>{item.cantidadHibrido}</TableCell>
                                <TableCell>{item.cantidadElectrico}</TableCell>
                                <TableCell>{item.montoTotalReparaciones}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
}

export default ReporteReparacionesVsTipoMotor;
