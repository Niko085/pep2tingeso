import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import automovilService from "../services/automovil.service";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import MenuItem from "@mui/material/MenuItem";
import SaveIcon from "@mui/icons-material/Save";
import Alert from "@mui/material/Alert";
import Select from "@mui/material/Select";
import InputLabel from "@mui/material/InputLabel";

const AddEditAutomovil = () => {
  // Estado del formulario
  const [patente, setPatente] = useState("");
  const [marca, setMarca] = useState("");
  const [modelo, setModelo] = useState("");
  const [tipo, setTipo] = useState("");
  const [anioFabricacion, setAnioFabricacion] = useState("");
  const [motor, setMotor] = useState("");
  const [cantAsientos, setCantAsientos] = useState("");
  const [kilometraje, setKilometraje] = useState("");
  const { id } = useParams();
  const [titleAutomovilForm, setTitleAutomovilForm] = useState("");
  const [errorMessage, setErrorMessage] = useState(""); // Estado para el mensaje de error
  const navigate = useNavigate();

  const saveAutomovil = (a) => {
    a.preventDefault();

    // Validar que todos los campos estén llenos
    if (
      !patente ||
      !marca ||
      !modelo ||
      !tipo ||
      !anioFabricacion ||
      !motor ||
      !cantAsientos ||
      !kilometraje
    ) {
      setErrorMessage("Por favor, rellena todos los campos.");
      return;
    }

    // Objeto con los datos del auto
    const automovil = { patente, marca, modelo, tipo, anioFabricacion, motor, cantAsientos, kilometraje, id };

    // Se verifica si el auto existe para actualizar o crear
    if (id) {
      // Actualizar Datos Automovil
      automovilService
        .update(automovil)
        .then((response) => {
          console.log("El Automovil ha sido actualizado.", response.data);
          navigate("/automovil/list");
        })
        .catch((error) => {
          console.log(
            "Ha ocurrido un error al intentar actualizar datos del automovil.",
            error
          );
        });
    } else {
      // Crear nuevo Automovil
      automovilService
        .create(automovil)
        .then((response) => {
          console.log("El automovil ha sido añadido.", response.data);
          navigate(`/historialreparaciones/add?patente=${patente}`);
        })
        .catch((error) => {
          console.log(
            "Ha ocurrido un error al intentar crear un nuevo automovil.",
            error
          );
        });
    }
  };

  // Obtener el año actual
  const year = new Date().getFullYear();

  useEffect(() => {
    if (id) {
      setTitleAutomovilForm("Editar Automovil");
      automovilService
        .get(id)
        .then((automovil) => {
          // Se establecen los valores del auto en el formulario
          setPatente(automovil.data.patente);
          setMarca(automovil.data.marca);
          setModelo(automovil.data.modelo);
          setTipo(automovil.data.tipo);
          setAnioFabricacion(automovil.data.anioFabricacion);
          setMotor(automovil.data.motor);
          setCantAsientos(automovil.data.cantAsientos);
          setKilometraje(automovil.data.kilometraje);
        })
        .catch((error) => {
          console.log("Se ha producido un error.", error);
        });
    } else {
      setTitleAutomovilForm("Nuevo Automovil");
    }
  }, [id]);

  // Generar lista de años desde 1995 hasta el año actual
  const years = Array.from({ length: year - 1995 + 1 }, (_, i) => 1995 + i);

  // Estilo del formulario
  return (
    // Recuadro
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
      onSubmit={saveAutomovil}
    >
      <h3>{titleAutomovilForm}</h3>
      <hr />
      {errorMessage && (
        <Alert severity="error" sx={{ marginBottom: "20px" }}>
          {errorMessage}
        </Alert>
      )}
      <Grid container spacing={2}>
        <Grid item xs={12} sm={6}>
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
        </Grid>
        <Grid item xs={12} sm={6}>
          <FormControl fullWidth>
            <TextField
              id="modelo"
              label="Modelo"
              value={modelo}
              variant="standard"
              onChange={(a) => setModelo(a.target.value)}
              helperText="Ej: Getz"
            />
          </FormControl>
        </Grid>
        <Grid item xs={12} sm={6}>
          <FormControl fullWidth>
            <TextField
              id="marca"
              label="Marca de automóvil"
              value={marca}
              variant="standard"
              onChange={(a) => setMarca(a.target.value)}
              helperText="Ej: Hyundai"
            />
          </FormControl>
        </Grid>
        <Grid item xs={12} sm={6}>
          <FormControl fullWidth>
            <TextField
              id="kilometraje"
              label="Kilometraje"
              type="number"
              value={kilometraje}
              variant="standard"
              inputProps={{ min: "1" }}
              onChange={(a) => setKilometraje(a.target.value)}
              helperText="Ej: 120000"
            />
          </FormControl>
        </Grid>
        <Grid item xs={12} sm={6}>
          <FormControl fullWidth>
            <InputLabel id="tipo-label">Tipo de automóvil</InputLabel>
            <Select
              labelId="tipo-label"
              id="tipo"
              value={tipo}
              label="Tipo de automóvil"
              onChange={(a) => setTipo(a.target.value)}
            >
              <MenuItem value={"Sedan"}>Sedán</MenuItem>
              <MenuItem value={"Hatchback"}>Hatchback</MenuItem>
              <MenuItem value={"Suv"}>SUV</MenuItem>
              <MenuItem value={"Pickup"}>Pickup</MenuItem>
              <MenuItem value={"Furgoneta"}>Furgoneta</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12} sm={6}>
          <FormControl fullWidth>
            <InputLabel id="motor-label">Tipo de motor</InputLabel>
            <Select
              labelId="motor-label"
              id="motor"
              value={motor}
              label="Tipo de motor"
              onChange={(a) => setMotor(a.target.value)}
            >
              <MenuItem value={"Gasolina"}>Gasolina</MenuItem>
              <MenuItem value={"Diesel"}>Diésel</MenuItem>
              <MenuItem value={"Hibrido"}>Híbrido</MenuItem>
              <MenuItem value={"Electrico"}>Eléctrico</MenuItem>
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12} sm={6}>
          <FormControl fullWidth>
            <InputLabel id="anioFabricacion-label">Año de fabricación</InputLabel>
            <Select
              labelId="anioFabricacion-label"
              id="anioFabricacion"
              value={anioFabricacion}
              label="Año de fabricación"
              onChange={(a) => setAnioFabricacion(a.target.value)}
            >
              {years.map((year) => (
                <MenuItem key={year} value={year}>
                  {year}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12} sm={6}>
          <FormControl fullWidth>
            <InputLabel id="cantAsientos-label">Cantidad de asientos</InputLabel>
            <Select
              labelId="cantAsientos-label"
              id="cantAsientos"
              value={cantAsientos}
              label="Cantidad de asientos"
              onChange={(a) => setCantAsientos(a.target.value)}
            >
              {[...Array(10)].map((_, index) => (
                <MenuItem key={index + 2} value={index + 2}>
                  {index + 2}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>
        <Grid item xs={12}>
          <Button
            type="submit"
            variant="contained"
            color="info"
            startIcon={<SaveIcon />}
            fullWidth
          >
            Guardar
          </Button>
        </Grid>
      </Grid>
      <hr />
      <Link to="/ingresoTaller">Retroceder</Link>
    </Box>
  );
};

export default AddEditAutomovil;
