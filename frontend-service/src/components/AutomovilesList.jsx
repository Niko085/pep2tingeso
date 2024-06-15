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
import DriveEtaIcon from "@mui/icons-material/DriveEta";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

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
      "¿Está seguro que desea borrar este automovil?"
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
    <div style={{ margin: "20px" }}>
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "20px" }}>
        <div>
          <h1 style={{ fontSize: "24px", fontWeight: "bold" }}>Vehiculos</h1>
          <h2 style={{ color: "#666", textTransform: "uppercase" }}>Lista de registrados</h2>
        </div>
        <Link to="/automovil/add" style={{ textDecoration: "none" }}>
          <Button
            variant="contained"
            color="primary"
            startIcon={<DriveEtaIcon />}
          >
            Registrar Vehiculo
          </Button>
        </Link>
      </div>
      <TableContainer component={Paper} style={{ boxShadow: "0px 0px 15px rgba(0, 0, 0, 0.1)", borderRadius: "12px" }}>
        <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
          <TableHead>
            <TableRow>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Patente</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Marca</TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>Modelo</TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>Tipo Vehiculo</TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>Año Fabricación</TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>Tipo Motor</TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>Cantidad Asientos</TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>Kilometraje</TableCell>
              <TableCell align="left" sx={{ fontWeight: "bold" }}>Operaciones</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {automoviles.map((automovil) => (
              <TableRow key={automovil.id} sx={{ "&:last-child td, &:last-child th": { border: 0 } }}>
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
    </div>
  );
};

export default AutomovilesList;
