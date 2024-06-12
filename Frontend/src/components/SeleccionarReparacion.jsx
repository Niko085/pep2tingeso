import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import MenuItem from "@mui/material/MenuItem";
import SaveIcon from "@mui/icons-material/Save";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import RemoveCircleIcon from "@mui/icons-material/RemoveCircle";
import reparacionService from "../services/reparacion.service";

const reparaciones = [
  { value: 1, label: "Reparaciones del Sistema de Frenos" },
  { value: 2, label: "Servicio del Sistema de Refrigeración" },
  { value: 3, label: "Reparaciones del Motor" },
  { value: 4, label: "Reparaciones de la Transmisión" },
  { value: 5, label: "Reparación del Sistema Eléctrico" },
  { value: 6, label: "Reparaciones del Sistema de Escape" },
  { value: 7, label: "Reparación de Neumáticos y Ruedas" },
  { value: 8, label: "Reparaciones de la Suspensión y la Dirección" },
  { value: 9, label: "Reparación del Sistema de Aire Acondicionado y Calefacción" },
  { value: 10, label: "Reparaciones del Sistema de Combustible" },
  { value: 11, label: "Reparación y Reemplazo del Parabrisas y Cristales" }
];

const ReparacionSelectionForm = () => {
  const [patente, setPatente] = useState("");
  const [reparacionesDisponibles, setReparacionesDisponibles] = useState(reparaciones);
  const [reparacionesSeleccionadas, setReparacionesSeleccionadas] = useState([]);
  const { idH, patenteH } = useParams();
  const [id, setId] = useState("");
  const [idHistorialReparaciones, setIdHistorialReparaciones] = useState("");
  const [titleReparacionForm, setTitleReparacionForm] = useState("");
  const navigate = useNavigate();

  const manejarSeleccionarReparacion = (index) => {
    setReparacionesSeleccionadas([...reparacionesSeleccionadas, reparacionesDisponibles[index]].sort((a, b) => a.value - b.value));
    const reparacionesDisponiblesAux = reparacionesDisponibles.filter((_, i) => i !== index);
    setReparacionesDisponibles(reparacionesDisponiblesAux);
  };

  const manejarEliminarReparacion = (index) => {
    setReparacionesDisponibles([...reparacionesDisponibles, reparacionesSeleccionadas[index]].sort((a, b) => a.value - b.value));
    const reparacionesSeleccionadasAux = reparacionesSeleccionadas.filter((_, i) => i !== index);
    setReparacionesSeleccionadas(reparacionesSeleccionadasAux);
  };

  const saveReparacion = (e) => {
    e.preventDefault();

    const reparacionesToSave = reparacionesSeleccionadas.map((reparacion) => ({
      patente,
      tipoReparacion: reparacion.value,
      descripcion: reparacion.label,
      idHistorialReparaciones,
      id,
    }));

    console.log("Reparaciones to Save:", reparacionesToSave); // Debug

    Promise.all(
      reparacionesToSave.map((reparacion) => reparacionService.create(reparacion))
    )
      .then((responses) => {
        console.log("Las reparaciones han sido añadidas.", responses);
        navigate("/reparaciones/list");
      })
      .catch((error) => {
        console.log("Ha ocurrido un error al intentar crear nuevas reparaciones.", error);
      });
  };

  useEffect(() => {
    setTitleReparacionForm("Seleccione las reparaciones a realizar");
    reparacionService
      .get(idH)
      .then((reparacion) => {
        console.log("Reparacion Data:", reparacion.data); // Debug
        setPatente(patenteH);
        setIdHistorialReparaciones(reparacion.data.id);
      })
      .catch((error) => {
        console.log("Se ha producido un error.", error);
      });
  }, [idH, patenteH]);

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      component="form"
      sx={{
        padding: "20px",
        borderRadius: "25px",
        boxShadow: "0px 0px 100px rgba(0, 0, 0, 0.3)",
        backgroundColor: "#f9f9f9",
        maxWidth: "450px",
        margin: "auto",
        marginTop: "30px",
      }}
    >
      <h3>{titleReparacionForm}</h3>
      <hr />
      <div>
        <h4>Reparaciones Disponibles</h4>
        {reparacionesDisponibles.map((reparacion, index) => (
          <MenuItem key={reparacion.value} onClick={() => manejarSeleccionarReparacion(index)} className="d-flex justify-content-between">
            {reparacion.label}
            <AddCircleIcon color="success" />
          </MenuItem>
        ))}
      </div>
      <div>
        <h4>Reparaciones Seleccionadas</h4>
        {reparacionesSeleccionadas.map((reparacion, index) => (
          <MenuItem key={reparacion.value} onClick={() => manejarEliminarReparacion(index)} className="d-flex justify-content-between">
            {reparacion.label}
            <RemoveCircleIcon color="error" />
          </MenuItem>
        ))}
      </div>
      <FormControl>
        <br />
        <Button
          variant="contained"
          color="info"
          onClick={saveReparacion}
          style={{ marginLeft: "0.5rem" }}
          startIcon={<SaveIcon />}
        >
          Guardar
        </Button>
      </FormControl>
      <hr />
      <Link to="/reparaciones/list">Volver a la lista de reparaciones</Link>
    </Box>
  );
};

export default ReparacionSelectionForm;
