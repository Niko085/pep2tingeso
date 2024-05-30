import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import Box from "@mui/material/Box";
import automovilService from "../services/automovil.service";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import MenuItem from "@mui/material/MenuItem";
import SaveIcon from "@mui/icons-material/Save";
import datosBonosService from "../services/datosBonos.service";

const AddEditBono = () => {
  //Esto es para poder escribir en el formulario
  const [marcaAutomovil, setMarcaAutomovil] = useState("");
  const [cantidadBonos, setCantidadBonos] = useState("");
  const [montoBono, setMontoBono] = useState("");
  const { id } = useParams();
  const [titleBonoForm, setTitleBonoForm] = useState("");
  const navigate = useNavigate();

  const saveBono = (a) => {
    a.preventDefault();

    //Objeto con los datos del bono
    const bono = { marcaAutomovil, cantidadBonos, montoBono, id };
    //Se verifica si el bono existe para actualizar o crear
  if (id) {
    //Actualizar Datos Bono
    datosBonosService
      .update(bono)
      .then((response) => {
        console.log("El bono ha sido actualizado.", response.data);
        navigate("/bonos/list");
      })
      .catch((error) => {
        console.log(
          "Ha ocurrido un error al intentar actualizar datos del bono.",
          error
        );
      });
  } else {
    //Crear nuevo Bono
    datosBonosService
      .create(bono)
      .then((response) => {
        console.log("El bono ha sido añadido.", response.data);
        navigate("/bonos/list");
      })
      .catch((error) => {
        console.log(
          "Ha ocurrido un error al intentar crear un nuevo bono.",
          error
        );
      });
  }
};

  useEffect(() => {
    if (id) {
      setTitleBonoForm("Editar Bono");
      datosBonosService
        .get(id)
        .then((bono) => {
          //Se establecen los valores del auto en el formulario
          setMarcaAutomovil(bono.data.marcaAutomovil);
          setCantidadBonos(bono.data.cantidadBonos);
          setMontoBono(bono.data.montoBono);
        })
        .catch((error) => {
          console.log("Se ha producido un error.", error);
        });
    } else {
      setTitleBonoForm("Nuevo Bono");
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
      <h3>{titleBonoForm}</h3>
      <hr />
      <form>
        <FormControl fullWidth>
          <TextField
            id="marcaAutomovil"
            label="Marca de automoviles"
            value={marcaAutomovil}
            variant="standard"
          />
        </FormControl>
  
        <FormControl fullWidth>
          <TextField
            id="cantidadBonos"
            label="cantidad de bonos"
            value={cantidadBonos}
            variant="standard"
            onChange={(a) => setCantidadBonos(a.target.value)}
            helperText="Ej: 5"
          />
        </FormControl>
  
        <FormControl fullWidth>
          <TextField
            id="montoBono"
            label="Monto del bono"
            value={montoBono}
            variant="standard"
            onChange={(a) => setMontoBono(a.target.value)}
            helperText="Ej: 70000"
          />
        </FormControl>

        <FormControl>
          <br />
          <Button
            variant="contained"
            color="info"
            onClick={(a) => saveBono(a)}
            style={{ marginLeft: "0.5rem" }}
            startIcon={<SaveIcon />}
          >
            Guardar
          </Button>
        </FormControl>
      </form>
      <hr />
      <Link to="/bonos/list">Volver a la lista de bonos</Link>
    </Box>
  );  
};

export default AddEditBono;
