import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";

function ReporteTiempoPromedio() {
    const [reporte, setReporte] = useState([]);

    useEffect(() => {
        const obtenerReporteTiempoPromedio = async () => {
            try {
                const response = await axios.get('http://localhost:8090/api/historialreparaciones/reporte/marcas-vs-tiempo-promedio');
                setReporte(response.data);
            } catch (error) {
                console.error('Error al obtener los datos:', error);
            }
        };
        obtenerReporteTiempoPromedio();
    }, []);

    return (
        <div className="container mt-4">
            <h2 className="text-center mb-4">Reporte de Tiempo Promedio de Reparaciones por Marca</h2>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
                    <TableHead>
                        <TableRow>
                            <TableCell scope="col">Marca</TableCell>
                            <TableCell scope="col">Tiempo Promedio (días)</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {/* Mapea cada elemento del reporte */}
                        {reporte.map(item => (
                            <TableRow key={item.marcaAutomovil}>
                                {/* Añade espacio entre las columnas */}
                                <TableCell >{item.marcaAutomovil}</TableCell>
                                <TableCell >{item.tiempoEnDias}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
}

export default ReporteTiempoPromedio;
