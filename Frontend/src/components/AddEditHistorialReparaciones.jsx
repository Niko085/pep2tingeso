import React, { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import Box from "@mui/material/Box";
import historialReparacionesService from "../services/historialReparaciones.service";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import SaveIcon from "@mui/icons-material/Save";

const AddEditHistorialReparaciones = () => {
  const [patente, setPatente] = useState("");
  const [fechaIngresoTaller, setFechaIngresoTaller] = useState("");
  const [horaIngresoTaller, setHoraIngresoTaller] = useState("");
  const [fechaSalidaTaller, setFechaSalidaTaller] = useState("");
  const [horaSalidaTaller, setHoraSalidaTaller] = useState("");
  const [fechaClienteSeLlevaVehiculo, setFechaClienteSeLlevaVehiculo] = useState("");
  const [horaClienteSeLlevaVehiculo, setHoraClienteSeLlevaVehiculo] = useState("");
  const { id } = useParams();
  const [titleHistorialReparacionesForm, setTitleHistorialReparacionesForm] = useState("");
  const navigate = useNavigate();

  const saveHistorial = (e) => {
    e.preventDefault();

    const historialReparaciones = {
      patente,
      fechaIngresoTaller,
      horaIngresoTaller,
      montoTotalPagar: 0,
      recargos: 0,
      descuentos: 0,
      iva: 0,
      fechaSalidaTaller,
      horaSalidaTaller,
      fechaClienteSeLlevaVehiculo,
      horaClienteSeLlevaVehiculo,
      pagado: false,
      id
    };

    if (id) {
      historialReparacionesService
        .update(historialReparaciones)
        .then((response) => {
          console.log("El Historial de Reparaciones ha sido actualizado.", response.data);
          navigate("/historialreparaciones/list");
        })
        .catch((error) => {
          console.log("Ha ocurrido un error al intentar actualizar datos del historial de reparaciones.", error);
        });
    } else {
      historialReparacionesService
        .create(historialReparaciones)
        .then((response) => {
          console.log("El historial de reparaciones ha sido añadido.", response.data);
          navigate("/historialreparaciones/list");
        })
        .catch((error) => {
          console.log("Ha ocurrido un error al intentar crear un nuevo historial de reparaciones.", error);
        });
    }
  };

  useEffect(() => {
    if (id) {
      setTitleHistorialReparacionesForm("Editar Historial de Reparaciones");
      historialReparacionesService
        .get(id)
        .then((historialReparaciones) => {
          setPatente(historialReparaciones.data.patente);
          setFechaIngresoTaller(historialReparaciones.data.fechaIngresoTaller);
          setHoraIngresoTaller(historialReparaciones.data.horaIngresoTaller);
          setFechaSalidaTaller(historialReparaciones.data.fechaSalidaTaller);
          setHoraSalidaTaller(historialReparaciones.data.horaSalidaTaller);
          setFechaClienteSeLlevaVehiculo(historialReparaciones.data.fechaClienteSeLlevaVehiculo);
          setHoraClienteSeLlevaVehiculo(historialReparaciones.data.horaClienteSeLlevaVehiculo);
        })
        .catch((error) => {
          console.log("Se ha producido un error.", error);
        });
    } else {
      setTitleHistorialReparacionesForm("Nuevo Historial de Reparaciones");
    }
  }, [id]);

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
        maxWidth: "490px",
        margin: "auto",
        marginTop: "30px",
      }}
    >
      <h3>{titleHistorialReparacionesForm}</h3>
      <hr />
      <form>
        <FormControl fullWidth>
          <TextField
            id="patente"
            label="Patente"
            value={patente}
            variant="standard"
            onChange={(e) => setPatente(e.target.value)}
            helperText="Ej: CFTF45"
            InputProps={{
              readOnly: !!id,
            }}
            InputLabelProps={{
              style: { position: 'absolute', top: 0, left: 230 } 
            }}
          />
        </FormControl>

        <FormControl fullWidth>
          <TextField
            id="fechaIngresoTaller"
            label="Fecha de ingreso al taller"
            type="date"
            value={fechaIngresoTaller}
            variant="standard"
            onChange={(e) => setFechaIngresoTaller(e.target.value)}
            InputProps={{
              readOnly: !!id,
            }}
            InputLabelProps={{
              style: { position: 'absolute', top: 0, left: 170 } 
            }}
          />
        </FormControl>

        <FormControl fullWidth>
            <TextField
            id="horaIngresoTaller"
            label="Hora de ingreso al taller"
            type="time"
            value={horaIngresoTaller}
            variant="standard"
            onChange={(e) => setHoraIngresoTaller(e.target.value)}
            InputProps={{
              readOnly: !!id,
            }}
            InputLabelProps={{
              style: { position: 'absolute', top: 0, left: 175 } 
            }}
            />
        </FormControl>

        {id && (
          <>
            <FormControl fullWidth>
              <TextField
                id="fechaSalidaTaller"
                label="Fecha de salida del taller"
                type="date"
                value={fechaSalidaTaller}
                variant="standard"
                onChange={(e) => setFechaSalidaTaller(e.target.value)}
                InputLabelProps={{
                  style: { position: 'absolute', top: 0, left: 170 } 
                }}
              />
            </FormControl>

            <FormControl fullWidth>
              <TextField
                id="horaSalidaTaller"
                label="Hora de salida del taller"
                type="time"
                value={horaSalidaTaller}
                variant="standard"
                onChange={(e) => setHoraSalidaTaller(e.target.value)}
                InputLabelProps={{
                  style: { position: 'absolute', top: 0, left: 175 } 
                }}
              />
            </FormControl>

            <FormControl fullWidth>
              <TextField
                id="fechaClienteSeLlevaVehiculo"
                label="Fecha en que el cliente se lleva el vehículo"
                type="date"
                value={fechaClienteSeLlevaVehiculo}
                variant="standard"
                onChange={(e) => setFechaClienteSeLlevaVehiculo(e.target.value)}
                InputLabelProps={{
                  style: { position: 'absolute', top: 0, left: 101 } 
                }}
              />
            </FormControl>

            <FormControl fullWidth>
              <TextField
                id="horaClienteSeLlevaVehiculo"
                label="Hora en que el cliente se lleva el vehículo"
                type="time"
                value={horaClienteSeLlevaVehiculo}
                variant="standard"
                onChange={(e) => setHoraClienteSeLlevaVehiculo(e.target.value)}
                InputLabelProps={{
                  style: { position: 'absolute', top: 0, left: 102 } 
                }}
              />
            </FormControl>
          </>
        )}

        <FormControl>
          <br />
          <Button
            variant="contained"
            color="info"
            onClick={(e) => saveHistorial(e)}
            style={{ marginLeft: "0.5rem" }}
            startIcon={<SaveIcon />}
          >
            Guardar
          </Button>
        </FormControl>
      </form>
      <hr />
      <Link to="/historialreparaciones/list">Volver a la lista de historial de reparaciones</Link>
    </Box>
  );
};

export default AddEditHistorialReparaciones;
