import React, { useEffect, useState } from 'react';
import axios from 'axios';
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import MenuItem from '@mui/material/MenuItem';

function ReporteReparacionesVsTiposAutos() {
    const [reporte, setReporte] = useState([]);
    const [mes, setMes] = useState(12); // Mes predeterminado: diciembre
    const [ano, setAno] = useState(2023); // Año predeterminado: 2023

    const meses = [
        { value: 1, label: "Enero" },
        { value: 2, label: "Febrero" },
        { value: 3, label: "Marzo" },
        { value: 4, label: "Abril" },
        { value: 5, label: "Mayo" },
        { value: 6, label: "Junio" },
        { value: 7, label: "Julio" },
        { value: 8, label: "Agosto" },
        { value: 9, label: "Septiembre" },
        { value: 10, label: "Octubre" },
        { value: 11, label: "Noviembre" },
        { value: 12, label: "Diciembre" }
    ];

    const obtenerReporteReparacionesVsTiposAutos = async (mes, ano) => {
        try {
            const response = await axios.get(`http://127.0.0.1:62823/reportes/reporte/reparaciones-vs-tipo-autos/${mes}/${ano}`);
            setReporte(response.data);
        } catch (error) {
            console.error('Error al obtener los datos:', error);
        }
    };

    useEffect(() => {
        obtenerReporteReparacionesVsTiposAutos(mes, ano);
    }, [mes, ano]);

    const handleMesChange = (event) => {
        setMes(event.target.value);
    };

    const handleAnoChange = (event) => {
        setAno(event.target.value);
    };

    return (
        <div className="container mt-4">
            <h2 className="text-center mb-4">Reporte de Reparaciones VS Tipos de auto</h2>

            <div className="d-flex justify-content-center mb-4">
                <TextField
                    select
                    label="Mes"
                    value={mes}
                    onChange={handleMesChange}
                    variant="outlined"
                    sx={{ marginRight: 2 }}
                >
                    {meses.map((option) => (
                        <MenuItem key={option.value} value={option.value}>
                            {option.label}
                        </MenuItem>
                    ))}
                </TextField>

                <TextField
                    label="Año"
                    type="number"
                    value={ano}
                    onChange={handleAnoChange}
                    variant="outlined"
                    sx={{ marginRight: 2 }}
                />

                <Button variant="contained" onClick={() => obtenerReporteReparacionesVsTiposAutos(mes, ano)}>
                    Consultar
                </Button>
            </div>

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
                        {reporte.map(item => (
                            <TableRow key={item.reparacion}>
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
