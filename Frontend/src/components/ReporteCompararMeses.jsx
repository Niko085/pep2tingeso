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

function ReporteCompararMeses() {
    const [reporte, setReporte] = useState([]);
    const [mes, setMes] = useState(1); // Mes predeterminado: enero
    const [ano, setAno] = useState(2024); // Año predeterminado: 2024

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
        try {
            const response = await axios.get(`http://127.0.0.1:62823/reportes/reporte/compararMeses/${mes}/${ano}`);
            setReporte(response.data);
        } catch (error) {
            console.error('Error al obtener los datos:', error);
        }
    };

    useEffect(() => {
        obtenerReporteCompararMeses(mes, ano);
    }, [mes, ano]);

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
                        {reporte.map(item => (
                            <TableRow key={item.reparacion}>
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
