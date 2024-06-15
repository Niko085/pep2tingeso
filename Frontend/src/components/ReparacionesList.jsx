import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import reparacionService from "../services/reparacion.service"; // Importa el servicio de reparaciones
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";

const ReparacionesList = () => {
  const [reparaciones, setReparaciones] = useState([]); // Estado para almacenar la lista de reparaciones
  const navigate = useNavigate(); // Hook para la navegación

  // Función para inicializar la lista de reparaciones
  const init = () => {
    reparacionService
      .getAll()
      .then((response) => {
        console.log("Mostrando listado de todas las reparaciones.", response.data);
        setReparaciones(response.data); // Actualiza el estado con los datos obtenidos
      })
      .catch((error) => {
        console.log("Se ha producido un error al intentar mostrar el listado de reparaciones.", error);
      });
  };

  // Hook useEffect para llamar a la función init cuando el componente se monta
  useEffect(() => {
    init();
  }, []);

  // Función para manejar la eliminación de una reparación
  const handleDelete = (id) => {
    console.log("Imprimiendo id", id);
    const confirmDelete = window.confirm("¿Estás seguro de que deseas eliminar esta reparación?");
    if (confirmDelete) {
      reparacionService
        .remove(id)
        .then((response) => {
          console.log("La reparación ha sido eliminada.", response.data);
          init(); // Vuelve a cargar la lista de reparaciones
        })
        .catch((error) => {
          console.log("Se ha producido un error al intentar eliminar la reparación.", error);
        });
    }
  };

  // Función para manejar la edición de una reparación
  const handleEdit = (id) => {
    console.log("Imprimiendo id", id);
    navigate(`/reparaciones/edit/${id}`); // Navega a la página de edición con el id de la reparación
  };

  return (
    <TableContainer component={Paper}>
      <br />
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell align="left">ID</TableCell>
            <TableCell align="left">Patente</TableCell>
            <TableCell align="left">N° de reparación</TableCell>
            <TableCell align="left">Descripción</TableCell>
            <TableCell align="left">ID Historial Reparaciones</TableCell>
            <TableCell align="left">Fecha Reparación</TableCell>
            <TableCell align="left">Hora Reparación</TableCell>
            <TableCell align="left">Monto</TableCell>
            <TableCell align="left">Acciones</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {reparaciones.map((reparacion) => (
            <TableRow key={reparacion.id}>
              <TableCell align="left">{reparacion.id}</TableCell>
              <TableCell align="left">{reparacion.patente}</TableCell>
              <TableCell align="left">{reparacion.tipoReparacion}</TableCell>
              <TableCell align="left">{reparacion.descripcion}</TableCell>
              <TableCell align="left">{reparacion.idHistorialReparaciones}</TableCell>
              <TableCell align="left">{reparacion.fechaReparacion}</TableCell>
              <TableCell align="left">{reparacion.horaReparacion}</TableCell>
              <TableCell align="left">{reparacion.montoReparacion}</TableCell>
              <TableCell>
                <Button
                  variant="contained"
                  color="info"
                  size="small"
                  onClick={() => handleEdit(reparacion.id)}
                  style={{ marginLeft: "0.5rem" }}
                  startIcon={<EditIcon />}
                >
                  Editar
                </Button>
                <Button
                  variant="contained"
                  color="error"
                  size="small"
                  onClick={() => handleDelete(reparacion.id)}
                  style={{ marginLeft: "0.5rem" }}
                  startIcon={<DeleteIcon />}
                >
                  Eliminar
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default ReparacionesList;
