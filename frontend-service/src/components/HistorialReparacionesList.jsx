import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import historialReparacionesService from "../services/historialReparaciones.service";
import automovilService from "../services/automovil.service";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import BuildIcon from "@mui/icons-material/Build";
import axios from "axios";
import IconButton from '@mui/material/IconButton';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';

const HistorialReparacionesList = () => {
  const [historialReparaciones, setHistorialReparaciones] = useState([]);

  const navigate = useNavigate();

  const init = async () => {
    try {
      const historialResponse = await historialReparacionesService.getAll();
      const historialData = historialResponse.data;

      const historialWithAutomovil = await Promise.all(historialData.map(async historial => {
        const automovilResponse = await historialReparacionesService.getAuto(historial.patente);
        const automovilData = automovilResponse.data;
        return { ...historial, automovil: automovilData };
      }));

      setHistorialReparaciones(historialWithAutomovil);
    } catch (error) {
      console.log("Error al cargar historial de reparaciones:", error);
    }
  };

  useEffect(() => {
    init();
  }, []);

  const handleDelete = (id) => {
    console.log("Eliminando historial de reparaciones con ID:", id);
    const confirmDelete = window.confirm(
      "¿Estás seguro de que deseas eliminar este historial de reparaciones?"
    );
    if (confirmDelete) {
      historialReparacionesService
        .remove(id)
        .then(() => {
          console.log("Historial de reparaciones eliminado con éxito");
          init();
        })
        .catch((error) => {
          console.log("Error al eliminar historial de reparaciones:", error);
        });
    }
  };

  const handleEdit = (id) => {
    console.log("Editando historial de reparaciones con ID:", id);
    navigate(`/historialreparaciones/edit/${id}`);
  };

  const handleAddReparacion = (idH, patenteH) => {
    console.log("Agregar reparacion con ID:", idH);
    navigate(`/reparaciones/select/${idH}/${patenteH}`);
  };

  const handleCalculate = (patente) => {
    console.log("Calculando historial de reparaciones para la patente:", patente);
    axios
      .get(`http://127.0.0.1:8081/historialreparaciones/calculate?patente=${patente}`)
      .then(() => {
        console.log("Historial de reparaciones calculado con éxito");
        init();
      })
      .catch((error) => {
        console.log("Error al calcular historial de reparaciones:", error);
      });
  };

  const handlePaymentChange = (id) => {
    const updatedHistorialReparaciones = historialReparaciones.map(item => {
      if (item.id === id) {
        return { ...item, pagado: true };
      }
      return item;
    });
    setHistorialReparaciones(updatedHistorialReparaciones);

    axios.put(`http://locahost:8081/historialreparaciones/pagar/${id}`, { pagado: true })
      .then(response => {
        console.log("Estado de pago actualizado en el servidor:", response.data);
      })
      .catch(error => {
        console.log("Error al actualizar estado de pago en el servidor:", error);
      });
  };

  return (
    <div style={{ margin: "20px" }}>
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "20px" }}>
        <div>
          <h1 style={{ fontSize: "24px", fontWeight: "bold" }}>Historial de Reparaciones</h1>
          <h2 style={{ color: "#666", textTransform: "uppercase" }}>Listado de registros</h2>
        </div>
      </div>
      <TableContainer component={Paper} style={{ boxShadow: "0px 0px 15px rgba(0, 0, 0, 0.1)", borderRadius: "12px" }}>
        <Table sx={{ minWidth: 650 }} size="small" aria-label="historial de reparaciones">
          <TableHead>
            <TableRow>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Patente</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Marca</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Modelo</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Tipo Vehiculo</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Año Fabricación</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Tipo Motor</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Fecha Ingreso Taller</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Hora Ingreso Taller</TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>Monto Total Reparaciones</TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>Recargos</TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>Descuentos</TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>IVA</TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>Monto Total Pagar</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Fecha Salida Taller</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Hora Salida Taller</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Fecha Retiro Cliente</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Hora Retiro Cliente</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Pagado</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {historialReparaciones.map((historialReparacion) => (
              <TableRow key={historialReparacion.id} sx={{ "&:last-child td, &:last-child th": { border: 0 } }}>
                <TableCell align="left">{historialReparacion.automovil.patente}</TableCell>
                <TableCell align="left">{historialReparacion.automovil.marca}</TableCell>
                <TableCell align="left">{historialReparacion.automovil.modelo}</TableCell>
                <TableCell align="left">{historialReparacion.automovil.tipo}</TableCell>
                <TableCell align="left">{historialReparacion.automovil.anioFabricacion}</TableCell>
                <TableCell align="left">{historialReparacion.automovil.motor}</TableCell>
                <TableCell align="left">{historialReparacion.fechaIngresoTaller}</TableCell>
                <TableCell align="left">{historialReparacion.horaIngresoTaller}</TableCell>
                <TableCell align="right">{historialReparacion.montoTotalReparaciones}</TableCell>
                <TableCell align="right">{historialReparacion.recargos}</TableCell>
                <TableCell align="right">{historialReparacion.descuentos}</TableCell>
                <TableCell align="right">{historialReparacion.iva}</TableCell>
                <TableCell align="right">{historialReparacion.montoTotalPagar}</TableCell>
                <TableCell align="left">{historialReparacion.fechaSalidaTaller}</TableCell>
                <TableCell align="left">{historialReparacion.horaSalidaTaller}</TableCell>
                <TableCell align="left">{historialReparacion.fechaClienteSeLlevaVehiculo}</TableCell>
                <TableCell align="left">{historialReparacion.horaClienteSeLlevaVehiculo}</TableCell>
                <TableCell align="left">{historialReparacion.pagado ? 'Sí' : 'No'}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};

export default HistorialReparacionesList;
