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
import datosBonosService from "../services/datosBonos.service";

const BonosList = () => {
  const [bonos, setBonos] = useState([]);

  const navigate = useNavigate();

  const init = () => {
    datosBonosService
      .getAll()
      .then((response) => {
        console.log("Mostrando listado de todos los bonos.", response.data);
        setBonos(response.data);
      })
      .catch((error) => {
        console.log(
          "Se ha producido un error al intentar mostrar listado de todos los bonos.",
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
      "¿Esta seguro que desea borrar este bono?"
    );
    if (confirmDelete) {
        datosBonosService
        .remove(id)
        .then((response) => {
          console.log("El bono ha sido eliminado.", response.data);
          init();
        })
        .catch((error) => {
          console.log(
            "Se ha producido un error al intentar eliminar el bono",
            error
          );
        });
    }
  };

  const handleEdit = (id) => {
    console.log("Printing id", id);
    navigate(`/bonos/edit/${id}`);
  };

  return (
    <TableContainer component={Paper}>
      <br />
      <Link
        to="/bonos/add"
        style={{ textDecoration: "none", marginBottom: "1rem" }}
      >
      </Link>
      <br /> <br />
      <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
        <TableHead>
          <TableRow>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              Marca Automovil
            </TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              Cantidad de bonos
            </TableCell>
            <TableCell align="right" sx={{ fontWeight: "bold" }}>
              Monto del bono
            </TableCell>
            <TableCell align="left" sx={{ fontWeight: "bold" }}>
              Operaciones
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {bonos.map((bono) => (
            <TableRow
              key={bono.id}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
            >
              <TableCell align="left">{bono.marcaAutomovil}</TableCell>
              <TableCell align="left">{bono.cantidadBonos}</TableCell>
              <TableCell align="right">{bono.montoBono}</TableCell>
              <TableCell>
                <Button
                //variant es el recuadro en azul
                  variant="contained"
                  color="info"
                  size="small"
                  onClick={() => handleEdit(bono.id)}
                  style={{ marginLeft: "0.5rem" }}
                  startIcon={<EditIcon />}
                >
                  Editar
                </Button>

                {/*<Button
                  variant="contained"
                  color="error"
                  size="small"
                  onClick={() => handleDelete(bono.id)}
                  style={{ marginLeft: "0.5rem" }}
                  startIcon={<DeleteIcon />}
                >
                  Eliminar
                </Button>
          */}
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default BonosList;
