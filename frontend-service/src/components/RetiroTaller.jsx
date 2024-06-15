import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '@mui/material/Button';
import DriveEtaIcon from '@mui/icons-material/DriveEta';
import historialReparacionesService from "../services/historialReparaciones.service";
import TextField from '@mui/material/TextField';
import Box from '@mui/material/Box';

const RetiroTaller = () => {
    const [patente, setPatente] = useState('');
    const navigate = useNavigate();

    const handleSearch = async () => {
        try {
            console.log("Buscando historial de reparaciones para la patente:", patente);
            const response = await historialReparacionesService.getNoPagadoByPatente(patente);
            console.log("Respuesta de la solicitud:", response);
            if (response.status === 200) {
                const data = response.data;
                console.log("Datos obtenidos:", data);
                // Redirigir a la ruta de edición con el id del primer historial obtenido
                navigate(`/historialreparaciones/edit/${data.id}`);
            } else {
                const confirmMessage = "No se encontró registro de visita al taller para la patente ingresada.";
                window.confirm(confirmMessage); // Muestra un cuadro de confirmación con el mensaje
                console.error('Error al buscar historial de reparaciones:', response.statusText);
            }
        } catch (error) {
            const errorMessage = "No se encontró registro de visita al taller para la patente ingresada o no se ha ingresado una patente válida";
            window.confirm(errorMessage); // Muestra un cuadro de confirmación con el mensaje
            console.error('Error al buscar historial de reparaciones:', error);
        }
    };

    const handleKeyDown = (event) => {
        if (event.key === 'Enter') {
            handleSearch();
        }
    };

    return (
        <div className="container">
            <h1>Retiro del taller</h1>
            <Box display="flex" justifyContent="center" alignItems="center" flexDirection="column" mt={4}>
                <TextField 
                    label="Ingrese la patente" 
                    variant="outlined" 
                    value={patente} 
                    onChange={(e) => setPatente(e.target.value)} 
                    onKeyDown={handleKeyDown} // Llamar a handleKeyDown cuando se presione una tecla
                    sx={{ marginBottom: 2, minWidth: 300 }}
                />
                <Button 
                    variant="contained" 
                    color="primary" 
                    onClick={handleSearch}
                    startIcon={<DriveEtaIcon />}
                    sx={{ padding: '10px 20px', fontSize: '16px' }}
                >
                    Buscar
                </Button>
            </Box>
        </div>
    );
};

export default RetiroTaller;
