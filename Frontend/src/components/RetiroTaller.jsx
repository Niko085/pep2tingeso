import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Button from '@mui/material/Button';
import DriveEtaIcon from '@mui/icons-material/DriveEta';
import historialReparacionesService from "../services/historialReparaciones.service";
import TextField from '@mui/material/TextField';

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
                console.error('Error al buscar historial de reparaciones:', response.statusText);
            }
        } catch (error) {
            console.error('Error al buscar historial de reparaciones:', error);
        }
    };

    return (
        <div className="container">
            <h1>Retiro del taller</h1>
            <div>
                <TextField 
                    label="Ingrese la patente" 
                    variant="outlined" 
                    value={patente} 
                    onChange={(e) => setPatente(e.target.value)} 
                />
                <Button 
                    variant="contained" 
                    color="primary" 
                    onClick={handleSearch}
                    startIcon={<DriveEtaIcon />}
                >
                    Buscar historial
                </Button>
            </div>
        </div>
    );
};

export default RetiroTaller;
