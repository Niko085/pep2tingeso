import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";

function ReporteCompararMeses() {
    const [reporte, setReporte] = useState([]);

    const meses = [
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    ];

    const obtenerNombreMes = (numeroMes) => {
        return meses[numeroMes - 1] || ""; // Restar 1 porque los índices del array comienzan en 0
    };


    useEffect(() => {
        const obtenerReporteCompararMeses = async () => {
            try {
                const response = await axios.get('http://localhost:8081/reportes/reporte/compararMeses/1/2024');
                setReporte(response.data);
            } catch (error) {
                console.error('Error al obtener los datos:', error);
            }
        };
        obtenerReporteCompararMeses();
    }, []);

    return (
        <div className="container mt-4">
            <h2 className="text-center mb-4">Reporte de comparación de meses</h2>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
                    <TableHead>
                        <TableRow>
                            <TableCell scope="col">Tipo de reparación</TableCell>
                            <TableCell scope="col">Mes</TableCell>
                            <TableCell scope="col">Cantidad autos</TableCell>
                            <TableCell scope="col">Monto</TableCell>
                            <TableCell scope="col">Mes</TableCell>
                            <TableCell scope="col">Cantidad autos</TableCell>
                            <TableCell scope="col">Monto</TableCell>
                            <TableCell scope="col">Variación cantidad</TableCell>
                            <TableCell scope="col">Variación monto</TableCell>
                            <TableCell scope="col">Mes</TableCell>
                            <TableCell scope="col">Cantidad autos</TableCell>
                            <TableCell scope="col">Monto</TableCell>
                            <TableCell scope="col">Variación cantidad</TableCell>
                            <TableCell scope="col">Variación monto</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {/* Mapea cada elemento del reporte */}
                        {reporte.map(item => (
                            <TableRow key={item.reparacion}>
                                {/* Añade espacio entre las columnas */}
                                <TableCell>{item.reparacion}</TableCell>
                                <TableCell>{obtenerNombreMes(item.mes1)}</TableCell>
                                <TableCell>{item.cantidadAutos1}</TableCell>
                                <TableCell>{item.monto1}</TableCell>
                                <TableCell>{obtenerNombreMes(item.mes2)}</TableCell>
                                <TableCell>{item.cantidadAutos2}</TableCell>
                                <TableCell>{item.monto2}</TableCell>
                                <TableCell>{item.variacionCantidad2}</TableCell>
                                <TableCell>{item.variacionMonto2}</TableCell>
                                <TableCell>{obtenerNombreMes(item.mes3)}</TableCell>
                                <TableCell>{item.cantidadAutos3}</TableCell>
                                <TableCell>{item.monto3}</TableCell>
                                <TableCell>{item.variacionCantidad3}</TableCell>
                                <TableCell>{item.variacionMonto3}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
}

export default ReporteCompararMeses;
