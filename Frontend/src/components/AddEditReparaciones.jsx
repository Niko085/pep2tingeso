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

const AddEditReparacion = () => {
  //Esto es para poder escribir en el formulario
  const [patente, setPatente] = useState("");
  const [tipoReparacion, setTipoReparacion] = useState("");
  const [descripcion, setDescripcion] = useState("");
  const [idHistorialReparaciones, setIdHistorialReparaciones] = useState("");
  const { id } = useParams();
  const [titleReparacionForm, setTitleReparacionForm] = useState("");
  const navigate = useNavigate();

  const saveReparacion = (a) => {
    a.preventDefault();

    //Objeto con los datos del auto
    const reparacion = { patente, tipoReparacion, descripcion, idHistorialReparaciones, id };
    //Se verifica si el auto existe para actualizar o crear
    if (id) {
      //Actualizar Datos Automovil
      reparacionService
        .update(reparacion)
        .then((response) => {
          console.log("La reparacion ha sido actualizada.", response.data);
          navigate("/reparaciones/list");
        })
        .catch((error) => {
          console.log(
            "Ha ocurrido un error al intentar actualizar datos de la reparacion.",
            error
          );
        });
    } else {
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
    }
  };

  useEffect(() => {
    if (id) {
      setTitleReparacionForm("Editar reparacion");
      reparacionService
        .get(id)
        .then((reparacion) => {
          //Se establecen los valores del auto en el formulario
          setPatente(reparacion.data.patente);
          setTipoReparacion(reparacion.data.tipoReparacion);
          setDescripcion(reparacion.data.descripcion);
          setIdHistorialReparaciones(reparacion.data.idHistorialReparaciones);
 
        })
        .catch((error) => {
          console.log("Se ha producido un error.", error);
        });
    } else {
      setTitleReparacionForm("Nueva reparacion");
    }
  }, []);

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
        maxWidth: "650px",
        margin: "auto",
        marginTop: "30px",
      }}
    >
      <h3>{titleReparacionForm}</h3>
      <hr />
      <form>
        <FormControl fullWidth>
          <TextField
            id="patente"
            label="Patente"
            value={patente}
            variant="standard"
            onChange={(a) => setPatente(a.target.value)}
            helperText="Ej: CFTF45"
          />
        </FormControl>
  
        <FormControl fullWidth>
          <TextField
            id="tipoReparacion"
            label="Tipo de reparacion"
            value={tipoReparacion}
            variant="standard"
            onChange={(a) => setTipoReparacion(a.target.value)}
            helperText="Ej: 1"
          />
        </FormControl>
  
        <FormControl fullWidth>
          <TextField
            id="descripcion"
            label="Reparacion"
            value={descripcion}
            variant="standard"
            onChange={(a) => setDescripcion(a.target.value)}
            helperText="Ej: Getz"
          />
        </FormControl>
  
        <FormControl fullWidth>
          <TextField
            id="idHistorialReparaciones"
            label="id del historial de reparaciones"
            type="number"
            value={idHistorialReparaciones}
            variant="standard"
            inputProps={{ min: "0" }} // Establece el valor mínimo permitido como 0
            onChange={(a) => setIdHistorialReparaciones(a.target.value)}
          />
        </FormControl>

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

export default AddEditReparacion;
