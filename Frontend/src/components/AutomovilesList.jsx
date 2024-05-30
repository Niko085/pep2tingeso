import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import automovilService from "../services/automovil.service";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell, { tableCellClasses } from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import Button from "@mui/material/Button";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import DriveEtaIcon from "@mui/icons-material/DriveEta";

const AutomovilesList = () => {
  const [automoviles, setAutomoviles] = useState([]);

  const navigate = useNavigate();

  const init = () => {
    automovilService
      .getAll()
      .then((response) => {
        console.log("Mostrando listado de todos los automoviles.", response.data);
        setAutomoviles(response.data);
      })
      .catch((error) => {
        console.log(
          "Se ha producido un error al intentar mostrar listado de todos los automoviles.",
          error
        );
      });
  };

  useEffect(() => {
    init();
  }, []);

  const handleDelete = (id) => {
    console.log("Printing id", id);
    const confirmDelete = window.confirm(
      "¿Esta seguro que desea borrar este automovil?"
    );
    if (confirmDelete) {
      automovilService
        .remove(id)
        .then((response) => {
          console.log("El automovil ha sido eliminado.", response.data);
          init();
        })
        .catch((error) => {
          console.log(
            "Se ha producido un error al intentar eliminar el automovil",
            error
          );
        });
    }
  };

  const handleEdit = (id) => {
    console.log("Printing id", id);
    navigate(`/automovil/edit/${id}`);
  };

  return (
    <TableContainer component={Paper}>
      <br />
      <Link
        to="/automovil/add"
        style={{ textDecoration: "none", marginBottom: "1rem" }}
      >
        <Button
          variant="contained"
          color="primary"
          startIcon={<DriveEtaIcon />}
        >
          Añadir Automovil
        </Button>
      </Link>
      <br /> <br />
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              Patente
            </TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              Marca
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              Modelo
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              Tipo de auto
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              Año de fabricación
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              Tipo de motor
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              Cantidad de asientos
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              Kilometraje
            </TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              Operaciones
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {automoviles.map((automovil) => (
            <TableRow
              key={automovil.id}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell align="left">{automovil.patente}</TableCell>
              <TableCell align="left">{automovil.marca}</TableCell>
              <TableCell align="right">{automovil.modelo}</TableCell>
              <TableCell align="right">{automovil.tipo}</TableCell>
              <TableCell align="right">{automovil.anioFabricacion}</TableCell>
              <TableCell align="right">{automovil.motor}</TableCell>
              <TableCell align="right">{automovil.cantAsientos}</TableCell>
              <TableCell align="right">{automovil.kilometraje}</TableCell>
              <TableCell>
                <Button
                //variant es el recuadro en azul
                  variant="contained"
                  color="info"
                  size="small"
                  onClick={() => handleEdit(automovil.id)}
                  style={{ marginLeft: "0.5rem" }}
                  startIcon={<EditIcon />}
                >
                  Editar
                </Button>

                <Button
                  variant="contained"
                  color="error"
                  size="small"
                  onClick={() => handleDelete(automovil.id)}
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

export default AutomovilesList;
