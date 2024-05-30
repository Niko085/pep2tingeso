import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import Box from "@mui/material/Box";
import automovilService from "../services/automovil.service";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import MenuItem from "@mui/material/MenuItem";
import SaveIcon from "@mui/icons-material/Save";
import reparacionService from "../services/reparacion.service";
import historialReparacionesService from "../services/historialReparaciones.service";

const ReparacionSelectionForm = () => {
  //Esto es para poder escribir en el formulario
  const [patente, setPatente] = useState("");
  const [tipoReparacion, setTipoReparacion] = useState("");
  const [descripcion, setDescripcion] = useState("");
  const {idH, patenteH} = useParams();
  const [id, setId] = useState("");
  const [idHistorialReparaciones, setIdHistorialReparaciones] = useState("");
  //const [idHistorialReparaciones, setIdHistorialReparaciones] = useState("");
  //const [id, setIdHistorialReparaciones] = useState("");
  //const { idHistorialReparaciones } = useParams();
  const [titleReparacionForm, setTitleReparacionForm] = useState("");
  const navigate = useNavigate();

  const saveReparacion = (a) => {
    a.preventDefault();

    //Objeto con los datos del auto
    const reparacion = { patente, tipoReparacion, descripcion, idHistorialReparaciones, id };

    //Crear nueva reparacion
    reparacionService
      .create(reparacion)
      .then((response) => {
        console.log("La reparacion ha sido añadida.", response.data);
        navigate("/reparaciones/list");
      })
      .catch((error) => {
        console.log(
          "Ha ocurrido un error al intentar crear una nueva reparacion.",
          error
        );
      });
};

  useEffect(() => {

      setTitleReparacionForm("Nueva reparacion");
      reparacionService
        .get(idH)
        .then((reparacion) => {
          console.log(reparacion.data);
          //Se establecen los valores del auto en el formulario
          setPatente(patenteH);
          setIdHistorialReparaciones(reparacion.data.id);
 
        })
        .catch((error) => {
          console.log("Se ha producido un error.", error);
        });

  }, []);

  useEffect(() => {
    // Establecer la descripción según el tipo de reparación seleccionado
    if (tipoReparacion === "1") {
      setDescripcion("Reparaciones del Sistema de Frenos");
    } else if (tipoReparacion === "2") {
      setDescripcion("Servicio del Sistema de Refrigeración");
    } else if (tipoReparacion === "3") {
      setDescripcion("Reparaciones del Motor");
    } else if (tipoReparacion === "4") {
      setDescripcion("Reparaciones de la Transmisión");
    } else if (tipoReparacion === "5") {
      setDescripcion("Reparación del Sistema Eléctrico");
    } else if (tipoReparacion === "6") {
      setDescripcion("Reparaciones del Sistema de Escape");
    } else if (tipoReparacion === "7") {
      setDescripcion("Reparación de Neumáticos y Ruedas");
    } else if (tipoReparacion === "8") {
      setDescripcion("Reparaciones de la Suspensión y la Dirección");
    } else if (tipoReparacion === "9") {
      setDescripcion("Reparación del Sistema de Aire Acondicionado y Calefacción");
    } else if (tipoReparacion === "10") {
      setDescripcion("Reparaciones del Sistema de Combustible");
    } else if (tipoReparacion === "11") {
      setDescripcion("Reparación y Reemplazo del Parabrisas y Cristales");
    }
  }, [tipoReparacion]);

  //Estilo del formulario
  return (
    //Recuadro
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
      <form>



      <div style={{ display: "flex",padding: "0.5rem", gap: "2rem" }}>
          <FormControl fullWidth>
            <TextField
              id="tipoReparacion"
              label="Tipo de reparacion"
              value={tipoReparacion}
              select
              variant="standard"
              defaultValue="Sedan"
              onChange={(a) => setTipoReparacion(a.target.value)}
              style={{ flex: 1 }}
            >
              <MenuItem value={"1"}>Reparaciones del Sistema de Frenos</MenuItem>
              <MenuItem value={"2"}>Servicio del Sistema de Refrigeración</MenuItem>
              <MenuItem value={"3"}>Reparaciones del Motor</MenuItem>
              <MenuItem value={"4"}>Reparaciones de la Transmisión</MenuItem>
              <MenuItem value={"5"}>Reparación del Sistema Eléctrico</MenuItem>
              <MenuItem value={"6"}>Reparaciones del Sistema de Escape</MenuItem>
              <MenuItem value={"7"}>Reparación de Neumáticos y Ruedas</MenuItem>
              <MenuItem value={"8"}>Reparaciones de la Suspensión y la Dirección</MenuItem>
              <MenuItem value={"9"}>Reparación del Sistema de Aire Acondicionado y Calefacción</MenuItem>
              <MenuItem value={"10"}>Reparaciones del Sistema de Combustible</MenuItem>
              <MenuItem value={"11"}>Reparación y Reemplazo del Parabrisas y Cristales</MenuItem>
            </TextField>
          </FormControl>
        </div>

        <FormControl>
          <br />
          <Button
            variant="contained"
            color="info"
            onClick={(a) => saveReparacion(a)}
            style={{ marginLeft: "0.5rem" }}
            startIcon={<SaveIcon />}
          >
            Guardar
          </Button>
        </FormControl>
      </form>
      <hr />
      <Link to="/reparaciones/list">Volver a la lista de reparaciones</Link>
    </Box>
  );  
};

export default ReparacionSelectionForm;