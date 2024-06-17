import { useState, useEffect } from "react";
import { Link, useParams, useNavigate } from "react-router-dom";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import FormControl from "@mui/material/FormControl";
import SaveIcon from "@mui/icons-material/Save";
import datosBonosService from "../services/datosBonos.service";

const AddEditBono = () => {
  const [marcaAutomovil, setMarcaAutomovil] = useState("");
  const [cantidadBonos, setCantidadBonos] = useState("");
  const [montoBono, setMontoBono] = useState("");
  const { id } = useParams();
  const [titleBonoForm, setTitleBonoForm] = useState("");
  const navigate = useNavigate();

  const saveBono = (event) => {
    event.preventDefault();

    const bono = { marcaAutomovil, cantidadBonos, montoBono, id };

    if (id) {
      datosBonosService
        .update(bono)
        .then((response) => {
          console.log("El bono ha sido actualizado.", response.data);
          navigate("/bonos/list"); // Navega a la lista después de actualizar
        })
        .catch((error) => {
          console.log("Error al actualizar el bono.", error);
        });
    } else {
      datosBonosService
        .create(bono)
        .then((response) => {
          console.log("El bono ha sido añadido.", response.data);
          navigate("/bonos/list"); // Navega a la lista después de crear
        })
        .catch((error) => {
          console.log("Error al crear el bono.", error);
        });
    }
  };

  useEffect(() => {
    if (id) {
      setTitleBonoForm("Editar Bono");
      datosBonosService
        .get(id)
        .then((bono) => {
          setMarcaAutomovil(bono.data.marcaAutomovil);
          setCantidadBonos(bono.data.cantidadBonos);
          setMontoBono(bono.data.montoBono);
        })
        .catch((error) => {
          console.log("Error al obtener el bono.", error);
        });
    } else {
      setTitleBonoForm("Nuevo Bono");
    }
  }, [id]); // Asegúrate de que useEffect se ejecute cuando id cambie

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
      <h3>{titleBonoForm}</h3>
      <hr />
      <form onSubmit={saveBono} style={{ width: "100%" }}>
        <FormControl fullWidth sx={{ marginBottom: "1rem" }}>
          <TextField
            id="marcaAutomovil"
            label="Marca de automóviles"
            value={marcaAutomovil}
            onChange={(e) => setMarcaAutomovil(e.target.value)}
            variant="outlined"
            helperText="Ej: Honda"
            required
            size="small"
          />
        </FormControl>

        <FormControl fullWidth sx={{ marginBottom: "1rem" }}>
          <TextField
            id="cantidadBonos"
            label="Cantidad de bonos"
            value={cantidadBonos}
            onChange={(e) => setCantidadBonos(e.target.value)}
            variant="outlined"
            required
            size="small"
            type="number"
            inputProps={{ min: "0" }}
            helperText="Ej: 5"
          />
        </FormControl>

        <FormControl fullWidth sx={{ marginBottom: "1rem" }}>
          <TextField
            id="montoBono"
            label="Monto del bono"
            value={montoBono}
            onChange={(e) => setMontoBono(e.target.value)}
            variant="outlined"
            required
            size="small"
            type="number"
            inputProps={{ min: "0" }}
            helperText="Ej: 70000"
          />
        </FormControl>

        <FormControl fullWidth sx={{ marginBottom: "1rem" }}>
          <Button
            type="submit"
            variant="contained"
            color="primary"
            onClick={(e) => saveBono(e)}
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
