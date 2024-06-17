import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import Box from "@mui/material/Box";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import SaveIcon from "@mui/icons-material/Save";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import RemoveCircleIcon from "@mui/icons-material/RemoveCircle";
import reparacionService from "../services/reparacion.service";
import historialReparacionesService from "../services/historialReparaciones.service";

const ReparacionSelectionForm = () => {
  const [patente, setPatente] = useState("");
  const [reparacionesDisponibles, setReparacionesDisponibles] = useState([]);
  const [reparacionesSeleccionadas, setReparacionesSeleccionadas] = useState([]);
  const { idH } = useParams();
  const [idHistorialReparaciones, setIdHistorialReparaciones] = useState("");
  const [titleReparacionForm, setTitleReparacionForm] = useState("");
  const [fechaIngresoTaller, setFechaIngresoTaller] = useState("");
  const [horaIngresoTaller, setHoraIngresoTaller] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    setTitleReparacionForm("Seleccione las reparaciones a realizar");
  
    // Fetching available repairs from the API
    if (patente) {
      fetch(`http://localhost:8081/historialreparaciones/monto/montos/${patente}`)
        .then((response) => {
          if (!response.ok) {
            throw new Error(`Network response was not ok: ${response.statusText}`);
          }
          return response.json();
        })
        .then((data) => {
          // Debug: Log the raw data from the API
          console.log("Raw data from API:", data);
  
          // Mapping the response data to match the format expected by the component
          const reparaciones = data.map((item) => ({
            id: item.id,
            tipoReparacion: item.tipoReparacion,
            descripcion: item.descripcion,
            numeroReparacion: item.numeroReparacion,
            monto: item.monto,
          }));
  
          // Debug: Log the mapped data
          console.log("Mapped reparaciones:", reparaciones);
  
          setReparacionesDisponibles(reparaciones);
        })
        .catch((error) => {
          console.error("Error fetching available repairs:", error);
        });
    }
  
    // Fetch historical repair data based on idH
    historialReparacionesService.get(idH)
      .then((response) => {
        const historial = response.data;
        setPatente(historial.patente);
        setIdHistorialReparaciones(historial.id);
        setFechaIngresoTaller(historial.fechaIngresoTaller);
        setHoraIngresoTaller(historial.horaIngresoTaller);
      })
      .catch((error) => {
        console.log("Se ha producido un error al obtener el historial de reparaciones.", error);
      });
  }, [idH, patente]);
  

  const manejarSeleccionarReparacion = (index) => {
    setReparacionesSeleccionadas([...reparacionesSeleccionadas, reparacionesDisponibles[index]]);
    const reparacionesDisponiblesAux = reparacionesDisponibles.filter((_, i) => i !== index);
    setReparacionesDisponibles(reparacionesDisponiblesAux);
  };

  const manejarEliminarReparacion = (index) => {
    setReparacionesDisponibles([...reparacionesDisponibles, reparacionesSeleccionadas[index]]);
    const reparacionesSeleccionadasAux = reparacionesSeleccionadas.filter((_, i) => i !== index);
    setReparacionesSeleccionadas(reparacionesSeleccionadasAux);
  };

  const saveReparacion = (e) => {
    e.preventDefault(); // Prevenir envío automático del formulario

    console.log("Reparaciones seleccionadas:", reparacionesSeleccionadas);

    const reparacionesToSave = reparacionesSeleccionadas.map((reparacion) => ({
      patente,
      tipoReparacion: reparacion.numeroReparacion,
      descripcion: reparacion.tipoReparacion,
      fechaReparacion: fechaIngresoTaller,
      horaReparacion: horaIngresoTaller,
      idHistorialReparaciones,
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

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      component="form"
      onSubmit={saveReparacion}
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
          <MenuItem key={reparacion.id} onClick={() => manejarSeleccionarReparacion(index)} className="d-flex justify-content-between">
            <div>
              <span>{reparacion.tipoReparacion}</span>
              <br />
              <span>Precio: {reparacion.monto}</span>
            </div>
            <AddCircleIcon color="success" />
          </MenuItem>
        ))}
      </div>
      <div>
        <h4>Reparaciones Seleccionadas</h4>
        {reparacionesSeleccionadas.map((reparacion, index) => (
          <MenuItem key={reparacion.id} onClick={() => manejarEliminarReparacion(index)} className="d-flex justify-content-between">
            <div>
              <span>{reparacion.tipoReparacion}</span>
              <br />
              <span>Precio: {reparacion.monto}</span>
            </div>
            <RemoveCircleIcon color="error" />
          </MenuItem>
        ))}
      </div>
      <FormControl>
        <br />
        <Button
          variant="contained"
          color="info"
          type="submit" // Asegúrate de que el tipo de botón sea 'submit'
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
