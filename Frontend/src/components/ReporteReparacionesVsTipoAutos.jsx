import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";

function ReporteReparacionesVsTiposAutos() {
    const [reporte, setReporte] = useState([]);

    useEffect(() => {
        const obtenerReporteReparacionesVsTiposAutos = async () => {
            try {
                const response = await axios.get('http://localhost:8081/reportes/reporte/reparaciones-vs-tipo-autos/12/2023');
                setReporte(response.data);
            } catch (error) {
                console.error('Error al obtener los datos:', error);
            }
        };
        obtenerReporteReparacionesVsTiposAutos();
    }, []);

    return (
        <div className="container mt-4">
            <h2 className="text-center mb-4">Reporte de Reparaciones VS Tipos de auto</h2>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
                    <TableHead>
                        <TableRow>
                            <TableCell scope="col">Tipo de reparacion</TableCell>
                            <TableCell scope="col">Cantidad Sedan</TableCell>
                            <TableCell scope="col">Monto Sedan</TableCell>
                            <TableCell scope="col">Cantidad Hatchback</TableCell>
                            <TableCell scope="col">Monto Hatchback</TableCell>
                            <TableCell scope="col">Cantidad Suv</TableCell>
                            <TableCell scope="col">Monto Suv</TableCell>
                            <TableCell scope="col">Cantidad Pickup</TableCell>
                            <TableCell scope="col">Monto Pickup</TableCell>
                            <TableCell scope="col">Cantidad Furgoneta</TableCell>
                            <TableCell scope="col">Monto Furgoneta</TableCell>
                            <TableCell scope="col">Monto total reparaciones</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {/* Mapea cada elemento del reporte */}
                        {reporte.map(item => (
                            <TableRow key={item.reparacion}>
                                {/* Añade espacio entre las columnas */}
                                <TableCell>{item.reparacion}</TableCell>
                                <TableCell>{item.cantidadSedan}</TableCell>
                                <TableCell>{item.montoSedan}</TableCell>
                                <TableCell>{item.cantidadHatchback}</TableCell>
                                <TableCell>{item.montoHatchback}</TableCell>
                                <TableCell>{item.cantidadSuv}</TableCell>
                                <TableCell>{item.montoSuv}</TableCell>
                                <TableCell>{item.cantidadPickup}</TableCell>
                                <TableCell>{item.montoPickup}</TableCell>
                                <TableCell>{item.cantidadFurgoneta}</TableCell>
                                <TableCell>{item.montoFurgoneta}</TableCell>
                                <TableCell>{item.montoTotalReparaciones}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
}

export default ReporteReparacionesVsTiposAutos;
