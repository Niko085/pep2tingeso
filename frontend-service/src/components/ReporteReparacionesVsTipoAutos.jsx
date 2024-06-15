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
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import reportesServices from "../services/reportes.service";

function ReporteReparacionesVsTiposAutos() {
    const [reporte, setReporte] = useState([]);
    const [mes, setMes] = useState(12); // Mes predeterminado: diciembre
    const [ano, setAno] = useState(2023); // Año predeterminado: 2023
    const [isLoading, setIsLoading] = useState(false);
    const [isSubmitted, setIsSubmitted] = useState(false);
    const [error, setError] = useState(null);

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
        setIsLoading(true);
        setIsSubmitted(false);
        setError(null);

        try {
            const response = await reportesServices.getReporteReparacionesVsTipoAuto({ mes, ano });
            setReporte(response.data);
            setIsSubmitted(true);
        } catch (error) {
            setError(error);
            console.error('Error al obtener los datos:', error);
        } finally {
            setIsLoading(false);
        }
    };

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

            {isLoading ? (
                <Box display="flex" alignItems="center" justifyContent="center" height="100vh">
                    <CircularProgress size={50} />
                    <Box ml={2}>Cargando...</Box>
                </Box>
            ) : error ? (
                <Typography variant="h6" color="error" sx={{ mt: 2 }}>
                    Error: {error.message}
                </Typography>
            ) : !isSubmitted ? (
                <Typography variant="h6" sx={{ mt: 2 }}>
                    Seleccione el mes y el año para ver el reporte.
                </Typography>
            ) : (
                <TableContainer component={Paper}>
                    <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
                        <TableHead>
                            <TableRow>
                                <TableCell>Tipo de reparacion</TableCell>
                                <TableCell>Cantidad Sedan</TableCell>
                                <TableCell>Monto Sedan</TableCell>
                                <TableCell>Cantidad Hatchback</TableCell>
                                <TableCell>Monto Hatchback</TableCell>
                                <TableCell>Cantidad Suv</TableCell>
                                <TableCell>Monto Suv</TableCell>
                                <TableCell>Cantidad Pickup</TableCell>
                                <TableCell>Monto Pickup</TableCell>
                                <TableCell>Cantidad Furgoneta</TableCell>
                                <TableCell>Monto Furgoneta</TableCell>
                                <TableCell>Monto total reparaciones</TableCell>
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
            )}
        </div>
    );
}

export default ReporteReparacionesVsTiposAutos;
