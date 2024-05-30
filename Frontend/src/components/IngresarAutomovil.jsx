import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import historialReparacionesService from "../services/historialReparaciones.service";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import EditIcon from "@mui/icons-material/Edit";
import BuildIcon from "@mui/icons-material/Build";

const HistorialReparacionesList = () => {
  const [historialReparaciones, setHistorialReparaciones] = useState([]);
  const navigate = useNavigate();

  const init = () => {
    historialReparacionesService
      .getAll()
      .then((response) => {
        console.log("Mostrando historial de reparaciones:", response.data);
        setHistorialReparaciones(response.data);
      })
      .catch((error) => {
        console.log("Error al cargar historial de reparaciones:", error);
      });
  };

  useEffect(() => {
    init();
  }, []);

  const handleEdit = (id) => {
    console.log("Editando historial de reparaciones con ID:", id);
    navigate(`/historialreparaciones/edit/${id}`);
  };

  return (
    <TableContainer component={Paper}>
      <br />
      <Link
        to="/historialreparaciones/add"
        style={{ textDecoration: "none", marginBottom: "1rem" }}
      >
        <Button
          variant="contained"
          color="primary"
          startIcon={<BuildIcon />}
        >
          Añadir Historial de Reparaciones
        </Button>
      </Link>
      <br /> <br />
      <Table sx={{ minWidth: 650 }} size="small" aria-label="historial de reparaciones">
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
            <TableCell align="left">Operaciones</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {historialReparaciones.map((historialReparaciones) => (
            <TableRow
              key={historialReparaciones.id}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell align="left">{historialReparaciones.fechaIngresoTaller}</TableCell>
              <TableCell align="left">{historialReparaciones.horaIngresoTaller}</TableCell>
              <TableCell align="right">{historialReparaciones.montoTotalPagar}</TableCell>
              <TableCell align="right">{historialReparaciones.recargos}</TableCell>
              <TableCell align="right">{historialReparaciones.descuentos}</TableCell>
              <TableCell align="right">{historialReparaciones.iva}</TableCell>
              <TableCell align="left">{historialReparaciones.fechaSalidaTaller}</TableCell>
              <TableCell align="left">{historialReparaciones.horaSalidaTaller}</TableCell>
              <TableCell align="left">{historialReparaciones.fechaClienteSeLlevaVehiculo}</TableCell>
              <TableCell align="left">{historialReparaciones.horaClienteSeLlevaVehiculo}</TableCell>
              <TableCell align="left">{historialReparaciones.pagado ? 'Sí' : 'No'}</TableCell>
              <TableCell>
                <Button
                  variant="contained"
                  color="info"
                  size="small"
                  onClick={() => handleEdit(historialReparaciones.id)}
                  style={{ marginLeft: "0.5rem" }}
                  startIcon={<EditIcon />}
                >
                  Editar
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default HistorialReparacionesList;
