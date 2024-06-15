import React, { useEffect, useState } from 'react';
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

function ReporteCompararMeses() {
    const [reporte, setReporte] = useState([]);
    const [mes, setMes] = useState(1); // Mes predeterminado: enero
    const [ano, setAno] = useState(2024); // Año predeterminado: 2024
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

    const obtenerNombreMes = (numeroMes) => {
        return meses.find(m => m.value === numeroMes)?.label || "";
    };

    const obtenerReporteCompararMeses = async (mes, ano) => {
        setIsLoading(true);
        setIsSubmitted(false);
        setError(null);

        try {
            const response = await reportesServices.getReporteCompararMeses({ mes, ano });
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
            <h2 className="text-center mb-4">Reporte de comparación de meses</h2>

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

                <Button variant="contained" onClick={() => obtenerReporteCompararMeses(mes, ano)}>
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
                                <TableCell>Tipo de reparación</TableCell>
                                <TableCell>Mes</TableCell>
                                <TableCell>Cantidad autos</TableCell>
                                <TableCell>Monto</TableCell>
                                <TableCell>Mes</TableCell>
                                <TableCell>Cantidad autos</TableCell>
                                <TableCell>Monto</TableCell>
                                <TableCell>Variación cantidad</TableCell>
                                <TableCell>Variación monto</TableCell>
                                <TableCell>Mes</TableCell>
                                <TableCell>Cantidad autos</TableCell>
                                <TableCell>Monto</TableCell>
                                <TableCell>Variación cantidad</TableCell>
                                <TableCell>Variación monto</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {reporte.map(item => (
                                <TableRow key={item.reparacion}>
                                    <TableCell>{item.reparacion}</TableCell>
                                    <TableCell>{obtenerNombreMes(item.mes1)}</TableCell>
                                    <TableCell>{item.cantidadAutos1}</TableCell>
                                    <TableCell>{item.monto1}</TableCell>
                                    <TableCell>{obtenerNombreMes(item.mes2)}</TableCell>
                                    <TableCell>{item.cantidadAutos2}</TableCell>
                                    <TableCell>{item.monto2}</TableCell>
                                    <TableCell>{item.variacionCantidad2}%</TableCell>
                                    <TableCell>{item.variacionMonto2}%</TableCell>
                                    <TableCell>{obtenerNombreMes(item.mes3)}</TableCell>
                                    <TableCell>{item.cantidadAutos3}</TableCell>
                                    <TableCell>{item.monto3}</TableCell>
                                    <TableCell>{item.variacionCantidad3}%</TableCell>
                                    <TableCell>{item.variacionMonto3}%</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            )}
        </div>
    );
}

export default ReporteCompararMeses;
